/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.debtors.opentransactions;

import emc.entity.debtors.DebtorsOpenTransactions;
import emc.entity.debtors.DebtorsTransactions;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import javax.ejb.Local;

/**
 * @description : Local interface for DebtorsOpenTransactionsBean.
 *
 * @date        : 18 May 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
@Local
public interface DebtorsOpenTransactionsLocal extends EMCEntityBeanLocalInterface {

    /**
     * Returns the open transaction related to the specified transaction.
     * @param trans Debtors transaction.
     * @param userData  User data.
     * @return The open transaction related to the specified transaction, or null,
     *         if no open transaction is found.
     */
    public DebtorsOpenTransactions getOpenTransaction(DebtorsTransactions trans, EMCUserData userData);

    /**
     * Returns the oldest open debit transaction for the specified customer.
     * @param customerId Customer id.
     * @param userData User data.
     * @return The oldest open debit transaction for the specified customer, or null,
     *         if the customer does not have any open debit transactions.
     */
    public DebtorsOpenTransactions getOldestOpenDebitTrans(String customerId, EMCUserData userData);
}
