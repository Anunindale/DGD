/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.stocktakeunreserved;

import emc.app.components.documents.EMCStringDocument;
import emc.app.components.documents.EMCStringFormDocument;
import emc.app.components.emcJButton;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextField;
import emc.app.components.emcMenuButton;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emcpicker.emcdatepicker.EMCDatePickerFormComponent;
import emc.app.components.emctable.editors.EMCGoToMainTableEditor;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.emctable.emcYesNoComponent;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.util.utilFunctions;
import emc.datatypes.EMCDataType;
import emc.entity.inventory.datasource.InventoryStocktakeUnreservedDS;
import emc.entity.inventory.journals.InventoryJournalMaster;
import emc.enums.inventory.transactions.InventoryTransactionsRefType;
import emc.forms.inventory.display.stocktakeunreserved.resources.SockTakeUnreservedDRM;
import emc.forms.inventory.display.stocktakeunreserved.resources.UnreservedStockTakeDeleteDialog;
import emc.framework.EMCUserData;
import emc.menus.inventory.menuitems.display.Dimension1;
import emc.menus.inventory.menuitems.display.Dimension2;
import emc.menus.inventory.menuitems.display.Dimension3;
import emc.menus.inventory.menuitems.display.ItemMaster;
import emc.menus.inventory.menuitems.display.Journals;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;

/**
 *
 * @author claudette
 */
public class InventoryStocktakeUnreservedForm extends BaseInternalFrame {

    private SockTakeUnreservedDRM dataManager;

    public InventoryStocktakeUnreservedForm(EMCUserData userData) {
        super("Stocktake Unreserved", true, true, true, true, userData);
        this.setBounds(20, 20, 1000, 350);
        dataManager = new SockTakeUnreservedDRM(new emcGenericDataSourceUpdate(new InventoryStocktakeUnreservedDS(), userData), userData);
        dataManager.setTheForm(this);
        this.setDataManager(dataManager);
        dataManager.setFormTextId1("referenceType");
        dataManager.setFormTextId2("referenceId");

        initFrame();
    }

