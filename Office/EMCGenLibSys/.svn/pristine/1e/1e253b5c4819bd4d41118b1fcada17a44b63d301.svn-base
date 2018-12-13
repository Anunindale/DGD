/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.debtors.basket.foreignkeys;

import emc.datatypes.debtors.basket.ItemId;
import emc.entity.inventory.InventoryItemMaster;
import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 *
 * @author asd_admin
 */
public class ItemIdFK extends ItemId {
    public ItemIdFK(){
        this.setRelatedTable(InventoryItemMaster.class.getName());
        this.setRelatedField("itemId");
        this.setDeleteAction(enumDeleteUpdateOptions.CLEARFIELD);
        this.setUpdateAction(enumDeleteUpdateOptions.CASCADE);
    }
}
