/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.sop.pricechangeapproval;

import emc.datatypes.sop.customers.foreignkeys.CustomerIdFK;

/**
 *
 * @author riaan
 */
public class CustomerId extends CustomerIdFK {

    /** Creates a new instance of CustomerId. */
    public CustomerId() {
        this.setEditable(false);
    }
}
