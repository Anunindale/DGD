/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.creditors.invoiceregister;

import emc.datatypes.EMCDouble;

/**
 *
 * @author riaan
 */
public class Quantity extends EMCDouble {

    /** Creates a new instance of Quantity. */
    public Quantity() {
        this.setEmcLabel("Quantity");
        this.setMinValue(Double.MIN_VALUE);
        this.setMinValue(-10000000);
    }
}
