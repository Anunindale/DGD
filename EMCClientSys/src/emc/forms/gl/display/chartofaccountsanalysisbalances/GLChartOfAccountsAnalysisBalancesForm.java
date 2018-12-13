/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.gl.display.chartofaccountsanalysisbalances;

import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.formlookup.EMCLookupFormComponent;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.gl.GLChartOfAccounts;
import emc.entity.gl.GLChartOfAccountsAnalysisBalances;
import emc.entity.gl.GLFinancialPeriods;
import emc.entity.gl.analysiscodes.GLAnalysisCode1;
import emc.entity.gl.analysiscodes.GLAnalysisCode2;
import emc.entity.gl.analysiscodes.GLAnalysisCode3;
import emc.entity.gl.analysiscodes.GLAnalysisCode4;
import emc.entity.gl.analysiscodes.GLAnalysisCode5;
import emc.entity.gl.analysiscodes.GLAnalysisCode6;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import emc.menus.gl.menuitems.display.GLAnalysisCode1Menu;
import emc.menus.gl.menuitems.display.GLAnalysisCode2Menu;
import emc.menus.gl.menuitems.display.GLAnalysisCode3Menu;
import emc.menus.gl.menuitems.display.GLAnalysisCode4Menu;
import emc.menus.gl.menuitems.display.GLAnalysisCode5Menu;
import emc.menus.gl.menuitems.display.GLAnalysisCode6Menu;
import emc.menus.gl.menuitems.display.GLChartOfAccountsMenu;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class GLChartOfAccountsAnalysisBalancesForm extends BaseInternalFrame {

    private emcDataRelationManagerUpdate dataManager;

    public GLChartOfAccountsAnalysisBalancesForm(EMCUserData userData) {
        super("COA Analysis Balances", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 290);
        dataManager = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(enumEMCModules.GENERAL_LEDGER.getId(), new GLChartOfAccountsAnalysisBalances(), userData), userData);
        dataManager.setTheForm(this);
        this.setDataManager(dataManager);
        dataManager.setFormTextId1("accountNumber");
        dataManager.setFormTextId2("periodId");
        initFrame();
    }

    private void initFrame() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Overview", overviewTab());
        tabbed.add("Analysis", analysisTab());
        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(tabbed, BorderLayout.CENTER);
        this.setContentPane(contentPane);
    }

    private emcTablePanelUpdate overviewTab() {
        List keys = new ArrayList();
        keys.add("accountNumber");
        keys.add("subAccountNumber");
        keys.add("periodId");
        keys.add("debit");
        keys.add("credit");
        emcTableModelUpdate model = new emcTableModelUpdate(dataManager, keys);
        emcJTableUpdate table = new emcJTableUpdate(model) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        //Lookups
        EMCLookupJTableComponent lkpAccount = new EMCLookupJTableComponent(new GLChartOfAccountsMenu());
        lkpAccount.setPopup(new EMCLookupPopup(new GLChartOfAccounts(), "accountNumber", dataManager.getUserData()));
        table.setLookupToColumn("accountNumber", lkpAccount);
        EMCLookupJTableComponent lkpPeriod = new EMCLookupJTableComponent(new emc.menus.gl.menuitems.display.GLFinancialPeriods());
        lkpPeriod.setPopup(new EMCLookupPopup(new GLFinancialPeriods(), "periodId", dataManager.getUserData()));
        table.setLookupToColumn("periodId", lkpPeriod);
        //Lookups
        dataManager.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        dataManager.setTablePanel(tableScroll);
        return tableScroll;
    }

    private emcJPanel analysisTab() {
        EMCLookupFormComponent lkpAnalysisCode1 = new EMCLookupFormComponent(new GLAnalysisCode1Menu(), dataManager, "analysisCode1");
        lkpAnalysisCode1.setPopup(new EMCLookupPopup(new GLAnalysisCode1(), "code", dataManager.getUserData()));
        EMCLookupFormComponent lkpAnalysisCode2 = new EMCLookupFormComponent(new GLAnalysisCode2Menu(), dataManager, "analysisCode2");
        lkpAnalysisCode2.setPopup(new EMCLookupPopup(new GLAnalysisCode2(), "code", dataManager.getUserData()));
        EMCLookupFormComponent lkpAnalysisCode3 = new EMCLookupFormComponent(new GLAnalysisCode3Menu(), dataManager, "analysisCode3");
        lkpAnalysisCode3.setPopup(new EMCLookupPopup(new GLAnalysisCode3(), "code", dataManager.getUserData()));
        EMCLookupFormComponent lkpAnalysisCode4 = new EMCLookupFormComponent(new GLAnalysisCode4Menu(), dataManager, "analysisCode4");
        lkpAnalysisCode4.setPopup(new EMCLookupPopup(new GLAnalysisCode4(), "code", dataManager.getUserData()));
        EMCLookupFormComponent lkpAnalysisCode5 = new EMCLookupFormComponent(new GLAnalysisCode5Menu(), dataManager, "analysisCode5");
        lkpAnalysisCode5.setPopup(new EMCLookupPopup(new GLAnalysisCode5(), "code", dataManager.getUserData()));
        EMCLookupFormComponent lkpAnalysisCode6 = new EMCLookupFormComponent(new GLAnalysisCode6Menu(), dataManager, "analysisCode6");
        lkpAnalysisCode6.setPopup(new EMCLookupPopup(new GLAnalysisCode6(), "code", dataManager.getUserData()));
        Component[][] comp = {{new emcJLabel("Analysis Code 1"), lkpAnalysisCode1},
            {new emcJLabel("Analysis Code 2"), lkpAnalysisCode2},
            {new emcJLabel("Analysis Code 3"), lkpAnalysisCode3},
            {new emcJLabel("Analysis Code 4"), lkpAnalysisCode4},
            {new emcJLabel("Analysis Code 5"), lkpAnalysisCode5},
            {new emcJLabel("Analysis Code 6"), lkpAnalysisCode6}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Analysis");
    }
}
