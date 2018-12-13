/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.gl.display.journal;

import emc.app.components.EMCFormComboBox;
import emc.app.components.documents.EMCStringFormDocument;
import emc.app.components.emcJButton;
import emc.app.components.emcJComboBox;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJSplitPane;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextArea;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emcpicker.emcdatepicker.EMCDatePickerFormComponent;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.emctable.emcYesNoComponent;
import emc.app.components.formlookup.EMCLookupFormComponent;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.components.queryswitchbutton.EMCQuerySwitchButton;
import emc.app.reporttools.JasperInformation;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.wsmanager.EMCReportWSManager;
import emc.app.wsmanager.EMCWSManager;
import emc.commands.EMCCommands;
import emc.entity.base.journals.BaseJournalDefinitionTable;
import emc.entity.gl.GLChartOfAccounts;
import emc.entity.gl.analysiscodes.GLAnalysisCode1;
import emc.entity.gl.analysiscodes.GLAnalysisCode2;
import emc.entity.gl.analysiscodes.GLAnalysisCode3;
import emc.entity.gl.analysiscodes.GLAnalysisCode4;
import emc.entity.gl.analysiscodes.GLAnalysisCode5;
import emc.entity.gl.analysiscodes.GLAnalysisCode6;
import emc.entity.gl.datasource.GLJournalLinesDS;
import emc.entity.gl.journals.GLJournalLines;
import emc.entity.gl.journals.GLJournalMaster;
import emc.enums.base.journals.JournalStatus;
import emc.enums.base.journals.Modules;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.enums.gl.journals.JournalLineType;
import emc.enums.gl.POSTDetailSummary;
import emc.enums.gl.VATInputOutput;
import emc.enums.inventory.journals.ContraTypes;
import emc.enums.modules.enumEMCModules;
import emc.forms.gl.display.journal.resources.ApproveButton;
import emc.forms.gl.display.journal.resources.GLJournalLinesDRM;
import emc.forms.gl.display.journal.resources.GLJournalMasterDRM;
import emc.forms.gl.display.journal.resources.PostButton;
import emc.forms.gl.display.journal.resources.TotalsButton;
import emc.forms.gl.display.journal.resources.ValidateButton;
import emc.framework.EMCCommandClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.menus.gl.menuitems.display.GLAnalysisCode1Menu;
import emc.menus.gl.menuitems.display.GLAnalysisCode2Menu;
import emc.menus.gl.menuitems.display.GLAnalysisCode3Menu;
import emc.menus.gl.menuitems.display.GLAnalysisCode4Menu;
import emc.menus.gl.menuitems.display.GLAnalysisCode5Menu;
import emc.menus.gl.menuitems.display.GLAnalysisCode6Menu;
import emc.menus.gl.menuitems.display.GLChartOfAccountsMenu;
import emc.menus.gl.menuitems.display.GLJournalDefinitions;
import emc.menus.gl.menuitems.display.GLVATCode;
import emc.methods.base.ServerBaseMethods;
import emc.methods.gl.ServerGLMethods;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JSplitPane;

/**
 *
 * @author wikus
 */
public class GLJournalsForm extends BaseInternalFrame {

    private GLJournalMasterDRM masterDRM;
    private EMCUserData masterUD;
    private GLJournalLinesDRM linesDRM;
    private EMCUserData linesUD;
    private EMCLookupJTableComponent lkpJournalDefinition;
    private List journalAccessDefs = new ArrayList();

