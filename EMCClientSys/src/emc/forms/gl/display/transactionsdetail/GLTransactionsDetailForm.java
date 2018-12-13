package emc.forms.gl.display.transactionsdetail;

import emc.app.components.documents.EMCBigDecimalFormDocument;
import emc.app.components.documents.EMCStringDocument;
import emc.app.components.documents.EMCStringFormDocument;
import emc.app.components.emcJButton;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextArea;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.editors.EMCGoToMainTableEditor;
import emc.app.components.emctable.emcDRMViewOnly;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.queryswitchbutton.EMCQuerySwitchButton;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.datatypes.EMCDataType;
import emc.entity.debtors.DebtorsCreditNoteMaster;
import emc.entity.debtors.DebtorsCustomerInvoiceMaster;
import emc.entity.gl.journals.GLJournalMaster;
import emc.entity.gl.transactions.GLTransactionsDetail;
import emc.entity.inventory.picking.InventoryPickingListMaster;
import emc.enums.enumQueryTypes;
import emc.enums.gl.TransactionSourceTypes;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.menus.debtors.menuitems.output.DebtorsCreditNoteMI;
import emc.menus.debtors.menuitems.output.DebtorsCustomerInvoiceMenu;
import emc.menus.gl.menuitems.display.GLChartOfAccountsMenu;
import emc.menus.gl.menuitems.display.GLJournalsMI;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;

/** 
 *
 * @author claudette
 */
public class GLTransactionsDetailForm extends BaseInternalFrame {

    private emcDRMViewOnly drm;

    /** Creates a new instance of GLTransactionsDetailForm. */
    public GLTransactionsDetailForm(EMCUserData userData) {
        super("Transaction Detail", true, true, true, true, userData);
        this.setBounds(20, 20, 950, 290);
        drm = new emcDRMViewOnly(new emcGenericDataSourceUpdate(new GLTransactionsDetail(), userData), userData);
        drm.setTheForm(this);
        this.setDataManager(drm);
        drm.setFormTextId1("transactionDate");
        drm.setFormTextId2("accountNumber");
        this.initFrame();
    }

    /** Initializes the frame. */
    private void initFrame() {
        this.setLayout(new BorderLayout());
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Overview", overviewTab());
        tabbed.add("Detail", detailTab());
        tabbed.add("Analysis", analysisTab());
        this.add(tabbed, BorderLayout.CENTER);
        this.add(createButtonsPanel(), BorderLayout.EAST);
    }

    /** Creates the Overview Tab. */
    private emcTablePanelUpdate overviewTab() {
        List keys = new ArrayList();
        keys.add("transactionDate");
        keys.add("accountNumber");
        keys.add("transactionSource");
        keys.add("sourceReference");
        keys.add("externalReference");
        keys.add("debit");
        keys.add("credit");
        emcTableModelUpdate model = new emcTableModelUpdate(drm, keys);
        emcJTableUpdate table = new emcJTableUpdate(model) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setColumnCellEditor("accountNumber", new EMCGoToMainTableEditor(new EMCStringDocument(), new GLChartOfAccountsMenu()));
        table.setColumnCellEditor("sourceReference", new EMCGoToMainTableEditor(new EMCStringDocument(), null) {

            @Override
            public Component getTableCellEditorComponent(JTable arg0, Object arg1, boolean arg2, int arg3, int arg4) {
                TransactionSourceTypes type = TransactionSourceTypes.fromString(drm.getFieldValueAt(drm.getLastRowAccessed(), "transactionSource").toString());
                EMCDataType dt = drm.getDataType("sourceReference");
                switch (type) {
                    case GL_JOURNAL:
                        this.changeMenuItem(new GLJournalsMI());
                        dt.setRelatedTable(GLJournalMaster.class.getName());
                        dt.setRelatedField("journalNumber");
                        break;
                    case INVOICE:
                        this.changeMenuItem(new DebtorsCustomerInvoiceMenu());
                        dt.setRelatedTable(DebtorsCustomerInvoiceMaster.class.getName());
                        dt.setRelatedField("invCNNumber");
                        break;
                    case CRN:
                        this.changeMenuItem(new DebtorsCreditNoteMI());
                        dt.setRelatedTable(DebtorsCreditNoteMaster.class.getName());
                        dt.setRelatedField("invCNNumber");
                        break;
                }
                return super.getTableCellEditorComponent(arg0, arg1, arg2, arg3, arg4);
            }
        });

        drm.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        drm.setTablePanel(tableScroll);
        return tableScroll;
    }

