/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.debtors.display.customertransactionsummary;

import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.debtors.DebtorsCustomerTransactionsSummary;
import emc.enums.modules.enumEMCModules;
import emc.forms.debtors.display.customertransactionsummary.resources.CustomerTransactionsSummaryDRM;
import emc.framework.EMCUserData;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author riaan
 */
public class CustomerTransactionsSummaryForm extends BaseInternalFrame {

    private CustomerTransactionsSummaryDRM drm;

    /** Creates a new instance of CustomerTransactionsSummaryForm */
    public CustomerTransactionsSummaryForm(EMCUserData userData) {
        super("Customer Transaction Summary", true, true, true, true, userData);
        this.setBounds(20, 20, 750, 550);

        drm = new CustomerTransactionsSummaryDRM(new emcGenericDataSourceUpdate(enumEMCModules.DEBTORS.getId(), new DebtorsCustomerTransactionsSummary(), userData), userData);

        this.setDataManager(drm);

        drm.setTheForm(this);
        drm.setFormTextId1("customerNo");
        drm.setFormTextId2("transactionNo");

        setupFrame();
    }

    /** Sets up the frame. */
    private void setupFrame() {
        emcJTabbedPane tabs = createTabs();
        tabs.setRelationManager(drm);
        emcJPanel pnlTransactionsSummary = new emcJPanel();
        pnlTransactionsSummary.setLayout(new BorderLayout());
        pnlTransactionsSummary.add(tabs, BorderLayout.CENTER);
        
        this.setLayout(new GridLayout(1, 1));

        this.add(pnlTransactionsSummary);
    }

    /** Creates the tabbed pane containing the components on the form. */
    private emcJTabbedPane createTabs() {
        emcJTabbedPane tabs = new emcJTabbedPane();

        tabs.add("Transactions Summary", createSummaryTab());

        return tabs;
    }

    /** Creates the summary tab. */
    private emcJPanel createSummaryTab() {
        emcJPanel pnlOverview = new emcJPanel();

        List keys = new ArrayList();
        keys.add("customerNo");
        keys.add("shipToCustomer");
        keys.add("transactionNo");
        keys.add("transactionType");
        keys.add("transactionDescription");
        keys.add("transactionDate");
        keys.add("source");
        keys.add("sourceRefId");
        keys.add("amountExclVAT");
        keys.add("vat");
        keys.add("totalInclVAT");
        keys.add("transClosed");
        keys.add("settlementId");
        keys.add("lastSettledDate");
        keys.add("totalSettledAmount");

        emcTableModelUpdate m = new emcTableModelUpdate(drm, keys);
        emcJTableUpdate tblMaster = new emcJTableUpdate(m);

        drm.setMainTableComponent(tblMaster);
        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(tblMaster);
        pnlOverview.setLayout(new GridLayout(1, 1));
        pnlOverview.add(topscroll);
        this.setTablePanel(topscroll);

        return pnlOverview;
    }
}
