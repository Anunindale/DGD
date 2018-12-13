/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.gl.transactionssuper;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class TransactionNumber extends EMCString {

    public TransactionNumber() {
        this.setEmcLabel("Transaction Number");
        this.setEditable(false);
    }
}
