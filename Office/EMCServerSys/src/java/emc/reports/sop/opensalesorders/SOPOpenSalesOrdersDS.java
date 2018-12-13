/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.sop.opensalesorders;

import java.math.BigDecimal;

/**
 *
 * @author wikus
 */
public class SOPOpenSalesOrdersDS {

    private String originalDate;
    private String requiredDate;
    private String salesOrder;
    private String status;
    private String customer;
    private String customerName;
    private String customerOrder;
    private String customerReference;
    private String salesRep;
    private BigDecimal units = BigDecimal.ZERO;
    private BigDecimal packs = BigDecimal.ZERO;
    private BigDecimal value = BigDecimal.ZERO;

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerOrder() {
        return customerOrder;
    }

    public void setCustomerOrder(String customerOrder) {
        this.customerOrder = customerOrder;
    }

    public String getCustomerReference() {
        return customerReference;
    }

    public void setCustomerReference(String customerReference) {
        this.customerReference = customerReference;
    }

    public BigDecimal getPacks() {
        return packs;
    }

    public void setPacks(BigDecimal packs) {
        this.packs = packs;
    }

    public String getRequiredDate() {
        return requiredDate;
    }

    public void setRequiredDate(String requiredDate) {
        this.requiredDate = requiredDate;
    }

    public String getSalesOrder() {
        return salesOrder;
    }

    public void setSalesOrder(String salesOrder) {
        this.salesOrder = salesOrder;
    }

    public String getSalesRep() {
        return salesRep;
    }

    public void setSalesRep(String salesRep) {
        this.salesRep = salesRep;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getUnits() {
        return units;
    }

    public void setUnits(BigDecimal units) {
        this.units = units;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getOriginalDate() {
        return originalDate;
    }

    public void setOriginalDate(String originalDate) {
        this.originalDate = originalDate;
    }
}