    private void initFrame() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Unreserved", tablePane());
        tabbed.add("Dimensions", dimensionPane());
        tabbed.add("Resolve", resolevePane());

        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(tabbed, BorderLayout.CENTER);
        contentPane.add(tabButtons(), BorderLayout.EAST);
        this.setContentPane(contentPane);
    }

    private emcJPanel tabButtons() {
        emcMenuButton btnInventory = new emcMenuButton("Inventory", new ItemMaster(), this, 0, false);

        emcJButton btnDelete = new emcJButton("Delete") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                new UnreservedStockTakeDeleteDialog(utilFunctions.findEMCDesktop(this), dataManager);
            }
        };

        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(btnInventory);
        buttonList.add(btnDelete);

        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }

    private emcTablePanelUpdate tablePane() {
        List<String> keys = new ArrayList<String>();
        keys.add("referenceJournal");
        keys.add("referenceType");
        keys.add("referenceId");
        keys.add("referenceRecStatus");
        keys.add("referenceRecType");
        keys.add("itemReference");
        keys.add("itemDescription");
        keys.add("dimension1");
        keys.add("dimension3");
        keys.add("dimension2");
        keys.add("resolved");
        emcTableModelUpdate model = new emcTableModelUpdate(dataManager, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        table.setColumnCellEditor("referenceJournal", new EMCGoToMainTableEditor(new EMCStringDocument(), new Journals()));
        table.setColumnCellEditor("itemReference", new EMCGoToMainTableEditor(new EMCStringDocument(), new ItemMaster()));
        table.setColumnCellEditor("dimension1", new EMCGoToMainTableEditor(new EMCStringDocument(), new Dimension1()));
        table.setColumnCellEditor("dimension2", new EMCGoToMainTableEditor(new EMCStringDocument(), new Dimension2()));
        table.setColumnCellEditor("dimension3", new EMCGoToMainTableEditor(new EMCStringDocument(), new Dimension3()));
        table.setColumnCellEditor("referenceId", new EMCGoToMainTableEditor(new EMCStringDocument(), null) {

            @Override
            public Component getTableCellEditorComponent(JTable arg0, Object arg1, boolean arg2, int arg3, int arg4) {
                InventoryTransactionsRefType type = InventoryTransactionsRefType.fromString(dataManager.getFieldValueAt(dataManager.getLastRowAccessed(), "referenceType").toString());
                EMCDataType dt = dataManager.getDataType("referenceId");
                switch (type) {
                    case Journal_Line:
                        this.changeMenuItem(new Journals());
                        dt.setRelatedTable(InventoryJournalMaster.class.getName());
                        dt.setRelatedField("journalNumber");
                        break;


                }
                return super.getTableCellEditorComponent(arg0, arg1, arg2, arg3, arg4);
            }
        });
        dataManager.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        dataManager.setTablePanel(tableScroll);
        return tableScroll;
    }

    private emcJPanel dimensionPane() {
        emcJTextField txtItemReference = new emcJTextField(new EMCStringFormDocument(dataManager, "itemReference"));
        txtItemReference.setEditable(false);
        emcJTextField txtItemDescription = new emcJTextField(new EMCStringFormDocument(dataManager, "itemDescription"));
        txtItemDescription.setEditable(false);
        emcJTextField txtDimension1 = new emcJTextField(new EMCStringFormDocument(dataManager, "dimension1"));
        txtDimension1.setEditable(false);
        emcJTextField txtDimension2 = new emcJTextField(new EMCStringFormDocument(dataManager, "dimension2"));
        txtDimension2.setEditable(false);
        emcJTextField txtDimension3 = new emcJTextField(new EMCStringFormDocument(dataManager, "dimension3"));
        txtDimension3.setEditable(false);
        emcJTextField txtWarehouse = new emcJTextField(new EMCStringFormDocument(dataManager, "warehouse"));
        txtWarehouse.setEditable(false);
        emcJTextField txtLocation = new emcJTextField(new EMCStringFormDocument(dataManager, "location"));
        txtLocation.setEditable(false);
        emcJTextField txtBatch = new emcJTextField(new EMCStringFormDocument(dataManager, "batch"));
        txtBatch.setEditable(false);
        emcJTextField txtSerial = new emcJTextField(new EMCStringFormDocument(dataManager, "serial"));
        txtSerial.setEditable(false);
        emcJTextField txtPallet = new emcJTextField(new EMCStringFormDocument(dataManager, "pallet"));
        txtPallet.setEditable(false);

        Component[][] comp = {{new emcJLabel(dataManager.getFieldEmcLabel("itemReference")), txtItemReference, new emcJLabel(dataManager.getFieldEmcLabel("itemDescription")), txtItemDescription},
            {new emcJLabel()},
            {new emcJLabel(dataManager.getFieldEmcLabel("dimension1")), txtDimension1, new emcJLabel(dataManager.getFieldEmcLabel("dimension3")), txtDimension3},
            {new emcJLabel(dataManager.getFieldEmcLabel("dimension2")), txtDimension2},
            {new emcJLabel()},
            {new emcJLabel(dataManager.getFieldEmcLabel("warehouse")), txtWarehouse, new emcJLabel(dataManager.getFieldEmcLabel("location")), txtLocation},
            {new emcJLabel()},
            {new emcJLabel(dataManager.getFieldEmcLabel("batch")), txtBatch, new emcJLabel(dataManager.getFieldEmcLabel("serial")), txtSerial},
            {new emcJLabel()},
            {new emcJLabel(dataManager.getFieldEmcLabel("pallet")), txtPallet}};

        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true);
    }

    private emcJPanel resolevePane() {
        emcYesNoComponent ynResolved = new emcYesNoComponent(dataManager, "resolved");
        emcJTextField txtResolvedBy = new emcJTextField(new EMCStringFormDocument(dataManager, "resolvedBy"));
        txtResolvedBy.setEditable(false);
        EMCDatePickerFormComponent dpResolvedDate = new EMCDatePickerFormComponent(dataManager, "resolvedDate");
        dpResolvedDate.setEnabled(false);

        Component[][] comp = {{new emcJLabel("Resolved"), ynResolved},
            {new emcJLabel()},
            {new emcJLabel(dataManager.getFieldEmcLabel("resolvedBy")), txtResolvedBy, new emcJLabel(dataManager.getFieldEmcLabel("resolvedDate")), dpResolvedDate}};

        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true);
    }
}
