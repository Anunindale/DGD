/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.sop;

import emc.datatypes.EMCDataType;
import emc.datatypes.inventory.itemreference.ItemReferenceIdNotMandatory;
import emc.datatypes.inventory.warehouses.foreignkeys.WarehouseFKNotMandatory;
import emc.datatypes.sop.stockenquiry.LinesDimension1;
import emc.datatypes.sop.stockenquiry.LinesDimension2;
import emc.datatypes.sop.stockenquiry.LinesDimension3;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "SOPSalesOrderStockEnquiryLines")
public class SOPSalesOrderStockEnquiryLines extends EMCEntityClass {

    private String userId;
    private String salesOrderId;
    private String orderNo;
    private String orderType;
    private double lineNo;
    private long masterRecordId;
    private String itemId;
    private String itemReference;
    private String itemDescription;
    private String dimension1;
    private String dimension2;
    private String dimension3;
    private String warehouse;
    private BigDecimal requiredQuantity = BigDecimal.ZERO;
    private BigDecimal availableQuantity = BigDecimal.ZERO;
    private boolean cantAssemble;
    @Temporal(TemporalType.DATE)
    private Date requiredDate;

    public BigDecimal getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(BigDecimal availableQuantity) {
        this.availableQuantity = availableQuantity;
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

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
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

    public long getMasterRecordId() {
        return masterRecordId;
    }

    public void setMasterRecordId(long masterRecordId) {
        this.masterRecordId = masterRecordId;
    }

    public BigDecimal getRequiredQuantity() {
        return requiredQuantity;
    }

    public void setRequiredQuantity(BigDecimal requiredQuantity) {
        this.requiredQuantity = requiredQuantity;
    }

    public String getSalesOrderId() {
        return salesOrderId;
    }

    public void setSalesOrderId(String salesOrderId) {
        this.salesOrderId = salesOrderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public double getLineNo() {
        return lineNo;
    }

    public void setLineNo(double lineNo) {
        this.lineNo = lineNo;
    }

    public boolean isCantAssemble() {
        return cantAssemble;
    }

    public void setCantAssemble(boolean cantAssemble) {
        this.cantAssemble = cantAssemble;
    }

    public Date getRequiredDate() {
        return requiredDate;
    }

    public void setRequiredDate(Date requiredDate) {
        this.requiredDate = requiredDate;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();


        toBuild.put("itemReference", new ItemReferenceIdNotMandatory());
        toBuild.put("warehouse", new WarehouseFKNotMandatory());
        toBuild.put("dimension1", new LinesDimension1());
        toBuild.put("dimension2", new LinesDimension2());
        toBuild.put("dimension3", new LinesDimension3());
        toBuild.put("cantAssemble", new emc.datatypes.sop.salesordermaster.Short());

        return toBuild;
    }

    @Override
    public EMCQuery buildQuery() {
        EMCQuery query = super.buildQuery();
        query.addAnd("masterRecordId", -1);
        return query;
    }
}
