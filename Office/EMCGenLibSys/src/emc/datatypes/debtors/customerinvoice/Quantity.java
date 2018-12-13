/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.debtors.customerinvoice;

import emc.datatypes.EMCBigDecimal;

/**
 *
 * @author riaan
 */
public class Quantity extends EMCBigDecimal {

    /** Creates a new instance of Quantity */
    public Quantity() {
    	this.setColumnWidth(53);
        this.setEmcLabel("Quantity");
        this.setMinValue(-1000000000);
    }
}
