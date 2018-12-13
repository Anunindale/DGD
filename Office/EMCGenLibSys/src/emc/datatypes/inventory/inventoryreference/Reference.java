/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.inventory.inventoryreference;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class Reference extends EMCString {

    /** Creates a new instance of Reference */
    public Reference() {
        this.setEmcLabel("Reference");
        this.setMandatory(true);
    	this.setColumnWidth(91);
        this.setEditable(true);
    }
}
