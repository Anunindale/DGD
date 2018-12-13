/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.debtors.creditstopreason;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class CreditStopReason extends EMCString {

    public CreditStopReason() {
        this.setEmcLabel("Credit Stop Reason");
        this.setMandatory(true);
    }
}
