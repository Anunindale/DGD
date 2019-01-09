/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.warehousestockenquiry.resources;

import emc.app.components.documents.EMCIntegerDocument;
import emc.app.components.documents.EMCStringDocument;
import emc.app.components.emcJButton;
import emc.app.components.emcJDialog;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emctable.emcYesNoComponent;
import emc.app.components.inventory.lookups.itemreference.ItemReferencePopup;
import emc.app.components.lookup.EMCLookup;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.wsmanager.EMCWSManager;
import emc.entity.inventory.InventoryLocation;
import emc.entity.inventory.InventoryProductGroup;
import emc.entity.inventory.InventoryWarehouse;
import emc.entity.inventory.classifications.InventoryItemClassification1;
import emc.entity.inventory.classifications.InventoryItemClassification5;
import emc.entity.inventory.dimensions.InventoryDimension1;
import emc.entity.inventory.dimensions.InventoryDimension2;
import emc.entity.inventory.dimensions.InventoryDimension3;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.menus.inventory.menuitems.display.Classifications1;
import emc.menus.inventory.menuitems.display.Classifications5;
import emc.menus.inventory.menuitems.display.Dimension1;
import emc.menus.inventory.menuitems.display.Dimension2;
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
public class PopulationDialog extends emcJDialog {

    private EMCUserData userData;
    private EMCLookup lkpItem;
    private EMCLookup lkpProductGroup;
    private EMCLookup lkpClass1;
    private EMCLookup lkpClass5;
    private EMCLookup lkpDimension1;
    private EMCLookup lkpDimension2;
    private EMCLookup lkpDimension3;
    private EMCLookup lkpWarehouse;
    private EMCLookup lkpLocation;
    private emcJTextField txtBatch;
    private emcJTextField txtSerial;
    private emcJTextField txtBoxLevel;
    private emcYesNoComponent ynDoAgeing;
    private boolean populated;

    public PopulationDialog(EMCUserData userData) {
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

        lkpProductGroup = new EMCLookup(new InventoryProductGroupMenu());
        lkpProductGroup.setPopup(new EMCLookupPopup(new InventoryProductGroup(), "productGroupId", userData));

        lkpClass1 = new EMCLookup(new Classifications1());
        lkpClass1.setPopup(new EMCLookupPopup(new InventoryItemClassification1(), "classification", userData));

        lkpClass5 = new EMCLookup(new Classifications5());
        lkpClass5.setPopup(new EMCLookupPopup(new InventoryItemClassification5(), "classification", userData));

        lkpDimension1 = new EMCLookup(new Dimension1());
        lkpDimension1.setPopup(new EMCLookupPopup(new InventoryDimension1(), "dimensionId", userData));

        lkpDimension2 = new EMCLookup(new Dimension2());
        lkpDimension2.setPopup(new EMCLookupPopup(new InventoryDimension2(), "dimensionId", userData));

        lkpDimension3 = new EMCLookup(new Dimension3());
        lkpDimension3.setPopup(new EMCLookupPopup(new InventoryDimension3(), "dimensionId", userData));

        txtBatch = new emcJTextField(new EMCStringDocument());

        txtSerial = new emcJTextField(new EMCStringDocument());

        txtBoxLevel = new emcJTextField(new EMCIntegerDocument());

        ynDoAgeing = new emcYesNoComponent();
        ynDoAgeing.setSelectedItem("No");


        Component[][] comp = {{new emcJLabel("Warehouse"), lkpWarehouse},
            {new emcJLabel("Location"), lkpLocation},
            {new emcJLabel("Item"), lkpItem},
            {new emcJLabel("Product Group"), lkpProductGroup},
            {new emcJLabel("Classification 1"), lkpClass1},
            {new emcJLabel("Classification 5"), lkpClass5},
            {new emcJLabel("Config"), lkpDimension1},
            {new emcJLabel("Colour"), lkpDimension3},
            {new emcJLabel("Size"), lkpDimension2},
            {new emcJLabel("Batch No"), txtBatch},
            {new emcJLabel("Serial No"), txtSerial},
            {new emcJLabel("Box Level"), txtBoxLevel},
            {new emcJLabel("Do Ageing"), ynDoAgeing}};
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

                EMCCommandClass cmd = new EMCCommandClass(ServerInventoryMethods.POPULATE_INVENTORYWAREHOUSESTOCKENQUIRY);

                List toSend = new ArrayList();
                toSend.add(lkpWarehouse.getValue());
                toSend.add(Functions.checkBlank(lkpLocation.getValue()) ? "" : lkpLocation.getValue());
                toSend.add(Functions.checkBlank(lkpItem.getValue()) ? "" : lkpItem.getValue());
                toSend.add(Functions.checkBlank(lkpProductGroup.getValue()) ? "" : lkpProductGroup.getValue());
                toSend.add("");
                toSend.add(Functions.checkBlank(lkpClass1.getValue()) ? "" : lkpClass1.getValue());
                toSend.add(Functions.checkBlank(lkpClass5.getValue()) ? "" : lkpClass5.getValue());
                toSend.add(Functions.checkBlank(lkpDimension1.getValue()) ? "" : lkpDimension1.getValue());
                toSend.add(Functions.checkBlank(lkpDimension2.getValue()) ? "" : lkpDimension2.getValue());
                toSend.add(Functions.checkBlank(lkpDimension3.getValue()) ? "" : lkpDimension3.getValue());
                toSend.add(Functions.checkBlank(txtBatch.getValue()) ? "" : txtBatch.getValue());
                toSend.add(Functions.checkBlank(txtSerial.getValue()) ? "" : txtSerial.getValue());
                toSend.add(Functions.checkBlank(txtBoxLevel.getValue()) ? 0 : Integer.valueOf(txtBoxLevel.getValue().toString()));
                toSend.add(ynDoAgeing.getSelectedItem().equals("Yes"));

                toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);

                if (toSend.size() > 1 && (Boolean) toSend.get(1)) {
                    populated = true;
                    PopulationDialog.this.dispose();
                } else {
                    Logger.getLogger("emc").log(Level.SEVERE, "Population failed.", userData);
                }
            }
        };
        emcJButton btnCancel = new emcJButton("Cancel") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                PopulationDialog.this.dispose();
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

    public boolean doAgeing() {
        return ynDoAgeing.getSelectedItem().equals("Yes");
    }
}
