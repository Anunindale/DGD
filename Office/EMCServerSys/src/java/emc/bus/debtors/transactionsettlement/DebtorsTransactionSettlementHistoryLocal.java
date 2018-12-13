/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.debtors.transactionsettlement;

import emc.entity.debtors.transactionsettlement.DebtorsTransactionSettlementHistory;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import javax.ejb.Local;

/**
 * @description : Local interface for DebtorsTransactionSettlementHistoryBean.
 *
 * @date        : 28 Jun 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
@Local
public interface DebtorsTransactionSettlementHistoryLocal extends EMCEntityBeanLocalInterface {

    /**
     * Deallocates the specified settlement from the given DebtorsTransactionSettlementHistory instance.
     * This call is forwarded to the Debtors Transaction Logic bean.
     * @param settlementHistory Settlement to deallocate.
     * @param userData User data.
     * @return A boolean indicating success.
     * @throws EMCEntityBeanException
     */
    public boolean deallocateSettlement(DebtorsTransactionSettlementHistory settlementHistory, EMCUserData userData) throws EMCEntityBeanException;

    /**
     * Deallocates all allocations made using the specified debit transaction.
     * @param creditTransRef Debit transaction record ID.
     * @param userData User data.
     * @return A boolean indicating success.
     * @throws EMCEntityBeanException
     */
    public boolean deallocateDebit(long debitTransRef, EMCUserData userData) throws EMCEntityBeanException;

    /**
     * Deallocates all allocations made using the specified credit transaction.
     * @param creditTransRef Credit transaction record ID.
     * @param userData User data.
     * @return A boolean indicating success.
     * @throws EMCEntityBeanException
     */
    public boolean deallocateCredit(long creditTransRef, EMCUserData userData) throws EMCEntityBeanException;
}
