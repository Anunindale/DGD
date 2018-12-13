/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.inventory.picking;

import emc.datatypes.EMCDataType;
import emc.datatypes.base.unitsofmeasure.foreignkeys.UnitOfMeasureFK;
import emc.datatypes.inventory.TransactionNumber;
import emc.datatypes.inventory.itemmaster.foreignkeys.ItemIdFK;
import emc.datatypes.inventory.location.foreignkeys.LocationFKNotManditory;
import emc.datatypes.inventory.pallet.foreignkeys.PalletIdFK;
import emc.datatypes.inventory.pickinglist.Cancelled;
import emc.datatypes.inventory.pickinglist.ConsolidatedPickingListLineId;
import emc.datatypes.inventory.pickinglist.Cut;
import emc.datatypes.inventory.pickinglist.DeliveryDate;
import emc.datatypes.inventory.pickinglist.Dimension1;
import emc.datatypes.inventory.pickinglist.Dimension2;
import emc.datatypes.inventory.pickinglist.Dimension3;
import emc.datatypes.inventory.pickinglist.Issued;
import emc.datatypes.inventory.pickinglist.IssuedQty;
import emc.datatypes.inventory.pickinglist.PickingListDate;
import emc.datatypes.inventory.pickinglist.Quantity;
import emc.datatypes.inventory.pickinglist.foreignkeys.PickingListIdFK;
import emc.datatypes.inventory.transactions.datasource.Batch;
import emc.datatypes.inventory.transactions.datasource.SerialNo;
import emc.datatypes.inventory.warehouses.foreignkeys.WarehouseFKNotMandatory;
import emc.datatypes.systemwide.PhysicalAddress1;
import emc.datatypes.systemwide.PhysicalAddress2;
import emc.datatypes.systemwide.PhysicalAddress3;
import emc.datatypes.systemwide.PhysicalAddress4;
import emc.datatypes.systemwide.PhysicalPostalCode;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import emc.inventory.ItemDimensionInterface;
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
@Table(name = "InventoryPickingListLines", uniqueConstraints = {@UniqueConstraint(columnNames = {"companyId", "pickingListId", "lineNum"})})
public class InventoryPickingListLines extends EMCEntityClass implements ItemDimensionInterface {

    private String itemId;
    private String pickingListId;
    @Temporal(TemporalType.DATE)
    private Date deliveryDate;
    private String uom;
    private double orderQty;
    private String transId;
    private double lineNum;
    @Temporal(TemporalType.DATE)
    private Date pickingListDate;
    private String dimension1;
    private String dimension2;
    private String dimension3;
    private String batch;
    private String serial;
    private String location;
    private String pallet;
    private String warehouse;
    private String deliveryAddress1;
    private String deliveryAddress2;
    private String deliveryAddress3;
    private String deliveryAddress4;
    private String deliveryAddressCode;
    private double issueQty;
    private boolean issued;
    private String refTransaction;
    private boolean cancelled = false;
    //N & L mod.  In case only part of a roll should be issued.
    private boolean cut;
    //N & L mod.  Consolidated Picking Lists.
    private long consolidatedPickingListLineId;
    private String consolidatedPickingListId;

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap toBuild = super.buildFieldList();

        toBuild.put("itemId", new ItemIdFK());
        toBuild.put("pickingListId", new PickingListIdFK());
        toBuild.put("deliveryDate", new DeliveryDate());
        toBuild.put("uom", new UnitOfMeasureFK());
        toBuild.put("orderQty", new Quantity());
        toBuild.put("pickingListDate", new PickingListDate());
        toBuild.put("dimension1", new Dimension1());
        toBuild.put("dimension2", new Dimension2());
        toBuild.put("dimension3", new Dimension3());
        toBuild.put("location", new LocationFKNotManditory());
        toBuild.put("pallet", new PalletIdFK());
        toBuild.put("deliveryAddress1", new PhysicalAddress1());
        toBuild.put("deliveryAddress2", new PhysicalAddress2());
        toBuild.put("deliveryAddress3", new PhysicalAddress3());
        toBuild.put("deliveryAddress4", new PhysicalAddress4());
        toBuild.put("deliveryAddressCode", new PhysicalPostalCode());
        toBuild.put("transId", new TransactionNumber());
        toBuild.put("issueQty", new IssuedQty());
        toBuild.put("issued", new Issued());
        toBuild.put("batch", new Batch());
        toBuild.put("serial", new SerialNo());
        toBuild.put("warehouse", new WarehouseFKNotMandatory());
        toBuild.put("cut", new Cut());
        toBuild.put("cancelled", new Cancelled());
        toBuild.put("consolidatedPickingListLineId", new ConsolidatedPickingListLineId());

