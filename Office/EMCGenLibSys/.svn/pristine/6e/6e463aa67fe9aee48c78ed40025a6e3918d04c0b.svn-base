/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.sop.reallocation;

import emc.datatypes.EMCDataType;
import emc.datatypes.sop.reallocation.Batch;
import emc.datatypes.sop.reallocation.CustomerId;
import emc.datatypes.sop.reallocation.CustomerName;
import emc.datatypes.sop.reallocation.Dimension1;
import emc.datatypes.sop.reallocation.Dimension2;
import emc.datatypes.sop.reallocation.Dimension3;
import emc.datatypes.sop.reallocation.DueDate;
import emc.datatypes.sop.reallocation.ItemRef;
import emc.datatypes.sop.reallocation.Location;
import emc.datatypes.sop.reallocation.Pallet;
import emc.datatypes.sop.reallocation.ReallocatedQuantity;
import emc.datatypes.sop.reallocation.ReservedQuantity;
import emc.datatypes.sop.reallocation.SalesOrderId;
import emc.datatypes.sop.reallocation.Serial;
import emc.datatypes.sop.reallocation.ToSalesOrderId;
import emc.datatypes.sop.reallocation.Unreserve;
import emc.datatypes.sop.reallocation.Warehouse;
import emc.framework.EMCEntityClass;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * This table is not normalized.
 * @author riaan
 */
@Entity
@Table(name = "SOPReallocation")
public class SOPReallocation extends EMCEntityClass {

    private String toSalesOrderId;
    private boolean unreserve;
    private BigDecimal reallocatedQuantity = BigDecimal.ZERO;
    private String customerId;
    private String customerName;
    private String salesOrderId;
    @Temporal(TemporalType.DATE)
    private Date dueDate;
    private String itemRef;
    private String dimension1;
    private String dimension2;
    private String dimension3;
    private String warehouse;
    private String location;
    private String batch;
    private String serial;
    private String pallet;
    private BigDecimal reservedQuantity = BigDecimal.ZERO;
    private long transactionRef;
    private long dimensionId;
    private long transactionVersionNumber;
    private boolean posted;
    private long sessionId;

    /** Creates a new instance of SOPReallocation. */
    public SOPReallocation() {
    }

    public String getToSalesOrderId() {
        return toSalesOrderId;
    }

    public void setToSalesOrderId(String toSalesOrderId) {
        this.toSalesOrderId = toSalesOrderId;
    }

    public boolean isUnreserve() {
        return unreserve;
    }

    public void setUnreserve(boolean unreserve) {
        this.unreserve = unreserve;
    }

    public BigDecimal getReallocatedQuantity() {
        return reallocatedQuantity;
    }

    public void setReallocatedQuantity(BigDecimal reallocatedQuantity) {
        this.reallocatedQuantity = reallocatedQuantity;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getSalesOrderId() {
        return salesOrderId;
    }

    public void setSalesOrderId(String salesOrderId) {
        this.salesOrderId = salesOrderId;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getItemRef() {
        return itemRef;
    }

    public void setItemRef(String itemRef) {
        this.itemRef = itemRef;
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

    public BigDecimal getReservedQuantity() {
        return reservedQuantity;
    }

    public void setReservedQuantity(BigDecimal reservedQuantity) {
        this.reservedQuantity = reservedQuantity;
    }

    public long getTransactionVersionNumber() {
        return transactionVersionNumber;
    }

    public void setTransactionVersionNumber(long transactionVersionNumber) {
        this.transactionVersionNumber = transactionVersionNumber;
    }

    public boolean isPosted() {
        return posted;
    }

    public void setPosted(boolean posted) {
        this.posted = posted;
    }

    public long getSessionId() {
        return sessionId;
    }

    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getPallet() {
        return pallet;
    }

    public void setPallet(String pallet) {
        this.pallet = pallet;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("toSalesOrderId", new ToSalesOrderId());
        toBuild.put("unreserve", new Unreserve());
        toBuild.put("reallocatedQuantity", new ReallocatedQuantity());
        toBuild.put("customerId", new CustomerId());
        toBuild.put("customerName", new CustomerName());
        toBuild.put("salesOrderId", new SalesOrderId());
        toBuild.put("dueDate", new DueDate());
        toBuild.put("itemRef", new ItemRef());
        toBuild.put("dimension1", new Dimension1());
        toBuild.put("dimension2", new Dimension2());
        toBuild.put("dimension3", new Dimension3());
        toBuild.put("warehouse", new Warehouse());
        toBuild.put("location", new Location());
        toBuild.put("batch", new Batch());
        toBuild.put("serial", new Serial());
        toBuild.put("pallet", new Pallet());
        toBuild.put("reservedQuantity", new ReservedQuantity());

        return toBuild;
    }

    public long getTransactionRef() {
        return transactionRef;
    }

    public void setTransactionRef(long transactionRef) {
        this.transactionRef = transactionRef;
    }

    public long getDimensionId() {
        return dimensionId;
    }

    public void setDimensionId(long dimensionId) {
        this.dimensionId = dimensionId;
    }
}
