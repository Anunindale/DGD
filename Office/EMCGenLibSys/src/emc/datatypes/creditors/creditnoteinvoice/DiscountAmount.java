/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.creditors.creditnoteinvoice;

import emc.datatypes.EMCBigDecimal;

/**
 *
 * @author wikus
 */
public class DiscountAmount extends EMCBigDecimal {

    public DiscountAmount() {
        this.setColumnWidth(42);
        this.setEmcLabel("Discount Amount");
    }
}
