/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.debtors.allocationimport;

import emc.datatypes.EMCString;

/**
 *
 * @author riaan
 */
public class AutoAllocationPaymentOrder extends EMCString {

    /** Creates a new instance of AutoAllocationPaymentOrder. */
    public AutoAllocationPaymentOrder() {
        this.setEmcLabel("Payment Order");
        this.setMandatory(true);
    }
}
