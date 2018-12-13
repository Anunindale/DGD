/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.debtors.transactionsettlement;

import emc.entity.debtors.transactionsettlement.DebtorsSettlementDiscHistory;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import javax.ejb.Local;

/**
 * @description : Local interface for DebtorsSettlementDiscHistoryBean.
 *
 * @date        : 28 Jun 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
@Local
public interface DebtorsSettlementDiscHistoryLocal extends EMCEntityBeanLocalInterface {

    /**
     * Returns a settlement discount history record for the specified invoice and transaction.
     * @param discountTransRef Discount transaction record ID.
     * @param invoiceNumber Number of the Invoice to which the discount was applied.
     * @param userData User data.
     * @return A DebtorsSettlementDiscHistory instance for the specified discount, or null, if none is found.
     */
    public DebtorsSettlementDiscHistory findSettlementDiscHistory(long discountTransRef, String invoiceNumber, EMCUserData userData);
}
