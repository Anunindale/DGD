/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.debtors.display.journals;

import emc.app.components.documents.EMCBigDecimalFormDocument;
import emc.app.components.documents.EMCStringFormDocument;
import emc.app.components.emcJButton;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJScrollPane;
import emc.app.components.emcJSplitPane;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextArea;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emcpicker.emcdatepicker.EMCDatePickerFormComponent;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.formlookup.EMCLookupFormComponent;
import emc.app.components.inventory.journals.ContraTypesFormDropdown;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.components.queryswitchbutton.EMCQuerySwitchButton;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.wsmanager.EMCWSManager;
import emc.entity.base.journals.BaseJournalDefinitionTable;
import emc.entity.debtors.journals.DebtorsJournalMaster;
import emc.entity.gl.GLChartOfAccounts;
import emc.entity.sop.SOPCustomers;
import emc.enums.base.journals.Modules;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.enums.base.journals.JournalStatus;
import emc.enums.modules.enumEMCModules;
import emc.forms.debtors.display.journals.resources.ApprovalButton;
import emc.forms.debtors.display.journals.resources.JournalLinesDRM;
import emc.forms.debtors.display.journals.resources.PostButton;
import emc.forms.debtors.display.journals.resources.PrintButton;
import emc.forms.debtors.display.journals.resources.ValidateButton;
import emc.framework.EMCCommandClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.menus.debtors.menuitems.display.DebtorsJournalDefinitions;
import emc.menus.gl.menuitems.display.GLChartOfAccountsMenu;
import emc.menus.gl.menuitems.display.GLVATCode;
import emc.menus.sop.menuitems.display.SOPCustomersMenu;
import emc.methods.base.ServerBaseMethods;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JSplitPane;

/**
 *
 * @author riaan
 */
public class DebtorsJournalsForm extends BaseInternalFrame {

    // <editor-fold defaultstate="collapsed" desc="Master Overview Tab">
    private EMCLookupJTableComponent lkpJournalDefinitionId;
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Master Detail Tab">
    private emcJTextArea txaJournalText = new emcJTextArea();
    private emcJLabel lblJournalPosting = new emcJLabel("Journal Posting");
    private emcJTextField txtJournalPosting = new emcJTextField();
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Master Accounting Tab">
    private emcJLabel lblJournalContraType;
    private emcJLabel lblJournalContraAccount;
    private emcJLabel lblJournalEnteredBy = new emcJLabel("Journal Entered By");
    private emcJTextField txtJournalEnteredBy = new emcJTextField();
    private emcJLabel lblJournalEnteredDate;
    private EMCDatePickerFormComponent journalEnteredDate;
    private emcJLabel lblJournalApprovedBy;
    private emcJTextField txtJournalApprovedBy = new emcJTextField();
    private emcJLabel lblJournalApprovedDate;
    private EMCDatePickerFormComponent journalApprovedDate;
    private emcJLabel lblJournalPostedBy;
    private emcJTextField txtJournalPostedBy = new emcJTextField();
    private emcJLabel lblJournalPostedDate;
    private EMCDatePickerFormComponent journalPostedDate;
    private EMCLookupFormComponent lkpJournalContraAccount;
    private ContraTypesFormDropdown cmbContraType;
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Lines General Tab">
    private EMCLookupJTableComponent tblLkpCustomer;
    private EMCLookupJTableComponent tblLkpVATCode;
    private emcJLabel lblLinesJournalContraType;
    private ContraTypesFormDropdown cmbLinesJournalContraType;
    private emcJLabel lblLinesJournalContraAccount;
    private EMCLookupFormComponent lkpJournalLineContraAccount;
    private emcJLabel lblLinesTotalCost;
    private emcJTextField txtLinesTotalCost = new emcJTextField();
    // </editor-fold>
    private emcDataRelationManagerUpdate masterDRM;
    private JournalLinesDRM linesDRM;
    private List journalAccessDefs = new ArrayList();

