/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.inventory.helper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author wikus
 */
public class GenerateStockTake {

    private List<String> itemGroups;
    private List<String> productGroups;
    private List<String> itemIds;
    private List<String> dimension1;
    private List<String> dimension2;
    private List<String> dimension3;
    private List<String> warehouses;
    private List<String> locations;
    private String journalDefinition;
    private boolean printCountSheet;
    private boolean splitPerWarehouse;
    private boolean splitPerLocation;
    private boolean forceLineSplit;
    private Date noMovementSince;
    private Date movementSince;

    public List<String> getItemGroups() {
        return itemGroups;
    }

    public void setItemGroups(List<String> itemGroups) {
        this.itemGroups = itemGroups;
    }

    public List<String> getItemIds() {
        return itemIds;
    }

    public void setItemIds(List<String> itemIds) {
        this.itemIds = itemIds;
    }

    public String getJournalDefinition() {
        return journalDefinition;
    }

    public void setJournalDefinition(String journalDefinition) {
        this.journalDefinition = journalDefinition;
    }

    public List<String> getLocations() {
        return locations;
    }

    public void setLocations(List<String> locations) {
        this.locations = locations;
    }

    public boolean isPrintCountSheet() {
        return printCountSheet;
    }

    public void setPrintCountSheet(boolean printCountSheet) {
        this.printCountSheet = printCountSheet;
    }

    public boolean isSplitPerWarehouse() {
        return splitPerWarehouse;
    }

    public void setSplitPerWarehouse(boolean splitPerWarehouse) {
        this.splitPerWarehouse = splitPerWarehouse;
    }

    public List<String> getWarehouses() {
        return warehouses;
    }

    public void setWarehouses(List<String> warehouses) {
        this.warehouses = warehouses;
    }

    public void addItemGroup(String itemGroup) {
        if (itemGroups == null) {
            itemGroups = new ArrayList<String>();
        }
        itemGroups.add(itemGroup);
    }

    public void addItemId(String itemId) {
        if (itemIds == null) {
            itemIds = new ArrayList<String>();
        }
        itemIds.add(itemId);
    }

    public void addWarehouse(String warehouse) {
        if (warehouses == null) {
            warehouses = new ArrayList<String>();
        }
        warehouses.add(warehouse);
    }

    public void addLocation(String location) {
        if (locations == null) {
            locations = new ArrayList<String>();
        }
        locations.add(location);
    }

    /**
     * @return the forceLineSplit
     */
    public boolean isForceLineSplit() {
        return forceLineSplit;
    }

    /**
     * @param forceLineSplit the forceLineSplit to set
     */
    public void setForceLineSplit(boolean forceLineSplit) {
        this.forceLineSplit = forceLineSplit;
    }

    public boolean isSplitPerLocation() {
        return splitPerLocation;
    }

    public void setSplitPerLocation(boolean splitPerLocation) {
        this.splitPerLocation = splitPerLocation;
    }

    public List<String> getDimension1() {
        return dimension1;
    }

    public void addDimension1(String dimension1) {
        if (this.dimension1 == null) {
            this.dimension1 = new ArrayList<String>();
        }
        this.dimension1.add(dimension1);
    }

    public List<String> getDimension2() {
        return dimension2;
    }

    public void addDimension2(String dimension2) {
        if (this.dimension2 == null) {
            this.dimension2 = new ArrayList<String>();
        }
        this.dimension2.add(dimension2);
    }

    public List<String> getDimension3() {
        return dimension3;
    }

    public void addDimension3(String dimension3) {
        if (this.dimension3 == null) {
            this.dimension3 = new ArrayList<String>();
        }
        this.dimension3.add(dimension3);
    }

    public List<String> getProductGroups() {
        return productGroups;
    }

    public void addProductGroup(String productGroup) {
        if (this.productGroups == null) {
            this.productGroups = new ArrayList<String>();
        }
        this.productGroups.add(productGroup);
    }

    public Date getNoMovementSince() {
        return noMovementSince;
    }

    public void setNoMovementSince(Date noMovementSince) {
        this.noMovementSince = noMovementSince;
    }

    public Date getMovementSince() {
        return movementSince;
    }

    public void setMovementSince(Date movementSince) {
        this.movementSince = movementSince;
    }
}
