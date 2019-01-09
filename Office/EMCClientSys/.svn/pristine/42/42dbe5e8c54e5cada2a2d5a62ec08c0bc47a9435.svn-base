/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.debtors.display.allocationimportsetup;

import emc.app.components.EMCMapComboBox;
import emc.app.components.emcJComboBox;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJSplitPane;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.emctable.renderers.EMCCellRenderer;
import emc.app.components.formlookup.controllookup.EMCControlLookupComponent;
import emc.app.components.formlookup.controllookup.EMCControlLookupComponentDRM;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.debtors.allocationimport.DebtorsAllocationImportSetupLines;
import emc.entity.debtors.allocationimport.DebtorsAllocationImportSetupMaster;
import emc.entity.debtors.transactionsettlement.DebtorsTransactionSettlement;
import emc.entity.sop.SOPCustomers;
import emc.enums.debtors.allocationimport.DebtorsAllocationImportSetupConditions;
import emc.framework.EMCUserData;
import emc.menus.sop.menuitems.display.SOPCustomersMenu;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JSplitPane;
import javax.swing.JTable;

/**
 *
 * @author riaan
 */
public class DebtorsAllocationImportSetupForm extends BaseInternalFrame {

    private EMCControlLookupComponentDRM masterDRM;
    private emcDataRelationManagerUpdate linesDRM;
    private EMCControlLookupComponent lkpCustomer;
    private emcJTextField txtCustomerName = new emcJTextField();
    private EMCMapComboBox allocationFieldsCombobox;

    //Map of field names & EMC labels.
    private Map<String, String> columns = new HashMap<String, String>();

    /** Creates a new instance of DebtorsAllocationImportForm. */
    public DebtorsAllocationImportSetupForm(EMCUserData userData) {
        super("Allocation Import Setup", true, true, true, true, userData);
        this.setBounds(20, 20, 650, 400);

        //New Constructor for emcGenericDataSourceUpdate not found. Uncomment when commit.
        this.masterDRM = new EMCControlLookupComponentDRM(new emcGenericDataSourceUpdate(new DebtorsAllocationImportSetupMaster(), userData), userData);
        this.linesDRM = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(new DebtorsAllocationImportSetupLines(), userData), userData);

        this.setDataManager(masterDRM);

        masterDRM.setTheForm(this);
        masterDRM.setFormTextId1("allocationField");
        masterDRM.setFormTextId2("createdBy");
        masterDRM.setLinesTable(linesDRM);
        masterDRM.setHeaderColumnID("recordID");

        linesDRM.setTheForm(this);
        linesDRM.setFormTextId1("spreadsheetMapping");
        linesDRM.setFormTextId2("mapConditionValue");
        linesDRM.setHeaderTable(masterDRM);
        linesDRM.setRelationColumnToHeader("masterID");

