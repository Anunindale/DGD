/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.inventory.safetystock;

import emc.datatypes.datasources.inventory.itemDescDS;

/**
 *
 * @author wikus
 */
public class ItemDescription1 extends itemDescDS {

    public ItemDescription1() {
    	this.setColumnWidth(80);
        this.setEditable(false);
    }
}
