/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.debtors.creditnoteregister;

import emc.datatypes.EMCDouble;

/**
 *
 * @author riaan
 */
public class RegisteredQuantity extends EMCDouble {

    /** Creates a new instance of RegisteredQuantity. */
    public RegisteredQuantity() {
        this.setEmcLabel("Registered Qty.");
        this.setMinValue(Double.MIN_VALUE);
        this.setMinValue(-10000000);
    }
}
