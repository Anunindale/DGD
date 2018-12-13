/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.sop.priceaudittrail;

import emc.datatypes.sop.customers.foreignkeys.CustomerIdFKNotMandatory;

/**
 *
 * @author wikus
 */
public class CustomerId extends CustomerIdFKNotMandatory {

    public CustomerId() {
    	this.setColumnWidth(84);
    }
}
