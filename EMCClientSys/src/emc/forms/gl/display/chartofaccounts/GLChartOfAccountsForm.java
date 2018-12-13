/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.gl.display.chartofaccounts;

import emc.app.components.documents.EMCStringFormDocument;
import emc.app.components.emcJButton;
import emc.app.components.emcJComboBox;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.emctable.emcYesNoComponent;
import emc.app.components.formlookup.EMCLookupFormComponent;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.components.menulistbutton.emcMenuButtonList;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.gl.GLChartOfAccounts;
import emc.entity.gl.analysiscodes.GLAnalysisCode1;
import emc.entity.gl.analysiscodes.GLAnalysisCode2;
import emc.entity.gl.analysiscodes.GLAnalysisCode3;
import emc.entity.gl.analysiscodes.GLAnalysisCode4;
import emc.entity.gl.analysiscodes.GLAnalysisCode5;
import emc.entity.gl.analysiscodes.GLAnalysisCode6;
import emc.entity.gl.rules.GLAllocationRules;
import emc.entity.gl.rules.GLAnalysisRules;
import emc.entity.gl.rules.GLGroupRules;
import emc.entity.gl.rules.GLSecurityRules;
import emc.enums.gl.COAAccountTypes;
import emc.enums.gl.COARecordTypes;
import emc.forms.gl.display.chartofaccounts.resources.GLChartOfAccountsDRM;
import emc.framework.EMCUserData;
import emc.menus.gl.menuitems.display.GLAllocationRulesMenu;
import emc.menus.gl.menuitems.display.GLAnalysisCode1Menu;
import emc.menus.gl.menuitems.display.GLAnalysisCode2Menu;
import emc.menus.gl.menuitems.display.GLAnalysisCode3Menu;
import emc.menus.gl.menuitems.display.GLAnalysisCode4Menu;
import emc.menus.gl.menuitems.display.GLAnalysisCode5Menu;
import emc.menus.gl.menuitems.display.GLAnalysisCode6Menu;
import emc.menus.gl.menuitems.display.GLAnalysisRulesMenu;
import emc.menus.gl.menuitems.display.GLCurrency;
import emc.menus.gl.menuitems.display.GLGroupRulesMI;
import emc.menus.gl.menuitems.display.GLSecurityRulesMenu;
import emc.menus.gl.menuitems.display.GLTransactionDaySummaryMI;
import emc.menus.gl.menuitems.display.GLTransactionPeriodSummaryMI;
import emc.menus.gl.menuitems.display.GLTransactionsDetailMI;
import emc.menus.gl.menuitems.display.GLTransactionsSummaryMI;
import emc.menus.gl.menuitems.display.GLVATCode;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author riaan
 */
public class GLChartOfAccountsForm extends BaseInternalFrame {

    private GLChartOfAccountsDRM dataManager;
    private EMCUserData userData;

    /** Creates a new instance of GLChartOfAccountsForm */
    public GLChartOfAccountsForm(EMCUserData userData) {
        super("Chart of Accounts", true, true, true, true, userData);
        this.setBounds(20, 20, 700, 400);
        this.userData = userData.copyUserDataAndDataList();
        dataManager = new GLChartOfAccountsDRM(new emcGenericDataSourceUpdate(new GLChartOfAccounts(), userData), userData);
        dataManager.setTheForm(this);
        this.setDataManager(dataManager);
        dataManager.setFormTextId1("accountNumber");
        dataManager.setFormTextId2("description");
        initForm();
    }

    private void initForm() {
        this.setLayout(new BorderLayout());
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Overview", overviewTab());
        tabbed.add("Company", companyTab());
        tabbed.add("Restrictions", restrictionsTab());
        tabbed.add("Analysis", analysisTab());
        tabbed.add("Rules", rulesTab());
        this.add(tabbed, BorderLayout.CENTER);
        this.add(createButtonsPanel(), BorderLayout.EAST);
    }

