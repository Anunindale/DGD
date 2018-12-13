/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.debtors.transactionsettlement;

/**
 * @description :Data type for debitAmountSettled on DebtorsTransactionSettlement.
 *
 * @date        : 14 Jun 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class DebitAmountSettled extends emc.datatypes.debtors.transactions.DebitAmountSettled{

    /** Creates a new instance of DebitAmountSettled */
    public DebitAmountSettled() {
    	this.setColumnWidth(68);
        this.setEmcLabel("Amt. Alloc.");
    }
}
