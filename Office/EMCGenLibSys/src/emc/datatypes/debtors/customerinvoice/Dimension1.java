/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.debtors.customerinvoice;

import emc.datatypes.inventory.dimensions.foreignkeys.Dimension1FKNotMandatory;

/**
 * @description : Data type for dimension1 on DebtorsCustomerInvoiceLines.
 *
 * @date        : 26 May 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class Dimension1 extends Dimension1FKNotMandatory {

    /** Creates a new instance of Dimension1 */
    public Dimension1() {
    	this.setColumnWidth(58);

    }
}
