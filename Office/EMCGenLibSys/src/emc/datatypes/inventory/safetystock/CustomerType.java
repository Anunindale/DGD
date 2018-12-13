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
public class CustomerType extends EMCString {

    public CustomerType() {
        this.setEmcLabel("Customer Type");
    	this.setColumnWidth(51);
        this.setMandatory(true);
    }
}