    public DebtorsJournalsForm(EMCUserData userData) {
        super("Journals", true, true, true, true, userData);
        this.setBounds(20, 20, 820, 700);

        try {
            EMCCommandClass cmd = new EMCCommandClass(ServerBaseMethods.GET_ACCESS_DEFS);
            journalAccessDefs = EMCWSManager.executeGenericWS(cmd, journalAccessDefs, userData);
            journalAccessDefs = (List) journalAccessDefs.get(1);
            if (journalAccessDefs.size() == 0) {
                journalAccessDefs.add("NoneFoundSoThere");
                Logger.getLogger("emc").log(Level.WARNING, "You do not have access to any journal definitions.", userData);
            }
            EMCQuery query;
            if(userData != null && userData.getUserData(0) !=null &&userData.getUserData(0) instanceof EMCQuery){
              query = (EMCQuery) userData.getUserData(0);
              query.addAndInList("journalDefinitionId", journalAccessDefs, true, false);
              
            }else{
           
            query = new EMCQuery(enumQueryTypes.SELECT, DebtorsJournalMaster.class);
            query.addAnd("journalStatus", JournalStatus.POSTED, EMCQueryConditions.NOT);
            query.addAnd("companyId", getUserData().getCompanyId());
            query.addAndInList("journalDefinitionId", journalAccessDefs, true, false);
            query.addOrderBy("journalNumber");
            }
            
            userData.setUserData(0, query);
            setupDRM(userData);

            createLabels();
            createDatePickers();
            initializeLookups(userData);
            setDocuments();
            setupDropdowns();
            setupUneditableFields();

            initFrame();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupDRM(EMCUserData userData) {
        masterDRM = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(
                enumEMCModules.DEBTORS.getId(),
                new DebtorsJournalMaster(), userData), userData);
        this.setDataManager(masterDRM);

        EMCUserData linesUD = userData.copyUserData();
        linesDRM = new JournalLinesDRM(linesUD);

        masterDRM.setHeaderColumnID("journalNumber");
        masterDRM.setTheForm(this);
        masterDRM.setFormTextId1("journalNumber");
        masterDRM.setFormTextId2("journalDescription");
        masterDRM.setLinesTable(linesDRM);

        linesDRM.setTheForm(this);
        linesDRM.setFormTextId1("journalNumber");
        linesDRM.setFormTextId2("lineRef");
        linesDRM.setHeaderTable(masterDRM);
        linesDRM.setRelationColumnToHeader("journalNumber");
    }

    private void createLabels() {
        lblJournalEnteredBy = new emcJLabel(masterDRM.getFieldEmcLabel("createdBy"));
        lblJournalEnteredDate = new emcJLabel(masterDRM.getFieldEmcLabel("createdDate"));
        lblJournalApprovedBy = new emcJLabel(masterDRM.getFieldEmcLabel("journalApprovedBy"));
        lblJournalApprovedDate = new emcJLabel(masterDRM.getFieldEmcLabel("journalApprovedDate"));
        lblJournalPostedBy = new emcJLabel(masterDRM.getFieldEmcLabel("journalPostedBy"));
        lblJournalPostedDate = new emcJLabel(masterDRM.getFieldEmcLabel("journalPostedDate"));

        lblLinesTotalCost = new emcJLabel(linesDRM.getFieldEmcLabel("lineTotal"));

        lblJournalContraAccount = new emcJLabel(masterDRM.getFieldEmcLabel("journalContraAccount"));
        lblJournalContraType = new emcJLabel(masterDRM.getFieldEmcLabel("journalContraType"));

        lblLinesJournalContraAccount = new emcJLabel(linesDRM.getFieldEmcLabel("contraAccount"));
        lblLinesJournalContraType = new emcJLabel(linesDRM.getFieldEmcLabel("contraType"));
    }

    private void initializeLookups(EMCUserData userData) {
        lkpJournalLineContraAccount = new EMCLookupFormComponent(new GLChartOfAccountsMenu(), linesDRM, "contraAccount", "glAccountNum");
        lkpJournalContraAccount = new EMCLookupFormComponent(new GLChartOfAccountsMenu(), masterDRM, "journalContraAccount", "glAccountNum");
        EMCLookupPopup contraAccountPopup = new EMCLookupPopup(new GLChartOfAccounts(), "accountNumber", userData);
        lkpJournalContraAccount.setPopup(contraAccountPopup);
        lkpJournalLineContraAccount.setPopup(contraAccountPopup);
        linesDRM.setDocument(lkpJournalLineContraAccount.getDocument());
        masterDRM.setDocument(lkpJournalContraAccount.getDocument());

        lkpJournalDefinitionId = new EMCLookupJTableComponent(new DebtorsJournalDefinitions());
        EMCLookupPopup journalDefinitionPopup = new EMCLookupPopup(new BaseJournalDefinitionTable(), "journalDefinitionId", userData);
        lkpJournalDefinitionId.setPopup(journalDefinitionPopup);
        //Only show definitions for correct module
        lkpJournalDefinitionId.getTheQuery().addAnd("journalModule", Modules.DEBTORS.toString());
        lkpJournalDefinitionId.getTheQuery().addAndInList("journalDefinitionId", journalAccessDefs, true, false);
        
        tblLkpCustomer = new EMCLookupJTableComponent(new SOPCustomersMenu());
        EMCLookupPopup customerPopup = new EMCLookupPopup(new SOPCustomers(), "customerId", userData);
        tblLkpCustomer.setPopup(customerPopup);

        tblLkpVATCode = new EMCLookupJTableComponent(new GLVATCode());
        EMCLookupPopup vatCodePopup = new EMCLookupPopup(new emc.entity.gl.GLVATCode(), "vatId", userData);
        tblLkpVATCode.setPopup(vatCodePopup);
    }

    private void setupDropdowns() {
        cmbContraType = new ContraTypesFormDropdown(masterDRM, "journalContraType");
        cmbLinesJournalContraType = new ContraTypesFormDropdown(linesDRM, "contraType");
    }

    private void createDatePickers() {
        journalEnteredDate = new EMCDatePickerFormComponent(masterDRM, "createdDate");
        journalApprovedDate = new EMCDatePickerFormComponent(masterDRM, "journalApprovedDate");
        journalPostedDate = new EMCDatePickerFormComponent(masterDRM, "journalPostedDate");
    }

    private void setupUneditableFields() {
        txtJournalApprovedBy.setEditable(false);
        txtJournalEnteredBy.setEditable(false);
        txtJournalPostedBy.setEditable(false);
        journalApprovedDate.setEnabled(false);
        journalEnteredDate.setEnabled(false);
        journalPostedDate.setEnabled(false);

        txtJournalPosting.setEditable(false);

        txtLinesTotalCost.setEditable(false);
    }

    private void setDocuments() {
        txaJournalText.setDocument(new EMCStringFormDocument(masterDRM, "journalText"));
        txtJournalPosting.setDocument(new EMCStringFormDocument(masterDRM, "journalPosting"));
        txtJournalEnteredBy.setDocument(new EMCStringFormDocument(masterDRM, "createdBy"));
        txtJournalApprovedBy.setDocument(new EMCStringFormDocument(masterDRM, "journalApprovedBy"));
        txtJournalPostedBy.setDocument(new EMCStringFormDocument(masterDRM, "journalPostedBy"));

        txtLinesTotalCost.setDocument(new EMCBigDecimalFormDocument(linesDRM, "lineTotal"));
    }

    private void initFrame() {
        this.setLayout(new GridLayout(1, 1));

        emcJPanel pnlMaster = new emcJPanel();
        pnlMaster.setLayout(new BorderLayout());

        pnlMaster.add(setupMaster(), BorderLayout.CENTER);
        pnlMaster.add(setupMasterButtons(), BorderLayout.EAST);

        emcJPanel pnlLines = new emcJPanel();
        pnlLines.setLayout(new BorderLayout());

        pnlLines.add(setupLines(), BorderLayout.CENTER);
        pnlLines.add(setupLinesButtons(), BorderLayout.EAST);

        this.add(pnlMaster);
        this.add(pnlLines);

        emcJSplitPane topBottomSplit = new emcJSplitPane(JSplitPane.VERTICAL_SPLIT, pnlMaster, pnlLines);
        topBottomSplit.setDividerSize(8);
        topBottomSplit.setContinuousLayout(true);
        topBottomSplit.setOneTouchExpandable(false);
        topBottomSplit.setDividerLocation(320);

        this.add(topBottomSplit);
    }

    private emcJTabbedPane setupMaster() {
        emcJTabbedPane masterTabs = new emcJTabbedPane();

        masterTabs.add("Overview", masterOverviewTab());
        masterTabs.add("Detail", masterDetailTab());
        masterTabs.add("Accounting", masterAccountingTab());

        return masterTabs;
    }

    private emcJPanel masterOverviewTab() {
        emcJPanel pnlOverview = new emcJPanel();
        pnlOverview.setLayout(new GridLayout(1, 1));

        List<String> masterKeys = new ArrayList<String>();
        masterKeys.add("journalDefinitionId");
        masterKeys.add("journalNumber");
        masterKeys.add("journalDescription");
        masterKeys.add("journalDate");
        masterKeys.add("journalStatus");
        masterKeys.add("createdDate");
        masterKeys.add("createdBy");

        emcTableModelUpdate m = new emcTableModelUpdate(masterDRM, masterKeys) {

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                if (columnIndex > 3) {
                    return false;
                }
                return super.isCellEditable(rowIndex, columnIndex);
            }
        };

        emcJTableUpdate tblMaster = new emcJTableUpdate(m);
        masterDRM.setMainTableComponent(tblMaster);

        tblMaster.setLookupToColumn("journalDefinitionId", lkpJournalDefinitionId);

        emcTablePanelUpdate masterScroll = new emcTablePanelUpdate(tblMaster);
        masterDRM.setTablePanel(masterScroll);
        pnlOverview.add(masterScroll);

        return pnlOverview;
    }

