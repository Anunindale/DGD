/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.sop.blanketorderstatusenquiry;

import emc.datatypes.inventory.itemmaster.foreignkeys.ItemReferenceFK;

/**
 *
 * @author riaan
 */
public class ItemReference extends ItemReferenceFK {

    /** Creates a new instance of ItemReference. */
    public ItemReference() {
    	this.setColumnWidth(92);
        this.setEditable(false);
    }
}
