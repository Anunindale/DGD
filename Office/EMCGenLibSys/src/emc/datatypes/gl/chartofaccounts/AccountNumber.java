/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.gl.chartofaccounts;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class AccountNumber extends EMCString {

    public AccountNumber() {
        this.setEmcLabel("Account Number");
        this.setMandatory(true);
    	this.setColumnWidth(106);
        this.setNumberSeqAllowed(true);
    }
}
