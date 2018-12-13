/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.debtors.customerinvoice.datasource;

import emc.datatypes.datasources.sop.CustomerNameDS;

/**
 *
 * @author riaan
 */
public class InvoiceToCustomerName extends CustomerNameDS {

    /** Creates a new instance of InvoiceToCustomerName. */
    public InvoiceToCustomerName() {
        super("invoiceToCustNo");
        this.setEmcLabel("Name");
        this.setEditable(false);
    	this.setColumnWidth(134);
        this.setLowerCaseAllowed(true);
        this.setStringSize(50);
    }
}
