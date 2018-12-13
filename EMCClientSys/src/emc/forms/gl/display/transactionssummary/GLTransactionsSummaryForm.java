package emc.forms.gl.display.transactionssummary;

import emc.app.components.documents.EMCBigDecimalFormDocument;
import emc.app.components.documents.EMCStringDocument;
import emc.app.components.documents.EMCStringFormDocument;
import emc.app.components.emcJButton;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextArea;
import emc.app.components.emcJTextField;
import emc.app.components.emcMenuButton;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.editors.EMCGoToMainTableEditor;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.gl.transactions.GLTransactionsSummary;
import emc.enums.modules.enumEMCModules;
import emc.forms.gl.display.transactionssummary.resources.GLTransactionsSummaryDRM;
import emc.framework.EMCUserData;
import emc.menus.gl.menuitems.display.GLChartOfAccountsMenu;
import emc.menus.gl.menuitems.display.GLTransactionsDetailMI;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

/** 
 *
 * @author claudette
 */
public class GLTransactionsSummaryForm extends BaseInternalFrame {

    private GLTransactionsSummaryDRM drm;

    /** Creates a new instance of GLTransactionsSummaryForm. */
    public GLTransactionsSummaryForm(EMCUserData userData) {
        super("Transaction Summary", true, true, true, true, userData);
        this.setBounds(20, 20, 950, 290);
        drm = new GLTransactionsSummaryDRM(new emcGenericDataSourceUpdate(enumEMCModules.GENERAL_LEDGER.getId(), new GLTransactionsSummary(), userData), userData);
        drm.setTheForm(this);
        this.setDataManager(drm);
        drm.setFormTextId1("groupGranularity");
        drm.setFormTextId2("groupDate");
        this.initFrame();
    }

    /** Initializes the frame. */
    private void initFrame() {
        emcJTabbedPane tabs = new emcJTabbedPane();
        tabs.add("Overview", createOverviewTab());
        tabs.add("Detail", detailTab());
        tabs.add("Analysis", analysisTab());
        this.add(tabs, BorderLayout.CENTER);
        this.add(createButtonsPanel(), BorderLayout.EAST);
    }

    /** Creates the Overview Tab. */
    private emcJPanel createOverviewTab() {
        emcJPanel panel = new emcJPanel(new GridLayout(1, 1));
        List<String> keys = new ArrayList<String>();
        keys.add("groupGranularity");
        keys.add("groupDate");
        keys.add("groupWeek");
        keys.add("groupPeriod");
        keys.add("accountNumber");
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

        drm.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        drm.setMainTableComponent(table);
        drm.setTablePanel(tableScroll);
        panel.add(tableScroll);
        return panel;
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

    /** Creates the buttons panel. */
    private emcJPanel createButtonsPanel() {
        List<emcJButton> buttons = new ArrayList<emcJButton>();

        buttons.add(new emcMenuButton("Detail", new GLTransactionsDetailMI(), this, 0, false));

        return emcSetGridBagConstraints.createButtonPanel(buttons);
    }
}