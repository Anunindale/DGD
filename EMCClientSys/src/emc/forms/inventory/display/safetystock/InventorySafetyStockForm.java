/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.safetystock;

import emc.app.components.emcJButton;
import emc.app.components.emcJComboBox;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.emctable.stock.StockDRM;
import emc.app.components.emctable.stock.StockDRMParameters;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.factory.EMCItemLookupFactory;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.util.utilFunctions;
import emc.entity.inventory.datasource.InventorySafetyStockDS;
import emc.entity.sop.SOPCustomers;
import emc.enums.inventory.safetystock.SafetyStockCustomerType;
import emc.enums.inventory.safetystock.SafetyStockItemType;
import emc.forms.inventory.display.safetystock.resources.InventorySafetyStockGenerationDialog;
import emc.framework.EMCUserData;
import emc.menus.sop.menuitems.display.SOPCustomersMenu;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author wikus
 */
public class InventorySafetyStockForm extends BaseInternalFrame {

    private StockDRM dataManager;
    private EMCUserData userData;

    public InventorySafetyStockForm(EMCUserData userData) {
        super("Generate Safety Stock", true, true, true, true, userData);
        this.setBounds(20, 20, 1000, 350);
        this.userData = userData.copyUserDataAndDataList();
        StockDRMParameters param = new StockDRMParameters("itemId", "dimension1", "dimension2", "dimension3", null, "serialNo", null, null, null);
        dataManager = new StockDRM(new emcGenericDataSourceUpdate(new InventorySafetyStockDS(), userData), param, userData);
        dataManager.setTheForm(this);
        this.setDataManager(dataManager);
        dataManager.setFormTextId1("customerType");
        dataManager.setFormTextId2("itemType");

        initFrame();
    }

    private void initFrame() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Safety Stock", safetyStockPane());

        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(tabbed, BorderLayout.CENTER);
        contentPane.add(buttonPane(), BorderLayout.EAST);

        this.setContentPane(contentPane);
    }

    private emcTablePanelUpdate safetyStockPane() {
        List<String> keys = new ArrayList<String>();
        keys.add("customerType");
        keys.add("customerId");
        keys.add("customerName");
        keys.add("itemType");
        keys.add("itemReference");
        keys.add("itemDescription");
        keys.add("dimension1");
        keys.add("dimension3");
        keys.add("dimension2");
        keys.add("serialNo");
        keys.add("fromDate");
        keys.add("toDate");
        keys.add("quantity");
        emcTableModelUpdate model = new emcTableModelUpdate(dataManager, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        setupTableLoookups(table);
        dataManager.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        dataManager.setTablePanel(tableScroll);
        return tableScroll;
    }

    private void setupTableLoookups(emcJTableUpdate table) {
        table.setComboBoxLookupToColumn("customerType", new emcJComboBox(SafetyStockCustomerType.values()));

        EMCLookupJTableComponent lkpCustomer = new EMCLookupJTableComponent(new SOPCustomersMenu());
        lkpCustomer.setPopup(new EMCLookupPopup(new SOPCustomers(), "customerId", userData));
        table.setLookupToColumn("customerId", lkpCustomer);

        table.setComboBoxLookupToColumn("itemType", new emcJComboBox(SafetyStockItemType.values()));

        HashMap<String, Object> itemDimLkpMap = EMCItemLookupFactory.createItemAndDimensionLookups(dataManager, "dimension1", "dimension2", "dimension3", userData);
        table.setLookupToColumn("itemReference", (EMCLookupJTableComponent) itemDimLkpMap.get("item"));
        table.setLookupToColumn("dimension1", (EMCLookupJTableComponent) itemDimLkpMap.get("dimension1"));
        table.setLookupToColumn("dimension2", (EMCLookupJTableComponent) itemDimLkpMap.get("dimension2"));
        table.setLookupToColumn("dimension3", (EMCLookupJTableComponent) itemDimLkpMap.get("dimension3"));
    }

    private emcJPanel buttonPane() {
        emcJButton btnGenerate = new emcJButton("Generate") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                new InventorySafetyStockGenerationDialog(utilFunctions.findEMCDesktop(this), dataManager, false, userData);
            }
        };
        emcJButton btnDelete = new emcJButton("Delete") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                new InventorySafetyStockGenerationDialog(utilFunctions.findEMCDesktop(this), dataManager, true, userData);
            }
        };
        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(btnGenerate);
        buttonList.add(btnDelete);
        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }
}
