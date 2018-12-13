/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.debtors.display.customerbalance;

import emc.app.components.emcJButton;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJScrollPane;
import emc.app.components.emcJTextField;
import emc.app.components.emcMenuButton;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.renderers.EMCCellRenderer;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.wsmanager.EMCWSManager;
import emc.datatypes.EMCBigDecimal;
import emc.entity.debtors.DebtorsPostDatedPayment;
import emc.entity.debtors.DebtorsTransactions;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.framework.EMCCommandClass;
import emc.framework.EMCDebug;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.helpers.debtors.DebtorsAgingHelper;
import emc.helpers.debtors.DebtorsCustomerBalanceHelper;
import emc.menus.debtors.menuitems.display.DebtorsPostDatedPayments;
import emc.menus.debtors.menuitems.display.DebtorsTransactionsMenu;
import emc.methods.debtors.ServerDebtorsMethods;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;

/**
 * @description : This form is used to display customer balances.
 *
 * @date        : 21 Jun 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class CustomerBalanceForm extends BaseInternalFrame {

    private String customerId;
    private String customerName;
    private Object creditLimit;
    private emcJTextField txtCustomerID = new emcJTextField();
    private emcJTextField txtCustomerName = new emcJTextField();
    private emcJTextField txtCustomerBalance = new emcJTextField();
    private emcJTextField txtCreditLimit = new emcJTextField();
    private emcJTextField txtPostDatedPaymentTotal = new emcJTextField();
    private emcJTextField txtUnallocatedCredits = new emcJTextField();
    private emcJTextField txtOpenPickingLists = new emcJTextField();
    private emcJTextField txtOpenASO = new emcJTextField();
    private emcJTableUpdate agingTable;
    private emcJTextField txtCustomerCreditsBalance = new emcJTextField();

    //Used to format BigDecimal values for display.
    private DecimalFormat decimalFormat;

    /** Creates a new instance of CustomerBalanceForm */
    public CustomerBalanceForm(EMCUserData userData) {
        super("Customer Balance", true, true, true, true, userData);
        this.setBounds(20, 20, 600, 420);

        //Get values passed from customer form in user data
        this.customerId = (String) userData.getUserData(3);
        this.customerName = (String) userData.getUserData(4);
        this.creditLimit = userData.getUserData(5);

        EMCBigDecimal bigDecimal = new EMCBigDecimal();
        this.decimalFormat = new DecimalFormat(bigDecimal.getFormatToDisplay());
        this.decimalFormat.setMaximumFractionDigits(bigDecimal.getNumberDecimalsDisplay());
        this.decimalFormat.setMinimumFractionDigits(bigDecimal.getNumberDecimalsDisplay());
        
        setupComponents();
        setComponentValues(userData);

        setupFrame();
    }

    @Override
    public void setUserData(EMCUserData userData) {
        super.setUserData(userData);

        //Get values passed from customer form in user data
        this.customerId = (String) userData.getUserData(3);
        this.customerName = (String) userData.getUserData(4);
        this.creditLimit = userData.getUserData(5);

        this.setComponentValues(userData);
    }

    /** Sets up components. */
    private void setupComponents() {
        this.txtCustomerID.setEditable(false);
        this.txtCustomerName.setEditable(false);
        this.txtCreditLimit.setEditable(false);
        this.txtCustomerBalance.setEditable(false);
        this.txtPostDatedPaymentTotal.setEditable(false);
        this.txtUnallocatedCredits.setEditable(false);
        this.txtOpenPickingLists.setEditable(false);
        this.txtOpenASO.setEditable(false);
        this.txtCustomerCreditsBalance.setEditable(false);
        
        //No data
        Object[][] data = new Object[0][0];
        //No columns
        Object[] columns = new Object[0];

        this.agingTable = new emcJTableUpdate(data, columns) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        //Setup cell renderer for this table.  This table can only display decimal values.
        agingTable.setDefaultRenderer(Object.class, new DoubleCellRenderer(2));
    }

    /** Sets components values. */
    private void setComponentValues(EMCUserData userData) {
        EMCCommandClass cmd = new EMCCommandClass(ServerDebtorsMethods.GET_CUSTOMER_BALANCE);

        List toSend = new ArrayList();
        toSend.add(customerId);

        toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);

        if (toSend != null && toSend.size() > 1 && toSend.get(1) instanceof DebtorsCustomerBalanceHelper) {
            DebtorsCustomerBalanceHelper balanceHelper = (DebtorsCustomerBalanceHelper) toSend.get(1);

            txtCustomerBalance.setText(decimalFormat.format(balanceHelper.getCurrentBalance()));
            txtPostDatedPaymentTotal.setText(decimalFormat.format(balanceHelper.getPostDatedPaymentTotal()));
            txtUnallocatedCredits.setText(decimalFormat.format(balanceHelper.getUnallocatedCredits()));
            txtOpenPickingLists.setText(decimalFormat.format(balanceHelper.getOpenPickingListValue()));
            txtOpenASO.setText(decimalFormat.format(balanceHelper.getOpenASOValue()));

            txtCustomerID.setText(customerId);
            txtCustomerName.setText(customerName);
            txtCreditLimit.setText(decimalFormat.format(creditLimit));
            txtCustomerCreditsBalance.setText(decimalFormat.format(balanceHelper.getCreditsBalance()));

            //Aging
            List<DebtorsAgingHelper> agingList = balanceHelper.getAgingList();
            Object[] columns = new Object[agingList.size()];
            Object[][] rowData = new Object[1][agingList.size()];

            int lastIndex = agingList.size() - 1;

            //Populate table in reverse order.
            for (int i = lastIndex; i >= 0; i--) {
                columns[lastIndex - i] = agingList.get(i).getBinName();
                rowData[0][lastIndex - i] = agingList.get(i).getBinAmount();
            }

            DefaultTableModel model = new DefaultTableModel(rowData, columns);
            this.agingTable.setModel(model);
        }
    }

    /** Adds components to the frame. */
    private void setupFrame() {
        this.setLayout(new BorderLayout());

        emcJPanel mainPanel = new emcJPanel(new BorderLayout());

        mainPanel.add(BorderLayout.NORTH, createBalancePanel());
        mainPanel.add(BorderLayout.CENTER, createAgingPanel());

        this.add(mainPanel, BorderLayout.CENTER);
        this.add(BorderLayout.EAST, createButtonsPanel());
    }

    /** Create the balance panel. */
    private emcJPanel createBalancePanel() {
        Component[][] components = new Component[][]{
            {new emcJLabel("Customer ID"), txtCustomerID},
            {new emcJLabel("Customer Name"), txtCustomerName},
            {new emcJLabel("Balance"), txtCustomerBalance},
            {new emcJLabel("Credits Balance"), txtCustomerCreditsBalance},
            {new emcJLabel("Credit Limit"), txtCreditLimit},
            {new emcJLabel("Post-dated Cheque Total"), txtPostDatedPaymentTotal},
            {new emcJLabel("Unallocated Credits"), txtUnallocatedCredits},
            {new emcJLabel("Open Picking List Total"), txtOpenPickingLists},
            {new emcJLabel("Open Assembly Order Total"), txtOpenASO}
        };

        return emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, true, "Customer Balance");
    }

    /** Creates the aging panel. */
    private emcJPanel createAgingPanel() {
        emcJPanel panel = new emcJPanel(new GridLayout(1, 1));
        panel.setBorder(BorderFactory.createTitledBorder("Ageing"));

        //Leave sufficient space for button
        this.agingTable.setPreferredSize(new Dimension(this.getWidth() - 50, 90));

        emcJScrollPane scrollPane = new emcJScrollPane(this.agingTable);
        scrollPane.setPreferredSize(new Dimension(this.getWidth() - 50, 180));

        panel.setPreferredSize(scrollPane.getPreferredSize());

        panel.add(scrollPane);

        return panel;
    }

    private emcJPanel createButtonsPanel() {
        List<emcJButton> buttons = new ArrayList<emcJButton>();

        buttons.add(new TransactionsButton(this));
        buttons.add(new emcMenuButton("Post Dated Pmt.", new DebtorsPostDatedPayments(), this, 8, false) {

            @Override
            public void doActionPerformed(ActionEvent e) {
                //Do not call super

                EMCUserData userData = getUserData();
                EMCQuery postDatedPaymentQuery = new EMCQuery(enumQueryTypes.SELECT, DebtorsPostDatedPayment.class);
                postDatedPaymentQuery.addAnd("customer", customerId);
                postDatedPaymentQuery.addAnd("processed", false);

                userData.setUserData(0, postDatedPaymentQuery);

                getDeskTop().createAndAdd(new DebtorsPostDatedPayments(), -1, -1, userData, null, 1);
            }
        });

        return emcSetGridBagConstraints.createButtonPanel(buttons);
    }

    //This button is used to open the transactions form
    class TransactionsButton extends emcJButton {

        private CustomerBalanceForm form;

        public TransactionsButton(CustomerBalanceForm form) {
            super("Transactions");
            this.form = form;
        }

        @Override
        public void doActionPerformed(ActionEvent evt) {
            EMCUserData userData = form.getUserData().copyUserData();

            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsTransactions.class);
            query.addAnd("customerId", form.customerId);
            query.addAnd("balance", 0, EMCQueryConditions.NOT);

            userData.setUserData(0, query);
            //Show allocate and deallocate buttons
            userData.setUserData(2, true);

            form.getDeskTop().createAndAdd(new DebtorsTransactionsMenu(), -1, -1, userData, null, 0);
        }
    }

    //Normal double cell renderer does not work on this form, as the table does not use and EMC table model.
    class DoubleCellRenderer extends EMCCellRenderer {

        //Should zeros be displayed?
        boolean surpressZero;
        //Decimal formatter
        DecimalFormat formatter = new DecimalFormat("#.#");

        {
            setHorizontalAlignment(JLabel.RIGHT);
        }

        /** Creates a new instance of DoubleCellRenderer */
        public DoubleCellRenderer() {
            this(2);

        }

        /** Creates a new instance of DoubleCellRenderer */
        public DoubleCellRenderer(int decimals) {
            //Configures formatter
            formatter.setMinimumFractionDigits(decimals);
            formatter.setMaximumFractionDigits(decimals);

        }

        @Override
        public void setValue(Object val) {
            try {
                setText(val == null || val.toString().equals("") || ((Double.parseDouble(val.toString()) == 0) && surpressZero) ? "" : formatter.format(Double.parseDouble(val.toString())));
            } catch (Exception ex) {
                if (EMCDebug.getDebug()) {
                    java.util.logging.Logger.getLogger("emc").log(Level.SEVERE, "Invalid parameter for setting renderEditorInformation of DoubleCellRendererInstance " + ex.getMessage(), ex);
                }
            }
        }

        @Override
        public void setRenderEditorInformation(Object renderEditorInformation) {
            this.setValue(renderEditorInformation);
        }
    }
}
