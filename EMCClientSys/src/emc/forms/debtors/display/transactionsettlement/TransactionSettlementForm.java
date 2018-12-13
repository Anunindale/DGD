/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.debtors.display.transactionsettlement;

import emc.app.components.emcJButton;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.wsmanager.EMCWSManager;
import emc.entity.debtors.transactionsettlement.DebtorsTransactionSettlement;
import emc.entity.sop.SOPCustomers;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.forms.debtors.display.transactionsettlement.resources.AllocateButton;
import emc.forms.debtors.display.transactionsettlement.resources.TransactionSettlementDRM;
import emc.forms.debtors.display.transactionsettlement.resources.TransactionSettlementLookup;
import emc.framework.EMCCommandClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.methods.debtors.ServerDebtorsMethods;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * @description : This form is used to settle Debtors transactions.
 *
 * @date        : 10 Jun 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class TransactionSettlementForm extends BaseInternalFrame {

    private TransactionSettlementLookup lkpCustomer;
    private emcJTextField txtCustomerName = new emcJTextField();
    private TransactionSettlementDRM debitDRM;
    private TransactionSettlementDRM creditDRM;
    //Current session id.
    private long sessionId;

    /** Creates a new instance of TransactionSettlementForm */
    public TransactionSettlementForm(EMCUserData userData) {
        super("Transaction Allocation", true, true, true, true, userData);
        this.setBounds(20, 20, 1000, 400);

        //Passed from customer form.
        String customerId = (String) userData.getUserData(2);
        Long sessionId = (Long) userData.getUserData(3);

        if (!Functions.checkBlank(customerId)) {
            if (sessionId != null) {
                //Remove session id from user data list
                userData.getUserData().remove(3);
            }
            
            //Remove customer from user data list
            userData.getUserData().remove(2);
            //Remove blank Strings from user data list
            if ("".equals(userData.getUserData(1))) {
                userData.getUserData().remove(1);
            }
            if ("".equals(userData.getUserData(0))) {
                userData.getUserData().remove(0);
            }
        } else {
            //Query to select no data.
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsTransactionSettlement.class);
            query.addAnd("sessionId", null);

            userData.setUserData(0, query);
        }

        setupDRM(userData);
        initComponents(userData);

        initFrame();

        if (!Functions.checkBlank(customerId)) {
            if (sessionId == null) {
                lkpCustomer.setValue(customerId);
            } else {
                lkpCustomer.setValueDoNotCreateData(customerId, sessionId);
            }
        }
    }

    /** Sets up data relation managers. */
    private void setupDRM(EMCUserData userData) {
        this.debitDRM = new TransactionSettlementDRM(new emcGenericDataSourceUpdate(enumEMCModules.DEBTORS.getId(), new DebtorsTransactionSettlement(), userData), userData);
        this.creditDRM = new TransactionSettlementDRM(new emcGenericDataSourceUpdate(enumEMCModules.DEBTORS.getId(), new DebtorsTransactionSettlement(), userData), userData);

        this.debitDRM.setTheForm(this);
        this.creditDRM.setTheForm(this);

        this.debitDRM.setFormTextId1("referenceType");
        this.debitDRM.setFormTextId2("referenceNumber");

        this.creditDRM.setFormTextId1("referenceType");
        this.creditDRM.setFormTextId2("referenceNumber");

        this.debitDRM.setOtherDRM(creditDRM);
        this.creditDRM.setOtherDRM(debitDRM);

    //DRM is not set on this here.  See DRM setHasTheFocus() method
    }

    /** Initializes components. */
    private void initComponents(EMCUserData userData) {
        this.lkpCustomer = new TransactionSettlementLookup(this, txtCustomerName);
        this.lkpCustomer.setPopup(new EMCLookupPopup(new SOPCustomers(), "customerId", userData));

        this.txtCustomerName.setEditable(false);
    }

    /** Initializes the frame. */
    private void initFrame() {
        this.setLayout(new BorderLayout(10, 10));
        emcJPanel mainPanel = new emcJPanel(new BorderLayout(10, 10));

        mainPanel.add(createTopPanel(), BorderLayout.NORTH);
        mainPanel.add(createBottomPanel(), BorderLayout.CENTER);

        mainPanel.setBorder(BorderFactory.createTitledBorder("Transaction Allocation"));

        this.add(mainPanel, BorderLayout.CENTER);
    }

    /** Creates the top part of the frame. */
    private emcJPanel createTopPanel() {
        emcJPanel pnlTop = new emcJPanel(new BorderLayout(10, 10));
        pnlTop.setPreferredSize(new Dimension(920, 25));

        emcJPanel pnlLookup = new emcJPanel(new GridLayout(1, 3, 10, 10));
        pnlLookup.add(new emcJLabel("Customer"));
        pnlLookup.add(lkpCustomer);
        pnlLookup.add(txtCustomerName);

        pnlTop.add(pnlLookup, BorderLayout.WEST);

        List<emcJButton> buttons = new ArrayList<emcJButton>();
        buttons.add(new AllocateButton(this));

        pnlTop.add(emcSetGridBagConstraints.createButtonPanel(buttons, false), BorderLayout.EAST);

        return pnlTop;
    }

    /** Creates the bottom part of the frame. */
    private emcJPanel createBottomPanel() {
        emcJPanel bottomPanel = new emcJPanel(new BorderLayout(5, 5));

        bottomPanel.add(createDebitPanel(), BorderLayout.WEST);
        bottomPanel.add(createCreditPanel(), BorderLayout.EAST);

        return bottomPanel;
    }

    /** Creates the debit panel. */
    private emcJPanel createDebitPanel() {
        emcJPanel panel = new emcJPanel(new BorderLayout());

        JLabel label = new JLabel("Debit");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label, BorderLayout.NORTH);

        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        List<String> keys = new ArrayList<String>();
        keys.add("tick");
        keys.add("referenceType");
        keys.add("referenceNumber");
        keys.add("transactionDate");
        keys.add("customerOrderNumber");
        keys.add("debitBalance");
        keys.add("debitAmountSettled");
        keys.add("discAvail");
        keys.add("discTaken");
        keys.add("rebate");

        emcTableModelUpdate model = new emcTableModelUpdate(debitDRM, keys);

        emcJTableUpdate table = new emcJTableUpdate(model) {

            @Override
            public boolean isCellEditable(int row, int column) {
                int tickCol = tableModel.getColumnByFieldName("tick");
                int settledAmountCol = tableModel.getColumnByFieldName("debitAmountSettled");

                if (column == tickCol) {
                    return debitDRM.canTickRow(row);
                } else if (column == settledAmountCol) {
                    return debitDRM.canChangeSettledQuantity(row);
                } else {
                    return super.isCellEditable(row, column);
                }
            }
        };

        emcTablePanelUpdate tablePanel = new emcTablePanelUpdate(table);

        debitDRM.setMainTableComponent(table);
        debitDRM.setTablePanel(tablePanel);

        panel.add(tablePanel, BorderLayout.CENTER);

        panel.setPreferredSize(new Dimension(590, 370));

        return panel;
    }

    /** Creates the credit panel. */
    private emcJPanel createCreditPanel() {
        emcJPanel panel = new emcJPanel(new BorderLayout());

        JLabel label = new JLabel("Credit");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label, BorderLayout.NORTH);

        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        List<String> keys = new ArrayList<String>();
        keys.add("tick");
        keys.add("referenceType");
        keys.add("referenceNumber");
        keys.add("originalCredit");
        keys.add("creditBalance");
        keys.add("creditAmountSettled");

        emcTableModelUpdate model = new emcTableModelUpdate(creditDRM, keys);

        emcJTableUpdate table = new emcJTableUpdate(model) {

            @Override
            public boolean isCellEditable(int row, int column) {
                int tickCol = tableModel.getColumnByFieldName("tick");
                int settledAmountCol = tableModel.getColumnByFieldName("creditAmountSettled");

                if (column == tickCol) {
                    return creditDRM.canTickRow(row);
                } else if (column == settledAmountCol) {
                    return creditDRM.canChangeSettledQuantity(row);
                } else {
                    return super.isCellEditable(row, column);
                }
            }
        };

        emcTablePanelUpdate tablePanel = new emcTablePanelUpdate(table);

        creditDRM.setMainTableComponent(table);
        creditDRM.setTablePanel(tablePanel);

        panel.add(tablePanel, BorderLayout.CENTER);

        panel.setPreferredSize(new Dimension(380, 370));

        return panel;
    }

    /** Generates and displays data for the specified customer.  If a session id is passed to this method, it will be used.  If not, new data will be generated.*/
    public void loadData(String customerId, Long sessionId, EMCUserData userData) {
        if (this.getSessionId() != 0) {
            //Clear previous settlement
            this.clearSettlement();
        }

        if (sessionId == null) {
            EMCCommandClass cmd = new EMCCommandClass(ServerDebtorsMethods.POPULATE_SETTLEMENT);

            List toSend = new ArrayList();
            toSend.add(customerId);

            toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);

            if (toSend != null && toSend.size() > 1 && toSend.get(1) instanceof Long) {
                sessionId = (Long) toSend.get(1);
            } else {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to populate transaction settlement table.", userData);
                return;
            }
        }

        this.sessionId = sessionId;

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsTransactionSettlement.class);
        query.addAnd("sessionId", sessionId);

        EMCUserData debitUserData = userData.copyUserData();
        EMCQuery debitQuery = query.copyQuery();
        debitQuery.addAnd("debit", 0, EMCQueryConditions.GREATER_THAN);

        debitUserData.setUserData(0, debitQuery);

        this.debitDRM.setUserData(debitUserData);
        //Prevents ordering/sorting from breaking.
        this.debitDRM.setOriginalQuery(debitQuery);

        EMCUserData creditUserData = userData.copyUserData();
        EMCQuery creditQuery = query.copyQuery();
        creditQuery.addAnd("credit", 0, EMCQueryConditions.GREATER_THAN);

        creditUserData.setUserData(0, creditQuery);

        this.creditDRM.setUserData(creditUserData);
        //Prevents ordering/sorting from breaking.
        this.creditDRM.setOriginalQuery(creditQuery);

        this.creditDRM.resetEditable();
        this.debitDRM.resetEditable();
    }

    /** Returns current session id. */
    public long getSessionId() {
        return sessionId;
    }

    /** Returns the current customer id. */
    public String getCustomerId() {
        return (String) lkpCustomer.getValue();
    }

    @Override
    public boolean doSaveOnClose() {
        boolean ret = super.doSaveOnClose();

        if (ret) {
            clearSettlement();
        }

        return ret;
    }

    /** Clears current settlement. */
    public void clearSettlement() {
        //Delete settlement records
        EMCCommandClass cmd = new EMCCommandClass(ServerDebtorsMethods.CLEAR_SETTLEMENT);

        List toSend = new ArrayList();
        toSend.add(getSessionId());

        EMCWSManager.executeGenericWS(cmd, toSend, getUserData());
    }
}
