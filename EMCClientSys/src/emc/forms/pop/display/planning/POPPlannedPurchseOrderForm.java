
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.pop.display.planning;

import emc.app.components.documents.EMCDoubleFormDocument;
import emc.app.components.documents.EMCStringDocument;
import emc.app.components.emcJButton;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.editors.EMCGoToMainTableEditor;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.emctable.stock.ItemLookupRelationManager;
import emc.app.components.emctable.stock.StockDRM;
import emc.app.components.emctable.stock.StockDRMParameters;
import emc.app.components.formlookup.EMCLookupFormComponent;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.factory.EMCItemLookupFactory;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.util.utilFunctions;
import emc.app.wsmanager.EMCWSManager;
import emc.entity.base.BaseUnitsOfMeasure;
import emc.entity.pop.planning.datasource.POPPlannedPurchaseOrdersDS;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.menus.base.menuItems.display.UnitsOfMeasure;
import emc.menus.gl.menuitems.display.GLVATCode;
import emc.menus.inventory.menuitems.display.ItemGroups;
import emc.methods.pop.ServerPOPMethods;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

/**
 *
 * @author wikus
 */
public class POPPlannedPurchseOrderForm extends BaseInternalFrame {

    private StockDRM dataManager;
    private EMCUserData userData;

    public POPPlannedPurchseOrderForm(EMCUserData userData) {
        super("Planned Purchase Orders", true, true, true, true, userData);
        this.setBounds(20, 20, 900, 390);
        this.userData = userData.copyUserDataAndDataList();
        StockDRMParameters param = new StockDRMParameters("itemId", "dimension1", "dimension2", "dimension3", null);
        dataManager = new StockDRM(new emcGenericDataSourceUpdate(new POPPlannedPurchaseOrdersDS(), userData), param, userData) {

            @Override
            public void setFieldValueAt(int rowIndex, String columnIndex, Object aValue) {
                super.setFieldValueAt(rowIndex, columnIndex, aValue);
                if (getLastSetFieldValueStatus() && columnIndex.equals("quantityRequired")) {
                    super.setFieldValueAt(rowIndex, "quantityToRelease", aValue);
                }
            }
        };
        dataManager.setTheForm(this);
        this.setDataManager(dataManager);
        dataManager.setFormTextId1("plannedPOId");
        dataManager.setFormTextId2("itemReference");
        initFrame();
    }

    private void initFrame() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Overview", tablePane());
        tabbed.add("Detail", detailPane());
        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(tabbed, BorderLayout.CENTER);
        contentPane.add(buttonPane(), BorderLayout.EAST);
        this.setContentPane(contentPane);
    }

    private emcTablePanelUpdate tablePane() {
        List<String> keys = new ArrayList<String>();
        keys.add("plannedPOId");
        keys.add("itemGroup");
        keys.add("itemReference");
        keys.add("itemDescription");
        keys.add("dimension1");
        keys.add("dimension3");
        keys.add("dimension2");
        keys.add("releaseDate");
        keys.add("dateRequired");
        keys.add("quantityRequired");
        emcTableModelUpdate model = new emcTableModelUpdate(dataManager, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        setupTableLookups(table);
        dataManager.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        dataManager.setTablePanel(tableScroll);
        return tableScroll;
    }

    private void setupTableLookups(emcJTableUpdate table) {
        HashMap<String, Object> lookupMap = EMCItemLookupFactory.createItemAndDimensionLookups(dataManager, "dimension1", "dimension2", "dimension3", userData);
        table.setColumnCellEditor("itemGroup", new EMCGoToMainTableEditor(new EMCStringDocument(), new ItemGroups()));
        table.setLookupToColumn("itemReference", (EMCLookupJTableComponent) lookupMap.get("item"));
        table.setLookupToColumn("dimension1", (EMCLookupJTableComponent) lookupMap.get("dimension1"));
        table.setLookupToColumn("dimension2", (EMCLookupJTableComponent) lookupMap.get("dimension2"));
        table.setLookupToColumn("dimension3", (EMCLookupJTableComponent) lookupMap.get("dimension3"));
        dataManager.addItemLookupRelationManager((ItemLookupRelationManager) lookupMap.get("lrm"));
        table.setColumnEditable("itemDescription", false);
        table.setColumnEditable("itemGroup", false);
    }

    private Component detailPane() {
        EMCLookupFormComponent lkpUOM = new EMCLookupFormComponent(new UnitsOfMeasure(), dataManager, "uom");
        lkpUOM.setPopup(new EMCLookupPopup(new BaseUnitsOfMeasure(), "unit", userData));
        EMCLookupFormComponent lkpVatCode = new EMCLookupFormComponent(new GLVATCode(), dataManager, "vatCode");
        lkpVatCode.setPopup(new EMCLookupPopup(new emc.entity.gl.GLVATCode(), "vatId", userData));
        emcJTextField txtItemPrice = new emcJTextField(new EMCDoubleFormDocument(dataManager, "itemPrice"));
        Component[][] comp = {{new emcJLabel("UOM"), lkpUOM},
            {new emcJLabel("Vat Code"), lkpVatCode},
            {new emcJLabel("Item Price"), txtItemPrice}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Detailed");
    }

    private emcJPanel buttonPane() {
        emcJButton releaseButton = new emcJButton("Release") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                new POPPlannedPurchaseOrderReleaseDialog(dataManager, userData);
            }
        };
        emcJButton firmButton = new emcJButton("Firm") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                String plannedPO = (String) dataManager.getLastFieldValueAt("plannedPOId");
                if (!Functions.checkBlank(plannedPO)) {
                    EMCCommandClass cmd = new EMCCommandClass(ServerPOPMethods.FIRM_PLANNED_PURCHASE_ORDER);
                    List toSend = new ArrayList();
                    toSend.add(plannedPO);
                    toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);
                    if (toSend.size() > 1 && (Boolean) toSend.get(1)) {
                        utilFunctions.logMessage(Level.INFO, "Confirmed Planned Purchase Order.", userData);
                        dataManager.refreshRecord(dataManager.getLastRowAccessed());
                    } else {
                        utilFunctions.logMessage(Level.SEVERE, "Failed to confirm Planned Purchase Order.", userData);
                    }
                }
            }
        };
        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(releaseButton);
        buttonList.add(firmButton);
      
        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }
}
