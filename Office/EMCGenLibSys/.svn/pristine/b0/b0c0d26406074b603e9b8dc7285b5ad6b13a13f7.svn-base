/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.inventory.journals.stocktakecapture;

import emc.datatypes.datasources.DSRelation;
import emc.datatypes.inventory.itemreference.ItemReferenceIdNotMandatory;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.journals.InventoryJournalLines;

/**
 *
 * @author wikus
 */
public class ItemReferenceDS extends ItemReferenceIdNotMandatory {

    public ItemReferenceDS() {
        DSRelation dsr = new DSRelation();
        dsr.setSourceTable(InventoryJournalLines.class.getName());
        dsr.setRelatedTable(InventoryItemMaster.class.getName());
        dsr.setRelatedField("itemReference");
        dsr.setPKField("itemId");
        dsr.setFKField("itemId");
    	this.setColumnWidth(85);
        this.setDsRelation(dsr);
    }
}
