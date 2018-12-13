/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.pop.purchaseordersuperclass;

import emc.datatypes.base.unitsofmeasure.foreignkeys.UnitOfMeasureFK;
import emc.datatypes.inventory.TransactionNumber;
import emc.datatypes.inventory.itemmaster.foreignkeys.ItemIdFK;
import emc.datatypes.inventory.warehouses.foreignkeys.WarehouseFKNotMandatory;
import emc.datatypes.pop.costchange.ReasonIdFKNotManditory;
import emc.datatypes.pop.purchaseorderlines.DeliveryDate;
import emc.datatypes.pop.purchaseorderlines.DiscountPercentage;
import emc.datatypes.pop.purchaseorderlines.ItemDimension1;
import emc.datatypes.pop.purchaseorderlines.ItemDimension2;
import emc.datatypes.pop.purchaseorderlines.ItemDimension3;
import emc.datatypes.pop.purchaseorderlines.ItemPrice;
import emc.datatypes.pop.purchaseorderlines.ItemsInvoiced;
import emc.datatypes.pop.purchaseorderlines.ItemsReceived;
import emc.datatypes.pop.purchaseorderlines.NetAmount;
import emc.datatypes.pop.purchaseorderlines.Quantity;
import emc.datatypes.pop.purchaseorderlines.ReceiveNow;
import emc.datatypes.pop.purchaseordermaster.foreignkeys.PurchaseOrderIdFKCascadeUseBean;
import emc.datatypes.systemwide.Comments;
import emc.datatypes.systemwide.LineNo;
import emc.datatypes.systemwide.VATCode;
import emc.framework.EMCEntityClass;
import java.util.Date;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author riaan
 */
@Entity
public class POPPurchaseOrderLinesSuper extends EMCEntityClass {

    private String purchaseOrderId;
    private double lineNo;
    private String itemId;
    private String itemName;
    private String serialNo;
    private String batchNo;
    private double quantity;
    private String uom;
    private double itemPrice;
    private double discountPercentage;
    private double netAmount;
    private String warehouse;
    private String comments;
    @Temporal(TemporalType.DATE)
    private Date deliveryDate;
    @Temporal(TemporalType.DATE)
    private Date confirmedDate;
    private String itemVatGroup;
    private String vatCode;
    private String itemDimension1;
    private String itemDimension2;
    private String itemDimension3;
    private double itemsInvoiced;
    private double itemsReceived;
    private double receiveNow;
    private String transactionNumber;
    private String reason;
    private Boolean costChange;
    private Boolean updateItem;
    private String whoApproved;
    private Double oldPrice;
    @Temporal(TemporalType.DATE)
    private Date dateApproved;
    private double overReceiveQty;
    /** The record id of the blanket order line from which a purchase order line was created. */
    private Long blanketOrderLineId;
    @Temporal(TemporalType.DATE)
    private Date revisedDate;

    /** Creates a new instance of POPPurchaseOrderLines */
    public POPPurchaseOrderLinesSuper() {
        //Prevent relations from breaking
        this.setDataSource(true);
    }

    public String getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(String purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getLineNo() {
        return lineNo;
    }

    public void setLineNo(double lineNo) {
        this.lineNo = lineNo;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public double getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(double netAmount) {
        this.netAmount = netAmount;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Date getConfirmedDate() {
        return confirmedDate;
    }

    public void setConfirmedDate(Date confirmedDate) {
        this.confirmedDate = confirmedDate;
    }

    public String getItemVatGroup() {
        return itemVatGroup;
    }

    public void setItemVatGroup(String itemVatGroup) {
        this.itemVatGroup = itemVatGroup;
    }

    public double getItemsReceived() {
        return itemsReceived;
    }

    public void setItemsReceived(double itemsReceived) {
        this.itemsReceived = itemsReceived;
    }

    public double getItemsInvoiced() {
        return itemsInvoiced;
    }

    public void setItemsInvoiced(double itemsInvoiced) {
        this.itemsInvoiced = itemsInvoiced;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getWhoApproved() {
        return whoApproved;
    }

    public void setWhoApproved(String whoApproved) {
        this.whoApproved = whoApproved;
    }

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();

        toBuild.put("uom", new UnitOfMeasureFK());
        toBuild.put("itemId", new ItemIdFK());
        toBuild.put("purchaseOrderId", new PurchaseOrderIdFKCascadeUseBean());
        //toBuild.put("itemName", new ItemName());
        toBuild.put("quantity", new Quantity());
        toBuild.put("uom", new UnitOfMeasureFK());
        toBuild.put("itemDimension1", new ItemDimension1());
        toBuild.put("itemDimension2", new ItemDimension2());
        toBuild.put("itemDimension3", new ItemDimension3());
        toBuild.put("itemPrice", new ItemPrice());
        toBuild.put("netAmount", new NetAmount());
        toBuild.put("discountPercentage", new DiscountPercentage());
        toBuild.put("transactionNumber", new TransactionNumber());
        toBuild.put("lineNo", new LineNo());
        toBuild.put("warehouse", new WarehouseFKNotMandatory());
        toBuild.put("comments", new Comments());
        toBuild.put("itemsReceived", new ItemsReceived());
        toBuild.put("itemsInvoiced", new ItemsInvoiced());
        toBuild.put("receiveNow", new ReceiveNow());
        toBuild.put("vatCode", new VATCode());
        toBuild.put("reason", new ReasonIdFKNotManditory());
        toBuild.put("deliveryDate", new DeliveryDate());

        return toBuild;
    }

    public double getReceiveNow() {
        return receiveNow;
    }

    public void setReceiveNow(double receiveNow) {
        this.receiveNow = receiveNow;
    }

    public String getItemDimension1() {
        return itemDimension1;
    }

    public void setItemDimension1(String itemDimension1) {
        this.itemDimension1 = itemDimension1;
    }

    public String getItemDimension2() {
        return itemDimension2;
    }

    public void setItemDimension2(String itemDimension2) {
        this.itemDimension2 = itemDimension2;
    }

    public String getItemDimension3() {
        return itemDimension3;
    }

    public void setItemDimension3(String itemDimension3) {
        this.itemDimension3 = itemDimension3;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public String getVatCode() {
        return vatCode;
    }

    public void setVatCode(String vatCode) {
        this.vatCode = vatCode;
    }

    public double getOverReceiveQty() {
        return overReceiveQty;
    }

    public void setOverReceiveQty(double overReceiveQty) {
        this.overReceiveQty = overReceiveQty;
    }

    public Long getBlanketOrderLineId() {
        return blanketOrderLineId;
    }

    public void setBlanketOrderLineId(Long blanketOrderLineId) {
        this.blanketOrderLineId = blanketOrderLineId;
    }

    public Boolean getCostChange() {
        return costChange == null ? false : costChange;
    }

    public void setCostChange(Boolean costChange) {
        this.costChange = costChange;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Double getOldPrice() {
        return oldPrice == null ? 0d : oldPrice;
    }

    public void setOldPrice(Double oldPrice) {
        this.oldPrice = oldPrice;
    }

    public Date getDateApproved() {
        return dateApproved;
    }

    public void setDateApproved(Date dateApproved) {
        this.dateApproved = dateApproved;
    }

    public Boolean getUpdateItem() {
        return updateItem == null ? false : updateItem;
    }

    public void setUpdateItem(Boolean updateItem) {
        this.updateItem = updateItem;
    }

    public Date getRevisedDate() {
        return revisedDate;
    }

    public void setRevisedDate(Date revisedDate) {
        this.revisedDate = revisedDate;
    }
}
