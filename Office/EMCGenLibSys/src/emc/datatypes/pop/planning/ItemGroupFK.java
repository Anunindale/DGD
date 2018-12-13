/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.pop.planning;

import emc.datatypes.datasources.DSRelation;
import emc.datatypes.inventory.itemgroup.ItemGroupId;
import emc.entity.inventory.InventoryItemGroup;
import emc.entity.inventory.InventoryItemMaster;

/**
 *
 * @author wikus
 */
public class ItemGroupFK extends ItemGroupId {

    public ItemGroupFK() {
        this.setRelatedTable(InventoryItemGroup.class.getName());
        this.setRelatedField("itemGroup");
        this.setEmcLabel("Group");
        this.setColumnWidth(70);

        DSRelation dsr = new DSRelation();
        dsr.setRelatedTable(InventoryItemMaster.class.getName());
        dsr.setRelatedField("itemGroup");
        dsr.setFKField("itemId");
        dsr.setPKField("itemId");
        this.setDsRelation(dsr);
    }
}
