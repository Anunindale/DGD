/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.debtors.transactionsettlement;

/**
 * @description : Data type for creditAmountSettled on DebtorsTransactionSettlement.
 *
 * @date        : 14 Jun 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class CreditAmountSettled extends emc.datatypes.debtors.transactions.CreditAmountSettled{

    /** Creates a new instance of CreditAmountSettled */
    public CreditAmountSettled() {
    	this.setColumnWidth(79);
        this.setEmcLabel("Amt. Alloc.");
    }
}
