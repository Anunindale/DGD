/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.inventory.requirementsplanning;

import emc.datatypes.datasources.inventory.itemDescDS;

/**
 *
 * @author wikus
 */
public class ItemDescription extends itemDescDS {

    public ItemDescription() {
    	this.setColumnWidth(140);
        this.setEditable(false);
    }
}
