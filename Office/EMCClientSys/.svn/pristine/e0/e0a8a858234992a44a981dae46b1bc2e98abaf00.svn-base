/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.gl.display.chartofaccountsbalances;

import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.gl.GLChartOfAccounts;
import emc.entity.gl.GLChartOfAccountsBalances;
import emc.entity.gl.GLFinancialPeriods;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import emc.menus.gl.menuitems.display.GLChartOfAccountsMenu;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class GLCharOfAccountsBalancesForm extends BaseInternalFrame {

    private emcDataRelationManagerUpdate dataManager;

    public GLCharOfAccountsBalancesForm(EMCUserData userData) {
        super("COA Balances", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 290);
        dataManager = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(enumEMCModules.GENERAL_LEDGER.getId(), new GLChartOfAccountsBalances(), userData), userData);
        dataManager.setTheForm(this);
        this.setDataManager(dataManager);
        dataManager.setFormTextId1("accountNumber");
        dataManager.setFormTextId2("periodId");
        initFrame();
    }

    private void initFrame() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Overview", overviewTab());
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
}
