/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.inventory.safetystock;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class ItemType extends EMCString {

    public ItemType() {
        this.setEmcLabel("Item Type");
    	this.setColumnWidth(50);
        this.setMandatory(true);
    }
}
