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
public class DiscountPercentage extends EMCBigDecimal {

    /** Creates a new instance of DiscountPercentage */
    public DiscountPercentage() {
    	this.setColumnWidth(42);
        this.setEmcLabel("Discount Percentage");
        this.setMaxValue(100);
        this.setMinValue(0);
    }
}
