/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.inventory.picking.rereservepicklist;

import emc.datatypes.EMCDataType;
import emc.datatypes.inventory.dimensions.foreignkeys.Dimension1FKNotMandatory;
import emc.datatypes.inventory.dimensions.foreignkeys.Dimension2FKNotMandatory;
import emc.datatypes.inventory.dimensions.foreignkeys.Dimension3FKNotMandatory;
import emc.datatypes.inventory.itemclassification.itembatch.foreignkeys.ItemBatchIdFK;
import emc.datatypes.inventory.itemmaster.foreignkeys.ItemIdFKNotMandatory;
import emc.datatypes.inventory.journals.journallines.SerialNumber;
import emc.datatypes.inventory.location.foreignkeys.LocationFKNotManditory;
import emc.datatypes.inventory.pallet.foreignkeys.PalletIdFK;
import emc.datatypes.inventory.pickinglist.foreignkeys.PickingListIdFK;
import emc.datatypes.inventory.warehouses.foreignkeys.WarehouseFKNotMandatory;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "InventoryReReservePickList", uniqueConstraints = {@UniqueConstraint(columnNames = {"companyId", "pickingListId", "pickListLine"})})
public class InventoryReReservePickList extends EMCEntityClass {

    private String pickingListId;
    private double pickListLine;
    private String itemId;
    private String dimension1;
    private String dimension2;
    private String dimension3;
    private String fromBatch;
    private String toBatch;
    private String fromSerial;
    private String toSetial;
    private String fromWarehouse;
    private String toWarehouse;
    private String fromLocation;
    private String toLocation;
    private String fromPallet;
    private String toPallet;
    private double quantity;
    private boolean posted;
    private String transId;

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("pickingListId", new PickingListIdFK());
        toBuild.put("itemId", new ItemIdFKNotMandatory());
        toBuild.put("dimension1", new Dimension1FKNotMandatory());
        toBuild.put("dimension2", new Dimension2FKNotMandatory());
        toBuild.put("dimension3", new Dimension3FKNotMandatory());
        toBuild.put("fromBatch", new ItemBatchIdFK());
        //toBuild.put("toBatch", new ItemBatchIdFK());
        toBuild.put("fromSerial", new SerialNumber());
        toBuild.put("toSetial", new SerialNumber());
        toBuild.put("fromWarehouse", new WarehouseFKNotMandatory());
        toBuild.put("toWarehouse", new WarehouseFKNotMandatory());
        toBuild.put("fromLocation", new LocationFKNotManditory());
        toBuild.put("toLocation", new LocationFKNotManditory());
        toBuild.put("fromPallet", new PalletIdFK());
        toBuild.put("toPallet", new PalletIdFK());
        return toBuild;
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

    public String getFromBatch() {
        return fromBatch;
    }

    public void setFromBatch(String fromBatch) {
        this.fromBatch = fromBatch;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getFromPallet() {
        return fromPallet;
    }

    public void setFromPallet(String fromPallet) {
        this.fromPallet = fromPallet;
    }

    public String getFromSerial() {
        return fromSerial;
    }

    public void setFromSerial(String fromSerial) {
        this.fromSerial = fromSerial;
    }

    public String getFromWarehouse() {
        return fromWarehouse;
    }

    public void setFromWarehouse(String fromWarehouse) {
        this.fromWarehouse = fromWarehouse;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public double getPickListLine() {
        return pickListLine;
    }

    public void setPickListLine(double pickListLine) {
        this.pickListLine = pickListLine;
    }

    public String getPickingListId() {
        return pickingListId;
    }

    public void setPickingListId(String pickingListId) {
        this.pickingListId = pickingListId;
    }

    public boolean isPosted() {
        return posted;
    }

    public void setPosted(boolean posted) {
        this.posted = posted;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getToBatch() {
        return toBatch;
    }

    public void setToBatch(String toBatch) {
        this.toBatch = toBatch;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public String getToPallet() {
        return toPallet;
    }

    public void setToPallet(String toPallet) {
        this.toPallet = toPallet;
    }

    public String getToSetial() {
        return toSetial;
    }

    public void setToSetial(String toSetial) {
        this.toSetial = toSetial;
    }

    public String getToWarehouse() {
        return toWarehouse;
    }

    public void setToWarehouse(String toWarehouse) {
        this.toWarehouse = toWarehouse;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }
}
