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
public class SerialNo extends EMCString {

    public SerialNo() {
    	this.setColumnWidth(70);
        this.setEmcLabel("Serial");
    }
}
