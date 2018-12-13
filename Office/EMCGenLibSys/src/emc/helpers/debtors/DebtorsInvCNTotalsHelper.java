/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.helpers.debtors;

import java.math.BigDecimal;

/**
 *
 * @author riaan
 */
public class DebtorsInvCNTotalsHelper {

    private BigDecimal salesTotal;
    private BigDecimal discountTotal;
    private BigDecimal vatTotal;
    private BigDecimal invoiceTotal;
    
    /** Creates a new instance of DebtorsInvoiceTotalHelper. */
    public DebtorsInvCNTotalsHelper() {
        
    }

    public BigDecimal getSalesTotal() {
        return salesTotal;
    }

    public void setSalesTotal(BigDecimal salesTotal) {
        this.salesTotal = salesTotal;
    }

    public BigDecimal getDiscountTotal() {
        return discountTotal;
    }

    public void setDiscountTotal(BigDecimal discountTotal) {
        this.discountTotal = discountTotal;
    }

    public BigDecimal getVatTotal() {
        return vatTotal;
    }

    public void setVatTotal(BigDecimal vatTotal) {
        this.vatTotal = vatTotal;
    }

    public BigDecimal getInvoiceTotal() {
        return invoiceTotal;
    }

    public void setInvoiceTotal(BigDecimal invoiceTotal) {
        this.invoiceTotal = invoiceTotal;
    }
}
