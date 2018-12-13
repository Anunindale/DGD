package emc.datatypes.gl.transactiondaysummary;

import emc.datatypes.EMCString;

/** 
*
* @author claudette
*/
public class AccountNumber extends EMCString {

    /** Creates a new instance of AccountNumber. */
    public AccountNumber() {
        this.setEmcLabel("Account Number");
        this.setEditable(false);
    }
}