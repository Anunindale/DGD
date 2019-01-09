package emc.forms.gl.display.budgetmodel;

import emc.app.components.emcJButton;
import emc.app.components.emcJPanel;
import emc.app.components.emcJSplitPane;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcMenuButton;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.util.utilFunctions;
import emc.entity.gl.GLBudgetLinesDS;
import emc.entity.gl.GLBudgetModel;
import emc.entity.gl.GLChartOfAccounts;
import emc.entity.gl.GLFinancialPeriods;
import emc.enums.modules.enumEMCModules;
import emc.forms.gl.display.budgetmodel.resources.BudgetModelLinesDRM;
import emc.forms.gl.display.budgetmodel.resources.BudgetModelMasterDRM;
import emc.forms.gl.display.budgetmodel.resources.GLAddAccountsDialog;
import emc.framework.EMCUserData;
import emc.menus.gl.menuitems.display.GLBudgetPeriodMI;
import emc.menus.gl.menuitems.display.GLChartOfAccountsMenu;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.swing.JSplitPane;

/**
 *
 * @author claudette
 */
public class GLBudgetModelForm extends BaseInternalFrame {

    private BudgetModelMasterDRM masterDRM;
    private BudgetModelLinesDRM linesDRM;
    private String modelId;
    private EMCUserData userData;

    /** Creates a new instance of GLBudgetModelForm. */
    public GLBudgetModelForm(EMCUserData userData) {
        super("Budget Model", true, true, true, true, userData);
        this.setBounds(20, 20, 800, 600);

        EMCUserData linesUD = userData.copyUserData();
        masterDRM = new BudgetModelMasterDRM(new emcGenericDataSourceUpdate(enumEMCModules.GENERAL_LEDGER.getId(), new GLBudgetModel(), userData), userData);
        linesDRM = new BudgetModelLinesDRM(new emcGenericDataSourceUpdate(enumEMCModules.GENERAL_LEDGER.getId(), new GLBudgetLinesDS(), linesUD), linesUD);

        this.setDataManager(masterDRM);
        this.userData = masterDRM.getUserData().copyUserDataAndDataList();
        masterDRM.setHeaderColumnID("modelId");
        masterDRM.setTheForm(this);
        masterDRM.setFormTextId1("modelId");
        masterDRM.setFormTextId2("description");
        masterDRM.setLinesTable(linesDRM);

        linesDRM.setTheForm(this);
        linesDRM.setHeaderTable(masterDRM);
        linesDRM.setFormTextId1("modelId");
        linesDRM.setFormTextId2("account");
        linesDRM.setRelationColumnToHeader("modelId");
        setupFrame();
    }

    private emcJTabbedPane createMastersTab() {
        emcJTabbedPane masterTabs = new emcJTabbedPane();

        masterTabs.add("Overview", createMasterOverviewTab());

        return masterTabs;
    }

    private emcJTabbedPane createLinesTab() {
        emcJTabbedPane linesTabs = new emcJTabbedPane();

        linesTabs.add("Overview", createLinesOverviewTab());

        return linesTabs;
    }

    private void setupFrame() {
        emcJTabbedPane masterTabs = createMastersTab();
        masterTabs.setRelationManager(masterDRM);
        emcJPanel pnlMaster = new emcJPanel(new BorderLayout());
        pnlMaster.add(masterTabs, BorderLayout.CENTER);
        pnlMaster.add(createMasterButtonsPanel(), BorderLayout.EAST);

        emcJTabbedPane linesTabs = createLinesTab();
        linesTabs.setRelationManager(linesDRM);
        emcJPanel pnlLines = new emcJPanel(new BorderLayout());
        pnlLines.add(linesTabs, BorderLayout.CENTER);
        pnlLines.add(createLinesButtonPanel(), BorderLayout.EAST);

        this.setLayout(new GridLayout(1, 1));

        this.add(pnlMaster);
        this.add(pnlLines);

        emcJSplitPane topBottomSplit = new emcJSplitPane(JSplitPane.VERTICAL_SPLIT, pnlMaster, pnlLines);
        topBottomSplit.setDividerSize(8);
        topBottomSplit.setContinuousLayout(true);
        topBottomSplit.setOneTouchExpandable(false);
        topBottomSplit.setDividerLocation(250);

        this.add(topBottomSplit);
    }

    /** Creates the Overview Tab. */
    private emcJPanel createMasterOverviewTab() {
        emcJPanel panel = new emcJPanel(new GridLayout(1, 1));
        List<String> keys = new ArrayList<String>();
        keys.add("modelId");
        keys.add("description");
        keys.add("fromPeriod");
        keys.add("toPeriod");

        emcTableModelUpdate model = new emcTableModelUpdate(masterDRM, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);

        EMCLookupJTableComponent lkpFromPeriod = new EMCLookupJTableComponent(new emc.menus.gl.menuitems.display.GLFinancialPeriods());
        lkpFromPeriod.setPopup(new EMCLookupPopup(new GLFinancialPeriods(), "periodId", masterDRM.getUserData()));
        table.setLookupToColumn("fromPeriod", lkpFromPeriod);

        EMCLookupJTableComponent lkpToPeriod = new EMCLookupJTableComponent(new emc.menus.gl.menuitems.display.GLFinancialPeriods());
        lkpToPeriod.setPopup(new EMCLookupPopup(new GLFinancialPeriods(), "periodId", masterDRM.getUserData()));
        table.setLookupToColumn("toPeriod", lkpToPeriod);

        masterDRM.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        masterDRM.setMainTableComponent(table);
        masterDRM.setTablePanel(tableScroll);
        panel.add(tableScroll);
        return panel;
    }

    private emcJPanel createMasterButtonsPanel() {
        List<emcJButton> buttons = new ArrayList<emcJButton>();
        emcJButton btnGenerate = new emcJButton("Generate") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                String masterModuleId = (String) masterDRM.getLastFieldValueAt("modelId");
                if (masterModuleId.equals(null)) {
                    utilFunctions.logMessage(Level.SEVERE, "No master record found", userData);
                }
                new GLAddAccountsDialog(masterModuleId, utilFunctions.findEMCDesktop(this), masterDRM);
                linesDRM.refreshData();
            }
        };
        buttons.add(btnGenerate);
        return emcSetGridBagConstraints.createButtonPanel(buttons, false);
    }

    private emcJPanel createLinesOverviewTab() {
        emcJPanel panel = new emcJPanel(new GridLayout(1, 1));
        List<String> keys = new ArrayList<String>();
        keys.add("account");
        keys.add("description");
        emcTableModelUpdate model = new emcTableModelUpdate(linesDRM, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);

        EMCLookupJTableComponent lkpAccounts = new EMCLookupJTableComponent(new GLChartOfAccountsMenu());
        lkpAccounts.setPopup(new EMCLookupPopup(new GLChartOfAccounts(), "accountNumber", linesDRM.getUserData()));
        table.setLookupToColumn("account", lkpAccounts);

        linesDRM.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        linesDRM.setMainTableComponent(table);
        linesDRM.setTablePanel(tableScroll);
        panel.add(tableScroll);
        return panel;
    }

    private emcJPanel createLinesButtonPanel() {
        List<emcJButton> buttons = new ArrayList<emcJButton>();
        buttons.add(new emcMenuButton("Amounts", new GLBudgetPeriodMI(), this, 0, true));
        return emcSetGridBagConstraints.createButtonPanel(buttons, false);
    }
}