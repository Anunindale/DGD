/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.debtors.display.transactions;

import emc.app.components.documents.EMCStringDocument;
import emc.app.components.emcJButton;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcMenuButton;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.editors.EMCGoToMainTableEditor;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.queryswitchbutton.EMCQuerySwitchButton;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.datatypes.EMCDataType;
import emc.entity.debtors.DebtorsCreditNoteMaster;
import emc.entity.debtors.DebtorsCustomerInvoiceMaster;
import emc.entity.debtors.DebtorsTransactions;
import emc.entity.debtors.journals.DebtorsJournalMaster;
import emc.enums.debtors.transactions.DebtorsTransactionRefTypes;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.modules.enumEMCModules;
import emc.forms.debtors.display.transactions.resources.DeallocateButton;
import emc.forms.debtors.display.transactions.resources.DebtorsTransactionsDRM;
import emc.forms.debtors.display.transactions.resources.TransSettlementButton;
import emc.framework.EMCMenuItem;
import emc.framework.EMCUserData;
import emc.menus.debtors.menuitems.display.DebtorsCreditNotes;
import emc.menus.debtors.menuitems.display.DebtorsCustomerInvoices;
import emc.menus.debtors.menuitems.display.DebtorsJournals;
import emc.menus.debtors.menuitems.display.DebtorsTransactionSettlementHistory;
import java.awt.BorderLayout;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;

/**
 *
 * @author wikus
 */
public class DebtorsTransactionsForm extends BaseInternalFrame {

    private DebtorsTransactionsDRM dataManager;
    //Indicates whether allocation buttons should be displayed.  When the form is opened from the
    //enquiry menu, the allocation buttons should not be displayed.
    private boolean allowAllocation = false;

    public DebtorsTransactionsForm(EMCUserData userData) {
        super("Transactions", true, true, true, true, userData);
        this.setBounds(20, 20, 850, 290);

        if (userData.getUserData(2) instanceof Boolean) {
            this.allowAllocation = (Boolean)userData.getUserData(2);
        }

        dataManager = new DebtorsTransactionsDRM(new emcGenericDataSourceUpdate(enumEMCModules.DEBTORS.getId(), new DebtorsTransactions(), userData), this.allowAllocation, userData);
        this.setDataManager(dataManager);
        dataManager.setTheForm(this);
        dataManager.setFormTextId1("transactionDate");
        dataManager.setFormTextId2("description");
        initFrame();
    }

    private void initFrame() {
        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Overview", tablePane());
        contentPane.add(tabbed, BorderLayout.CENTER);
        contentPane.add(buttonPane(), BorderLayout.EAST);
        this.setContentPane(contentPane);
    }

    private emcTablePanelUpdate tablePane() {
        List<String> keys = new ArrayList<String>();
        keys.add("referenceNumber");
        keys.add("referenceType");
        keys.add("transactionDate");
        keys.add("customerId");
        keys.add("customerOrderNumber");
        keys.add("description");
        keys.add("debit");
        keys.add("credit");
        keys.add("balance");

        emcTableModelUpdate model = new emcTableModelUpdate(dataManager, keys);
        emcJTableUpdate table = new emcJTableUpdate(model) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        EMCGoToMainTableEditor mainTableEditor = new EMCGoToMainTableEditor(new EMCStringDocument(), null) {

            @Override
            public Component getTableCellEditorComponent(JTable arg0, Object arg1, boolean arg2, int arg3, int arg4) {
                DebtorsTransactionRefTypes type = DebtorsTransactionRefTypes.fromString(dataManager.getLastFieldValueAt("referenceType").toString());
                EMCDataType dt = dataManager.getDataType("referenceNumber");
                switch (type) {
                    case CREDIT_NOTE:
                        this.changeMenuItem(new DebtorsCreditNotes());
                        dt.setRelatedTable(DebtorsCreditNoteMaster.class.getName());
                        dt.setRelatedField("invCNNumber");
                        break;
                    case INTEREST:
                        break;
                    case INVOICE:
                        this.changeMenuItem(new DebtorsCustomerInvoices());
                        dt.setRelatedTable(DebtorsCustomerInvoiceMaster.class.getName());
                        dt.setRelatedField("invCNNumber");
                        break;
                    case DEBIT_JOURNAL:
                    //Fall through
                    case CREDIT_JOURNAL:
                    //Fall through
                    case PAYMENT:
                        this.changeMenuItem(new DebtorsJournals());
                        dt.setRelatedTable(DebtorsJournalMaster.class.getName());
                        dt.setRelatedField("journalNumber");
                        break;
                    case RETURNED_PAYMENT:
                        break;
                    case SETTLEMENT_DISC:
                        //This table is no longer being used.
                        //this.changeMenuItem(new DebtorsSettlementDiscHistory());
                        //dt.setRelatedTable(null/*emc.entity.debtors.transactionsettlement.DebtorsSettlementDiscHistory.class.getName()*/);
                        //dt.setRelatedField(null/*"discountTransactionRef"*/);
                        break;
                    case TRANSFER:
                        break;
                }
                return super.getTableCellEditorComponent(arg0, arg1, arg2, arg3, arg4);
            }
        };
        table.setColumnCellEditor("referenceNumber", mainTableEditor);
        dataManager.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        dataManager.setTablePanel(tableScroll);
        return tableScroll;
    }

    private emcJPanel buttonPane() {
        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        //buttonList.add(new DebtorsBalanceButton(this));

        if (this.allowAllocation) {
            buttonList.add(new TransSettlementButton(this, dataManager));
            buttonList.add(new DeallocateButton(this, dataManager));
        }

        EMCMenuItem histMenu = new DebtorsTransactionSettlementHistory();
        histMenu.setDoNotOpenForm(false);
        buttonList.add(new emcMenuButton("Alloc. Hist.", histMenu, this, 1, false));

        EMCQuerySwitchButton switchButton = new EMCQuerySwitchButton("Open Trans", dataManager);
        switchButton.addQuery("Open Trans", DebtorsTransactions.class.getName(), "balance", EMCQueryConditions.NOT, 0);
        //Ignore check on All Transactions view.
        switchButton.addQuery("All Trans", DebtorsTransactions.class.getName(), "balance", null, null);

        buttonList.add(switchButton);

        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }
}
