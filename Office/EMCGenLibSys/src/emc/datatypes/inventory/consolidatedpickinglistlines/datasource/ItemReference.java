/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.consolidatedpickinglistlines.datasource;

import emc.datatypes.inventory.itemmaster.foreignkeys.ItemReferenceFK;

/**
 *
 * @author riaan
 */
public class ItemReference extends ItemReferenceFK {

    /** Creates a new instance of ItemReference. */
    public ItemReference() {
    	this.setColumnWidth(70);
        this.setEditable(false);
    }
}
