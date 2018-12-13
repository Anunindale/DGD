/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.itemreference;

import emc.datatypes.EMCString;
import emc.datatypes.inventory.itemmaster.foreignkeys.*;
import emc.datatypes.inventory.itemmaster.*;
import emc.entity.inventory.InventoryItemMaster;
import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 *
 * @author riaan
 */
public class ItemId extends EMCString {

    /** Creates a new instance of ItemIdFK */
    public ItemId() {
        this.setColumnWidth(60);
        this.setRelatedTable(InventoryItemMaster.class.getName());
        this.setEmcLabel("Item Id");
        this.setRelatedField("itemId");
        this.setNumberSeqAllowed(false);
        this.setDeleteAction(enumDeleteUpdateOptions.CASCADE);
        this.setUpdateAction(enumDeleteUpdateOptions.CASCADE);
    }
}
