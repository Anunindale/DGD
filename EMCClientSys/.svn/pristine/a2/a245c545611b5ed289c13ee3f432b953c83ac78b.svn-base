/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.debtors.display.settlementdischistory;

import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDRMViewOnly;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.debtors.transactionsettlement.DebtorsSettlementDiscHistory;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author riaan
 */
public class SettlementDiscHistoryForm extends BaseInternalFrame {

    private emcDRMViewOnly drm;

    /** Creates a new instance of CustomerTransactionsSummaryForm */
    public SettlementDiscHistoryForm(EMCUserData userData) {
        super("Settlement Discount History", true, true, true, true, userData);
        this.setBounds(20, 20, 600, 350);

        drm = new emcDRMViewOnly(new emcGenericDataSourceUpdate(enumEMCModules.DEBTORS.getId(), new DebtorsSettlementDiscHistory(), userData), userData);

        this.setDataManager(drm);

        drm.setTheForm(this);
        drm.setFormTextId1("invoiceNo");
        drm.setFormTextId2("discountAmount");

        setupFrame();
    }

    /** Sets up the frame. */
    private void setupFrame() {
        emcJTabbedPane tabs = createTabs();
        tabs.setRelationManager(drm);
        emcJPanel pnlMain = new emcJPanel();
        pnlMain.setLayout(new BorderLayout());
        pnlMain.add(tabs, BorderLayout.CENTER);
        
        this.setLayout(new GridLayout(1, 1));

        this.add(pnlMain);
    }

    /** Creates the tabbed pane containing the components on the form. */
    private emcJTabbedPane createTabs() {
        emcJTabbedPane tabs = new emcJTabbedPane();

        tabs.add("Settlement Discount History", createSummaryTab());

        return tabs;
    }

    /** Creates the summary tab. */
    private emcJPanel createSummaryTab() {
        emcJPanel pnlOverview = new emcJPanel();

        List keys = new ArrayList();
        keys.add("invoiceNo");
        keys.add("discountAmount");
        keys.add("discountDate");
        keys.add("discountOverridden");

        emcTableModelUpdate m = new emcTableModelUpdate(drm, keys);
        emcJTableUpdate tblMaster = new emcJTableUpdate(m) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        drm.setMainTableComponent(tblMaster);
        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(tblMaster);
        pnlOverview.setLayout(new GridLayout(1, 1));
        pnlOverview.add(topscroll);
        drm.setTablePanel(topscroll);

        return pnlOverview;
    }
}
