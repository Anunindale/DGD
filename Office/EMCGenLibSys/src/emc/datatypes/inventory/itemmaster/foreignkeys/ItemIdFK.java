/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.itemmaster.foreignkeys;

import emc.datatypes.inventory.itemmaster.*;
import emc.entity.inventory.InventoryItemMaster;
import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 *
 * @author riaan
 */
public class ItemIdFK extends ItemId {

    /** Creates a new instance of ItemIdFK */
    public ItemIdFK() {
        this.setColumnWidth(75);
        this.setRelatedTable(InventoryItemMaster.class.getName());
        this.setRelatedField("itemId");
        this.setNumberSeqAllowed(false);
        this.setDeleteAction(enumDeleteUpdateOptions.RESTRICT);
    }
}