    public GLJournalsForm(EMCUserData userData) {
        super("Journals", true, true, true, true, userData);
        this.setBounds(20, 20, 950, 550);

        EMCCommandClass cmd = new EMCCommandClass(ServerBaseMethods.GET_ACCESS_DEFS);
        journalAccessDefs = EMCWSManager.executeGenericWS(cmd, journalAccessDefs, userData);
        journalAccessDefs = (List) journalAccessDefs.get(1);
        if (journalAccessDefs.size() == 0) {
            journalAccessDefs.add("NoneFound");
            Logger.getLogger("emc").log(Level.WARNING, "You do not have access to any journal definitions.", userData);
        }
        EMCQuery query = null;
        if (userData.getUserData(0) instanceof EMCQuery) {
            query = (EMCQuery) userData.getUserData(0);
        } else {
            query = new EMCQuery(enumQueryTypes.SELECT, GLJournalMaster.class);
            //If form is opened from menu, only select non-posted journals.
            query.addAnd("journalStatus", JournalStatus.POSTED, EMCQueryConditions.NOT);
        }
        query.addAnd("companyId", getUserData().getCompanyId());
        query.addAndInList("journalDefinitionId", journalAccessDefs, true, false);
        query.addOrderBy("journalNumber");
        userData.setUserData(0, query);

        //Master
        masterUD = userData.copyUserDataAndDataList();
        masterDRM = new GLJournalMasterDRM(new emcGenericDataSourceUpdate(new GLJournalMaster(), masterUD), masterUD);
        masterDRM.setTheForm(this);
        this.setDataManager(masterDRM);
        masterDRM.setFormTextId1("journalNumber");
        masterDRM.setFormTextId2("journalDescription");
        //Lines
        linesUD = userData.copyUserData();
        linesDRM = new GLJournalLinesDRM(new emcGenericDataSourceUpdate(new GLJournalLinesDS(), linesUD), linesUD);
        linesDRM.setTheForm(this);
        linesDRM.setFormTextId1("lineNumber");
        linesDRM.setFormTextId2("extReference");
        //Link
        masterDRM.setLinesTable(linesDRM);
        masterDRM.setHeaderColumnID("journalNumber");
        linesDRM.setHeaderTable(masterDRM);
        linesDRM.setRelationColumnToHeader("journalNumber");

        initLookups(userData);
        //Form
        initFrame();
    }

    /** Initializes lookups. */
    private void initLookups(EMCUserData userData) {
        EMCCommandClass cmd = new EMCCommandClass(ServerBaseMethods.GET_ACCESS_DEFS);
        List journalAccessDefs = EMCWSManager.executeGenericWS(cmd, new ArrayList(), userData);
        journalAccessDefs = (List) journalAccessDefs.get(1);
        if (journalAccessDefs.size() == 0) {
            journalAccessDefs.add(null);
            Logger.getLogger("emc").log(Level.WARNING, "You do not have access to any journal definitions.", userData);
        }

        lkpJournalDefinition = new EMCLookupJTableComponent(new GLJournalDefinitions());
        EMCLookupPopup journalDefinitionPopup = new EMCLookupPopup(new BaseJournalDefinitionTable(), "journalDefinitionId", userData);
        lkpJournalDefinition.setPopup(journalDefinitionPopup);
        //Only show definitions for correct module
        lkpJournalDefinition.getTheQuery().addAnd("journalModule", Modules.GL.toString());
        lkpJournalDefinition.getTheQuery().addAndInList("journalDefinitionId", journalAccessDefs, true, false);
    }

    private void initFrame() {
        emcJSplitPane topBottomSplit = new emcJSplitPane(JSplitPane.VERTICAL_SPLIT, masterPane(), linesPane());
        topBottomSplit.setDividerLocation(230);
        this.setContentPane(topBottomSplit);
    }

