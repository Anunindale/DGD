/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.debtors.opentransactions;

import emc.entity.debtors.DebtorsOpenTransactions;
import emc.entity.debtors.DebtorsTransactions;
import emc.enums.emcquery.EMCQueryOrderByDirections;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.util.List;
import javax.ejb.Stateless;

/**
 * @description : Entity bean for DebtorsOpenTransactions.
 *
 * @date        : 18 May 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
@Stateless
public class DebtorsOpenTransactionsBean extends EMCEntityBean implements DebtorsOpenTransactionsLocal {

    /** Creates a new instance of DebtorsOpenTransactionsBean */
    public DebtorsOpenTransactionsBean() {

    }

    /**
     * Returns the open transaction related to the specified transaction.
     * @param trans Debtors transaction.
     * @param userData  User data.
     * @return The open transaction related to the specified transaction, or null,
     *         if no open transaction is found.
     */
    public DebtorsOpenTransactions getOpenTransaction(DebtorsTransactions trans, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsOpenTransactions.class);
        query.addAnd("debtorsTransRef", trans.getRecordID());

        return (DebtorsOpenTransactions)util.executeSingleResultQuery(query, userData);
    }

    /**
     * Returns the oldest open debit transaction for the specified customer.
     * @param customerId Customer id.
     * @param userData User data.
     * @return The oldest open debit transaction for the specified customer, or null,
     *         if the customer does not have any open debit transactions.
     */
    public DebtorsOpenTransactions getOldestOpenDebitTrans(String customerId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsOpenTransactions.class);
        query.addAnd("customerId", customerId);
        query.addOrderBy("transactionDate", DebtorsOpenTransactions.class.getName(), EMCQueryOrderByDirections.ASC);

        List<DebtorsOpenTransactions> openTrans = (List<DebtorsOpenTransactions>)util.executeGeneralSelectQuery(query, userData);

        if (openTrans == null || openTrans.isEmpty()) {
            return null;
        } else {
            return openTrans.get(0);
        }
    }
}
