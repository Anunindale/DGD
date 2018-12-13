/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.debtors.transactionsettlement;

import emc.datatypes.EMCDate;

/**
 *
 * @author riaan
 */
public class TransactionDate extends EMCDate {

    /** Creates a new instance of TransactionDate. */
    public TransactionDate() {
    	this.setColumnWidth(75);
        this.setEmcLabel("Date");
    }
}
