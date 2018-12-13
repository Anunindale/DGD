/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.debtors.invoiceregister;

import java.math.BigDecimal;

/**
 *
 * @author wikus
 */
public class DebtorsInvoiceRegisterReportDS {

    private String customrtId;
    private String customerName;
    private String invoiceDate;
    private String invoiceNo;
    private String salesOrderNo;
    private String salesRep;
    private String salesRepName;
    private BigDecimal sundri;
    private BigDecimal salesValue;
    private BigDecimal vatAmount;
    private BigDecimal invoiceTotal;
    private BigDecimal grossTotal;
    private BigDecimal discountTotal;
    private String repBreak;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomrtId() {
        return customrtId;
    }

    public void setCustomrtId(String customrtId) {
        this.customrtId = customrtId;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public BigDecimal getInvoiceTotal() {
        return invoiceTotal;
    }

    public void setInvoiceTotal(BigDecimal invoiceTotal) {
        this.invoiceTotal = invoiceTotal;
    }

    public String getRepBreak() {
        return repBreak;
    }

    public void setRepBreak(String repBreak) {
        this.repBreak = repBreak;
    }

    public String getSalesOrderNo() {
        return salesOrderNo;
    }

    public void setSalesOrderNo(String salesOrderNo) {
        this.salesOrderNo = salesOrderNo;
    }

    public String getSalesRep() {
        return salesRep;
    }

    public void setSalesRep(String salesRep) {
        this.salesRep = salesRep;
    }

    public String getSalesRepName() {
        return salesRepName;
    }

    public void setSalesRepName(String salesRepName) {
        this.salesRepName = salesRepName;
    }

    public BigDecimal getSalesValue() {
        return salesValue;
    }

    public void setSalesValue(BigDecimal salesValue) {
        this.salesValue = salesValue;
    }

    public BigDecimal getVatAmount() {
        return vatAmount;
    }

    public void setVatAmount(BigDecimal vatAmount) {
        this.vatAmount = vatAmount;
    }

    public BigDecimal getSundri() {
        return sundri;
    }

    public void setSundri(BigDecimal sundri) {
        this.sundri = sundri;
    }

    public BigDecimal getGrossTotal() {
        return grossTotal;
    }

    public void setGrossTotal(BigDecimal grossTotal) {
        this.grossTotal = grossTotal;
    }

    public BigDecimal getDiscountTotal() {
        return discountTotal;
    }

    public void setDiscountTotal(BigDecimal discountTotal) {
        this.discountTotal = discountTotal;
    }
}
