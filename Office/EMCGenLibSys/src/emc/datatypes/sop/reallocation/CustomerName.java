/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.sop.reallocation;

/**
 *
 * @author riaan
 */
public class CustomerName extends emc.datatypes.sop.customers.CustomerName {

    /** Creates a new instance of CustomerName. */
    public CustomerName() {
    	this.setColumnWidth(130);
        this.setEditable(false);
        this.setMandatory(false);
    }
}
