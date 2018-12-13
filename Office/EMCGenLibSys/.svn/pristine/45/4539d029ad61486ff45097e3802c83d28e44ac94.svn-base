/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.consolidatedpickinglistsetup.foreignkeys;

import emc.datatypes.inventory.consolidatedpickinglistmaster.ConsolidatedPickingListId;
import emc.entity.inventory.consolidatedpickinglist.InventoryConsolidatedPLMaster;
import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 *
 * @author riaan
 */
public class ConsolidatedPickingListIdFK extends ConsolidatedPickingListId {

    /** Creates a new instance of ConsolidatedPickingListIdFK. */
    public ConsolidatedPickingListIdFK() {
        this.setRelatedTable(InventoryConsolidatedPLMaster.class.getName());
        this.setRelatedField("consolidatedPickingListId");
        this.setUpdateAction(enumDeleteUpdateOptions.CASCADE);
        this.setDeleteAction(enumDeleteUpdateOptions.CASCADE);
        this.setNumberSeqAllowed(false);
    }
}
