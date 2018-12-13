/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.sop.priceaudittrail;

import emc.datatypes.datasources.DSRelation;
import emc.datatypes.inventory.itemreference.ItemReferenceIdNotMandatory;
import emc.entity.inventory.InventoryItemMaster;

/**
 *
 * @author wikus
 */
public class ItemReference extends ItemReferenceIdNotMandatory {

    public ItemReference() {
        DSRelation relation = new DSRelation();
        this.setColumnWidth(91);
        relation.setRelatedTable(InventoryItemMaster.class.getName());
        relation.setRelatedField("itemReference");//UserTable, field that you want.
        relation.setPKField("itemId");//Usertable
        relation.setFKField("itemId");//soppriceAuditTrail
        this.setDsRelation(relation);
    }
}
