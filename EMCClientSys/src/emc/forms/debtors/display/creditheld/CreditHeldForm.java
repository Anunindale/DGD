/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.debtors.display.creditheld;

import emc.app.components.documents.EMCStringDocument;
import emc.app.components.documents.EMCStringFormDocument;
import emc.app.components.emcJButton;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJSplitPane;
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
import emc.app.components.queryswitchbutton.EMCQuerySwitchButton;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.datatypes.EMCDataType;
import emc.entity.debtors.DebtorsCustomerInvoiceLines;
import emc.entity.debtors.DebtorsCustomerInvoiceMaster;
import emc.entity.debtors.creditheld.DebtorsCreditHeldMaster;
import emc.entity.debtors.datasource.DebtorsCreditHeldMasterDS;
import emc.entity.debtors.datasource.DebtorsCustomerInvoiceLinesDS;
import emc.entity.sop.SOPSalesOrderLines;
import emc.entity.sop.SOPSalesOrderMaster;
import emc.entity.sop.datasource.SOPSalesOrderLinesDS;
import emc.enums.debtors.creditheld.DebtorsCreditHeldRefType;
import emc.enums.debtors.creditheld.DebtorsCreditHeldStatus;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.forms.debtors.display.creditheld.resources.CreditHeldLinesDRM;
import emc.forms.debtors.display.creditheld.resources.CreditHeldMasterApproveButton;
import emc.forms.debtors.display.creditheld.resources.CreditHeldMasterDRM;
import emc.framework.EMCMenuItem;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.menus.debtors.menuitems.display.DebtorsCustomerBalance;
import emc.menus.debtors.menuitems.display.DebtorsCustomerInvoices;
import emc.menus.debtors.menuitems.display.DebtorsTotalCreditHeldMI;
import emc.menus.sop.menuitems.display.SOPCustomersMenu;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JSplitPane;
import javax.swing.JTable;

