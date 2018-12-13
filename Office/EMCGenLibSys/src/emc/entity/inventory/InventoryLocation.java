/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.inventory;

import emc.datatypes.EMCDataType;
import emc.datatypes.inventory.location.LocationPK;
import emc.datatypes.inventory.warehouses.foreignkeys.WarehouseFK;
import emc.datatypes.systemwide.Description;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author riaan
 */
@Entity
@Table(name = "InventoryLocation", uniqueConstraints = {@UniqueConstraint(columnNames = {"locationId", "warehouseId", "companyId"})})
public class InventoryLocation extends EMCEntityClass {

    private String locationId;
    private String description;
    private String warehouseId;
    private String aisle;
    private String rack;
    private String shelf;
    private String bin;
    @Temporal(TemporalType.DATE)
    private Date lastCountedDate;
    private boolean stockTaking;


    /** Creates a new instance of InventoryLocation */
    public InventoryLocation() {
    }

    public Date getLastCountedDate() {
        return lastCountedDate;
    }

    public void setLastCountedDate(Date lastCountedDate) {
        this.lastCountedDate = lastCountedDate;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAisle() {
        return aisle;
    }

    public void setAisle(String aisle) {
        this.aisle = aisle;
    }

    public String getBin() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }

    public String getRack() {
        return rack;
    }

    public void setRack(String rack) {
        this.rack = rack;
    }

    public String getShelf() {
        return shelf;
    }

    public void setShelf(String shelf) {
        this.shelf = shelf;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public boolean isStockTaking() {
        return stockTaking;
    }

    public void setStockTaking(boolean stockTaking) {
        this.stockTaking = stockTaking;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap ret = super.buildFieldList();
        ret.put("locationId", new LocationPK());
        ret.put("description", new Description());
        ret.put("warehouseId", new WarehouseFK());
        return ret;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List ret = super.buildDefaultLookupFieldList();
        ret.add("locationId");
        ret.add("description");
        ret.add("warehouseId");
        return ret;
    }

    @Override
    public EMCQuery buildQuery() {
        EMCQuery query = super.buildQuery();
        query.addOrderBy("locationId");
        return query;
    }
}