    private emcJPanel detailTab() {
        emcJTextField txtTransNum = new emcJTextField(new EMCStringFormDocument(drm, "transactionNumber"));
        emcJTextField txtForeignCurrency = new emcJTextField(new EMCStringFormDocument(drm, "foreignCurrency"));
        emcJTextField txtVATCode = new emcJTextField(new EMCStringFormDocument(drm, "vatCode"));
        emcJTextArea txaText = new emcJTextArea(new EMCStringFormDocument(drm, "text"));
        emcJTextField txtForeignCurrencDebit = new emcJTextField(new EMCBigDecimalFormDocument(drm, "foreignCurrencyDebit"));
        emcJTextField txtForeignCurrencCredit = new emcJTextField(new EMCBigDecimalFormDocument(drm, "foreignCurrencyCredit"));
        Component[][] comp = {{new emcJLabel("Transaction No."), txtTransNum, new emcJLabel("VAT Code"), txtVATCode},
            {new emcJLabel("Foreign Currency"), txtForeignCurrency},
            {new emcJLabel("Foreign Currency Debit"), txtForeignCurrencDebit, new emcJLabel("Foreign Currency Credit"), txtForeignCurrencCredit},
            {txaText, new emcJLabel("Text")}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Detail");
    }

    private emcJPanel analysisTab() {
        emcJTextField txtAnalysis1 = new emcJTextField(new EMCStringFormDocument(drm, "analysisCode1"));
        emcJTextField txtAnalysis2 = new emcJTextField(new EMCStringFormDocument(drm, "analysisCode2"));
        emcJTextField txtAnalysis3 = new emcJTextField(new EMCStringFormDocument(drm, "analysisCode3"));
        emcJTextField txtAnalysis4 = new emcJTextField(new EMCStringFormDocument(drm, "analysisCode4"));
        emcJTextField txtAnalysis5 = new emcJTextField(new EMCStringFormDocument(drm, "analysisCode5"));
        emcJTextField txtAnalysis6 = new emcJTextField(new EMCStringFormDocument(drm, "analysisCode6"));
        Component[][] comp = {{new emcJLabel("Analysis Code 1"), txtAnalysis1},
            {new emcJLabel("Analysis Code 2"), txtAnalysis2},
            {new emcJLabel("Analysis Code 3"), txtAnalysis3},
            {new emcJLabel("Analysis Code 4"), txtAnalysis4},
            {new emcJLabel("Analysis Code 5"), txtAnalysis5},
            {new emcJLabel("Analysis Code 6"), txtAnalysis6}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Analysis");
    }

    /** Creates buttons panel. */
    private emcJPanel createButtonsPanel() {
        List<emcJButton> buttons = new ArrayList<emcJButton>();

        //Set up queries for Query Switch Button
        EMCQuerySwitchButton querySwitchButton = new EMCQuerySwitchButton("View Transactions", drm);

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, GLTransactionsDetail.class);
        query.addAnd("processed", false);
        query.addAnd("companyId", getUserData().getCompanyId());
        query.addOrderBy("transactionDate");

        querySwitchButton.addQuery("Open Transactions", query);

        query = new EMCQuery(enumQueryTypes.SELECT, GLTransactionsDetail.class);
        query.addAnd("companyId", getUserData().getCompanyId());
        query.addOrderBy("transactionDate");

        querySwitchButton.addQuery("All Transactions", query);

        buttons.add(querySwitchButton);

        return emcSetGridBagConstraints.createButtonPanel(buttons);
    }
}