/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.debtors.transactionsettlement;

import emc.entity.debtors.transactionsettlement.DebtorsSettlementDiscHistory;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import javax.ejb.Stateless;

/**
 * @description : Entity bean for DebtorsSettlementDiscHistory.
 *
 * @date        : 28 Jun 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
@Stateless
public class DebtorsSettlementDiscHistoryBean extends EMCEntityBean implements DebtorsSettlementDiscHistoryLocal {

    /** Creates a new instance of DebtorsSettlementDiscHistoryBean */
    public DebtorsSettlementDiscHistoryBean() {
    }

    public DebtorsSettlementDiscHistory findSettlementDiscHistory(long discountTransRef, String invoiceNumber, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsSettlementDiscHistory.class);
        query.addAnd("discountTransactionRef", discountTransRef);
        query.addAnd("invoiceNo", invoiceNumber);

        return (DebtorsSettlementDiscHistory) util.executeSingleResultQuery(query, userData);
    }
}