    private emcTablePanelUpdate overviewTab() {
        List keys = new ArrayList();
        keys.add("accountNumber");
        keys.add("accountType");
        keys.add("description");
        keys.add("recordType");
        keys.add("vatCode");
        emcTableModelUpdate model = new emcTableModelUpdate(dataManager, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        setupTableLookups(table);
        dataManager.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        dataManager.setTablePanel(tableScroll);
        return tableScroll;
    }

    private void setupTableLookups(emcJTableUpdate table) {
        table.setComboBoxLookupToColumn("recordType", new emcJComboBox(COARecordTypes.values()));
        table.setComboBoxLookupToColumn("accountType", new emcJComboBox(COAAccountTypes.values()));

        EMCLookupJTableComponent lkpCurrency = new EMCLookupJTableComponent(new GLCurrency());
        lkpCurrency.setPopup(new EMCLookupPopup(new emc.entity.gl.GLCurrency(), "currency", userData));

        EMCLookupJTableComponent lkpVatCodes = new EMCLookupJTableComponent(new GLVATCode());
        lkpVatCodes.setPopup(new EMCLookupPopup(new emc.entity.gl.GLVATCode(), "vatId", userData));
        table.setLookupToColumn("vatCode", lkpVatCodes);
    }

    private emcJPanel companyTab() {
        emcJTextField txtGroupCompanyAccount = new emcJTextField(new EMCStringFormDocument(dataManager, "groupCompanyAccount"));
        Component[][] comp = {{new emcJLabel(dataManager.getColumnName("groupCompanyAccount")), txtGroupCompanyAccount}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Company");
    }

    private emcJPanel restrictionsTab() {
        emcYesNoComponent ynRestrictedUse = new emcYesNoComponent(dataManager, "restrictedUse");
        emcYesNoComponent ynLocked = new emcYesNoComponent(dataManager, "locked");
        emcYesNoComponent ynClosed = new emcYesNoComponent(dataManager, "accountClosed");
        Component[][] comp = {{new emcJLabel("Restricted Use/Access"), ynRestrictedUse},
            {new emcJLabel("Locked"), ynLocked},
            {new emcJLabel("Closed"), ynClosed}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Restrictions");
    }

    private emcJPanel analysisTab() {
        EMCLookupFormComponent lkpAnalysisCode1 = new EMCLookupFormComponent(new GLAnalysisCode1Menu(), dataManager, "analysisCode1");
        lkpAnalysisCode1.setPopup(new EMCLookupPopup(new GLAnalysisCode1(), "analysisCode", userData));
        EMCLookupFormComponent lkpAnalysisCode2 = new EMCLookupFormComponent(new GLAnalysisCode2Menu(), dataManager, "analysisCode2");
        lkpAnalysisCode2.setPopup(new EMCLookupPopup(new GLAnalysisCode2(), "analysisCode", userData));
        EMCLookupFormComponent lkpAnalysisCode3 = new EMCLookupFormComponent(new GLAnalysisCode3Menu(), dataManager, "analysisCode3");
        lkpAnalysisCode3.setPopup(new EMCLookupPopup(new GLAnalysisCode3(), "analysisCode", userData));
        EMCLookupFormComponent lkpAnalysisCode4 = new EMCLookupFormComponent(new GLAnalysisCode4Menu(), dataManager, "analysisCode4");
        lkpAnalysisCode4.setPopup(new EMCLookupPopup(new GLAnalysisCode4(), "analysisCode", userData));
        EMCLookupFormComponent lkpAnalysisCode5 = new EMCLookupFormComponent(new GLAnalysisCode5Menu(), dataManager, "analysisCode5");
        lkpAnalysisCode5.setPopup(new EMCLookupPopup(new GLAnalysisCode5(), "analysisCode", userData));
        EMCLookupFormComponent lkpAnalysisCode6 = new EMCLookupFormComponent(new GLAnalysisCode6Menu(), dataManager, "analysisCode6");
        lkpAnalysisCode6.setPopup(new EMCLookupPopup(new GLAnalysisCode6(), "analysisCode", userData));
        Component[][] comp = {{new emcJLabel("Analysis Code 1"), lkpAnalysisCode1},
            {new emcJLabel("Analysis Code 2"), lkpAnalysisCode2},
            {new emcJLabel("Analysis Code 3"), lkpAnalysisCode3},
            {new emcJLabel("Analysis Code 4"), lkpAnalysisCode4},
            {new emcJLabel("Analysis Code 5"), lkpAnalysisCode5},
            {new emcJLabel("Analysis Code 6"), lkpAnalysisCode6}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Analysis");
    }

    private emcJPanel rulesTab() {
        EMCLookupFormComponent lkpSecurity = new EMCLookupFormComponent(new GLSecurityRulesMenu(), dataManager, "securityRules");
        lkpSecurity.setPopup(new EMCLookupPopup(new GLSecurityRules(), "ruleId", userData));
        EMCLookupFormComponent lkpAnalysis = new EMCLookupFormComponent(new GLAnalysisRulesMenu(), dataManager, "analysisRules");
        lkpAnalysis.setPopup(new EMCLookupPopup(new GLAnalysisRules(), "ruleId", userData));
        EMCLookupFormComponent lkpAllocation = new EMCLookupFormComponent(new GLAllocationRulesMenu(), dataManager, "allocationRules");
        lkpAllocation.setPopup(new EMCLookupPopup(new GLAllocationRules(), "ruleId", userData));
        EMCLookupFormComponent lkpGroupRules = new EMCLookupFormComponent(new GLGroupRulesMI(), dataManager, "groupRules");
        lkpGroupRules.setPopup(new EMCLookupPopup(new GLGroupRules(), "groupRules", userData));

        Component[][] comp = {{new emcJLabel("Allocation Rules"), lkpAllocation},
            {new emcJLabel("Analysis Rules"), lkpAnalysis},
            {new emcJLabel("Security Rules"), lkpSecurity},
            {new emcJLabel("Group Rules"), lkpGroupRules}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Analysis");
    }

    /** Creates buttons panel. */
    private emcJPanel createButtonsPanel() {
        List<emcJButton> buttons = new ArrayList<emcJButton>();

        emcMenuButtonList btnEnquiry = new emcMenuButtonList("Enquiry", this);
        btnEnquiry.addMenuItem("Trans. Detail.", new GLTransactionsDetailMI(), 0, false);
        btnEnquiry.addMenuItem("Trans. Summary.", new GLTransactionsSummaryMI(), 1, false);
        btnEnquiry.addMenuItem("Day. Summary.", new GLTransactionDaySummaryMI(), 2, false);
        btnEnquiry.addMenuItem("Per. Summary.", new GLTransactionPeriodSummaryMI(), 3, false);

        buttons.add(btnEnquiry);

        return emcSetGridBagConstraints.createButtonPanel(buttons);
    }
}
