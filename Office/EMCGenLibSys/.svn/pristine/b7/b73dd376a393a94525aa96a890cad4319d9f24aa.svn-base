/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.sop;

import emc.datatypes.EMCDataType;
import emc.datatypes.sop.mtoworkbench.Dimension1;
import emc.datatypes.sop.mtoworkbench.Dimension2;
import emc.datatypes.sop.mtoworkbench.Dimension3;
import emc.datatypes.sop.mtoworkbench.ItemDescription;
import emc.datatypes.sop.mtoworkbench.ItemReference;
import emc.datatypes.sop.mtoworkbench.OrderId;
import emc.datatypes.sop.mtoworkbench.OrderLevel;
import emc.datatypes.sop.mtoworkbench.OrderStatus;
import emc.datatypes.sop.mtoworkbench.OrderType;
import emc.datatypes.sop.mtoworkbench.PeggedQuantity;
import emc.datatypes.sop.mtoworkbench.Warehouse;
import emc.framework.EMCEntityClass;
import java.math.BigDecimal;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "SOPMTOWorkBenchLines")
public class SOPMTOWorkBenchLines extends EMCEntityClass {

    private String userId;
    //
    private long masterRecordId;
    private int orderLevel;
    private long parentRecordId;
    private String orderType;
    private String orderId;
    private String orderStatus;
    private String itemId;
    private String itemReference;
    private String itemDescription;
    private String dimension1;
    private String dimension2;
    private String dimension3;
    private String warehouse;
    private BigDecimal orderQuantity;
    private BigDecimal peggedQuantity;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getMasterRecordId() {
        return masterRecordId;
    }

    public void setMasterRecordId(long masterRecordId) {
        this.masterRecordId = masterRecordId;
    }

    public int getOrderLevel() {
        return orderLevel;
    }

    public void setOrderLevel(int orderLevel) {
        this.orderLevel = orderLevel;
    }

    public long getParentRecordId() {
        return parentRecordId;
    }

    public void setParentRecordId(long parentRecordId) {
        this.parentRecordId = parentRecordId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemReference() {
        return itemReference;
    }

    public void setItemReference(String itemReference) {
        this.itemReference = itemReference;
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

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public BigDecimal getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(BigDecimal orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public BigDecimal getPeggedQuantity() {
        return peggedQuantity;
    }

    public void setPeggedQuantity(BigDecimal peggedQuantity) {
        this.peggedQuantity = peggedQuantity;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("orderLevel", new OrderLevel());
        toBuild.put("orderType", new OrderType());
        toBuild.put("orderId", new OrderId());
        toBuild.put("orderStatus", new OrderStatus());
        toBuild.put("itemReference", new ItemReference());
        toBuild.put("itemDescription", new ItemDescription());
        toBuild.put("dimension1", new Dimension1());
        toBuild.put("dimension2", new Dimension2());
        toBuild.put("dimension3", new Dimension3());
        toBuild.put("warehouse", new Warehouse());
        toBuild.put("peggedQuantity", new PeggedQuantity());

        return toBuild;
    }
}
