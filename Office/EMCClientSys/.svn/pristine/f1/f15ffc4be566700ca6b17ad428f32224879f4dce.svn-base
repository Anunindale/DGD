/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.sop.display.customeractivity;

import emc.app.components.emcJButton;
import emc.app.components.emcJLabel;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcpicker.emcdatepicker.EMCDatePicker;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.wsmanager.EMCWSManager;
import emc.datatypes.EMCBigDecimal;
import emc.entity.debtors.DebtorsCreditNoteMaster;
import emc.entity.debtors.DebtorsCustomerInvoiceMaster;
import emc.entity.debtors.DebtorsTransactions;
import emc.entity.sop.SOPCustomers;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.helpers.customeractivity.CustomerActivityHelper;
import emc.menus.debtors.menuitems.display.DebtorsCustomerInvoices;
import emc.menus.debtors.menuitems.display.DebtorsTransactionsMenu;
import emc.menus.sop.menuitems.display.SOPCustomersMenu;
import emc.methods.sop.ServerSOPMethods;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @description : This form is used to show customer activity.
 *
 * @date        : 07 Jul 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class CustomerActivityForm extends BaseInternalFrame {

    private emcJLabel lblCustomerId = new emcJLabel("Customer");
    private emcJTextField txtCustomerId = new emcJTextField();
    private emcJLabel lblCustomerName = new emcJLabel("Customer Name");
    private emcJTextField txtCustomerName = new emcJTextField();
    private emcJLabel lblNumberOfOpenOrders = new emcJLabel("Number of Open Orders");
    private emcJTextField txtNumberOfOpenOrders = new emcJTextField();
    private emcJLabel lblOpenOrderValue = new emcJLabel("Open Order Value");
    private emcJTextField txtOpenOrderValue = new emcJTextField();
    private emcJLabel lblLastInvoice = new emcJLabel("Last Invoice No.");
    private emcJTextField txtLastInvoice = new emcJTextField();
    private emcJLabel lblLastInvoiceDate = new emcJLabel("Last Invoice Date");
    private EMCDatePicker dpkLastInvoiceDate = new EMCDatePicker();
    private emcJLabel lblLastInvoiceAmount = new emcJLabel("Last Invoice Amount");
    private emcJTextField txtLastInvoiceAmount = new emcJTextField();
    private emcJLabel lblLastCreditNote = new emcJLabel("Last Credit Note No.");
    private emcJTextField txtLastCreditNote = new emcJTextField();
    private emcJLabel lblLastCreditNoteDate = new emcJLabel("Last Credit Note Date");
    private EMCDatePicker dpkLastCreditNoteDate = new EMCDatePicker();
    private emcJLabel lblLastCreditNoteAmount = new emcJLabel("Last Credit Note Amount");
    private emcJTextField txtLastCreditNoteAmount = new emcJTextField();
    private emcJLabel lblLastSalesOrder = new emcJLabel("Last Sales Order No.");
    private emcJTextField txtLastSalesOrder = new emcJTextField();
    private emcJLabel lblLastSalesOrderDate = new emcJLabel("Last Sales Order Date");
    private EMCDatePicker dpkLastSalesOrderDate = new EMCDatePicker();
    private emcJLabel lblLastSalesOrderAmount = new emcJLabel("Last Sales Order Amount");
    private emcJTextField txtLastSalesOrderAmount = new emcJTextField();
    private emcJLabel lblLastPayment = new emcJLabel("Last Payment No.");
    private emcJTextField txtLastPayment = new emcJTextField();
    private emcJLabel lblLastPaymentDate = new emcJLabel("Last Payment Date");
    private EMCDatePicker dpkLastPaymentDate = new EMCDatePicker();
    private emcJLabel lblLastPaymentAmount = new emcJLabel("Last Payment Amount");
    private emcJTextField txtLastPaymentAmount = new emcJTextField();
    private emcJLabel lblLastReturnedPayment = new emcJLabel("Last RD Payment No.");
    private emcJTextField txtLastReturnedPayment = new emcJTextField();
    private emcJLabel lblLastReturnedPaymentDate = new emcJLabel("Last RD Payment Date");
    private EMCDatePicker dpkLastReturnedPaymentDate = new EMCDatePicker();
    private emcJLabel lblLastReturnedPaymentAmount = new emcJLabel("Last RD Payment Amount");
    private emcJTextField txtLastReturnedPaymentAmount = new emcJTextField();
    //Used to format BigDecimal values for display.
    private DecimalFormat decimalFormat;

    /** Creates a new instance of CustomerActivityForm */
    public CustomerActivityForm(EMCUserData userData) {
        super("Customer Activity", true, true, true, true, userData);
        this.setBounds(20, 20, 800, 500);

        String customerId = (String) userData.getUserData(3);

        this.setupComponents(userData);
        this.setupForm();
        this.populateForm(customerId, userData);
    }

    /** Sets up components. */
    private void setupComponents(EMCUserData userData) {
        txtCustomerId.setEditable(false);
        txtCustomerName.setEditable(false);
        txtNumberOfOpenOrders.setEditable(false);
        txtOpenOrderValue.setEditable(false);
        txtLastInvoice.setEditable(false);
        dpkLastInvoiceDate.setEnabled(false);
        txtLastInvoiceAmount.setEditable(false);
        txtLastCreditNote.setEditable(false);
        dpkLastCreditNoteDate.setEnabled(false);
        txtLastCreditNoteAmount.setEditable(false);
        txtLastSalesOrder.setEditable(false);
        dpkLastSalesOrderDate.setEnabled(false);
        txtLastSalesOrderAmount.setEditable(false);
        txtLastPayment.setEditable(false);
        dpkLastPaymentDate.setEnabled(false);
        txtLastPaymentAmount.setEditable(false);
        txtLastReturnedPayment.setEditable(false);
        dpkLastReturnedPaymentDate.setEnabled(false);
        txtLastReturnedPaymentAmount.setEditable(false);

        EMCBigDecimal bigDecimal = new EMCBigDecimal();
        this.decimalFormat = new DecimalFormat(bigDecimal.getFormatToDisplay());
        this.decimalFormat.setMaximumFractionDigits(bigDecimal.getNumberDecimalsDisplay());
        this.decimalFormat.setMinimumFractionDigits(bigDecimal.getNumberDecimalsDisplay());

        this.txtCustomerId.enableRightClickPopup(this, "customerId", SOPCustomers.class, new SOPCustomersMenu(), userData);
        this.txtLastCreditNote.enableRightClickPopup(this, "invCNNumber", DebtorsCreditNoteMaster.class, new DebtorsCustomerInvoices(), userData);
        this.txtLastInvoice.enableRightClickPopup(this, "invCNNumber", DebtorsCustomerInvoiceMaster.class, new DebtorsCustomerInvoices(), userData);

        //User data used when opening transactions form.  Show allocate and deallocate buttons.
        EMCUserData txUserData = userData.copyUserData();
        txUserData.setUserData(2, true);

        this.txtLastPayment.enableRightClickPopup(this, "referenceNumber", DebtorsTransactions.class, new DebtorsTransactionsMenu(), txUserData);
        this.txtLastReturnedPayment.enableRightClickPopup(this, "referenceNumber", DebtorsTransactions.class, new DebtorsTransactionsMenu(), txUserData);
    }

    /** Sets up the activity form. */
    private void setupForm() {
        Component[][] components = new Component[][]{
            {lblCustomerId, txtCustomerId},
            {lblCustomerName, txtCustomerName},
            {new emcJLabel()},
            {lblNumberOfOpenOrders, txtNumberOfOpenOrders},
            {lblOpenOrderValue, txtOpenOrderValue},
            {new emcJLabel()},
            {lblLastInvoice, txtLastInvoice, lblLastCreditNote, txtLastCreditNote},
            {lblLastInvoiceDate, dpkLastInvoiceDate, lblLastCreditNoteDate, dpkLastCreditNoteDate},
            {lblLastInvoiceAmount, txtLastInvoiceAmount, lblLastCreditNoteAmount, txtLastCreditNoteAmount},
            {new emcJLabel()},
            {lblLastSalesOrder, txtLastSalesOrder},
            {lblLastSalesOrderDate, dpkLastSalesOrderDate},
            {lblLastSalesOrderAmount, txtLastSalesOrderAmount},
            {new emcJLabel()},
            {lblLastPayment, txtLastPayment, lblLastReturnedPayment, txtLastReturnedPayment},
            {lblLastPaymentDate, dpkLastPaymentDate, lblLastReturnedPaymentDate, dpkLastReturnedPaymentDate},
            {lblLastPaymentAmount, txtLastPaymentAmount, lblLastReturnedPaymentAmount, txtLastReturnedPaymentAmount},};

        this.add(emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, true, "Customer Activity"), BorderLayout.CENTER);
    }

    /** Populates the activity form. */
    private void populateForm(String customerId, EMCUserData userData) {
        EMCCommandClass cmd = new EMCCommandClass(ServerSOPMethods.GET_CUSTOMER_ACTIVITY);

        List toSend = new ArrayList();
        toSend.add(customerId);

        toSend = EMCWSManager.executeGenericWS(cmd, toSend, getUserData());

        if (toSend != null && toSend.size() > 1 && toSend.get(1) instanceof CustomerActivityHelper) {
            CustomerActivityHelper activityHelper = (CustomerActivityHelper) toSend.get(1);

            txtCustomerId.setText(activityHelper.getCustomerId());
            txtCustomerName.setText(activityHelper.getCustomerName());
            txtNumberOfOpenOrders.setText(String.valueOf(activityHelper.getNumberOfOpenOrders()));
            txtOpenOrderValue.setText(decimalFormat.format(activityHelper.getOpenOrderValue()));
            txtLastInvoice.setText(activityHelper.getLastInvoiceNo());
            dpkLastInvoiceDate.setDate(activityHelper.getLastInvoiceDate());
            txtLastInvoiceAmount.setText(decimalFormat.format(activityHelper.getLastInvoiceAmount()));
            txtLastCreditNote.setText(activityHelper.getLastCreditNoteNo());
            dpkLastCreditNoteDate.setDate(activityHelper.getLastCreditNoteDate());
            txtLastCreditNoteAmount.setText(decimalFormat.format(activityHelper.getLastCreditNoteAmount()));
            txtLastSalesOrder.setText(activityHelper.getLastSalesOrderNo());
            dpkLastSalesOrderDate.setDate(activityHelper.getLastSalesOrderDate());
            txtLastSalesOrderAmount.setText(decimalFormat.format(activityHelper.getLastSalesOrderAmount()));
            txtLastPayment.setText(activityHelper.getLastPaymentNo());
            dpkLastPaymentDate.setDate(activityHelper.getLastPaymentDate());
            txtLastPaymentAmount.setText(decimalFormat.format(activityHelper.getLastPaymentAmount()));
            txtLastReturnedPayment.setText(activityHelper.getLastReturnedPaymentNo());
            dpkLastReturnedPaymentDate.setDate(activityHelper.getLastReturnedPaymentDate());
            txtLastReturnedPaymentAmount.setText(decimalFormat.format(activityHelper.getLastReturnedPaymentAmount()));
        } else {
            Logger.getLogger("emc").log(Level.SEVERE, "Failed to get customer activity.", getUserData());
        }
    }
}
