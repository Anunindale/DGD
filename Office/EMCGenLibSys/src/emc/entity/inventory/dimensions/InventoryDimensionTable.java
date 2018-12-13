/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.inventory.dimensions;

import emc.datatypes.EMCDataType;
import emc.datatypes.datasources.inventory.BatchByDimIdDS;
import emc.datatypes.datasources.inventory.PalletByDimIdDS;
import emc.datatypes.datasources.inventory.SerialByDimIdDS;
import emc.datatypes.inventory.dimensions.foreignkeys.Dimension1FKNotMandatory;
import emc.datatypes.inventory.dimensions.foreignkeys.Dimension2FKNotMandatory;
import emc.datatypes.inventory.dimensions.foreignkeys.Dimension3FKNotMandatory;
import emc.datatypes.inventory.location.foreignkeys.LocationFKNotManditory;
import emc.datatypes.inventory.warehouses.foreignkeys.WarehouseFKNotMandatory;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author riaan
 */
@Entity
@Table(name = "InventoryDimensionTable", uniqueConstraints = {@UniqueConstraint(columnNames = {"dimensionId", "companyId"})})
public class InventoryDimensionTable extends EMCEntityClass {

    private String dimensionId;
    private String batchId;
    private String dimension1Id;
    private String dimension2Id;
    private String dimension3Id;
    private String warehouseId;
    private String locationId;
    private String palletId;
    private String itemSerialId;
    
    /** Creates a new instance of InventoryDimensionTable */
    public InventoryDimensionTable() {
        
    }

    public String getDimensionId() {
        return dimensionId;
    }

    public void setDimensionId(String dimensionId) {
        this.dimensionId = dimensionId;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getDimension1Id() {
        return dimension1Id;
    }

    public void setDimension1Id(String dimension1Id) {
        this.dimension1Id = dimension1Id;
    }

    public String getDimension2Id() {
        return dimension2Id;
    }

    public void setDimension2Id(String dimension2Id) {
        this.dimension2Id = dimension2Id;
    }

    public String getDimension3Id() {
        return dimension3Id;
    }

    public void setDimension3Id(String dimension3Id) {
        this.dimension3Id = dimension3Id;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getPalletId() {
        return palletId;
    }

    public void setPalletId(String palletId) {
        this.palletId = palletId;
    }

    public String getItemSerialId() {
        return itemSerialId;
    }

    public void setItemSerialId(String itemSerialId) {
        this.itemSerialId = itemSerialId;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("dimension1Id", new Dimension1FKNotMandatory());
        toBuild.put("dimension2Id", new Dimension2FKNotMandatory());
        toBuild.put("dimension3Id", new Dimension3FKNotMandatory());
        toBuild.put("warehouseId", new WarehouseFKNotMandatory());
        toBuild.put("locationId", new LocationFKNotManditory());
        toBuild.put("batchId", new BatchByDimIdDS());
        toBuild.put("itemSerialId", new SerialByDimIdDS());
        toBuild.put("palletId", new PalletByDimIdDS());

        return toBuild;
    }

}
