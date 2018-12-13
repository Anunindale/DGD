/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.sop.salesordermaster;

import emc.datatypes.sop.customers.foreignkeys.CustomerIdFK;

/**
 *
 * @author riaan
 */
public class CustomerNo extends CustomerIdFK {

    /** Creates a new instance of CustomerNo. */
    public CustomerNo() {
    	this.setColumnWidth(71);
        
    }
}
