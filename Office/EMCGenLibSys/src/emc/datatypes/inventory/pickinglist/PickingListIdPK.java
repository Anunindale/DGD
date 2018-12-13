/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.inventory.pickinglist;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class PickingListIdPK extends EMCString {

    /** Creates a new instance of PickingListIdPK */
    public PickingListIdPK() {
        this.setEmcLabel("Picking List");
        this.setMandatory(true);
        this.setEditable(true);
        this.setNumberSeqAllowed(true);
    }
}
