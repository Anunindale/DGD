/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.debtors.customerinvoice;

import emc.datatypes.sop.customers.foreignkeys.CustomerIdFK;

/**
 *
 * @author riaan
 */
public class InvoiceToCustNo extends CustomerIdFK {

    /** Creates a new instance of InvoiceToCustNo. */
    public InvoiceToCustNo() {
        this.setEmcLabel("Invoice to Customer No");
        this.setEditable(false);
    }
}
