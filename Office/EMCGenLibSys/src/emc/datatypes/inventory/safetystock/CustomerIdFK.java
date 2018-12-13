/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.inventory.safetystock;

import emc.datatypes.sop.customers.foreignkeys.CustomerIdFKNotMandatory;

/**
 *
 * @author wikus
 */
public class CustomerIdFK extends CustomerIdFKNotMandatory {

    public CustomerIdFK() {
    	this.setColumnWidth(55);
    }
}
