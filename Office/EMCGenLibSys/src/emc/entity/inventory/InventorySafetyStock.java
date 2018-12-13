/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.inventory;

import emc.datatypes.EMCDataType;
import emc.datatypes.inventory.safetystock.CustomerIdFK;
import emc.datatypes.inventory.safetystock.CustomerType;
import emc.datatypes.inventory.safetystock.Dimension1FK;
import emc.datatypes.inventory.safetystock.Dimension2FK;
import emc.datatypes.inventory.safetystock.Dimension3FK;
import emc.datatypes.inventory.safetystock.FromDate;
import emc.datatypes.inventory.safetystock.Generated;
import emc.datatypes.inventory.safetystock.ItemIdFK;
import emc.datatypes.inventory.safetystock.ItemType;
import emc.datatypes.inventory.safetystock.Quantity;
import emc.datatypes.inventory.safetystock.SerialNo;
import emc.datatypes.inventory.safetystock.ToDate;
import emc.enums.inventory.safetystock.SafetyStockType;
import emc.framework.EMCEntityClass;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "InventorySafetyStock", uniqueConstraints = {@UniqueConstraint(columnNames = {"uniqueKey", "companyId"})})
public class InventorySafetyStock extends EMCEntityClass {

    private String customerType;
    private String customerId;
    private String itemType;
    private String itemId;
    private String dimension1;
    private String dimension2;
    private String dimension3;
    private String serialNo;
    @Temporal(TemporalType.DATE)
    private Date fromDate;
    @Temporal(TemporalType.DATE)
    private Date toDate;
    private BigDecimal quantity = BigDecimal.ZERO;
    private boolean generated;
    private String safetyType = SafetyStockType.SS.toString();
    //UniqueContraints
    private String uniqueKey;
    private long mpsStartReference;
    private long mpsEndReference;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
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

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public boolean isGenerated() {
        return generated;
    }

    public void setGenerated(boolean generated) {
        this.generated = generated;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    public String getSafetyType() {
        return safetyType;
    }

    public void setSafetyType(String safetyType) {
        this.safetyType = safetyType;
    }

    public long getMpsStartReference() {
        return mpsStartReference;
    }

    public void setMpsStartReference(long mpsStartReference) {
        this.mpsStartReference = mpsStartReference;
    }

    public long getMpsEndReference() {
        return mpsEndReference;
    }

    public void setMpsEndReference(long mpsEndReference) {
        this.mpsEndReference = mpsEndReference;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("customerType", new CustomerType());
        toBuild.put("customerId", new CustomerIdFK());
        toBuild.put("itemType", new ItemType());
        toBuild.put("itemId", new ItemIdFK());
        toBuild.put("dimension1", new Dimension1FK());
        toBuild.put("dimension2", new Dimension2FK());
        toBuild.put("dimension3", new Dimension3FK());
        toBuild.put("serialNo", new SerialNo());
        toBuild.put("fromDate", new FromDate());
        toBuild.put("toDate", new ToDate());
        toBuild.put("quantity", new Quantity());
        toBuild.put("generated", new Generated());
        return toBuild;
    }
}
