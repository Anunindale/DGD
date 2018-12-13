/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.inventory.movestock;

import emc.datatypes.EMCDataType;
import emc.datatypes.datasources.inventory.BatchByDimIdDS;
import emc.datatypes.datasources.inventory.Dim1ByDimIdDS;
import emc.datatypes.datasources.inventory.Dim2ByDimIdDS;
import emc.datatypes.datasources.inventory.Dim3ByDimIdDS;
import emc.datatypes.datasources.inventory.LocationByDimIdDS;
import emc.datatypes.datasources.inventory.PalletByDimIdDS;
import emc.datatypes.datasources.inventory.SerialByDimIdDS;
import emc.datatypes.datasources.inventory.WarehouseByDimIdDS;
import emc.datatypes.datasources.inventory.itemDescDS;
import emc.datatypes.datasources.inventory.itemPrimaryReferenceDS;
import emc.inventory.DimensionIDInterface;
import emc.inventory.ItemReferenceInterface;
import java.util.HashMap;

/**
 *
 * @author wikus
 */
public class InventoryMoveStockSummaryDS extends InventoryMoveStockSummary implements ItemReferenceInterface, DimensionIDInterface {

    private String itemReference;
    private String itemDescription;
    private String dimension1;
    private String dimension2;
    private String dimension3;
    private String warehouse;
    private String location;
    private String pallet;
    private String serial;
    private String batch;

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap toBuild = super.buildFieldList();
        toBuild.put("itemReference", new itemPrimaryReferenceDS());
        toBuild.put("itemDescription", new itemDescDS());
        toBuild.put("dimension1", new Dim1ByDimIdDS());
        toBuild.put("dimension2", new Dim2ByDimIdDS());
        toBuild.put("dimension3", new Dim3ByDimIdDS());
        toBuild.put("warehouse", new WarehouseByDimIdDS());
        toBuild.put("location", new LocationByDimIdDS());
        toBuild.put("pallet", new PalletByDimIdDS());
        toBuild.put("serial", new SerialByDimIdDS());
        toBuild.put("batch", new BatchByDimIdDS());
        return toBuild;
    }

    public InventoryMoveStockSummaryDS() {
        this.setDataSource(true);
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

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemReference() {
        return itemReference;
    }

    public void setItemReference(String itemReference) {
        this.itemReference = itemReference;
    }

    public String getLocation() {
        return this.location;
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

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }
}
