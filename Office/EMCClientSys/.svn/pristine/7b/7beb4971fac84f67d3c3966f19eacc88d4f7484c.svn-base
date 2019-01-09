/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.debtors.display.transactionsettlementhistory;

import emc.app.components.documents.EMCStringDocument;
import emc.app.components.emcJButton;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.editors.EMCGoToMainTableEditor;
import emc.app.components.emctable.emcDRMViewOnly;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.datatypes.EMCDataType;
import emc.entity.debtors.DebtorsTransactions;
import emc.entity.debtors.datasource.DebtorsTransactionSettlementHistoryDS;
import emc.enums.modules.enumEMCModules;
import emc.forms.debtors.display.transactionsettlementhistory.resources.DeallocateButton;
import emc.framework.EMCUserData;
import emc.menus.debtors.menuitems.display.DebtorsTransactionsMenu;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author riaan
 */
public class TransactionSettlementHistoryForm extends BaseInternalFrame {

    private emcDRMViewOnly drm;

    //JTable columns are update dynamically, based on value in user data.
    private emcTableModelUpdate tableModel;
    private emcJTableUpdate table;

    //Indicates whether deallocation should be allowed.
    private boolean allowDeallocate;

    /** Creates a new instance of TransactionSettlementHistoryForm */
    public TransactionSettlementHistoryForm(EMCUserData userData) {
        super("Allocation History", true, true, true, true, userData);
        this.setBounds(20, 20, 750, 400);

        if (userData.getUserData(3) instanceof Boolean) {
            this.allowDeallocate = (Boolean) userData.getUserData(3);
        }

        drm = new emcDRMViewOnly(new emcGenericDataSourceUpdate(enumEMCModules.DEBTORS.getId(), new DebtorsTransactionSettlementHistoryDS(), userData), userData);

        this.setDataManager(drm);

        drm.setTheForm(this);
        drm.setFormTextId1("customerName");
        drm.setFormTextId2("debitSettled");

        setupFrame();
        if (userData.getUserData(2) instanceof Boolean) {
            updateColumns(userData);
        }
    }

    @Override
    public void populateUserDataForPermissions(EMCUserData userData) {
        super.populateUserDataForPermissions(userData);

    }

    /** Sets up the frame. */
    private void setupFrame() {
        emcJTabbedPane tabs = createTabs();
        tabs.setRelationManager(drm);
        emcJPanel pnlTransactionsSummary = new emcJPanel();
        pnlTransactionsSummary.setLayout(new BorderLayout());
        pnlTransactionsSummary.add(tabs, BorderLayout.CENTER);

        pnlTransactionsSummary.add(createButtonsPanel(), BorderLayout.EAST);

        this.setLayout(new GridLayout(1, 1));

        this.add(pnlTransactionsSummary);
    }

    /** Creates the tabbed pane containing the components on the form. */
    private emcJTabbedPane createTabs() {
        emcJTabbedPane tabs = new emcJTabbedPane();

        tabs.add("Allocation History", createSummaryTab());

        return tabs;
    }

    /** Creates the summary tab. */
    private emcJPanel createSummaryTab() {
        emcJPanel pnlOverview = new emcJPanel();

        List keys = new ArrayList();
        keys.add("customerId");

        this.tableModel = new emcTableModelUpdate(drm, keys);
        table = new emcJTableUpdate(this.tableModel) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        drm.setMainTableComponent(table);
        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(table);
        pnlOverview.setLayout(new GridLayout(1, 1));
        pnlOverview.add(topscroll);
        drm.setTablePanel(topscroll);

        return pnlOverview;
    }

    /** Creates buttons panel. */
    private emcJPanel createButtonsPanel() {
        List<emcJButton> buttons = new ArrayList<emcJButton>();

        if (this.allowDeallocate) {
            buttons.add(new DeallocateButton(this, drm));
        }

        return emcSetGridBagConstraints.createButtonPanel(buttons);
    }

    @Override
    public void setUserData(EMCUserData userData) {
        super.setUserData(userData);
        this.updateColumns(userData);
    }

    /** Updates columns displayed on table. */
    private void updateColumns(EMCUserData userData) {
        List<String> columns = new ArrayList<String>();

        columns.add("customerId");
        columns.add("createdDate");
        
        if (userData.getUserData(2) instanceof Boolean) {
            if ((Boolean) userData.getUserData(2)) {
                //Show debit
                columns.add("debitTransactionRef");
                columns.add("debitTransactionRefType");
                columns.add("debitSettled");
            } else {
                //Show credit
                columns.add("creditTransactionRef");
                columns.add("creditTransactionRefType");
                columns.add("creditSettled");
            }
        }

        

        this.tableModel.setKeys(columns);
        this.tableModel.fireTableStructureChanged();

        EMCGoToMainTableEditor mainTableEditor = new EMCGoToMainTableEditor(new EMCStringDocument(), null) {

            {
                EMCDataType dt = tableModel.getKeys().contains("debitTransactionRef") ? drm.getDataType("debitTransactionRef") : drm.getDataType("creditTransactionRef");
                dt.setRelatedField("referenceNumber");
                dt.setRelatedTable(DebtorsTransactions.class.getName());
                this.changeMenuItem(new DebtorsTransactionsMenu());
            }
        };

        if (userData.getUserData(2) instanceof Boolean && (Boolean) userData.getUserData(2)) {
            table.setColumnCellEditor("debitTransactionRef", mainTableEditor);
        } else {
            table.setColumnCellEditor("creditTransactionRef", mainTableEditor);
        }
    }
}
