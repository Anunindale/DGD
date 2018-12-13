/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.journals.journalgenerator;

import emc.app.components.dialogs.EMCDialogFactory;
import emc.app.components.documents.EMCStringDocument;
import emc.app.components.emcJButton;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.lookup.EMCLookup;
import emc.app.components.lookup.factory.EMCDimensionLookupFactory;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.components.tables.EMCFieldsMapComboBox;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.wsmanager.EMCWSManager;
import emc.entity.base.journals.BaseJournalDefinitionTable;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.InventoryLocation;
import emc.entity.inventory.InventoryPallet;
import emc.entity.inventory.InventoryWarehouse;
import emc.entity.inventory.dimensions.InventoryDimension1;
import emc.entity.inventory.dimensions.InventoryDimension2;
import emc.entity.inventory.dimensions.InventoryDimension3;
import emc.entity.inventory.serialbatch.InventorySerialBatch;
import emc.forms.inventory.display.journals.journalgenerator.resources.InventoryTransferJournalFieldMapComboBox;
import emc.forms.inventory.display.journals.journalgenerator.resources.InventoryTransferJournalTableMapComboBox;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.helpers.inventory.JournalGeneratorHelper;
import emc.menus.inventory.menuitems.display.Dimension1;
import emc.menus.inventory.menuitems.display.Dimension2;
import emc.menus.inventory.menuitems.display.Dimension3;
import emc.menus.inventory.menuitems.display.InventSerialBatchMenu;
import emc.menus.inventory.menuitems.display.ItemMaster;
import emc.menus.inventory.menuitems.display.JournalDefinitions;
import emc.menus.inventory.menuitems.display.LocationMenu;
import emc.menus.inventory.menuitems.display.PalletMenu;
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
import javax.swing.JOptionPane;

/**
 *
 * @author wikus
 */
public class InventoryJournalGenerator extends BaseInternalFrame {

    private EMCUserData userData;
    //Master Fields
    private EMCLookup lkpJournalDefinition;
    private emcJTextField txtJournalDescription;
    //From Fields
    private EMCLookup lkpFromItem;
    private EMCLookup lkpFromDimension1;
    private EMCLookup lkpFromDimension2;
    private EMCLookup lkpFromDimension3;
    private EMCLookup lkpFromWarehouse;
    private EMCLookup lkpFromLocation;
    private EMCLookup lkpFromBatch;
    private EMCLookup lkpFromSerial;
    private EMCLookup lkpFromPallet;
    //To Fields
    private EMCLookup lkpToItem;
    private EMCLookup lkpToDimension1;
    private EMCLookup lkpToDimension2;
    private EMCLookup lkpToDimension3;
    private EMCLookup lkpToWarehouse;
    private EMCLookup lkpToLocation;
    private EMCLookup lkpToBatch;
    private EMCLookup lkpToSerial;
    private EMCLookup lkpToPallet;
    //Additional Selection
    InventoryTransferJournalTableMapComboBox selectionTable1;
    InventoryTransferJournalFieldMapComboBox selectionField1;
    EMCLookup selectionValue1;
    InventoryTransferJournalTableMapComboBox selectionTable2;
    InventoryTransferJournalFieldMapComboBox selectionField2;
    EMCLookup selectionValue2;
    InventoryTransferJournalTableMapComboBox selectionTable3;
    InventoryTransferJournalFieldMapComboBox selectionField3;
    EMCLookup selectionValue3;
    InventoryTransferJournalTableMapComboBox selectionTable4;
    InventoryTransferJournalFieldMapComboBox selectionField4;
    EMCLookup selectionValue4;
    InventoryTransferJournalTableMapComboBox selectionTable5;
    InventoryTransferJournalFieldMapComboBox selectionField5;
    EMCLookup selectionValue5;

    public InventoryJournalGenerator(EMCUserData userData) {
        super("Transfer Journal Generator", true, true, true, true, userData);
        this.setBounds(20, 20, 750, 400);
        this.userData = userData.copyUserDataAndDataList();
        initFrame();
    }

