/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.debtors.customerinvoice;

import emc.datatypes.inventory.warehouses.foreignkeys.WarehouseFKNotMandatory;

/**
 * @description : Data type for warehouse on DebtorsCustomerInvoiceLines.
 *
 * @date        : 26 May 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class Warehouse extends WarehouseFKNotMandatory {

    /** Creates a new instance of Warehouse */
    public Warehouse() {
    	this.setColumnWidth(46);
    	
    }
}