/**
 * @description : This form is used to approve credit.
 *
 * @date        : 30 Jul 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class CreditHeldForm extends BaseInternalFrame {

    private CreditHeldMasterDRM masterDRM;

    //Store individual lines DRM instance for Sales Order lines & Invoice lines.
    private CreditHeldLinesDRM invoiceLinesDRM;
    private CreditHeldLinesDRM salesOrderLinesDRM;

    //Lines panel containing tables for Sales Order lines & Invoice lines.
    private emcJPanel pnlLinesTable;
    private CardLayout linesCards;

    /** Creates a new instance of CreditHeldForm */
    public CreditHeldForm(EMCUserData userData) {
        super("Credit Held", true, true, true, true, userData);
        this.setBounds(20, 20, 750, 450);

        initMasterDRM(userData);

        initInvoiceLinesDRM(userData.copyUserData());
        initSalesOrderLinesDRM(userData.copyUserData());

        setupForm();

        //Default to Invoice lines
        this.useInvoiceLines();
        //Force refresh
        this.masterDRM.tableRowChanged(0);
        this.masterDRM.doRelation(0);
    }

    /** Creates the master DRM. */
    private void initMasterDRM(EMCUserData userData) {
        masterDRM = new CreditHeldMasterDRM(new emcGenericDataSourceUpdate(enumEMCModules.DEBTORS.getId(), new DebtorsCreditHeldMasterDS(), userData), this, userData);
        masterDRM.setHeaderColumnID("reference");
        masterDRM.setTheForm(this);
        masterDRM.setFormTextId1("reference");
        masterDRM.setFormTextId2("referenceType");

        this.setDataManager(masterDRM);
    }

    /** Creates the Invoice lines DRM. */
    private void initInvoiceLinesDRM(EMCUserData userData) {
        //Initially, select nothing
        userData = userData.copyUserData();
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsCustomerInvoiceLines.class);
        query.addAnd("invCNNumber", "");

        userData.setUserData(0, query);

        this.invoiceLinesDRM = new CreditHeldLinesDRM(new emcGenericDataSourceUpdate(enumEMCModules.DEBTORS.getId(), new DebtorsCustomerInvoiceLinesDS(), userData), userData);

        invoiceLinesDRM.setTheForm(this);
        invoiceLinesDRM.setFormTextId1("invCNNumber");
        invoiceLinesDRM.setFormTextId2("itemReference");
        invoiceLinesDRM.setHeaderTable(masterDRM);
        invoiceLinesDRM.setRelationColumnToHeader("invCNNumber");
    }

    /** Creates the Invoice lines DRM. */
    private void initSalesOrderLinesDRM(EMCUserData userData) {
        //Initially, select nothing
        userData = userData.copyUserData();
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, SOPSalesOrderLines.class);
        query.addAnd("salesOrderNo", "");

        userData.setUserData(0, query);

        this.salesOrderLinesDRM = new CreditHeldLinesDRM(new emcGenericDataSourceUpdate(enumEMCModules.SOP.getId(), new SOPSalesOrderLinesDS(), userData), userData);

        salesOrderLinesDRM.setTheForm(this);
        salesOrderLinesDRM.setFormTextId1("salesOrderNo");
        salesOrderLinesDRM.setFormTextId2("itemReference");
        salesOrderLinesDRM.setHeaderTable(masterDRM);
        salesOrderLinesDRM.setRelationColumnToHeader("salesOrderNo");
    }

    /** Sets up the form. */
    private void setupForm() {
        emcJSplitPane topBottomSplit = new emcJSplitPane(JSplitPane.VERTICAL_SPLIT, setupMaster(), setupLines());
        topBottomSplit.setDividerSize(8);
        topBottomSplit.setContinuousLayout(true);
        topBottomSplit.setOneTouchExpandable(false);
        topBottomSplit.setDividerLocation(170);

        this.add(topBottomSplit, BorderLayout.CENTER);
    }

    /** Sets up the master section of the form. .*/
    private emcJPanel setupMaster() {
        emcJPanel pnlMaster = new emcJPanel(new BorderLayout());

        emcJTabbedPane masterTabs = new emcJTabbedPane();

        masterTabs.add("Credit Held", createMasterCreditHeldTab());
        masterTabs.add("Approval", createApprovalTab());

        pnlMaster.add(masterTabs, BorderLayout.CENTER);
        pnlMaster.add(createMasterButtons(), BorderLayout.EAST);

        return pnlMaster;
    }

    /** Creates the master Credit Held tab. */
    private emcJPanel createMasterCreditHeldTab() {
        emcJPanel pnlCreditHeld = new emcJPanel();

        List keys = new ArrayList();
        keys.add("reference");
        keys.add("referenceType");
        keys.add("customerId");
        keys.add("customerName");
        keys.add("rep");
        keys.add("creditHeldStatus");
        keys.add("creditHeldReason");
        keys.add("totalCreditHeld");
        keys.add("heldDate");

        emcTableModelUpdate m = new emcTableModelUpdate(masterDRM, keys);
        emcJTableUpdate tblMaster = new emcJTableUpdate(m) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        setMasterCellEditors(tblMaster);
        masterDRM.setMainTableComponent(tblMaster);

        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(tblMaster);
        pnlCreditHeld.setLayout(new GridLayout(1, 1));
        pnlCreditHeld.add(topscroll);

        masterDRM.setTablePanel(topscroll);

        return pnlCreditHeld;
    }

    /** Sets editors for go to main table support. */
    private void setMasterCellEditors(emcJTableUpdate tblMaster) {
        tblMaster.setColumnCellEditor("customerId", new EMCGoToMainTableEditor(new EMCStringDocument(), new SOPCustomersMenu()));

        EMCGoToMainTableEditor mainTableEditor = new EMCGoToMainTableEditor(new EMCStringDocument(), null) {

            @Override
            public Component getTableCellEditorComponent(JTable arg0, Object arg1, boolean arg2, int arg3, int arg4) {
                DebtorsCreditHeldRefType type = DebtorsCreditHeldRefType.fromString(masterDRM.getLastFieldValueAt("referenceType").toString());
                EMCDataType dt = masterDRM.getDataType("reference");
                switch (type) {
                  case INVOICE:
                        this.changeMenuItem(new DebtorsCustomerInvoices());
                        dt.setRelatedTable(DebtorsCustomerInvoiceMaster.class.getName());
                        dt.setRelatedField("invCNNumber");
                        break;
                }
                return super.getTableCellEditorComponent(arg0, arg1, arg2, arg3, arg4);
            }
        };
        tblMaster.setColumnCellEditor("reference", mainTableEditor);
    }

    /** Creates the approval tab. */
    private emcJPanel createApprovalTab() {
        Component[][] components = new Component[][]{
            {new emcJLabel(masterDRM.getColumnName("approvedBy")), new emcJTextField(new EMCStringFormDocument(masterDRM, "approvedBy"))},
            {new emcJLabel(masterDRM.getColumnName("approvedDate")), new EMCDatePickerFormComponent(masterDRM, "approvedDate")}
        };

        return emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, true);
    }

    /** Creates master buttons panel. */
    private emcJPanel createMasterButtons() {
        List<emcJButton> buttons = new ArrayList<emcJButton>();

        buttons.add(new CreditHeldMasterApproveButton(masterDRM));

        EMCMenuItem totalHeldMenuItem = new DebtorsTotalCreditHeldMI();
        totalHeldMenuItem.setDoNotOpenForm(false);
        buttons.add(new emcMenuButton("Total Held", totalHeldMenuItem, this, 2, false));

        EMCMenuItem balanceItem = new DebtorsCustomerBalance();
        balanceItem.setDoNotOpenForm(false);
        buttons.add(new emcMenuButton("Balance", balanceItem, this, 1, false));

        EMCQuerySwitchButton querySwitchButton = new EMCQuerySwitchButton("Open", masterDRM);
        querySwitchButton.addQuery("Open", DebtorsCreditHeldMaster.class.getName(), "creditHeldStatus", EMCQueryConditions.EQUALS, DebtorsCreditHeldStatus.HELD.toString());
        querySwitchButton.addQuery("All", DebtorsCreditHeldMaster.class.getName(), "creditHeldStatus", null, null);

        buttons.add(querySwitchButton);

        return emcSetGridBagConstraints.createButtonPanel(buttons);
    }

    /** Creates lines buttons panel. */
    private emcJPanel createLinesButtons() {
        List<emcJButton> buttons = new ArrayList<emcJButton>();

        return emcSetGridBagConstraints.createButtonPanel(buttons);
    }

    /** Sets up the lines section of the form. */
    private emcJPanel setupLines() {
        emcJPanel pnlLines = new emcJPanel(new BorderLayout());

        linesCards = new CardLayout();
        pnlLinesTable = new emcJPanel(linesCards);
        pnlLinesTable.add(createInvoiceLinesCreditHeldTab(), "Invoice");
        pnlLinesTable.add(createSalesOrderLinesCreditHeldTab(), "SO");

        emcJTabbedPane linesTabs = new emcJTabbedPane();

        linesTabs.add("Credit Held Lines", pnlLinesTable);

        pnlLines.add(linesTabs, BorderLayout.CENTER);
        pnlLines.add(createLinesButtons(), BorderLayout.EAST);

        return pnlLines;
    }

    /** Creates the Invoice lines Credit Held tab. */
    private emcJPanel createInvoiceLinesCreditHeldTab() {
        emcJPanel pnlCreditHeld = new emcJPanel();

        List keys = new ArrayList();
        keys.add("itemReference");
        keys.add("dimension1");
        keys.add("dimension3");
        keys.add("dimension2");
        keys.add("quantity");
        keys.add("totalHeld");
        keys.add("vatIncluded");
        //keys.add("creditHeldReason");
        //keys.add("creditHeldStatus");

        emcTableModelUpdate linesTableModel = new emcTableModelUpdate(invoiceLinesDRM, keys);

        emcJTableUpdate tblLines = new emcJTableUpdate(linesTableModel) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        invoiceLinesDRM.setMainTableComponent(tblLines);

        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(tblLines);

        pnlCreditHeld.setLayout(new GridLayout(1, 1));
        pnlCreditHeld.add(topscroll);

        invoiceLinesDRM.setTablePanel(topscroll);

        return pnlCreditHeld;
    }

    /** Creates the Sales Order lines Credit Held tab. */
    private emcJPanel createSalesOrderLinesCreditHeldTab() {
        emcJPanel pnlCreditHeld = new emcJPanel();

        List keys = new ArrayList();
        keys.add("itemReference");
        keys.add("dimension1");
        keys.add("dimension3");
        keys.add("dimension2");
        keys.add("quantity");
        keys.add("totalHeld");
        keys.add("vatIncluded");
        //keys.add("creditHeldReason");
        //keys.add("creditHeldStatus");

        emcTableModelUpdate linesTableModel = new emcTableModelUpdate(salesOrderLinesDRM, keys);

        emcJTableUpdate tblLines = new emcJTableUpdate(linesTableModel) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        salesOrderLinesDRM.setMainTableComponent(tblLines);

        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(tblLines);

        pnlCreditHeld.setLayout(new GridLayout(1, 1));
        pnlCreditHeld.add(topscroll);

        salesOrderLinesDRM.setTablePanel(topscroll);

        return pnlCreditHeld;
    }

    /** This method should be called whenever the selected row on the master is changed. */
    public void masterRowChanged(String reference, DebtorsCreditHeldRefType referenceType, EMCUserData userData) {
        if (linesCards == null) {
            //Called while constructing form.
            return;
        }

        if (DebtorsCreditHeldRefType.INVOICE.equals(referenceType)) {
            useInvoiceLines();
        } else if (DebtorsCreditHeldRefType.SALES_ORDER.equals(referenceType)) {
            useSalesOrderLines();
        }
    }

    /** Display Invoice lines. */
    private void useInvoiceLines() {
        masterDRM.setLinesTable(invoiceLinesDRM);
        invoiceLinesDRM.setHeaderTable(masterDRM);

        this.linesCards.show(pnlLinesTable, "Invoice");
    }

    /** Display Sales Order lines. */
    private void useSalesOrderLines() {
        masterDRM.setLinesTable(salesOrderLinesDRM);
        salesOrderLinesDRM.setHeaderTable(masterDRM);

        this.linesCards.show(pnlLinesTable, "SO");
    }
}
