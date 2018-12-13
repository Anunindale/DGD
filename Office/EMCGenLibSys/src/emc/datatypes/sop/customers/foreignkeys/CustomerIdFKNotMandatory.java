/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.sop.customers.foreignkeys;

/**
 *
 * @author riaan
 */
public class CustomerIdFKNotMandatory extends CustomerIdFK {

    /** Creates a new instance of CustomerId */
    public CustomerIdFKNotMandatory() {
    	this.setColumnWidth(50);
        this.setMandatory(false);
    }
}
