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
public class Quantity extends EMCBigDecimal {

    public Quantity() {
        this.setColumnWidth(53);
        this.setEmcLabel("Quantity");
        this.setMinValue(-1000000000);
    }
}
