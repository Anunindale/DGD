/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.gl.transactionssuper;

import emc.datatypes.EMCDate;

/**
 *
 * @author wikus
 */
public class TransactionDate extends EMCDate {

    public TransactionDate() {
        this.setEmcLabel("Date");
        this.setEditable(false);
    }
}
