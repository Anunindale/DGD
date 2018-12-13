/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.inventory.inventoryreference;

import emc.datatypes.EMCString;
import emc.entity.inventory.InventoryItemMaster;
import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 *
 * @author wikus
 */
public class ItemId extends EMCString {

    /** Creates a new instance of ItemId */
    public ItemId() {
        this.setRelatedTable(InventoryItemMaster.class.getName());
        this.setRelatedField("itemId");
        this.setMandatory(true);
        this.setStringSize(20);
        this.setDeleteAction(enumDeleteUpdateOptions.CASCADE);
    }
}

