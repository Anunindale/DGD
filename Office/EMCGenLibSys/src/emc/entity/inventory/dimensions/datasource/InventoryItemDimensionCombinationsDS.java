/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.inventory.dimensions.datasource;

import emc.datatypes.datasources.inventory.itemPrimaryReferenceDS;
import emc.entity.inventory.dimensions.InventoryItemDimensionCombinations;
import java.util.HashMap;

/**
 *
 * @author riaan
 */
public class InventoryItemDimensionCombinationsDS extends InventoryItemDimensionCombinations {
    
    private String itemPrimaryReference;

    public String getItemPrimaryReference() {
        return itemPrimaryReference;
    }

    public void setItemPrimaryReference(String itemPrimaryReference) {
        this.itemPrimaryReference = itemPrimaryReference;
    }
    
    

    /** Creates a new instance of ItemDimensionCombinations. */
    public InventoryItemDimensionCombinationsDS() {
        setDataSource(true);
    }
    
    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();
        toBuild.put("itemPrimaryReference", new itemPrimaryReferenceDS());
        return toBuild;
    }
}