    private void initFrame() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Create Transfer Journal", selectionPane());
        tabbed.add("Additional Selection", additionalSelectionPane());

        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(tabbed, BorderLayout.CENTER);
        contentPane.add(buttonPane(), BorderLayout.EAST);
        this.setContentPane(contentPane);
    }

    private emcJPanel selectionPane() {
        emcJPanel selectionPane = new emcJPanel(new BorderLayout());
        emcJPanel itemPane = new emcJPanel(new BorderLayout());

        //Master Fields
        lkpJournalDefinition = new EMCLookup(new JournalDefinitions());
        lkpJournalDefinition.setPopup(new EMCLookupPopup(new BaseJournalDefinitionTable(), "journalDefinitionId", userData));

        txtJournalDescription = new emcJTextField(new EMCStringDocument());

        Component[][] masterFieldComp = {{new emcJLabel("Journal Definition"), lkpJournalDefinition, new emcJLabel("Journal Description"), txtJournalDescription}};

        selectionPane.add(emcSetGridBagConstraints.createSimplePanel(masterFieldComp, GridBagConstraints.NONE, true, "Journal Master"), BorderLayout.NORTH);


        //From Fields
        lkpFromItem = new EMCLookup(new ItemMaster()) {

            @Override
            public void setValue(Object value) {
                super.setValue(value);
                if (lkpToItem != null && Functions.checkBlank(lkpToItem.getValue())) {
                    lkpToItem.setValue(value);
                }
            }
        };
        lkpFromItem.setPopup(new EMCLookupPopup(new InventoryItemMaster(), "itemReference", userData));

        lkpFromDimension1 = new EMCLookup(new Dimension1()) {

            @Override
            public void setValue(Object value) {
                super.setValue(value);
                if (lkpToDimension1 != null && Functions.checkBlank(lkpToDimension1.getValue())) {
                    lkpToDimension1.setValue(value);
                }
            }
        };
        lkpFromDimension1.setPopup(new EMCLookupPopup(new InventoryDimension1(), "dimensionId", userData));

        lkpFromDimension2 = new EMCLookup(new Dimension2()) {

            @Override
            public void setValue(Object value) {
                super.setValue(value);
                if (lkpToDimension2 != null && Functions.checkBlank(lkpToDimension2.getValue())) {
                    lkpToDimension2.setValue(value);
                }
            }
        };
        lkpFromDimension2.setPopup(new EMCLookupPopup(new InventoryDimension2(), "dimensionId", userData));

        lkpFromDimension3 = new EMCLookup(new Dimension3()) {

            @Override
            public void setValue(Object value) {
                super.setValue(value);
                if (lkpToDimension3 != null && Functions.checkBlank(lkpToDimension3.getValue())) {
                    lkpToDimension3.setValue(value);
                }
            }
        };
        lkpFromDimension3.setPopup(new EMCLookupPopup(new InventoryDimension3(), "dimensionId", userData));

        lkpFromWarehouse = new EMCLookup(new Warehouse()) {

            @Override
            public void setValue(Object value) {
                super.setValue(value);
                if (lkpToWarehouse != null && Functions.checkBlank(lkpToWarehouse.getValue())) {
                    lkpToWarehouse.setValue(value);
                }
            }
        };
        lkpFromWarehouse.setPopup(new EMCLookupPopup(new InventoryWarehouse(), "warehouseId", userData));

        lkpFromLocation = new EMCLookup(new LocationMenu()) {

            @Override
            public void setValue(Object value) {
                super.setValue(value);
                if (lkpToLocation != null && Functions.checkBlank(lkpToLocation.getValue())) {
                    lkpToLocation.setValue(value);
                }
            }
        };
        lkpFromLocation.setPopup(new EMCLookupPopup(new InventoryLocation(), "locationId", userData));

        lkpFromBatch = new EMCLookup(new InventSerialBatchMenu()) {

            @Override
            public void setValue(Object value) {
                super.setValue(value);
                if (lkpToBatch != null && Functions.checkBlank(lkpToBatch.getValue())) {
                    lkpToBatch.setValue(value);
                }
            }
        };
        lkpFromBatch.setPopup(new EMCLookupPopup(new InventorySerialBatch(), "batch", userData));

        lkpFromSerial = new EMCLookup(new InventSerialBatchMenu()) {

            @Override
            public void setValue(Object value) {
                super.setValue(value);
                if (lkpToSerial != null && Functions.checkBlank(lkpToSerial.getValue())) {
                    lkpToSerial.setValue(value);
                }
            }
        };
        lkpFromSerial.setPopup(new EMCLookupPopup(new InventorySerialBatch(), "serial", userData));

        lkpFromPallet = new EMCLookup(new PalletMenu()) {

            @Override
            public void setValue(Object value) {
                super.setValue(value);
                if (lkpToPallet != null && Functions.checkBlank(lkpToPallet.getValue())) {
                    lkpToPallet.setValue(value);
                }
            }
        };
        lkpFromPallet.setPopup(new EMCLookupPopup(new InventoryPallet(), "palletId", userData));

        Component[][] fromFieldComp = {{new emcJLabel("From Item"), lkpFromItem},
            {new emcJLabel("From Config"), lkpFromDimension1},
            {new emcJLabel("From Color"), lkpFromDimension3},
            {new emcJLabel("From Size"), lkpFromDimension2},
            {new emcJLabel("From Warehouse"), lkpFromWarehouse},
            {new emcJLabel("From Location"), lkpFromLocation},
            {new emcJLabel("From Batch"), lkpFromBatch},
            {new emcJLabel("From Serial"), lkpFromSerial},
            {new emcJLabel("From Pallet"), lkpFromPallet}};

        itemPane.add(emcSetGridBagConstraints.createSimplePanel(fromFieldComp, GridBagConstraints.NONE, true, "From"), BorderLayout.WEST);


        //To Fields
        lkpToItem = new EMCLookup(new ItemMaster());
        lkpToItem.setPopup(new EMCLookupPopup(new InventoryItemMaster(), "itemReference", userData));
        lkpToDimension1 = EMCDimensionLookupFactory.createDimension1Lookup(userData);
        lkpToDimension2 = EMCDimensionLookupFactory.createDimension2Lookup(userData);
        lkpToDimension3 = EMCDimensionLookupFactory.createDimension3Lookup(userData);
        lkpToWarehouse = EMCDimensionLookupFactory.createWarehouseLookup(userData);
        lkpToLocation = EMCDimensionLookupFactory.createLocationLookup(userData);
        lkpToBatch = EMCDimensionLookupFactory.createBatchLookup(userData);
        lkpToSerial = EMCDimensionLookupFactory.createSerialLookup(userData);
        lkpToPallet = EMCDimensionLookupFactory.createPalletLookup(userData);

        Component[][] toFieldComp = {{new emcJLabel("To Item"), lkpToItem},
            {new emcJLabel("To Config"), lkpToDimension1},
            {new emcJLabel("To Color"), lkpToDimension3},
            {new emcJLabel("To Size"), lkpToDimension2},
            {new emcJLabel("To Warehouse"), lkpToWarehouse},
            {new emcJLabel("To Location"), lkpToLocation},
            {new emcJLabel("To Batch"), lkpToBatch},
            {new emcJLabel("To Serial"), lkpToSerial},
            {new emcJLabel("To Pallet"), lkpToPallet}};

        itemPane.add(emcSetGridBagConstraints.createSimplePanel(toFieldComp, GridBagConstraints.NONE, true, "To"), BorderLayout.EAST);

        selectionPane.add(itemPane, BorderLayout.CENTER);

        return selectionPane;
    }

    private emcJPanel buttonPane() {
        emcJButton btnGenerate = new emcJButton("Generate") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);

                if (Functions.checkBlank(lkpJournalDefinition.getValue())) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Please select the journal definition to to used.", userData);
                    return;
                } else if (Functions.checkBlank(txtJournalDescription.getText())) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Please select the journal description to to used.", userData);
                    return;
                }
                if (EMCDialogFactory.createQuestionDialog(this, "Generate", "Are you sure that you want generate the journals?") == JOptionPane.YES_OPTION) {
                    JournalGeneratorHelper helper = new JournalGeneratorHelper();
                    //Master Fields
                    helper.setJournalDefinition((String) lkpJournalDefinition.getValue());
                    helper.setJournalDescription(txtJournalDescription.getText());
                    //From Fields
                    helper.setFromItem((String) lkpFromItem.getValue());
                    helper.setFromDimension1((String) lkpFromDimension1.getValue());
                    helper.setFromDimension2((String) lkpFromDimension2.getValue());
                    helper.setFromDimension3((String) lkpFromDimension3.getValue());
                    helper.setFromWarehouse((String) lkpFromWarehouse.getValue());
                    helper.setFromLocation((String) lkpFromLocation.getValue());
                    helper.setFromBatch((String) lkpFromBatch.getValue());
                    helper.setFromSerial((String) lkpFromSerial.getValue());
                    helper.setFromPallet((String) lkpFromPallet.getValue());
                    //To Fields
                    helper.setToItem((String) lkpToItem.getValue());
                    helper.setToDimension1((String) lkpToDimension1.getValue());
                    helper.setToDimension2((String) lkpToDimension2.getValue());
                    helper.setToDimension3((String) lkpToDimension3.getValue());
                    helper.setToWarehouse((String) lkpToWarehouse.getValue());
                    helper.setToLocation((String) lkpToLocation.getValue());
                    helper.setToBatch((String) lkpToBatch.getValue());
                    helper.setToSerial((String) lkpToSerial.getValue());
                    helper.setToPallet((String) lkpToPallet.getValue());

                    helper.setSelectionTable1((String)selectionTable1.getSelectedItem());
                    helper.setSelectionField1((String)selectionField1.getSelectedItem());
                    helper.setSelectionValue1((String)selectionValue1.getValue());
                    helper.setSelectionTable2((String)selectionTable2.getSelectedItem());
                    helper.setSelectionField2((String)selectionField2.getSelectedItem());
                    helper.setSelectionValue2((String)selectionValue2.getValue());
                    helper.setSelectionTable3((String)selectionTable3.getSelectedItem());
                    helper.setSelectionField3((String)selectionField3.getSelectedItem());
                    helper.setSelectionValue3((String)selectionValue3.getValue());
                    helper.setSelectionTable4((String)selectionTable4.getSelectedItem());
                    helper.setSelectionField4((String)selectionField4.getSelectedItem());
                    helper.setSelectionValue4((String)selectionValue4.getValue());
                    helper.setSelectionTable5((String)selectionTable5.getSelectedItem());
                    helper.setSelectionField5((String)selectionField5.getSelectedItem());
                    helper.setSelectionValue5((String)selectionValue5.getValue());

                    EMCCommandClass cmd = new EMCCommandClass(ServerInventoryMethods.GENERATE_TRANSFER_JOURNAL);

                    List toSend = new ArrayList();
                    toSend.add(helper);

                    toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);
                    if (toSend.size() > 1 && (Boolean) toSend.get(1)) {
                        Logger.getLogger("emc").log(Level.INFO, "Journals generated successfully.", userData);
                    } else {
                        Logger.getLogger("emc").log(Level.SEVERE, "Failed to generate journals.", userData);
                    }
                }
            }
        };
        emcJButton btnCancel = new emcJButton("Cancel") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                if (EMCDialogFactory.createQuestionDialog(this, "Cancel", "Are you sure that you want to cancel the journal generation?") == JOptionPane.YES_OPTION) {
                    InventoryJournalGenerator.this.dispose();
                }
            }
        };
        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(btnGenerate);
        buttonList.add(btnCancel);
        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }

    private emcJPanel additionalSelectionPane() {
        selectionValue1 = new EMCLookup(null);
        selectionField1 = new InventoryTransferJournalFieldMapComboBox(selectionValue1, userData);
        selectionTable1 = new InventoryTransferJournalTableMapComboBox(selectionField1, userData);

        selectionValue2 = new EMCLookup(null);
        selectionField2 = new InventoryTransferJournalFieldMapComboBox(selectionValue2, userData);
        selectionTable2 = new InventoryTransferJournalTableMapComboBox(selectionField2, userData);

        selectionValue3 = new EMCLookup(null);
        selectionField3 = new InventoryTransferJournalFieldMapComboBox(selectionValue3, userData);
        selectionTable3 = new InventoryTransferJournalTableMapComboBox(selectionField3, userData);

        selectionValue4 = new EMCLookup(null);
        selectionField4 = new InventoryTransferJournalFieldMapComboBox(selectionValue4, userData);
        selectionTable4 = new InventoryTransferJournalTableMapComboBox(selectionField4, userData);

        selectionValue5 = new EMCLookup(null);
        selectionField5 = new InventoryTransferJournalFieldMapComboBox(selectionValue5, userData);
        selectionTable5 = new InventoryTransferJournalTableMapComboBox(selectionField5, userData);

        Component[][] comp = {{new emcJLabel("Table"), selectionTable1, new emcJLabel("Field"), selectionField1, new emcJLabel("Value"), selectionValue1},
            {new emcJLabel("Table"), selectionTable2, new emcJLabel("Field"), selectionField2, new emcJLabel("Value"), selectionValue2},
            {new emcJLabel("Table"), selectionTable3, new emcJLabel("Field"), selectionField3, new emcJLabel("Value"), selectionValue3},
            {new emcJLabel("Table"), selectionTable4, new emcJLabel("Field"), selectionField4, new emcJLabel("Value"), selectionValue4},
            {new emcJLabel("Table"), selectionTable5, new emcJLabel("Field"), selectionField5, new emcJLabel("Value"), selectionValue5}};

        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true);
    }
}
