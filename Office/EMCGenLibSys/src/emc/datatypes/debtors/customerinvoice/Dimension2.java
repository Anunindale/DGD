/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.debtors.customerinvoice;

import emc.datatypes.inventory.dimensions.foreignkeys.Dimension2FKNotMandatory;

/**
 * @description : Data type for dimension2 on DebtorsCustomerInvoiceLines.
 *
 * @date        : 26 May 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class Dimension2 extends Dimension2FKNotMandatory {

    /** Creates a new instance of Dimension2 */
    public Dimension2() {
    	this.setColumnWidth(57);

    }
}