    private emcJPanel masterDetailTab() {
        emcJPanel pnlDetail = new emcJPanel();
        pnlDetail.setLayout(new GridBagLayout());
        pnlDetail.setBorder(BorderFactory.createTitledBorder("Detail"));

        emcJPanel pnlJournalText = new emcJPanel();
        pnlJournalText.setBorder(BorderFactory.createTitledBorder("Journal Text"));
        pnlJournalText.setLayout(new GridLayout(1, 1));

        emcJScrollPane journalTextPane = new emcJScrollPane(txaJournalText);
        journalTextPane.setPreferredSize(new Dimension(70, 70));
        pnlJournalText.add(journalTextPane);
        int y = 0;

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1;
        gbc.weighty = 1.5;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;

        pnlDetail.add(pnlJournalText, gbc);

        y++;

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;

        pnlDetail.add(emcSetGridBagConstraints.createSimplePanel(new Component[][]{{lblJournalPosting, txtJournalPosting}}, GridBagConstraints.BOTH, false), gbc);

        y++;

        gbc = emcSetGridBagConstraints.endPanel(y);

        pnlDetail.add(new emcJLabel(), gbc);

        return pnlDetail;
    }

    private emcJPanel masterAccountingTab() {
        Component[][] components = {
            {lblJournalContraType, cmbContraType},
            {lblJournalContraAccount, lkpJournalContraAccount},
            {lblJournalEnteredBy, txtJournalEnteredBy},
            {lblJournalEnteredDate, journalEnteredDate},
            {lblJournalApprovedBy, txtJournalApprovedBy},
            {lblJournalApprovedDate, journalApprovedDate},
            {lblJournalPostedBy, txtJournalPostedBy},
            {lblJournalPostedDate, journalPostedDate},};

        emcJPanel pnlAccounting = emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, true);
        pnlAccounting.setBorder(BorderFactory.createTitledBorder("Accounting"));

