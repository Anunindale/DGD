/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.debtors.transactions;

import emc.datatypes.EMCBigDecimal;

/**
 * @description : Data type for credit on DebtorsTransactions.
 *
 * @date        : 14 Jun 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class Credit extends EMCBigDecimal {

    /** Creates a new instance of Credit */
    public Credit() {
    	this.setColumnWidth(69);
        this.setEmcLabel("Credit");
    }
}
