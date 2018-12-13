/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.pop.display.planning;

import emc.app.components.emcJButton;
import emc.app.components.emcJDialog;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcpicker.emcdatepicker.EMCDatePicker;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.lookup.EMCLookup;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.util.utilFunctions;
import emc.app.wsmanager.EMCWSManager;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.InventoryWarehouse;
import emc.entity.pop.POPSuppliers;
import emc.entity.pop.planning.POPPlannedPurchaseOrders;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.framework.EMCCommandClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.menus.inventory.menuitems.display.ItemMaster;
import emc.menus.inventory.menuitems.display.Warehouse;
import emc.menus.pop.menuitems.display.POPPlannedPurchaseOrderReleaseMenu;
import emc.menus.pop.menuitems.display.Suppliers;
import emc.methods.pop.ServerPOPMethods;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wikus
 */
public class POPPlannedPurchaseOrderReleaseDialog extends emcJDialog {

    private emcDataRelationManagerUpdate dataManager;
    private List<String> itemFields;
    private EMCUserData userData;
    private EMCDatePicker dpFromDate;
    private EMCDatePicker dpToDate;
    private EMCDatePicker dpRequiredDate;
    private EMCLookup lkpSelectItem;
    private EMCLookup lkpSelectSupplier;
//    private emcJTextField txtItem;
    private EMCLookup lkpItemField1;
    private EMCLookup lkpItemField2;
    private EMCLookup lkpSupplier;
    private EMCLookup lkpWarehouse;
    private String itemId;

    public POPPlannedPurchaseOrderReleaseDialog(emcDataRelationManagerUpdate dataManager, EMCUserData userData) {
        super(null, "Release Planned Purchase Orders", true);
        this.userData = userData.copyUserData();
        this.dataManager = dataManager;
        EMCCommandClass cmd = new EMCCommandClass(ServerPOPMethods.GET_PLANNED_RELEASE_ITEM_FIELD);
        itemFields = (List<String>) EMCWSManager.executeGenericWS(cmd, new ArrayList<Object>(), userData).get(1);
        if (itemFields.isEmpty()) {
            Logger.getLogger("emc").log(Level.WARNING, "Release item fields are not set up in POP Parameters.", userData);
        }

        this.itemId = (String) dataManager.getLastFieldValueAt("itemId");

        this.initFrame(userData);
        this.pack();
        this.setVisible(true);
    }

    private void initFrame(EMCUserData userData) {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Release", releasePane(userData));
        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(tabbed, BorderLayout.CENTER);
        contentPane.add(buttonPane(), BorderLayout.EAST);
        this.setContentPane(contentPane);
    }