        return pnlAccounting;
    }

    private emcJPanel setupMasterButtons() {
        List<emcJButton> buttons = new ArrayList<emcJButton>();
        buttons.add(new ValidateButton(this.masterDRM));

        buttons.add(new ApprovalButton(this));

        buttons.add(new PostButton(this.masterDRM));
        buttons.add(new PrintButton(masterDRM));

        //Set up queries for Query Switch Button
        EMCQuerySwitchButton querySwitchButton = new EMCQuerySwitchButton("Active Journals", masterDRM);

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsJournalMaster.class);
        query.addAnd("journalStatus", JournalStatus.POSTED, EMCQueryConditions.NOT);
        query.addAnd("companyId", getUserData().getCompanyId());
        query.addAndInList("journalDefinitionId", journalAccessDefs, true, false);
        query.addOrderBy("journalNumber");

        querySwitchButton.addQuery("Active Journals", query);

        query = new EMCQuery(enumQueryTypes.SELECT, DebtorsJournalMaster.class);
        query.addAnd("companyId", getUserData().getCompanyId());
        query.addAndInList("journalDefinitionId", journalAccessDefs, true, false);
        query.addOrderBy("journalNumber");

        querySwitchButton.addQuery("All Journals", query);

        buttons.add(querySwitchButton);

        return emcSetGridBagConstraints.createButtonPanel(buttons);
    }

    private emcJTabbedPane setupLines() {
        emcJTabbedPane linesTabs = new emcJTabbedPane();

        linesTabs.add("Overview", linesOverviewTab());
        linesTabs.add("General", linesGeneralTab());

        return linesTabs;
    }

    private emcJPanel linesOverviewTab() {
        emcJPanel pnlOverview = new emcJPanel();
        pnlOverview.setLayout(new BorderLayout());

        List<String> linesKeys = new ArrayList<String>();
        linesKeys.add("lineRef");
        linesKeys.add("lineDescription");
        linesKeys.add("lineDate");
        linesKeys.add("customerId");
        linesKeys.add("lineAmount");
        linesKeys.add("vatCode");
        linesKeys.add("vatAmount");
        linesKeys.add("lineTotal");

        emcTableModelUpdate m = new emcTableModelUpdate(linesDRM, linesKeys);
        emcJTableUpdate tblLines = new emcJTableUpdate(m);

        linesDRM.setMainTableComponent(tblLines);
        emcTablePanelUpdate linesScroll = new emcTablePanelUpdate(tblLines);
        masterDRM.setTablePanel(linesScroll);
        tblLines.setLookupToColumn("customerId", tblLkpCustomer);
        tblLines.setLookupToColumn("vatCode", tblLkpVATCode);

        pnlOverview.add(linesScroll, BorderLayout.CENTER);

        return pnlOverview;
    }

    private emcJPanel linesGeneralTab() {
        emcJPanel pnlAdditional = new emcJPanel();
        pnlAdditional.setBorder(BorderFactory.createTitledBorder("General"));

        pnlAdditional.setLayout(new GridLayout(1, 1));

        Component[][] components = {
            {lblLinesJournalContraType, cmbLinesJournalContraType},
            {lblLinesJournalContraAccount, lkpJournalLineContraAccount},
            {lblLinesTotalCost, txtLinesTotalCost}
        };

        emcJPanel pnlLeft = emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.HORIZONTAL, true);
        pnlLeft.setBorder(BorderFactory.createTitledBorder("Misc"));

        pnlAdditional.add(pnlLeft);

        return pnlAdditional;
    }

    /**
     * Creates the panel for buttons on the lines
     * @return the newly created panel
     */
    private emcJPanel setupLinesButtons() {
        List<emcJButton> buttonList = new ArrayList<emcJButton>();

        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }
}
 