        this.initComponents(userData);
        this.setupForm();
    }

    /** Initializes components. */
    private void initComponents(EMCUserData userData) {
        DebtorsTransactionSettlement settlement = new DebtorsTransactionSettlement();
        columns.put("referenceNumber", settlement.getDisplayLabelForField("referenceNumber", userData));
        columns.put("customerOrderNumber", settlement.getDisplayLabelForField("customerOrderNumber", userData));
        columns.put("debitAmountSettled", settlement.getDisplayLabelForField("debitAmountSettled", userData));
        columns.put("discTaken", settlement.getDisplayLabelForField("discTaken", userData));
        columns.put("rebate", settlement.getDisplayLabelForField("rebate", userData));
        columns.put("creditAmountSettled", settlement.getDisplayLabelForField("creditAmountSettled", userData));

        this.allocationFieldsCombobox = new EMCMapComboBox(columns);
        
        this.lkpCustomer = new EMCControlLookupComponent(new SOPCustomersMenu(), masterDRM, "customerId", txtCustomerName, "customerName", DebtorsAllocationImportSetupMaster.class.getName());

        this.lkpCustomer.setPopup(new EMCLookupPopup(new SOPCustomers(), "customerId", userData));

        this.masterDRM.setLookup(lkpCustomer);
    }

    /** Sets up the form. */
    private void setupForm() {
        this.add(createControlLookupPanel(), BorderLayout.NORTH);
        this.add(createSetupTab(), BorderLayout.CENTER);
    }

    /** Creates the control lookup panel. */
    private emcJPanel createControlLookupPanel() {
        emcJPanel pnlTop = new emcJPanel();

        pnlTop.setBorder(javax.swing.BorderFactory.createTitledBorder("Customer"));
        int y = 0;
        GridBagConstraints gbc;
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.FIRST_LINE_START);
        pnlTop.add(new emcJLabel("Customer"), gbc);
        gbc = emcSetGridBagConstraints.changePosition(gbc, 1, y, 0.1);
        pnlTop.add(lkpCustomer, gbc);
        gbc = emcSetGridBagConstraints.changePosition(gbc, 2, y, 0.1);
        pnlTop.add(new emcJLabel("Name"), gbc);
        gbc = emcSetGridBagConstraints.changePosition(gbc, 3, y, 0.1);
        pnlTop.add(txtCustomerName, gbc);

        return pnlTop;
    }

    /** Creates the setup tab. */
    private emcJPanel createSetupTab() {
        emcJPanel setupTab = new emcJPanel(new BorderLayout());

        emcJSplitPane topBottomSplit = new emcJSplitPane(JSplitPane.VERTICAL_SPLIT, createSetupMasterTabs(), createSetupLinesTabs());
        topBottomSplit.setDividerSize(8);
        topBottomSplit.setContinuousLayout(true);
        topBottomSplit.setOneTouchExpandable(false);
        topBottomSplit.setDividerLocation(160);

        setupTab.add(topBottomSplit);

        return setupTab;
    }

    /** Creates setup master tabs. */
    private emcJTabbedPane createSetupMasterTabs() {
        emcJTabbedPane masterTabs = new emcJTabbedPane();

        masterTabs.add("Setup Master", createSetupMasterTab());

        return masterTabs;
    }

    /** Creates setup master tab.*/
    private emcJPanel createSetupMasterTab() {
        emcJPanel panel = new emcJPanel(new GridLayout(1, 1));

        List<String> keys = new ArrayList<String>();
        keys.add("allocationField");

        emcTableModelUpdate model = new emcTableModelUpdate(masterDRM, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);

        table.setComboBoxLookupToColumn("allocationField", allocationFieldsCombobox);
        table.getColumnModel().getColumn(0).setCellRenderer(new ColumnNameRenderer(columns));

        masterDRM.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        masterDRM.setTablePanel(tableScroll);

        panel.add(tableScroll);

        return panel;
    }

    /** Creates setup lines tabs. */
    private emcJTabbedPane createSetupLinesTabs() {
        emcJTabbedPane linesTabs = new emcJTabbedPane();

        linesTabs.add("Setup Lines", createSetupLinesTab());

        return linesTabs;
    }

    /** Creates setup lines tab. */
    private emcJPanel createSetupLinesTab() {
        emcJPanel panel = new emcJPanel(new GridLayout(1, 1));

        List<String> keys = new ArrayList<String>();
        keys.add("spreadsheetMapping");
        keys.add("mapConditionField");
        keys.add("mapCondition");
        keys.add("mapConditionValue");

        emcTableModelUpdate model = new emcTableModelUpdate(linesDRM, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);

        table.setComboBoxLookupToColumn("mapCondition", new emcJComboBox(DebtorsAllocationImportSetupConditions.values()));

        linesDRM.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        linesDRM.setTablePanel(tableScroll);

        panel.add(tableScroll);

        return panel;
    }

    /** Renders EMC labels instead of field names. */
    private class ColumnNameRenderer extends EMCCellRenderer {

        private Map<String, String> rendererMap;

        /** Creates a new instance of ColumnNameRenderer. */
        public ColumnNameRenderer(Map<String, String> rendererMap) {
            this.rendererMap = rendererMap;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            value = (String)this.rendererMap.get((String)value);
            Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            return comp;
        }
    }
}
