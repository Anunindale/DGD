/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.debtors.display.customerinvoice.resources;

import emc.app.components.documents.EMCBigDecimalDocument;
import emc.app.components.emcJDialog;
import emc.app.components.emcJLabel;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.util.utilFunctions;
import emc.app.wsmanager.EMCWSManager;
import emc.datatypes.EMCBigDecimal;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.helpers.debtors.DebtorsInvCNTotalsHelper;
import emc.methods.debtors.ServerDebtorsMethods;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 *
 * @author riaan
 */
public class InvoiceCreditNoteTotalDialog extends emcJDialog {

    //Used to format BigDecimal values for display.
    private DecimalFormat decimalFormat;
    private Boolean invoice;

    /** Creates a new instance of InvoiceCreditNoteTotalDialog. */
    public InvoiceCreditNoteTotalDialog(String invCNNumber, boolean invoice, EMCUserData userData) {
        super(null, "Totals for " + invCNNumber);

        this.invoice = invoice;

        EMCBigDecimal bigDecimal = new EMCBigDecimal();
        this.decimalFormat = new DecimalFormat(bigDecimal.getFormatToDisplay());
        this.decimalFormat.setMaximumFractionDigits(bigDecimal.getNumberDecimalsDisplay());
        this.decimalFormat.setMinimumFractionDigits(bigDecimal.getNumberDecimalsDisplay());

        DebtorsInvCNTotalsHelper helper = getTotals(invCNNumber, userData);

        if (helper == null) {
            utilFunctions.logMessage(Level.SEVERE, "Failed to get totals.", userData);
            this.dispose();
        } else {
            this.initDialog(helper);
        }
    }

    private void initDialog(DebtorsInvCNTotalsHelper totalsHelper) {
        this.setLayout(new BorderLayout());

        EMCBigDecimal bigDec = new EMCBigDecimal();
        bigDec.setMinValue(-9999999);
        
        EMCBigDecimalDocument salesTotalDoc = new EMCBigDecimalDocument();
        salesTotalDoc.setDataType(bigDec);
        
        emcJTextField txtSalesTotal = new emcJTextField(salesTotalDoc);
        txtSalesTotal.setText(decimalFormat.format(totalsHelper.getSalesTotal()));
        txtSalesTotal.setEditable(false);

        EMCBigDecimalDocument discountTotalDoc = new EMCBigDecimalDocument();
        discountTotalDoc.setDataType(bigDec);

        emcJTextField txtDiscountTotal = new emcJTextField(discountTotalDoc);
        txtDiscountTotal.setText(decimalFormat.format(totalsHelper.getDiscountTotal()));
        txtDiscountTotal.setEditable(false);

        EMCBigDecimalDocument vatTotalDoc = new EMCBigDecimalDocument();
        vatTotalDoc.setDataType(bigDec);

        emcJTextField txtVATTotal = new emcJTextField(vatTotalDoc);
        txtVATTotal.setText(decimalFormat.format(totalsHelper.getVatTotal()));
        txtVATTotal.setEditable(false);

        EMCBigDecimalDocument invoiceTotalDoc = new EMCBigDecimalDocument();
        invoiceTotalDoc.setDataType(bigDec);

        emcJTextField txtInvoiceTotal = new emcJTextField(invoiceTotalDoc);
        txtInvoiceTotal.setText(decimalFormat.format(totalsHelper.getInvoiceTotal()));
        txtInvoiceTotal.setEditable(false);

        Component[][] components = new Component[][]{
            {new emcJLabel("Sales Total"), txtSalesTotal},
            {new emcJLabel("Discount Included"), txtDiscountTotal},
            {new emcJLabel("VAT Total"), txtVATTotal},
            {new emcJLabel("Invoice Total"), txtInvoiceTotal}
        };

        this.add(emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, true), BorderLayout.CENTER);

        this.pack();
        this.setVisible(true);
        this.requestFocus();
    }

    private DebtorsInvCNTotalsHelper getTotals(String invCNNumber, EMCUserData userData) {
        EMCCommandClass cmd;

        if (invoice) {
            cmd = new EMCCommandClass(ServerDebtorsMethods.GET_INV_TOTALS);
        } else {
            cmd = new EMCCommandClass(ServerDebtorsMethods.GET_INV_CN_TOTALS);
        }

        List toSend = new ArrayList();
        toSend.add(invCNNumber);

        toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);

        if (toSend != null && toSend.size() > 1 && toSend.get(1) instanceof DebtorsInvCNTotalsHelper) {
            return (DebtorsInvCNTotalsHelper) toSend.get(1);
        } else {
            return null;
        }
    }
}
