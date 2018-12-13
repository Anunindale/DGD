/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.inventory.datasource;

import emc.datatypes.EMCDataType;
import emc.datatypes.inventory.inventorystocktakeunreserved.Batch;
import emc.datatypes.inventory.inventorystocktakeunreserved.Dimension1;
import emc.datatypes.inventory.inventorystocktakeunreserved.Dimension2;
import emc.datatypes.inventory.inventorystocktakeunreserved.Dimension3;
import emc.datatypes.inventory.inventorystocktakeunreserved.ItemDescription;
import emc.datatypes.inventory.inventorystocktakeunreserved.ItemReference;
import emc.datatypes.inventory.inventorystocktakeunreserved.Location;
import emc.datatypes.inventory.inventorystocktakeunreserved.Pallet;
import emc.datatypes.inventory.inventorystocktakeunreserved.Serial;
import emc.datatypes.inventory.inventorystocktakeunreserved.Warehouse;
import emc.entity.inventory.InventoryStocktakeUnreserved;
import java.util.HashMap;

/**
 *
 * @author wikus
 */
public class InventoryStocktakeUnreservedDS extends InventoryStocktakeUnreserved {

    private String itemReference;
    private String itemDescription;
    private String dimension1;
    private String dimension2;
    private String dimension3;
    private String warehouse;
    private String location;
    private String batch;
    private String serial;
    private String pallet;
    private String referenceRecStatus;
    private String referenceRecType;

    public InventoryStocktakeUnreservedDS() {
        this.setDataSource(true);
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("itemReference", new ItemReference());
        toBuild.put("itemDescription", new ItemDescription());
        toBuild.put("dimension1", new Dimension1());
        toBuild.put("dimension2", new Dimension2());
        toBuild.put("dimension3", new Dimension3());
        toBuild.put("warehouse", new Warehouse());
        toBuild.put("location", new Location());
        toBuild.put("batch", new Batch());
        toBuild.put("serial", new Serial());
        toBuild.put("pallet", new Pallet());
        return toBuild;
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

    public String getReferenceRecStatus() {
        return referenceRecStatus;
    }

    public void setReferenceRecStatus(String referenceRecStatus) {
        this.referenceRecStatus = referenceRecStatus;
    }

    public String getReferenceRecType() {
        return referenceRecType;
    }

    public void setReferenceRecType(String referenceRecType) {
        this.referenceRecType = referenceRecType;
    }
}
