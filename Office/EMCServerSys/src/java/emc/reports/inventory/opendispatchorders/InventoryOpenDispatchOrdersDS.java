/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.inventory.opendispatchorders;

import java.math.BigDecimal;

/**
 *
 * @author wikus
 */
public class InventoryOpenDispatchOrdersDS {

    private String deliveryDate;
    private String pickingList;
    private String createdDate;
    private String customer;
    private String customerName;
    private String salesOrder;
    private String customerOrder;
    private String customerReference;
    private BigDecimal units = BigDecimal.ZERO;
    private BigDecimal packs = BigDecimal.ZERO;
    private BigDecimal cost = BigDecimal.ZERO;
    private String extDeliveryDate;

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

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

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public BigDecimal getPacks() {
        return packs;
    }

    public void setPacks(BigDecimal packs) {
        this.packs = packs;
    }

    public String getPickingList() {
        return pickingList;
    }

    public void setPickingList(String pickingList) {
        this.pickingList = pickingList;
    }

    public String getSalesOrder() {
        return salesOrder;
    }

    public void setSalesOrder(String salesOrder) {
        this.salesOrder = salesOrder;
    }

    public BigDecimal getUnits() {
        return units;
    }

    public void setUnits(BigDecimal units) {
        this.units = units;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getExtDeliveryDate() {
        return extDeliveryDate;
    }

    public void setExtDeliveryDate(String extDeliveryDate) {
        this.extDeliveryDate = extDeliveryDate;
    }
}
