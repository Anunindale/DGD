/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.inventory.transactions;

import emc.datatypes.inventory.dimensions.foreignkeys.Dimension1FKNotMandatory;
import emc.datatypes.inventory.dimensions.foreignkeys.Dimension2FKNotMandatory;
import emc.datatypes.inventory.dimensions.foreignkeys.Dimension3FKNotMandatory;
import emc.datatypes.inventory.itemmaster.foreignkeys.ItemIdFKDeleteRestrict;
import emc.datatypes.inventory.summary.Location;
import emc.datatypes.inventory.summary.QCStatus;
import emc.datatypes.inventory.summary.QtySOPBlanketOrder;
import emc.datatypes.inventory.transactions.OrderedOut;
import emc.datatypes.inventory.transactions.Quarantine;
import emc.datatypes.inventory.transactions.ReceivedArea;
import emc.datatypes.inventory.transactions.TransactionRecordIdFK;
import emc.datatypes.inventory.transactions.datasource.Batch;
import emc.datatypes.inventory.transactions.datasource.Pallet;
import emc.datatypes.inventory.transactions.datasource.SerialNo;
import emc.datatypes.inventory.transactions.datasource.Warehouse;
import emc.datatypes.inventory.transactions.orderedAvailable;
import emc.datatypes.inventory.transactions.orderedReserved;
import emc.datatypes.inventory.transactions.orderedTotal;
import emc.datatypes.inventory.transactions.physicalAvailable;
import emc.datatypes.inventory.transactions.physicalReserved;
import emc.datatypes.inventory.transactions.physicalTotal;
import emc.framework.EMCEntityClass;
import java.math.BigDecimal;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author rico
 */
@Entity
@Table(name = "InventorySummary")
public class InventorySummary extends EMCEntityClass {
    //overview
    private String itemId;
    private double physicalTotal;
    private double physicalReserved;
    private double physicalAvailable;
    private double orderedTotal;
    private double orderedReserved;
    private double orderedAvailable;
    private double orderedOut;
    private double picked;
    private double received;
    private double posted;
    private double deducted;
    private double registered;
    private double qtyOSBlanketOrd;
    private double recAvailable;
    private double qcAvailable;
    private String dimension3;
    private String dimension1;
    private String dimension2;
    private String warehouse;
    private String batch;
    private String location;
    private String pallet;
    private String serialNo;
    private long itemDimId;
    private long inventoryTransRef;
    private String QCStatus;
    //Outstanding quantity on Sales Order Blanket Orders.
    private BigDecimal qtySOPBlanketOrder;
    private long referenceRecordID;

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();

        toBuild.put("itemId", new ItemIdFKDeleteRestrict());
        toBuild.put("dimension1", new Dimension1FKNotMandatory());
        toBuild.put("dimension2", new Dimension2FKNotMandatory());
        toBuild.put("dimension3", new Dimension3FKNotMandatory());
        toBuild.put("warehouse", new Warehouse());
        toBuild.put("batch", new Batch());
        toBuild.put("location", new Location());
        toBuild.put("pallet", new Pallet());
        toBuild.put("serialNo", new SerialNo());
        toBuild.put("inventoryTransRef", new TransactionRecordIdFK());
        toBuild.put("physicalTotal", new physicalTotal());
        toBuild.put("physicalReserved", new physicalReserved());
        toBuild.put("physicalAvailable", new physicalAvailable());
        toBuild.put("orderedTotal", new orderedTotal());
        toBuild.put("orderedReserved", new orderedReserved());
        toBuild.put("orderedAvailable", new orderedAvailable());
        toBuild.put("recAvailable", new ReceivedArea());
        toBuild.put("qcAvailable", new Quarantine());
        toBuild.put("orderedOut", new OrderedOut());
        toBuild.put("QCStatus", new QCStatus());
        toBuild.put("qtySOPBlanketOrder", new QtySOPBlanketOrder());

        return toBuild;
    }

    public long getItemDimId() {
        return itemDimId;
    }

    public void setItemDimId(long itemDimId) {
        this.itemDimId = itemDimId;
    }

    public InventorySummary() {

    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public double getPhysicalTotal() {
        return physicalTotal;
    }

    public void setPhysicalTotal(double physicalTotal) {
        this.physicalTotal = physicalTotal;
    }

    public double getPhysicalReserved() {
        return physicalReserved;
    }

    public void setPhysicalReserved(double physicalReserved) {
        this.physicalReserved = physicalReserved;
    }

    public double getPhysicalAvailable() {
        return physicalAvailable;
    }

    public void setPhysicalAvailable(double physicalAvailable) {
        this.physicalAvailable = physicalAvailable;
    }

    public double getOrderedTotal() {
        return orderedTotal;
    }

    public void setOrderedTotal(double orderedTotal) {
        this.orderedTotal = orderedTotal;
    }

    public double getOrderedReserved() {
        return orderedReserved;
    }

    public void setOrderedReserved(double orderedReserved) {
        this.orderedReserved = orderedReserved;
    }

    public double getOrderedAvailable() {
        return orderedAvailable;
    }

    public void setOrderedAvailable(double orderedAvailable) {
        this.orderedAvailable = orderedAvailable;
    }

    public double getPicked() {
        return picked;
    }

    public void setPicked(double picked) {
        this.picked = picked;
    }

    public double getReceived() {
        return received;
    }

    public void setReceived(double received) {
        this.received = received;
    }

    public double getPosted() {
        return posted;
    }

    public void setPosted(double posted) {
        this.posted = posted;
    }

    public double getDeducted() {
        return deducted;
    }

    public void setDeducted(double deducted) {
        this.deducted = deducted;
    }

    public double getRegistered() {
        return registered;
    }

    public void setRegistered(double registered) {
        this.registered = registered;
    }

    public double getQtyOSBlanketOrd() {
        return qtyOSBlanketOrd;
    }

    public void setQtyOSBlanketOrd(double qtyOSBlanketOrd) {
        this.qtyOSBlanketOrd = qtyOSBlanketOrd;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPallet() {
        return pallet;
    }

    public void setPallet(String pallet) {
        this.pallet = pallet;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public long getInventoryTransRef() {
        return inventoryTransRef;
    }

    public void setInventoryTransRef(long inventoryTransRef) {
        this.inventoryTransRef = inventoryTransRef;
    }

    public double getQcAvailable() {
        return qcAvailable;
    }

    public void setQcAvailable(double qcAvailable) {
        this.qcAvailable = qcAvailable;
    }

    public double getRecAvailable() {
        return recAvailable;
    }

    public void setRecAvailable(double recAvailable) {
        this.recAvailable = recAvailable;
    }

    public double getOrderedOut() {
        return orderedOut;
    }

    public void setOrderedOut(double orderedOut) {
        this.orderedOut = orderedOut;
    }

    public String getQCStatus() {
        return QCStatus;
    }

    public void setQCStatus(String QCStatus) {
        this.QCStatus = QCStatus;
    }

    public BigDecimal getQtySOPBlanketOrder() {
        return qtySOPBlanketOrder;
    }

    public void setQtySOPBlanketOrder(BigDecimal qtySOPBlanketOrder) {
        this.qtySOPBlanketOrder = qtySOPBlanketOrder;
    }

    public long getReferenceRecordID() {
        return referenceRecordID;
    }

    public void setReferenceRecordID(long referenceRecordID) {
        this.referenceRecordID = referenceRecordID;
    }
}
