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
public class PickingListStatus extends EMCString {

    /** Creates a new instance of PickingListStatus. */
    public PickingListStatus() {
        this.setEmcLabel("Status");
        this.setEditable(false);
    }
}
