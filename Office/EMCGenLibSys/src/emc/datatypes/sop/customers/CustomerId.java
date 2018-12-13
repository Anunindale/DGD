/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.sop.customers;

import emc.datatypes.EMCString;

/**
 *
 * @author riaan
 */
public class CustomerId extends EMCString {

    /** Creates a new instance of CustomerId */
    public CustomerId() {
        this.setEmcLabel("Customer");
        this.setMandatory(true);
    	this.setColumnWidth(74);
        this.setNumberSeqAllowed(true);
    }
}
