/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.reports.pop.purchaseorders;


/**
 *
 * @author riaan
 */
public class POPGoodsReceivedDS{

    private String supplier;
    private String purchaseOrder;
    private String deliveryDate;
    private String itemId;
    private String itemDescription;
    private String dimension1;
    private String dimension2;
    private String dimension3;
    private String uom;
    private double itemPrice;
    private String deliveryNote;
    private double qtyOutstanding;
    private double qtyReceived;
    private double valueReceived;
    
    /** Creates a new instance of POPGoodsReceivedDS. */
    public POPGoodsReceivedDS() {
        
    }

    public String getDeliveryNote() {
        return deliveryNote;
    }

    public void setDeliveryNote(String deliveryNote) {
        this.deliveryNote = deliveryNote;
    }

    public double getQtyOutstanding() {
        return qtyOutstanding;
    }

    public void setQtyOutstanding(double qtyOutstanding) {
        this.qtyOutstanding = qtyOutstanding;
    }

    public double getQtyReceived() {
        return qtyReceived;
    }

    public void setQtyReceived(double qtyReceived) {
        this.qtyReceived = qtyReceived;
    }

    public double getValueReceived() {
        return valueReceived;
    }

    public void setValueReceived(double valueReceived) {
        this.valueReceived = valueReceived;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(String purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
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
}
