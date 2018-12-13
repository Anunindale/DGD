/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.inventory.dimensions.datasource;

import emc.datatypes.datasources.inventory.Dim3DescDS;
import emc.entity.inventory.dimensions.InventoryItemDimension3Setup;
import java.util.HashMap;

/**
 *
 * @author riaan
 */
public class InventoryItemDimension3SetupFormDS extends InventoryItemDimension3Setup implements Comparable {

    private String itemPrimaryReference;
    private String description;
    private int sortCode;
    
    /** Creates a new instance of InventoryItemDimension3SetupFormDS */
    public InventoryItemDimension3SetupFormDS() {
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
        InventoryItemDimension3SetupFormDS toCompare = (InventoryItemDimension3SetupFormDS)ob;
        
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
        toBuild.put("description", new Dim3DescDS());
        return toBuild;
    }
}