    private emcJPanel releasePane(EMCUserData userData) {
        dpFromDate = new EMCDatePicker();
        dpToDate = new EMCDatePicker();
        dpRequiredDate = new EMCDatePicker();
        lkpSelectItem = new EMCLookup(new ItemMaster());
        lkpSelectItem.setPopup(new EMCLookupPopup(new InventoryItemMaster(), "itemReference", userData));
        lkpSelectSupplier = new EMCLookup(new Suppliers());
        lkpSelectSupplier.setPopup(new EMCLookupPopup(new POPSuppliers(), "supplierId", userData));




        lkpItemField1 = new EMCLookup(new ItemMaster());
        if (itemFields.size() > 0) {
            List<String> popupFields = new ArrayList<String>();
            popupFields.add(itemFields.get(0));
            EMCLookupPopup popup = new EMCLookupPopup(new InventoryItemMaster(), itemFields.get(0), popupFields, userData);
            lkpItemField1.setPopup(popup);
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemMaster.class);
            query.addGroupBy(itemFields.get(0));
            lkpItemField1.setTheQuery(query);
        } else {
            lkpItemField1.setEnabled(false);
        }
        lkpItemField2 = new EMCLookup(new ItemMaster());
        if (itemFields.size() > 1) {
            List<String> popupFields = new ArrayList<String>();
            popupFields.add(itemFields.get(1));
            EMCLookupPopup popup = new EMCLookupPopup(new InventoryItemMaster(), itemFields.get(1), popupFields, userData);
            lkpItemField2.setPopup(popup);
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemMaster.class);
            query.addGroupBy(itemFields.get(1));
            lkpItemField2.setTheQuery(query);
        } else {
            lkpItemField2.setEnabled(false);
        }
        lkpWarehouse = new EMCLookup(new Warehouse());
        lkpWarehouse.setPopup(new EMCLookupPopup(new InventoryWarehouse(), "warehouseId", userData));
        lkpSupplier = new EMCLookup(new Suppliers());
        lkpSupplier.setPopup(new EMCLookupPopup(new POPSuppliers(), "supplierId", userData));


        EMCCommandClass cmd = new EMCCommandClass(ServerPOPMethods.FIND_DEFAULT_ITEM_VALUES);
        List toSend = new ArrayList();
        toSend.add(itemId);
        toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);
        if (toSend.size() > 1 && toSend.get(1) instanceof Object[]) {
            Object[] itemInfo = (Object[]) toSend.get(1);

            if (!Functions.checkBlank(dataManager.getLastFieldValueAt("warehouse"))) {
                lkpWarehouse.setValue(dataManager.getLastFieldValueAt("warehouse"));
            } else {
                lkpWarehouse.setValue(itemInfo[0]);
            }
            
            lkpSupplier.setValue(itemInfo[1]);

            lkpSelectItem.setValue(itemInfo[2]);
            lkpSelectSupplier.setValue(itemInfo[1]);
        }

        Component[][] comp = {
            {new emcJLabel("From Date"), dpFromDate},
            {new emcJLabel("To Date"), dpToDate},
            {new emcJLabel("Item"), lkpSelectItem},
            {new emcJLabel("Supplier"), lkpSelectSupplier},
            {new emcJLabel(buildLabel(0, userData)), lkpItemField1},
            {new emcJLabel(buildLabel(1, userData)), lkpItemField2},
            {new emcJLabel()},
            {new emcJLabel("PO Required Date"), dpRequiredDate},
            {new emcJLabel("PO Supplier"), lkpSupplier},
            {new emcJLabel("PO Warehouse"), lkpWarehouse}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true);
    }

    private String buildLabel(int pos, EMCUserData userData) {
        if (itemFields.size() > pos) {
            String field = itemFields.get(pos);
            //Fields always come from Item Master
            String label = new InventoryItemMaster().getDisplayLabelForField(field, userData);

            //Assume that no EMC label is specified
            if (field.equals(label)) {
                label = utilFunctions.convertName(label);
            }
            return label;
        } else {
            return "Not Set";
        }
    }

    private emcJPanel buttonPane() {
        emcJButton btnOk = new emcJButton("OK") {
            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                Date required = dpRequiredDate.getDate();
                String supplier = (String) lkpSupplier.getValue();
                String warehouse = (String) lkpWarehouse.getValue();
                if (Functions.checkBlank(required)) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Please specify the required date.", userData);
                } else if (Functions.checkBlank(supplier)) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Please specify the supplier.", userData);
                } else if (Functions.checkBlank(warehouse)) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Please specify the warehouse.", userData);
                } else {
                    Date from = dpFromDate.getDate();
                    Date to = dpToDate.getDate();
                    String itemField1 = (String) lkpItemField1.getValue();
                    String itemField2 = (String) lkpItemField2.getValue();
                    boolean addedItemMaster = false;
                    EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPPlannedPurchaseOrders.class);
                    if (!Functions.checkBlank(from)) {
                        query.addAnd("releaseDate", from, EMCQueryConditions.GREATER_THAN_EQ);
                    }
                    if (!Functions.checkBlank(to)) {
                        query.addAnd("releaseDate", to, EMCQueryConditions.LESS_THAN_EQ);
                    }
                    if (!Functions.checkBlank(lkpSelectItem.getValue())) {
                        if (!addedItemMaster) {
                            query.addTableAnd(InventoryItemMaster.class.getName(), "itemId", POPPlannedPurchaseOrders.class.getName(), "itemId");
                            addedItemMaster = true;
                        }
                        query.addAnd("itemReference", lkpSelectItem.getValue(), InventoryItemMaster.class.getName());
                    }
                    if (!Functions.checkBlank(lkpSelectSupplier.getValue())) {
                        if (!addedItemMaster) {
                            query.addTableAnd(InventoryItemMaster.class.getName(), "itemId", POPPlannedPurchaseOrders.class.getName(), "itemId");
                            addedItemMaster = true;
                        }
                        query.addAnd("purchaseDefaultSupplier", lkpSelectSupplier.getValue(), InventoryItemMaster.class.getName());
                    }

                    if (!Functions.checkBlank(itemField1)) {
                        if (!addedItemMaster) {
                            query.addTableAnd(InventoryItemMaster.class.getName(), "itemId", POPPlannedPurchaseOrders.class.getName(), "itemId");
                            addedItemMaster = true;
                        }
                        query.addAnd(itemFields.get(0), itemField1, InventoryItemMaster.class.getName());
                    }
                    if (!Functions.checkBlank(itemField2)) {
                        if (!addedItemMaster) {
                            query.addTableAnd(InventoryItemMaster.class.getName(), "itemId", POPPlannedPurchaseOrders.class.getName(), "itemId");
                            addedItemMaster = true;
                        }
                        query.addAnd(itemFields.get(1), itemField2, InventoryItemMaster.class.getName());
                    }
                    query.addAnd("released", false);
                    POPPlannedPurchaseOrderReleaseMenu menu = new POPPlannedPurchaseOrderReleaseMenu();
                    menu.setDoNotOpenForm(false);
                    List udList = new ArrayList();
                    udList.add(dataManager);
                    udList.add(query);
                    udList.add(required);
                    udList.add(supplier);
                    udList.add(warehouse);
                    userData.setUserData(udList);
                    dataManager.getTheForm().getDeskTop().createAndAdd(menu, 20, 20, userData, null, 0);
                    POPPlannedPurchaseOrderReleaseDialog.this.dispose();
                }
            }
        };
        emcJButton btnCancel = new emcJButton("Cancel") {
            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                POPPlannedPurchaseOrderReleaseDialog.this.dispose();
            }
        };
        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(btnOk);
        buttonList.add(btnCancel);
        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }
}