    private emcJPanel masterPane() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Overview", masterOverviewTab());
        tabbed.add("Additional", masterAdditionalTab());
        tabbed.add("Analysis", masterAnalysisTab());
        tabbed.add("Status", masterStatusTab());
        emcJPanel thePanel = new emcJPanel(new BorderLayout());
        thePanel.add(tabbed, BorderLayout.CENTER);
        thePanel.add(createMasterButtonsPanel(), BorderLayout.EAST);
        return thePanel;
    }

    private emcTablePanelUpdate masterOverviewTab() {
        List keys = new ArrayList();
        keys.add("journalDefinitionId");
        keys.add("journalNumber");
        keys.add("journalDescription");
        keys.add("journalDate");
        keys.add("journalStatus");
        emcTableModelUpdate model = new emcTableModelUpdate(masterDRM, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        table.setColumnEditable("journalStatus", false);

        table.setLookupToColumn("journalDefinitionId", lkpJournalDefinition);

        masterDRM.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        masterDRM.setTablePanel(tableScroll);
        return tableScroll;
    }

    private Component masterAdditionalTab() {
        EMCFormComboBox cbContraType = new EMCFormComboBox(ContraTypes.values(), masterDRM, "journalContraType");
        EMCLookupFormComponent lkpContraAccount = new EMCLookupFormComponent(new GLChartOfAccountsMenu(), masterDRM, "journalContraAccount");
        lkpContraAccount.setPopup(new EMCLookupPopup(new GLChartOfAccounts(), "accountNumber", masterUD));
        EMCFormComboBox cbPostDetailSummary = new EMCFormComboBox(POSTDetailSummary.values(), masterDRM, "postDetailSummary");
        emcYesNoComponent ynVATIncluded = new emcYesNoComponent(masterDRM, "vatIncluded");
        EMCLookupFormComponent lkpVATCode = new EMCLookupFormComponent(new GLVATCode(), masterDRM, "vatCode");
        lkpVATCode.setPopup(new EMCLookupPopup(new emc.entity.gl.GLVATCode(), "vatId", masterUD));
        EMCFormComboBox cbVATInputOutput = new EMCFormComboBox(VATInputOutput.values(), masterDRM, "vatInputOutput");
        emcJTextArea txaText = new emcJTextArea(new EMCStringFormDocument(masterDRM, "text"));
        Component[][] comp = {{new emcJLabel("Contra Type"), cbContraType, new emcJLabel("VAT Included"), ynVATIncluded},
            {new emcJLabel("Contra Account"), lkpContraAccount, new emcJLabel("VAT Code"), lkpVATCode},
            {new emcJLabel("Post Detail/Summary"), cbPostDetailSummary, new emcJLabel("VAT Input/Output"), cbVATInputOutput},
            {txaText, new emcJLabel("Text")}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Additional");
    }

    private emcJPanel masterAnalysisTab() {
        EMCLookupFormComponent lkpAnalysisCode1 = new EMCLookupFormComponent(new GLAnalysisCode1Menu(), masterDRM, "analysisCode1");
        lkpAnalysisCode1.setPopup(new EMCLookupPopup(new GLAnalysisCode1(), "analysisCode", masterUD));
        EMCLookupFormComponent lkpAnalysisCode2 = new EMCLookupFormComponent(new GLAnalysisCode2Menu(), masterDRM, "analysisCode2");
        lkpAnalysisCode2.setPopup(new EMCLookupPopup(new GLAnalysisCode2(), "analysisCode", masterUD));
        EMCLookupFormComponent lkpAnalysisCode3 = new EMCLookupFormComponent(new GLAnalysisCode3Menu(), masterDRM, "analysisCode3");
        lkpAnalysisCode3.setPopup(new EMCLookupPopup(new GLAnalysisCode3(), "analysisCode", masterUD));
        EMCLookupFormComponent lkpAnalysisCode4 = new EMCLookupFormComponent(new GLAnalysisCode4Menu(), masterDRM, "analysisCode4");
        lkpAnalysisCode4.setPopup(new EMCLookupPopup(new GLAnalysisCode4(), "analysisCode", masterUD));
        EMCLookupFormComponent lkpAnalysisCode5 = new EMCLookupFormComponent(new GLAnalysisCode5Menu(), masterDRM, "analysisCode5");
        lkpAnalysisCode5.setPopup(new EMCLookupPopup(new GLAnalysisCode5(), "analysisCode", masterUD));
        EMCLookupFormComponent lkpAnalysisCode6 = new EMCLookupFormComponent(new GLAnalysisCode6Menu(), masterDRM, "analysisCode6");
        lkpAnalysisCode6.setPopup(new EMCLookupPopup(new GLAnalysisCode6(), "analysisCode", masterUD));
        Component[][] comp = {{new emcJLabel("Analysis Code 1"), lkpAnalysisCode1},
            {new emcJLabel("Analysis Code 2"), lkpAnalysisCode2},
            {new emcJLabel("Analysis Code 3"), lkpAnalysisCode3},
            {new emcJLabel("Analysis Code 4"), lkpAnalysisCode4},
            {new emcJLabel("Analysis Code 5"), lkpAnalysisCode5},
            {new emcJLabel("Analysis Code 6"), lkpAnalysisCode6}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Analysis");
    }

    private Component masterStatusTab() {
        emcJTextField txtApprovedBy = new emcJTextField(new EMCStringFormDocument(masterDRM, "journalApprovedBy"));
        txtApprovedBy.setEditable(false);
        EMCDatePickerFormComponent dpApprovedDate = new EMCDatePickerFormComponent(masterDRM, "journalApprovedDate");
        dpApprovedDate.setEnabled(false);
        emcJTextField txtPostedBy = new emcJTextField(new EMCStringFormDocument(masterDRM, "journalPostedBy"));
        txtPostedBy.setEditable(false);
        EMCDatePickerFormComponent dpPostedDate = new EMCDatePickerFormComponent(masterDRM, "journalPostedDate");
        dpPostedDate.setEnabled(false);
        Component[][] comp = {{new emcJLabel(masterDRM.getColumnName("journalApprovedBy")), txtApprovedBy, new emcJLabel(masterDRM.getColumnName("journalPostedBy")), txtPostedBy},
            {new emcJLabel(masterDRM.getColumnName("journalApprovedDate")), dpApprovedDate, new emcJLabel(masterDRM.getColumnName("journalPostedDate")), dpPostedDate}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Status");
    }

    private emcJPanel linesPane() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Overview", linesOverviewTab());
        tabbed.add("Additional", linesAdditionalTab());
        tabbed.add("Analysis", linesAnalysisTab());
        emcJPanel thePanel = new emcJPanel(new BorderLayout());
        thePanel.add(tabbed, BorderLayout.CENTER);
        return thePanel;
    }

    private emcTablePanelUpdate linesOverviewTab() {
        List keys = new ArrayList();
        keys.add("lineDate");
        keys.add("extReference");
        keys.add("lineType");
        keys.add("account");
        keys.add("accountDescription");
        keys.add("debit");
        keys.add("credit");
        keys.add("vatAmount");
        keys.add("contraType");
        keys.add("contraAccount");
        keys.add("contraAccountDescription");
        keys.add("contraDebit");
        keys.add("contraCredit");

        emcTableModelUpdate model = new emcTableModelUpdate(linesDRM, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);

        EMCLookupJTableComponent lkpAccount = new EMCLookupJTableComponent(new GLChartOfAccountsMenu());
        lkpAccount.setPopup(new EMCLookupPopup(new GLChartOfAccounts(), "accountNumber", linesUD));
        table.setLookupToColumn("account", lkpAccount);

        EMCLookupJTableComponent lkpContraAccount = new EMCLookupJTableComponent(new GLChartOfAccountsMenu());
        lkpContraAccount.setPopup(new EMCLookupPopup(new GLChartOfAccounts(), "accountNumber", linesUD));
        table.setLookupToColumn("contraAccount", lkpContraAccount);

        table.setComboBoxLookupToColumn("contraType", new emcJComboBox(ContraTypes.values()));
        table.setComboBoxLookupToColumn("lineType", new emcJComboBox(JournalLineType.values()));

        linesDRM.setMainTableComponent(table);

        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        linesDRM.setTablePanel(tableScroll);
        return tableScroll;
    }

    private Component linesAdditionalTab() {
        emcJTextField txtTransId = new emcJTextField(new EMCStringFormDocument(linesDRM, "journalTransId"));
        txtTransId.setEditable(false);
        EMCFormComboBox cbContraType = new EMCFormComboBox(ContraTypes.values(), linesDRM, "contraType");
        EMCLookupFormComponent lkpContraAccount = new EMCLookupFormComponent(new GLChartOfAccountsMenu(), linesDRM, "contraAccount");
        lkpContraAccount.setPopup(new EMCLookupPopup(new GLChartOfAccounts(), "accountNumber", linesUD));
        emcYesNoComponent ynVATIncluded = new emcYesNoComponent(linesDRM, "vatIncluded");
        EMCLookupFormComponent lkpVATCode = new EMCLookupFormComponent(new GLVATCode(), linesDRM, "vatCode");
        lkpVATCode.setPopup(new EMCLookupPopup(new emc.entity.gl.GLVATCode(), "vatId", linesUD));
        EMCFormComboBox cbVATInputOutput = new EMCFormComboBox(VATInputOutput.values(), linesDRM, "vatInputOutput");
        emcJTextArea txaText = new emcJTextArea(new EMCStringFormDocument(linesDRM, "text"));
        Component[][] comp = {{new emcJLabel("Transaction Id"), txtTransId, new emcJLabel("VAT Included"), ynVATIncluded},
            {new emcJLabel("Contra Type"), cbContraType, new emcJLabel("VAT Code"), lkpVATCode},
            {new emcJLabel("Contra Account"), lkpContraAccount, new emcJLabel("VAT Input/Output"), cbVATInputOutput},
            {txaText, new emcJLabel("Text")}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Additional");
    }

    private emcJPanel linesAnalysisTab() {
        EMCLookupFormComponent lkpAnalysisCode1 = new EMCLookupFormComponent(new GLAnalysisCode1Menu(), linesDRM, "analysisCode1");
        lkpAnalysisCode1.setPopup(new EMCLookupPopup(new GLAnalysisCode1(), "analysisCode", linesUD));
        EMCLookupFormComponent lkpAnalysisCode2 = new EMCLookupFormComponent(new GLAnalysisCode2Menu(), linesDRM, "analysisCode2");
        lkpAnalysisCode2.setPopup(new EMCLookupPopup(new GLAnalysisCode2(), "analysisCode", linesUD));
        EMCLookupFormComponent lkpAnalysisCode3 = new EMCLookupFormComponent(new GLAnalysisCode3Menu(), linesDRM, "analysisCode3");
        lkpAnalysisCode3.setPopup(new EMCLookupPopup(new GLAnalysisCode3(), "analysisCode", linesUD));
        EMCLookupFormComponent lkpAnalysisCode4 = new EMCLookupFormComponent(new GLAnalysisCode4Menu(), linesDRM, "analysisCode4");
        lkpAnalysisCode4.setPopup(new EMCLookupPopup(new GLAnalysisCode4(), "analysisCode", linesUD));
        EMCLookupFormComponent lkpAnalysisCode5 = new EMCLookupFormComponent(new GLAnalysisCode5Menu(), masterDRM, "analysisCode5");
        lkpAnalysisCode5.setPopup(new EMCLookupPopup(new GLAnalysisCode5(), "analysisCode", masterUD));
        EMCLookupFormComponent lkpAnalysisCode6 = new EMCLookupFormComponent(new GLAnalysisCode6Menu(), masterDRM, "analysisCode6");
        lkpAnalysisCode6.setPopup(new EMCLookupPopup(new GLAnalysisCode6(), "analysisCode", masterUD));
        Component[][] comp = {{new emcJLabel("Analysis Code 1"), lkpAnalysisCode1},
            {new emcJLabel("Analysis Code 2"), lkpAnalysisCode2},
            {new emcJLabel("Analysis Code 3"), lkpAnalysisCode3},
            {new emcJLabel("Analysis Code 4"), lkpAnalysisCode4},
            {new emcJLabel("Analysis Code 5"), lkpAnalysisCode5},
            {new emcJLabel("Analysis Code 6"), lkpAnalysisCode6}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Analysis");
    }

    /** Creates master buttons panel. */
    private emcJPanel createMasterButtonsPanel() {
        List<emcJButton> buttons = new ArrayList<emcJButton>();

        buttons.add(new ValidateButton(masterDRM));
        buttons.add(new ApproveButton(masterDRM));
        buttons.add(new PostButton(masterDRM));
        buttons.add(new TotalsButton(masterDRM));

        //Set up queries for Query Switch Button
        EMCQuerySwitchButton querySwitchButton = new EMCQuerySwitchButton("Active Journals", masterDRM);

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, GLJournalMaster.class);
        query.addAnd("journalStatus", JournalStatus.POSTED, EMCQueryConditions.NOT);
        query.addAnd("companyId", getUserData().getCompanyId());
        query.addAndInList("journalDefinitionId", journalAccessDefs, true, false);
        query.addOrderBy("journalNumber");

        querySwitchButton.addQuery("Active Journals", query);

        query = new EMCQuery(enumQueryTypes.SELECT, GLJournalMaster.class);
        query.addAnd("companyId", getUserData().getCompanyId());
        query.addAndInList("journalDefinitionId", journalAccessDefs, true, false);
        query.addOrderBy("journalNumber");

        querySwitchButton.addQuery("All Journals", query);

        emcJButton butPrintJournal = new emcJButton("Print Journal") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                Boolean journalCheck = Functions.checkBlank(masterDRM.getLastFieldValueAt("journalNumber"));
                if (!journalCheck) {
                    EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, GLJournalMaster.class);
                    query.addTableAnd(GLJournalLines.class.getName(), "journalNumber", GLJournalMaster.class.getName(), "journalNumber");
                    query.addAnd("journalNumber", masterDRM.getLastFieldValueAt("journalNumber"));
                    List toSend = new ArrayList();
                    toSend.add(query);

                    EMCCommandClass cmd = new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.GENERAL_LEDGER.getId(), ServerGLMethods.PRINT_JOURNAL_REPORT.toString());

                    JasperInformation jasperInfo = new JasperInformation();
                    jasperInfo.setJasperTemplate("/emc/reports/gl/journals/GLJournalReport.jrxml");
                    jasperInfo.setXmlNodePath("/emcmsg/emc.reports.gl.journals.GLJournalReportDS");
                    jasperInfo.setReportTitle("Journal");

                    EMCReportWSManager.generateReport(cmd, jasperInfo, EnumReports.GL_JOURNAL_REPORT, toSend, masterDRM.getUserData());
                } else {
                    Logger.getLogger("emc").log(Level.SEVERE, "Please enter a valid Journal number.", masterUD);
                }
            }
        };

        buttons.add(butPrintJournal);

        buttons.add(querySwitchButton);
        return emcSetGridBagConstraints.createButtonPanel(buttons);
    }
}
