package emc.forms.creditors.display.creditorstransactionsettlementhistory;

import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDRMViewOnly;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.creditors.CreditorsTransactionSettlementHistory;
import emc.framework.EMCUserData;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

/** 
 *
 * @author Owner
 */
public class CreditorsTransactionSettlementHistoryForm extends BaseInternalFrame {

    private emcDRMViewOnly drm;

    /** Creates a new instance of CreditorsTransactionSettlementHistoryForm. */
    public CreditorsTransactionSettlementHistoryForm(EMCUserData userData) {
        super("Allocation History", true, true, true, true, userData);
        this.setBounds(20, 20, 650, 290);
        drm = new emcDRMViewOnly(new emcGenericDataSourceUpdate(new CreditorsTransactionSettlementHistory(), userData), userData);
        drm.setTheForm(this);
        this.setDataManager(drm);
        drm.setFormTextId1("debitTransRef");
        drm.setFormTextId2("creditTransRef");
        this.initFrame();
    }

    /** Initializes the frame. */
    private void initFrame() {
        emcJTabbedPane tabs = new emcJTabbedPane();
        tabs.add("Overview", createOverviewTab());
        this.add(tabs, BorderLayout.CENTER);
    }

    /** Creates the Overview Tab. */
    private emcJPanel createOverviewTab() {
        emcJPanel panel = new emcJPanel(new GridLayout(1, 1));
        List<String> keys = new ArrayList<String>();
        keys.add("debitTransRef");
        keys.add("creditTransRef");
        keys.add("debitSettled");
        keys.add("creditSettled");
        keys.add("debitTransactionDate");
        keys.add("creditTransactionDate");
        keys.add("debitTransactionCreatedDate");
        keys.add("creditTransactionCreatedDate");
        keys.add("customerID");
        keys.add("sessionID");
        keys.add("systemAllocated");

        emcTableModelUpdate model = new emcTableModelUpdate(drm, keys);
        emcJTableUpdate table = new emcJTableUpdate(model) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        drm.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        drm.setMainTableComponent(table);
        drm.setTablePanel(tableScroll);
        panel.add(tableScroll);
        return panel;
    }
}
