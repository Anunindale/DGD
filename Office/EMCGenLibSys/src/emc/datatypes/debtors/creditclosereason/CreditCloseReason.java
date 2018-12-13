/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.debtors.creditclosereason;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class CreditCloseReason extends EMCString {

    public CreditCloseReason() {
        this.setEmcLabel("Credit Close Reason");
        this.setMandatory(true);
    }
}
