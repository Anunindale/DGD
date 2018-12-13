/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.consolidatedpickinglistmaster;

import emc.datatypes.EMCString;

/**
 *
 * @author riaan
 */
public class ConsolidatedPickingListId extends EMCString {

    /** Creates a new instance of ConsolidatedPickingListId. */
    public ConsolidatedPickingListId() {
        this.setEmcLabel("Picking List ID");
        this.setNumberSeqAllowed(true);
    }
}
