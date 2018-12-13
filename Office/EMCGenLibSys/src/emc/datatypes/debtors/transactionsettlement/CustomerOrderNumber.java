/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.debtors.transactionsettlement;

import emc.datatypes.EMCString;

/**
 *
 * @author riaan
 */
public class CustomerOrderNumber extends EMCString {

    /** Creates a new instance of CustomerOrderNumber. */
    public CustomerOrderNumber() {
        this.setEmcLabel("Cust. Order No");
    	this.setColumnWidth(68);
        this.setEditable(false);
    }
}