        return toBuild;
    }

    public String getDeliveryAddress1() {
        return deliveryAddress1;
    }

    public void setDeliveryAddress1(String deliveryAddress1) {
        this.deliveryAddress1 = deliveryAddress1;
    }

    public String getDeliveryAddress2() {
        return deliveryAddress2;
    }

    public void setDeliveryAddress2(String deliveryAddress2) {
        this.deliveryAddress2 = deliveryAddress2;
    }

    public String getDeliveryAddress3() {
        return deliveryAddress3;
    }

    public void setDeliveryAddress3(String deliveryAddress3) {
        this.deliveryAddress3 = deliveryAddress3;
    }

    public String getDeliveryAddress4() {
        return deliveryAddress4;
    }

    public void setDeliveryAddress4(String deliveryAddress4) {
        this.deliveryAddress4 = deliveryAddress4;
    }

    public String getDeliveryAddressCode() {
        return deliveryAddressCode;
    }

    public void setDeliveryAddressCode(String deliveryAddressCode) {
        this.deliveryAddressCode = deliveryAddressCode;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getDimension1() {
        return dimension1;
    }

    public String getDimension2() {
        return dimension2;
    }

    public String getDimension3() {
        return dimension3;
    }

    public void setDimension1(String dimension1) {
        this.dimension1 = dimension1;
    }

    public void setDimension2(String dimension2) {
        this.dimension2 = dimension2;
    }

    public void setDimension3(String dimension3) {
        this.dimension3 = dimension3;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public double getLineNum() {
        return lineNum;
    }

    public void setLineNum(double lineNum) {
        this.lineNum = lineNum;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(double orderQty) {
        this.orderQty = orderQty;
    }

    public String getPallet() {
        return pallet;
    }

    public void setPallet(String pallet) {
        this.pallet = pallet;
    }

    public Date getPickingListDate() {
        return pickingListDate;
    }

    public void setPickingListDate(Date pickingListDate) {
        this.pickingListDate = pickingListDate;
    }

    public String getPickingListId() {
        return pickingListId;
    }

    public void setPickingListId(String pickingListId) {
        this.pickingListId = pickingListId;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public double getIssueQty() {
        return issueQty;
    }

    public void setIssueQty(double issueQty) {
        this.issueQty = issueQty;
    }

    public boolean getIssued() {
        return issued;
    }

    public void setIssued(boolean issued) {
        this.issued = issued;
    }

    public String getRefTransaction() {
        return refTransaction;
    }

    public void setRefTransaction(String refTransaction) {
        this.refTransaction = refTransaction;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
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

    public boolean getCut() {
        return cut;
    }

    public void setCut(boolean cut) {
        this.cut = cut;
    }

    @Override
    public EMCQuery buildQuery() {
        EMCQuery query = super.buildQuery();

        query.addOrderBy("lineNum");

        return query;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public long getConsolidatedPickingListLineId() {
        return consolidatedPickingListLineId;
    }

    public void setConsolidatedPickingListLineId(long consolidatedPickingListLineId) {
        this.consolidatedPickingListLineId = consolidatedPickingListLineId;
    }

    public String getConsolidatedPickingListId() {
        return consolidatedPickingListId;
    }

    public void setConsolidatedPickingListId(String consolidatedPickingListId) {
        this.consolidatedPickingListId = consolidatedPickingListId;
    }
}
