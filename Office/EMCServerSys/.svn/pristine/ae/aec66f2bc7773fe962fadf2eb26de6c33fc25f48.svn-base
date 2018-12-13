/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.debtors.datasource.transactionsettlementhistory;

import emc.entity.debtors.datasource.DebtorsTransactionSettlementHistoryDS;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import javax.ejb.Local;

/**
 * @description : Local interface for DebtorsTransactionSettlementHistoryDSBean.
 *
 * @date        : 27 Jul 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
@Local
public interface DebtorsTransactionSettlementHistoryDSLocal extends EMCEntityBeanLocalInterface {

    /**
     * Deallocates the specified settlement from the given DebtorsTransactionSettlementHistoryDS instance.
     * This call is forwarded to the Debtors Transaction Settlement History bean.
     * @param settlementHistory Settlement to deallocate.
     * @param userData User data.
     * @return A boolean indicating success.
     * @throws EMCEntityBeanException
     */
    public boolean deallocateSettlement(DebtorsTransactionSettlementHistoryDS settlementHistoryDS, EMCUserData userData) throws EMCEntityBeanException;
}
