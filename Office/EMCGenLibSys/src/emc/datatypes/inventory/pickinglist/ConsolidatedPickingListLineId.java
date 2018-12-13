/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.pickinglist;

import emc.datatypes.EMCLong;
import emc.entity.inventory.consolidatedpickinglist.InventoryConsolidatedPLLines;
import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 *
 * @author riaan
 */
public class ConsolidatedPickingListLineId extends EMCLong {

    /** Creates a new instance of ConsolidatedPickingListLineId. */
    public ConsolidatedPickingListLineId() {
        this.setEmcLabel("Consolidated Picking List Line ID");
        this.setRelatedTable(InventoryConsolidatedPLLines.class.getName());
        this.setRelatedField("recordID");
        this.setUpdateAction(enumDeleteUpdateOptions.CASCADE);
        this.setDeleteAction(enumDeleteUpdateOptions.CASCADE);
    }
}
