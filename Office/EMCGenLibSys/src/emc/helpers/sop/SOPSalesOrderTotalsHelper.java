/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.helpers.sop;

import java.math.BigDecimal;

/**
 *
 * @author riaan
 */
public class SOPSalesOrderTotalsHelper {

    private BigDecimal salesTotal;
    private BigDecimal discountTotal;
    private BigDecimal vatTotal;
    private BigDecimal invoiceTotal;
    private BigDecimal totalItems;
    
    /** Creates a new instance of DebtorsInvoiceTotalHelper. */
    public SOPSalesOrderTotalsHelper() {
        
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

    public BigDecimal getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(BigDecimal totalItems) {
        this.totalItems = totalItems;
    }
}
