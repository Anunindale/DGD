/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.app.components.inventory.register.forms.normal;

import emc.app.components.documents.EMCDoubleFormDocument;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJSplitPane;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.inventory.register.forms.superclass.RegisterDRM;
import emc.app.components.inventory.register.forms.superclass.RegisterLRM;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.factory.EMCDimensionLookupFactory;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.base.BaseUnitsOfMeasure;
import emc.entity.inventory.register.InventoryRegister;
import emc.enums.inventory.register.RegisterFormTypeEnum;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.menus.base.menuItems.display.UnitsOfMeasure;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JSplitPane;

/**
 *
 * @author wikus
 */
public class RegisterForm extends BaseInternalFrame {

    private RegisterDRM dataRelation;
    private EMCUserData userData;
    private List keys;
    // <editor-fold defaultstate="collapsed" desc="Title Text Fields">
    private emcJTextField txtItem = new emcJTextField();
    private emcJTextField txtDesc = new emcJTextField();
    private emcJTextField txtDimension1 = new emcJTextField();
    private emcJTextField txtDimension2 = new emcJTextField();
    private emcJTextField txtDimension3 = new emcJTextField();
    private emcJTextField txtTotalQty = new emcJTextField();
    private emcJTextField txtRegisteredQty = new emcJTextField();
    // </editor-fold>

    public RegisterForm(EMCUserData userData) {
        super("Register Items", true, true, true, true, userData);
        this.setBounds(20, 20, 820, 600);
        try {
            this.keys = (List) userData.getUserData(12);
            userData.getUserData().remove(12);
            this.userData = userData.copyUserDataAndDataList();
            dataRelation = new RegisterDRM(
                    new emcGenericDataSourceUpdate(enumEMCModules.INVENTORY.getId(), new InventoryRegister(), userData), true, userData) {

                @Override
                public void setUserData(EMCUserData userData) {
                    super.setUserData(userData);
                    RegisterForm.this.userData = userData;

                    RegisterForm.this.setTitleValues();
                }
            };
            this.setDataManager(dataRelation);
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("masterId");
            dataRelation.setFormTextId2("transId");
        } catch (Exception ex) {
        }
        setTitleValues();
        initFrame();
    }

    private void setTitleValues() {
        Object item = userData.getUserData(12);
        Object desc = userData.getUserData(5);
        Object dim1 = userData.getUserData(6);
        Object dim2 = userData.getUserData(7);
        Object dim3 = userData.getUserData(8);
        Object qty = userData.getUserData(9);
        txtItem.setText(Functions.checkBlank(item) ? "" : item.toString());
        txtDesc.setText(Functions.checkBlank(desc) ? "" : desc.toString());
        txtDimension1.setText(Functions.checkBlank(dim1) ? "" : dim1.toString());
        txtDimension2.setText(Functions.checkBlank(dim2) ? "" : dim2.toString());
        txtDimension3.setText(Functions.checkBlank(dim3) ? "" : dim3.toString());
        txtTotalQty.setText(Functions.checkBlank(qty) ? "" : qty.toString());
        dataRelation.setMasterId(userData.getUserData(2).toString());
        dataRelation.setTransId(userData.getUserData(3).toString());
        dataRelation.setWarehouse(userData.getUserData(10) == null ? "" : userData.getUserData(10).toString());
        dataRelation.setType(userData.getUserData(11).toString());
        dataRelation.setFormType(RegisterFormTypeEnum.FIRST_TIME.toString());
        dataRelation.setFieldValueAt(dataRelation.getLastRowAccessed(), "totalQty", Double.valueOf(Functions.checkBlank(qty) ? 0 : Double.valueOf(qty.toString())));
    }

    private void initFrame() {
        emcJPanel thePanel = new emcJPanel(new BorderLayout());
        emcJSplitPane topBottomSplit = new emcJSplitPane(JSplitPane.VERTICAL_SPLIT, titlePane(), tablePane());
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Register", topBottomSplit);
        topBottomSplit.setDividerLocation(220);
        thePanel.add(tabbed);
        this.add(thePanel);
    }

    private emcJPanel titlePane() {
        txtItem.setEditable(false);
        txtDesc.setEditable(false);
        txtDimension1.setEditable(false);
        txtDimension2.setEditable(false);
        txtDimension3.setEditable(false);
        txtTotalQty.setEditable(false);
        txtTotalQty.setDocument(new EMCDoubleFormDocument(dataRelation, "totalQty"));
        txtRegisteredQty.setEditable(false);
        txtRegisteredQty.setDocument(new EMCDoubleFormDocument(dataRelation, "registeredQty"));
        Component[][] comps = {{new emcJLabel("Item Id"), txtItem, new emcJLabel("Description"), txtDesc},
            {new emcJLabel("Config"), txtDimension1, new emcJLabel("Colour"), txtDimension3},
            {new emcJLabel("Size"), txtDimension2},
            {new emcJLabel("Total Qty"), txtTotalQty, new emcJLabel("Registered Qty"), txtRegisteredQty}};
        return emcSetGridBagConstraints.createSimplePanel(comps, GridBagConstraints.NONE, true, "Item");
    }

    private emcJPanel tablePane() {
        keys.add("quantity");
        emcTableModelUpdate model = new emcTableModelUpdate(dataRelation, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        setupLookups(table);
        dataRelation.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        this.setTablePanel(tableScroll);
        emcJPanel thePanel = new emcJPanel(new GridLayout());
        thePanel.add(tableScroll);
        return thePanel;
    }

    private void setupLookups(emcJTableUpdate table) {
        EMCLookupJTableComponent lkpWarehouse = EMCDimensionLookupFactory.createWarehouseJTableLookup(userData);
        EMCLookupJTableComponent lkpLocation = EMCDimensionLookupFactory.createLocationJTableLookup(userData);
        EMCLookupJTableComponent lkpPallet = EMCDimensionLookupFactory.createPalletJTableLookup(userData);
        EMCLookupJTableComponent lkpUOM = new EMCLookupJTableComponent(new UnitsOfMeasure());
        EMCLookupPopup uomPop = new EMCLookupPopup(new BaseUnitsOfMeasure(), "unit", userData);
        lkpUOM.setPopup(uomPop);
        if (keys.contains("warehouse")) {
            table.setLookupToColumn("warehouse", lkpWarehouse);
        }
        if (keys.contains("location")) {
            table.setLookupToColumn("location", lkpLocation);
        }
        if (keys.contains("pallet")) {
            table.setLookupToColumn("pallet", lkpPallet);
        }
        if (keys.contains("uom")) {
            table.setLookupToColumn("uom", lkpUOM);
        }
        RegisterLRM lrm = new RegisterLRM(userData);
        lrm.addLookup(lkpWarehouse, "warehouse");
        lrm.addLookup(lkpLocation, "location");
        lrm.addLookup(lkpPallet, "pallet");
        lrm.initializeLookups();
    }

    @Override
    public void populateUserDataForPermissions(EMCUserData userData) {
        for (int i = 0; i < 12; i++) {
            userData.setUserData(i, "");
        }

        userData.setUserData(12, new ArrayList());
    }
}
