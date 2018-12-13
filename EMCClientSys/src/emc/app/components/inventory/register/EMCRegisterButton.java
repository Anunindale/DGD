/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.app.components.inventory.register;

import emc.app.components.emcJButton;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.wsmanager.EMCWSManager;
import emc.commands.EMCCommands;
import emc.entity.debtors.creditnoteregister.DebtorsCreditNoteRegister;
import emc.entity.inventory.register.InventoryRegister;
import emc.entity.inventory.register.InventoryRemoveRegister;
import emc.entity.inventory.register.InventoryStocktakeRegister;
import emc.entity.sop.SOPSalesOrderPostRegister;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.register.RegisterFormTypeEnum;
import emc.enums.inventory.register.RegisterFromTypeEnum;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCMenuItem;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.menus.debtors.menuitems.display.DebtorsCreditNoteRegisterMI;
import emc.menus.inventory.menuitems.display.InventoryRegisterMenu;
import emc.menus.inventory.menuitems.display.InventoryRemoveRegisterMenu;
import emc.menus.inventory.menuitems.display.InventoryStockTakeRegisterMenu;
import emc.methods.inventory.ServerInventoryMethods;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wikus
 */
public class EMCRegisterButton extends emcJButton {

    private emcDataRelationManagerUpdate dataRelation;
    private EMCMenuItem menuItem;
    private String itemField;
    private String descField;
    private String dim1Field;
    private String dim2Field;
    private String dim3Field;
    private String quantityField;
    private String masterField;
    private String transField;
    private String warehouseField;
    private EMCQuery query;
    private String type;
    private String primaryReferenceField;
    private RegisterFormTypeEnum registerFormType;

    //This field enables us to use either the 
    public EMCRegisterButton(RegisterFormTypeEnum registerFormType, RegisterFromTypeEnum fromType, emcDataRelationManagerUpdate dataRelation,
            String masterField, String transField, String itemField, String descField,
            String dim1Field, String dim2Field, String dim3Field, String warehouseField, String quantityField, String primaryReferenceField) {
        super("Register");
        this.dataRelation = dataRelation;
        this.itemField = itemField;
        this.descField = descField;
        this.dim1Field = dim1Field;
        this.dim2Field = dim2Field;
        this.dim3Field = dim3Field;
        this.quantityField = quantityField;
        this.masterField = masterField;
        this.transField = transField;
        this.warehouseField = warehouseField;
        this.primaryReferenceField = primaryReferenceField;
        this.type = fromType.toString();
        this.registerFormType = registerFormType;
        if (registerFormType.equals(RegisterFormTypeEnum.FIRST_TIME)) {
            menuItem = new InventoryRegisterMenu();
            query = new EMCQuery(enumQueryTypes.SELECT, InventoryRegister.class.getName());
        } else if (registerFormType.equals(RegisterFormTypeEnum.MUST_EXIST)) {
            menuItem = new InventoryRemoveRegisterMenu();
            query = new EMCQuery(enumQueryTypes.SELECT, InventoryRemoveRegister.class.getName());
        } else if (registerFormType.equals(RegisterFormTypeEnum.STOCK_TAKE)) {
            menuItem = new InventoryStockTakeRegisterMenu();
            query = new EMCQuery(enumQueryTypes.SELECT, InventoryStocktakeRegister.class.getName());
        } else if (registerFormType.equals(RegisterFormTypeEnum.RETURN)) {
            menuItem = new DebtorsCreditNoteRegisterMI();
            menuItem.setDoNotOpenForm(false);
            query = new EMCQuery(enumQueryTypes.SELECT, DebtorsCreditNoteRegister.class);
        }
    }

