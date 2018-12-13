/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.inventory.requirementsplanning;

import emc.framework.EMCEntityClass;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author riaan
 */
@Entity
@Table(name = "InventoryRequirementsPlanningHistory")
public class InventoryRequirementsPlanningHistory extends EMCEntityClass {

    //Source
    private long referenceRecordID;
    private String referenceRecordRef;
    private int referenceRecordType;
    private String itemId;
    private long dimensionId;
    private String serialNumber;
    //Demand Supply Split
    private int recordType;
    //Demand Fields
    @Temporal(TemporalType.DATE)
    private Date demandDate;
    private BigDecimal demandQuantity = BigDecimal.ZERO;
    private boolean demandClosed;
    //Supply Fields
    @Temporal(TemporalType.DATE)
    private Date supplyDate;
    private BigDecimal supplyQuantity = BigDecimal.ZERO;
    private boolean supplyClosed;
    //Management
    private boolean firm;
    private int managedBy;
    private int recordLevel;

    public Date getDemandDate() {
        return demandDate;
    }

    public void setDemandDate(Date demandDate) {
        this.demandDate = demandDate;
    }

    public BigDecimal getDemandQuantity() {
        return demandQuantity;
    }

    public void setDemandQuantity(BigDecimal demandQuantity) {
        this.demandQuantity = demandQuantity;
    }

    public long getDimensionId() {
        return dimensionId;
    }

    public void setDimensionId(long dimensionId) {
        this.dimensionId = dimensionId;
    }

    public boolean isFirm() {
        return firm;
    }

    public void setFirm(boolean firm) {
        this.firm = firm;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public int getManagedBy() {
        return managedBy;
    }

    public void setManagedBy(int managedBy) {
        this.managedBy = managedBy;
    }

    public int getRecordType() {
        return recordType;
    }

    public void setRecordType(int recordType) {
        this.recordType = recordType;
    }

    public Date getSupplyDate() {
        return supplyDate;
    }

    public void setSupplyDate(Date supplyDate) {
        this.supplyDate = supplyDate;
    }

    public BigDecimal getSupplyQuantity() {
        return supplyQuantity;
    }

    public void setSupplyQuantity(BigDecimal supplyQuantity) {
        this.supplyQuantity = supplyQuantity;
    }

    public int getRecordLevel() {
        return recordLevel;
    }

    public void setRecordLevel(int recordLevel) {
        this.recordLevel = recordLevel;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public boolean isDemandClosed() {
        return demandClosed;
    }

    public void setDemandClosed(boolean demandClosed) {
        this.demandClosed = demandClosed;
    }

    public boolean isSupplyClosed() {
        return supplyClosed;
    }

    public void setSupplyClosed(boolean supplyClosed) {
        this.supplyClosed = supplyClosed;
    }

    public long getReferenceRecordID() {
        return referenceRecordID;
    }

    public void setReferenceRecordID(long referenceRecordID) {
        this.referenceRecordID = referenceRecordID;
    }

    public String getReferenceRecordRef() {
        return referenceRecordRef;
    }

    public void setReferenceRecordRef(String referenceRecordRef) {
        this.referenceRecordRef = referenceRecordRef;
    }

    public int getReferenceRecordType() {
        return referenceRecordType;
    }

    public void setReferenceRecordType(int referenceRecordType) {
        this.referenceRecordType = referenceRecordType;
    }
}
