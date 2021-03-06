/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.inventory.dimensions.datasource;

import emc.datatypes.datasources.inventory.Dim1DescDS;
import emc.entity.inventory.dimensions.InventoryItemDimension1Setup;
import java.util.HashMap;

/**
 *
 * @author riaan
 * This datasource is used specifically for the item dimension 1 setup form opened from the item master form.
 */
public class InventoryItemDimension1SetupFormDS extends InventoryItemDimension1Setup implements Comparable {

    private String itemPrimaryReference;
    private String description;
    private int sortCode;
    
    /** Creates a new instance of InventoryItemDimension1SetupFormDS */
    public InventoryItemDimension1SetupFormDS() {
        this.setDataSource(true);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSortCode() {
        return sortCode;
    }

    public void setSortCode(int sortCode) {
        this.sortCode = sortCode;
    }

    public String getItemPrimaryReference() {
        return itemPrimaryReference;
    }

    public void setItemPrimaryReference(String itemPrimaryReference) {
        this.itemPrimaryReference = itemPrimaryReference;
    }
    
    
    
    public int compareTo(Object ob) {
        InventoryItemDimension1SetupFormDS toCompare = (InventoryItemDimension1SetupFormDS)ob;
        
        if (toCompare.getSortCode() < this.getSortCode()) {
            return 1;
        } else if (toCompare.getSortCode() > this.getSortCode()) {
            return -1;
        } else {
            return 0;
        }
    }
    
    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();
        toBuild.put("description", new Dim1DescDS());
        return toBuild;
    }
}
