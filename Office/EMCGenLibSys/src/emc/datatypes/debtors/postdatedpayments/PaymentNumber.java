/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.debtors.postdatedpayments;

import emc.datatypes.EMCString;

/**
 *
 * @author riaan
 */
public class PaymentNumber extends EMCString {

    /** Creates a new instance of PaymentNumber. */
    public PaymentNumber() {
        this.setEmcLabel("Payment Number");
        this.setMandatory(true);
    }
}
