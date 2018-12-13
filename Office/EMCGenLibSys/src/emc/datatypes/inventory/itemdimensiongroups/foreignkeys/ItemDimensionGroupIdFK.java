/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.itemdimensiongroups.foreignkeys;

import emc.datatypes.inventory.itemdimensiongroups.ItemDimensionGroupId;
import emc.entity.inventory.dimensions.InventoryItemDimensionGroup;
import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 *
 * @author riaan
 */
public class ItemDimensionGroupIdFK extends ItemDimensionGroupId {
    
    /** Creates a new instance of ItemDimensionGroupIdFK. */
    public ItemDimensionGroupIdFK() {
        this.setRelatedTable(InventoryItemDimensionGroup.class.getName());
        this.setRelatedField("itemDimensionGroupId");
        this.setDeleteAction(enumDeleteUpdateOptions.RESTRICT);
        this.setUpdateAction(enumDeleteUpdateOptions.RESTRICT);
    }
}
