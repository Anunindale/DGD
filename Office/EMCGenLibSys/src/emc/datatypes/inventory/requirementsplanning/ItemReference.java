/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.inventory.requirementsplanning;

import emc.datatypes.datasources.DSRelation;
import emc.datatypes.inventory.itemreference.ItemReferenceIdNotMandatory;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.requirementsplanning.InventoryRequirementsPlanning;

/**
 *
 * @author wikus
 */
public class ItemReference extends ItemReferenceIdNotMandatory {

    public ItemReference() {
        DSRelation dsr = new DSRelation();
        dsr.setSourceTable(InventoryRequirementsPlanning.class.getName());
        dsr.setRelatedTable(InventoryItemMaster.class.getName());
        dsr.setRelatedField("itemReference");
        dsr.setPKField("itemId");
        dsr.setFKField("itemId");
    	this.setColumnWidth(58);
        this.setDsRelation(dsr);
    }
}