    @Override
    public void doActionPerformed(ActionEvent evt) {
        super.doActionPerformed(evt);
        if (dataRelation.getLastUpdateStatus()) {
            EMCCommandClass cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.INVENTORY.getId(),
                    registerFormType.equals(RegisterFormTypeEnum.STOCK_TAKE) ? ServerInventoryMethods.GET_ST_REGISTER_COLUMNS.toString() : ServerInventoryMethods.GET_REGISTER_COLUMNS.toString());

            if (Functions.checkBlank(dataRelation.getFieldValueAt(dataRelation.getLastRowAccessed(), itemField))) {
                Logger.getLogger("emc").log(Level.SEVERE, "No item selected!", dataRelation.getUserData());
                return;
            }

            List toSend = new ArrayList();
            toSend.add(dataRelation.getFieldValueAt(dataRelation.getLastRowAccessed(), itemField));
            toSend = EMCWSManager.executeGenericWS(cmd, toSend, dataRelation.getUserData());
            if (toSend.size() > 1) {
                EMCUserData userData = dataRelation.getUserData().copyUserDataAndDataList();
                List userDataList = new ArrayList();
                query.removeAnd("masterId");
                query.addAnd("masterId", dataRelation.getFieldValueAt(dataRelation.getLastRowAccessed(), masterField));
                query.removeAnd("transId");
                query.addAnd("transId", dataRelation.getFieldValueAt(dataRelation.getLastRowAccessed(), transField));
                userDataList.add(0, query);
                userDataList.add(1, "");
                userDataList.add(2, dataRelation.getFieldValueAt(dataRelation.getLastRowAccessed(), masterField));
                userDataList.add(3, dataRelation.getFieldValueAt(dataRelation.getLastRowAccessed(), transField));
                userDataList.add(4, dataRelation.getFieldValueAt(dataRelation.getLastRowAccessed(), itemField));
                userDataList.add(5, dataRelation.getFieldValueAt(dataRelation.getLastRowAccessed(), descField));
                userDataList.add(6, dataRelation.getFieldValueAt(dataRelation.getLastRowAccessed(), dim1Field));
                userDataList.add(7, dataRelation.getFieldValueAt(dataRelation.getLastRowAccessed(), dim2Field));
                userDataList.add(8, dataRelation.getFieldValueAt(dataRelation.getLastRowAccessed(), dim3Field));
                userDataList.add(9, dataRelation.getFieldValueAt(dataRelation.getLastRowAccessed(), registerFormType.equals(RegisterFormTypeEnum.STOCK_TAKE) ? "countQOH" : quantityField));
                userDataList.add(10, dataRelation.getFieldValueAt(dataRelation.getLastRowAccessed(), warehouseField));
                userDataList.add(11, type);
                userDataList.add(12, dataRelation.getFieldValueAt(dataRelation.getLastRowAccessed(), primaryReferenceField));
                toSend.remove(0);
                userDataList.add(12, toSend);

                //Hack for Credit Note register.  We need the data relation manager to refresh Credit Note lines.
                if (registerFormType.equals(RegisterFormTypeEnum.RETURN)) {
                    userDataList.add(13, dataRelation);
                }

                userData.setUserData(userDataList);
                dataRelation.getTheForm().getDeskTop().createAndAdd(menuItem, -1, -1, userData, dataRelation, -1000);
            } else {
                Logger.getLogger("emc").log(Level.INFO, "The selected item does not require registration", dataRelation.getUserData());
            }
        }
    }

    protected void setTypeAccordingToQuantity(boolean stockTake) {
        double qty = (Double) dataRelation.getFieldValueAt(dataRelation.getLastRowAccessed(), quantityField);
        if (stockTake) {
            menuItem = new InventoryStockTakeRegisterMenu();
            query = new EMCQuery(enumQueryTypes.SELECT, InventoryStocktakeRegister.class.getName());
            this.registerFormType = RegisterFormTypeEnum.STOCK_TAKE;
        } else {
            if (qty >= 0) {
                menuItem = new InventoryRegisterMenu();
                query = new EMCQuery(enumQueryTypes.SELECT, InventoryRegister.class.getName());
                this.registerFormType = RegisterFormTypeEnum.FIRST_TIME;
            } else {
                menuItem = new InventoryRemoveRegisterMenu();
                query = new EMCQuery(enumQueryTypes.SELECT, InventoryRemoveRegister.class.getName());
                this.registerFormType = RegisterFormTypeEnum.MUST_EXIST;
            }
        }
    }
}
