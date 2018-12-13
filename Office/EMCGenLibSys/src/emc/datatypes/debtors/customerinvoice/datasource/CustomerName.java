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
public class CustomerName extends CustomerNameDS {

    /** Creates a new instance of CustomerName. */
    public CustomerName() {
        super("customerNo");
        this.setEmcLabel("Name");
        this.setEditable(false);
    	this.setColumnWidth(130);
        this.setLowerCaseAllowed(true);
        this.setStringSize(80);
    }
}
