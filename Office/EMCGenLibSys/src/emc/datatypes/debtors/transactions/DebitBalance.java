/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.debtors.transactions;

import emc.datatypes.EMCBigDecimal;

/**
 *
 * @author riaan
 */
public class DebitBalance extends EMCBigDecimal {

    /** Creates a new instance of DebitBalance. */
    public DebitBalance() {
        this.setEmcLabel("Debit Balance");
        this.setEditable(false);
    }
}
