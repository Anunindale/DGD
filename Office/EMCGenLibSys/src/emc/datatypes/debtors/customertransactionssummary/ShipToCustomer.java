/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.debtors.customertransactionssummary;

import emc.datatypes.sop.customers.foreignkeys.CustomerIdFK;

/**
 *
 * @author riaan
 */
public class ShipToCustomer extends CustomerIdFK {

    /** Creates a new instance of ShipToCustomer */
    public ShipToCustomer() {
        this.setEmcLabel("Ship To Customer");
    }
}
