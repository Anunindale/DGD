/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.warehousestockenquiry.resources;

import emc.app.components.emcJButton;
import emc.app.components.emcJDialog;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emctable.emcYesNoComponent;
import emc.app.components.inventory.lookups.itemreference.ItemReferencePopup;
import emc.app.components.lookup.EMCLookup;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.wsmanager.EMCWSManager;
import emc.entity.inventory.InventoryBrandGroup;
import emc.entity.inventory.InventoryLocation;
import emc.entity.inventory.InventoryProductGroup;
import emc.entity.inventory.InventoryWarehouse;
import emc.entity.inventory.classifications.InventoryItemClassification1;
import emc.entity.inventory.classifications.InventoryItemClassification5;
import emc.entity.inventory.dimensions.InventoryDimension1;
import emc.entity.inventory.dimensions.InventoryDimension3;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.helpers.inventory.WarehouseEnquiryHelperClass;
import emc.menus.inventory.menuitems.display.BrandGroups;
import emc.menus.inventory.menuitems.display.Classifications1;
import emc.menus.inventory.menuitems.display.Classifications5;
import emc.menus.inventory.menuitems.display.Dimension1;
import emc.menus.inventory.menuitems.display.Dimension3;
import emc.menus.inventory.menuitems.display.InventoryProductGroupMenu;
import emc.menus.inventory.menuitems.display.ItemMaster;
import emc.menus.inventory.menuitems.display.LocationMenu;
import emc.menus.inventory.menuitems.display.Warehouse;
import emc.methods.inventory.ServerInventoryMethods;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wikus
 */
public class BySizePopulationDialog extends emcJDialog {

    private EMCUserData userData;
    private EMCLookup lkpItem;
    private EMCLookup lkpBrandGroup;
    private EMCLookup lkpProductGroup;
    private EMCLookup lkpClass1;
    private EMCLookup lkpClass5;
    private EMCLookup lkpDimension1;
    private EMCLookup lkpDimension3;
    private EMCLookup lkpWarehouse;
    private EMCLookup lkpLocation;
    private emcYesNoComponent ynTotalQuantities;
    private emcYesNoComponent ynCrates;
    private boolean populated;
    private List<List<String>> sizeColumnsHolderList;

    public BySizePopulationDialog(EMCUserData userData) {
        super(null, "Populate", true);
        this.userData = userData;
        this.populated = false;
        initDialog();
        this.pack();
        this.setVisible(true);
    }

    private void initDialog() {
        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Population", selectionPane());
        contentPane.add(tabbed, BorderLayout.CENTER);
        contentPane.add(buttonPane(), BorderLayout.EAST);
        this.setContentPane(contentPane);
    }

    private Component selectionPane() {
        lkpWarehouse = new EMCLookup(new Warehouse());
        lkpWarehouse.setPopup(new EMCLookupPopup(new InventoryWarehouse(), "warehouseId", userData));

        lkpLocation = new EMCLookup(new LocationMenu());
        lkpLocation.setPopup(new EMCLookupPopup(new InventoryLocation(), "locationId", userData));

        lkpItem = new EMCLookup(new ItemMaster());
        lkpItem.setPopup(new ItemReferencePopup("reference", userData));

        lkpBrandGroup = new EMCLookup(new BrandGroups());
        lkpBrandGroup.setPopup(new EMCLookupPopup(new InventoryBrandGroup(), "brandGroupId", userData));

        lkpProductGroup = new EMCLookup(new InventoryProductGroupMenu());
        lkpProductGroup.setPopup(new EMCLookupPopup(new InventoryProductGroup(), "productGroupId", userData));

      lkpClass1 = new EMCLookup(new Classifications1());
        lkpClass1.setPopup(new EMCLookupPopup(new InventoryItemClassification1(), "classification", userData));

        lkpClass5 = new EMCLookup(new Classifications5());
        lkpClass5.setPopup(new EMCLookupPopup(new InventoryItemClassification5(), "classification", userData));

        lkpDimension1 = new EMCLookup(new Dimension1());
        lkpDimension1.setPopup(new EMCLookupPopup(new InventoryDimension1(), "dimensionId", userData));

        lkpDimension3 = new EMCLookup(new Dimension3());
        lkpDimension3.setPopup(new EMCLookupPopup(new InventoryDimension3(), "dimensionId", userData));

        ynTotalQuantities = new emcYesNoComponent();

        ynCrates = new emcYesNoComponent();
        ynCrates.setSelectedItem("No");

        Component[][] comp = {{new emcJLabel("Warehouse"), lkpWarehouse},
            {new emcJLabel("Location"), lkpLocation},
            {new emcJLabel("Item"), lkpItem},
            {new emcJLabel("Brand Group"), lkpBrandGroup},
            {new emcJLabel("Product Group"), lkpProductGroup},
            {new emcJLabel("Classification 1"), lkpClass1},
            {new emcJLabel("Classification 5"), lkpClass5},
            {new emcJLabel("Config"), lkpDimension1},
            {new emcJLabel("Color"), lkpDimension3},
            {new emcJLabel("Crates Only"), ynCrates},
            {new emcJLabel("Total Quantities"), ynTotalQuantities}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true);
    }

    private emcJPanel buttonPane() {
        emcJButton btnOK = new emcJButton("OK") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                if (Functions.checkBlank(lkpWarehouse.getValue())) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Please select a warehouse", userData);
                    return;

                }
                WarehouseEnquiryHelperClass helper = new WarehouseEnquiryHelperClass();
                helper.setRecordOwner(userData.getUserName());
                helper.setWarehouse((String) lkpWarehouse.getValue());
                helper.setLocation((String) lkpLocation.getValue());
                helper.setItem((String) lkpItem.getValue());
                helper.setProductGroup((String) lkpProductGroup.getValue());
                helper.setPlanningGroup("");
                helper.setClassification1((String) lkpClass1.getValue());
                helper.setClassification5((String) lkpClass5.getValue());
                helper.setBrandGroup((String) lkpBrandGroup.getValue());
                helper.setDimension1((String) lkpDimension1.getValue());
                helper.setDimension3((String) lkpDimension3.getValue());
                helper.setTotalQuantity("Yes".equals(ynTotalQuantities.getSelectedItem()));
                helper.setCrates("Yes".equals(ynCrates.getSelectedItem()));

                EMCCommandClass cmd = new EMCCommandClass(ServerInventoryMethods.POPULATE_INVENTORYWAREHOUSESTOCKENQUIRYBYSIZE);

                List toSend = new ArrayList();
                toSend.add(helper);

                toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);

                if (toSend.size() > 1 && toSend.get(1) instanceof List) {
                    populated = true;
                    sizeColumnsHolderList = (List<List<String>>) toSend.get(1);
                    BySizePopulationDialog.this.dispose();
                } else {
                    Logger.getLogger("emc").log(Level.SEVERE, "Population failed.", userData);
                }
            }
        };
        emcJButton btnCancel = new emcJButton("Cancel") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                BySizePopulationDialog.this.dispose();
            }
        };
        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(btnOK);
        buttonList.add(btnCancel);
        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }

    public boolean isPopulated() {
        return populated;
    }

    public List<List<String>> getSizeColumnsHolderList() {
        return sizeColumnsHolderList;
    }
}
