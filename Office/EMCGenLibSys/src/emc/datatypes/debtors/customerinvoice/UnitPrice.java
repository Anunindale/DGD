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
public class UnitPrice extends EMCBigDecimal {

    /** Creates a new instance of UnitPrice */
    public UnitPrice() {
    	this.setColumnWidth(57);
        this.setEmcLabel("Unit Price");
    }
}
