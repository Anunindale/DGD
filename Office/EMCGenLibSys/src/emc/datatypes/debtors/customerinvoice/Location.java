/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.debtors.customerinvoice;

import emc.datatypes.inventory.location.foreignkeys.LocationFKNotManditory;

/**
 * @description : Data type for location on DebtorsCustomerInvoiceLines.
 *
 * @date        : 26 May 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class Location extends LocationFKNotManditory {

    /** Creates a new instance of Location */
    public Location() {
    	this.setColumnWidth(50);
    	
    }
}
