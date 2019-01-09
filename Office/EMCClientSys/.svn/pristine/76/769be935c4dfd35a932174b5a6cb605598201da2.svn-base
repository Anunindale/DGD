package emc.forms.creditors.display.transactions;

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
public class CreditorsTransactionsForm extends BaseInternalFrame {

    private emcDataRelationManagerUpdate drm;

    /** Creates a new instance of CreditorsTransactionsForm. */
    public CreditorsTransactionsForm(EMCUserData userData) {
        super("Transactions", true, true, true, true, userData);
        this.setBounds(20, 20, 650, 290);
        drm = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(new CreditorsTransactions(), userData), userData);
        drm.setTheForm(this);
        this.setDataManager(drm);
        drm.setFormTextId1("transactionDate");
        drm.setFormTextId2("description");
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
        keys.add("transactionDate");
        keys.add("description");
        keys.add("transactionSource");
        keys.add("debit");
        keys.add("credit");
        keys.add("supplierId");
        keys.add("vatAmount");
        keys.add("referenceNumber");
        keys.add("referenceType");
        keys.add("debitAmountSettled");
        keys.add("creditAmountSettled");
        keys.add("lastSettledDate");
        keys.add("balance");
        keys.add("supplierOrderNumber");
        keys.add("settlementDiscDate");
        keys.add("settlementDiscPercentage");
        keys.add("paymentDueDate");
        keys.add("salesOrderNo");
        keys.add("salesRep");

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
