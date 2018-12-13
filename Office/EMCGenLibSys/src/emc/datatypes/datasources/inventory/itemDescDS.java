/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.datasources.inventory;

import emc.datatypes.datasources.DSRelation;
import emc.datatypes.systemwide.Description;
import emc.entity.inventory.InventoryItemMaster;

/**
 *
 * @author wikus
 */
public class itemDescDS extends Description {

    public itemDescDS() {
        super();
        DSRelation dsr = new DSRelation();
        dsr.setRelatedTable(InventoryItemMaster.class.getName());
        dsr.setRelatedField("description");
        dsr.setFKField("itemId");
        dsr.setPKField("itemId");
        this.setColumnWidth(161);
        this.setDsRelation(dsr);
    }
}
