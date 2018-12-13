/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.creditors.display.transactionsettlement;

import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.creditors.CreditorsTransactions;
import emc.framework.EMCUserData;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Owner
 */
public class TransactionSettlementForm extends BaseInternalFrame {

    private emcDataRelationManagerUpdate drm;

    public TransactionSettlementForm(EMCUserData userData) {
        super("Transaction Allocation", true, true, true, true, userData);
        this.setBounds(20, 20, 650, 290);
        drm = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(new CreditorsTransactions(), userData), userData);
        drm.setTheForm(this);
        this.setDataManager(drm);

        this.initFrame();

    }

    /** Initializes the frame. */
    private void initFrame() {
        emcJTabbedPane tabs = new emcJTabbedPane();
        tabs.add("Overview", createOverviewTab());
        this.add(tabs, BorderLayout.CENTER);
    }

    private emcJPanel createOverviewTab() {
        emcJPanel panel = new emcJPanel(new GridLayout(1, 1));
        List<String> keys = new ArrayList<String>();

        emcTableModelUpdate model = new emcTableModelUpdate(drm, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        drm.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        drm.setMainTableComponent(table);
        drm.setTablePanel(tableScroll);
        panel.add(tableScroll);
        return panel;
    }
}
