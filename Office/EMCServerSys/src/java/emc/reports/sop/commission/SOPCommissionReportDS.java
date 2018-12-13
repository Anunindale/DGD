/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.sop.commission;

import java.math.BigDecimal;

/**
 *
 * @author wikus
 */
public class SOPCommissionReportDS {

    private String salesRep;
    private String salesRepName;
    private String customerItemHeader1;
    private String customerItemHeader2;
    private String customerId;
    private String customerName;
    private String customerItemField1;
    private String customerItemField2;
    private String invoiceNo;
    private String salesOrderNo;
    private String customerOrderNo;
    private BigDecimal value = BigDecimal.ZERO;
    private BigDecimal commissionPerc = BigDecimal.ZERO;
    private BigDecimal commissionValue = BigDecimal.ZERO;

    public BigDecimal getCommissionPerc() {
        return commissionPerc;
    }

    public void setCommissionPerc(BigDecimal commissionPerc) {
        this.commissionPerc = commissionPerc;
    }

    public BigDecimal getCommissionValue() {
        return commissionValue;
    }

    public void setCommissionValue(BigDecimal commissionValue) {
        this.commissionValue = commissionValue;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerItemField1() {
        return customerItemField1;
    }

    public void setCustomerItemField1(String customerItemField1) {
        this.customerItemField1 = customerItemField1;
    }

    public String getCustomerItemField2() {
        return customerItemField2;
    }

    public void setCustomerItemField2(String customerItemField2) {
        this.customerItemField2 = customerItemField2;
    }

    public String getCustomerItemHeader1() {
        return customerItemHeader1;
    }

    public void setCustomerItemHeader1(String customerItemHeader1) {
        this.customerItemHeader1 = customerItemHeader1;
    }

    public String getCustomerItemHeader2() {
        return customerItemHeader2;
    }

    public void setCustomerItemHeader2(String customerItemHeader2) {
        this.customerItemHeader2 = customerItemHeader2;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerOrderNo() {
        return customerOrderNo;
    }

    public void setCustomerOrderNo(String customerOrderNo) {
        this.customerOrderNo = customerOrderNo;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
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

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
