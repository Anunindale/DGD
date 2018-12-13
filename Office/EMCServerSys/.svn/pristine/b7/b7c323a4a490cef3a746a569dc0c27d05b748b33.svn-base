/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.debtors.customerinvoice;

import emc.entity.debtors.DebtorsCustomerInvoiceLines;
import emc.entity.debtors.DebtorsCustomerInvoiceMaster;
import emc.enums.debtors.DebtorsInvoiceCreditNoteType;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCEntityBeanProtectedInterface;
import emc.framework.EMCUserData;
import emc.helpers.debtors.DebtorsInvCNTotalsHelper;
import java.math.BigDecimal;
import javax.ejb.Local;

/**
 *
 * @author riaan
 */
@Local
public interface DebtorsCustomerInvoiceMasterLocal extends EMCEntityBeanLocalInterface, EMCEntityBeanProtectedInterface {

    /**
     * Returns the customer invoice record with the given invoice no.
     *
     * @param invoiceNo Invoice number.
     * @param userData User data.
     * @return A DebtorsCustomerInvoiceMaster instance, or null, if none is
     * found.
     */
    public DebtorsCustomerInvoiceMaster getInvoiceMaster(String invoiceNo, EMCUserData userData);

    /**
     * Posts the specified invoice.
     *
     * @param invoiceNo Invoice number of invoice to post.
     * @param userData User data.
     * @return A boolean indicating whether the invoice was posted succesfully.
     * @throws EMCEntityBeanException
     */
    public boolean postInvoice(String invoiceNo, EMCUserData userData) throws EMCEntityBeanException;

    /**
     * Returns the total of the lines on the specified Invoice.
     *
     * @param invoiceNo Invoice number to sum on.
     * @param includeVAT Indicates whether VAT should be included in the
     * returned total.
     * @param userData User data.
     * @return The total of the lines on the specified Invoice.
     */
    public BigDecimal getInvoiceTotal(String invoiceNo, boolean includeVAT, EMCUserData userData);

    /**
     * Returns the total VAT of the lines on the specified Invoice.
     *
     * @param invoiceNo Invoice number to sum on.
     * @param userData User data.
     * @return The total of the lines on the specified Invoice.
     */
    public BigDecimal getTotalVAT(String invoiceNo, EMCUserData userData);

    public void invoicePrinted(String invoiceNo, DebtorsInvoiceCreditNoteType type, EMCUserData userData) throws EMCEntityBeanException;

    /**
     * Gets total discount on an invoice.
     *
     * @param invoiceNo Invoice number.
     * @param userData User data.
     * @return Total discount on the specified invoice.
     */
    public BigDecimal getDiscountTotal(String invoiceNo, EMCUserData userData);

    /**
     * Returns a helper class instance containing Invoice/Credit Note totals.
     *
     * @param invoiceNumber Invoice/Credit Note number to get totals for.
     * @param userData User data.
     * @return A DebtorsInvoiceTotalsHelper instance.
     */
    public DebtorsInvCNTotalsHelper getInvoiceTotalHelper(String invoiceNumber, EMCUserData userData);

    /**
     * Returns a line for the delivery charge on the specified invoice contains
     * the specified item. This is used to ensure that a delivery charge item is
     * only added to an Invoice once. The specified item must only occur on the
     * invoice once.
     *
     * @param invoiceNumber Invoice number.
     * @param itemId Delivery charge item id.
     * @param userData User data.
     * @return Delivery charge line on the specified Invoice, or null, if it is
     * not found.
     */
    public DebtorsCustomerInvoiceLines getDeliveryChargeLine(String invoiceNumber, String itemId, EMCUserData userData);

    public BigDecimal getInvoiceOnlyTotal(String invoiceNo, EMCUserData userData);

    public BigDecimal getInvoiceOnlyDiscountTotal(String invoiceNo, EMCUserData userData);

    public BigDecimal getInvoiceOnlyTotalVAT(String invoiceNo, EMCUserData userData);

    public DebtorsInvCNTotalsHelper getInvoiceOnlyTotalHelper(String invoiceNumber, EMCUserData userData);

    public boolean cancelInvoice(String invCNNumber, EMCUserData userData) throws EMCEntityBeanException;
}
