/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.itemmaster;

import emc.datatypes.inventory.itemgroup.ItemGroupId;
import emc.entity.inventory.InventoryItemGroup;

/**
 *
 * @author rico
 */
public class ItemGroupFK extends ItemGroupId{
    public ItemGroupFK(){
        this.setRelatedTable(InventoryItemGroup.class.getName());
        this.setRelatedField("itemGroup");
        this.setMandatory(true);
        this.setEmcLabel("Group");
        this.setColumnWidth(70);
    }

}
