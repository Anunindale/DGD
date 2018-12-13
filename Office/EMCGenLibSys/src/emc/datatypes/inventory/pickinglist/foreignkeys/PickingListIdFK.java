/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.inventory.pickinglist.foreignkeys;

import emc.datatypes.inventory.pickinglist.PickingListIdPK;
import emc.entity.inventory.picking.InventoryPickingListMaster;
import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 *
 * @author wikus
 */
public class PickingListIdFK extends PickingListIdPK {

    public PickingListIdFK() {
        this.setNumberSeqAllowed(false);
        this.setRelatedTable(InventoryPickingListMaster.class.getName());
        this.setRelatedField("pickingListId");
        this.setDeleteAction(enumDeleteUpdateOptions.CASCADE);
        this.setUpdateAction(enumDeleteUpdateOptions.CASCADE);
        this.setColumnWidth(71);
        this.setNumberSeqAllowed(false);
    }
}
