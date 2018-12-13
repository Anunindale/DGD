/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.debtors.customerinvoice;

import emc.datatypes.inventory.dimensions.foreignkeys.Dimension3FKNotMandatory;

/**
 * @description : Data type for dimension2 on DebtorsCustomerInvoiceLines.
 *
 * @date        : 26 May 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class Dimension3 extends Dimension3FKNotMandatory {

    /** Creates a new instance of Dimension3 */
    public Dimension3() {
    	this.setColumnWidth(59);

    }
}
