/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.inventory.consolidatedpickinglist;

/**
 *
 * @author wikus
 */
public class ConsolidatedPickingListreportDS {

    private String pickingListNo;
    private String orderNo;
    private String customerId;
    private String deliveryTems;
    private String deliveryMode;
    private String pickingDate;
    private String itemReference;
    private String itemDesc;
    private String dimension1;
    private String dimension2;
    private String dimension3;
    private String UoM;
    private double qtyToPick;
    private String locationId;
    private String pallet;
    private String batch;
    private String serialNo;
    private String colourDesc;
    private String comments;
    //N & L specific fields.
    private String cut;
    private double width;
    //"Special" field.  Displays Work Center for Production Picking Lists.
    private String additionalValue;
    //Used to indicate that a line should be backflushed
    private String pickType;
    private String widthRefTitle;
    //N & L mod.  This is used to seperate items in the 'David' warehouse from other items on the Picking List
    private String subGroupId;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getDeliveryMode() {
        return deliveryMode;
    }

    public void setDeliveryMode(String deliveryMode) {
        this.deliveryMode = deliveryMode;
    }

    public String getDeliveryTems() {
        return deliveryTems;
    }

    public void setDeliveryTems(String deliveryTems) {
        this.deliveryTems = deliveryTems;
    }

    public String getDimension1() {
        return dimension1;
    }

    public void setDimension1(String dimension1) {
        this.dimension1 = dimension1;
    }

    public String getDimension2() {
        return dimension2;
    }

    public void setDimension2(String dimension2) {
        this.dimension2 = dimension2;
    }

    public String getDimension3() {
        return dimension3;
    }

    public void setDimension3(String dimension3) {
        this.dimension3 = dimension3;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public String getItemReference() {
        return itemReference;
    }

    public void setItemReference(String itemReference) {
        this.itemReference = itemReference;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPallet() {
        return pallet;
    }

    public void setPallet(String pallet) {
        this.pallet = pallet;
    }

    public String getPickingDate() {
        return pickingDate;
    }

    public void setPickingDate(String pickingDate) {
        this.pickingDate = pickingDate;
    }

    public String getPickingListNo() {
        return pickingListNo;
    }

    public void setPickingListNo(String pickingListNo) {
        this.pickingListNo = pickingListNo;
    }

    public double getQtyToPick() {
        return qtyToPick;
    }

    public void setQtyToPick(double qtyToPick) {
        this.qtyToPick = qtyToPick;
    }

    public String getUoM() {
        return UoM;
    }

    public void setUoM(String UoM) {
        this.UoM = UoM;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getColourDesc() {
        return colourDesc;
    }

    public void setColourDesc(String colourDesc) {
        this.colourDesc = colourDesc;
    }

    public String isCut() {
        return cut;
    }

    public void setCut(boolean cut) {
        this.cut = cut ? "Y" : "N";
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public String getAdditionalValue() {
        return additionalValue;
    }

    public void setAdditionalValue(String additionalValue) {
        this.additionalValue = additionalValue;
    }

    public String getPickType() {
        return pickType;
    }

    public void setPickType(String pickType) {
        this.pickType = pickType;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getWidthRefTitle() {
        return widthRefTitle;
    }

    public void setWidthRefTitle(String widthRefTitle) {
        this.widthRefTitle = widthRefTitle;
    }

    public String getSubGroupId() {
        return subGroupId;
    }

    public void setSubGroupId(String subGroupId) {
        this.subGroupId = subGroupId;
    }
}
