/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.debtors.transactionsettlement;

import emc.bus.debtors.transactions.DebtorsTransactionsLocal;
import emc.bus.debtors.transactions.logic.DebtorsTransactionLogicLocal;
import emc.entity.debtors.DebtorsTransactions;
import emc.entity.debtors.transactionsettlement.DebtorsTransactionSettlementHistory;
import emc.enums.debtors.transactions.DebtorsTransactionRefTypes;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * @description : Entity bean for DebtorsTransactionSettlementHistory.
 *
 * @date        : 28 Jun 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
@Stateless
public class DebtorsTransactionSettlementHistoryBean extends EMCEntityBean implements DebtorsTransactionSettlementHistoryLocal {

    @EJB
    private DebtorsTransactionLogicLocal transactionLogicBean;
    @EJB
    private DebtorsTransactionsLocal transactionBean;

    /** Creates a new instance of DebtorsTransactionSettlementHistoryBean */
    public DebtorsTransactionSettlementHistoryBean() {
    }

    /**
     * Deallocates the specified settlement from the given DebtorsTransactionSettlementHistory instance.
     * This call is forwarded to the Debtors Transaction Logic bean.
     * @param settlementHistory Settlement to deallocate.
     * @param userData User data.
     * @return A boolean indicating success.
     * @throws EMCEntityBeanException
     */
    public boolean deallocateSettlement(DebtorsTransactionSettlementHistory settlementHistory, EMCUserData userData) throws EMCEntityBeanException {
        return transactionLogicBean.deallocateSettlement(settlementHistory, userData);
    }

    /**
     * Deallocates the specified rebate or settlement discount from the given DebtorsTransactionSettlementHistory instance.
     * This call is forwarded to the Debtors Transaction Logic bean.
     * @param settlementHistory Settlement to deallocate.
     * @param reverseTransaction Reverse transaction.  This will be updated when deallocating the specified settlement.  If it is null, a new transaction will be created.
     * @param userData User data.
     * @return The deallocation transaction that was created.  This method does not save the transaction.  It needs to be saved manually.
     * @throws EMCEntityBeanException
     */
    public DebtorsTransactions deallocateRebateOrDisc(DebtorsTransactionSettlementHistory settlementHistory, DebtorsTransactions reverseTransaction, EMCUserData userData) throws EMCEntityBeanException {
        return transactionLogicBean.deallocateSettlement(settlementHistory, reverseTransaction, userData);
    }

    /**
     * Deallocates all allocations made using the specified credit transaction.
     * @param creditTransRef Credit transaction record ID.
     * @param userData User data.
     * @return A boolean indicating success.
     * @throws EMCEntityBeanException
     */
    public boolean deallocateCredit(long creditTransRef, EMCUserData userData) throws EMCEntityBeanException {
        boolean ret = true;

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsTransactionSettlementHistory.class);
        query.addAnd("creditTransRef", creditTransRef);
        query.addAnd("systemAllocated", false);

        List<DebtorsTransactionSettlementHistory> historyRecords = (List<DebtorsTransactionSettlementHistory>) util.executeGeneralSelectQuery(query, userData);

        DebtorsTransactions creditTransaction = transactionBean.getTransaction(creditTransRef, userData);

        //If multiple rebates/settlement discounts are being deallocated, create only one reverse transaction
        DebtorsTransactions reverseTrans = null;
        for (DebtorsTransactionSettlementHistory historyRecord : historyRecords) {
            DebtorsTransactionRefTypes refType = creditTransaction != null ? DebtorsTransactionRefTypes.fromString(creditTransaction.getReferenceType()) : null;
            
            if (refType == DebtorsTransactionRefTypes.REBATE || refType == DebtorsTransactionRefTypes.SETTLEMENT_DISC) {
                reverseTrans = deallocateRebateOrDisc(historyRecord, reverseTrans, userData);
            } else {
                ret = ret && deallocateSettlement(historyRecord, userData);
            }

            //Do not allocate settlement discounts if a rebate is being deallocated.
            if (creditTransaction != null && DebtorsTransactionRefTypes.fromString(creditTransaction.getReferenceType()) != DebtorsTransactionRefTypes.REBATE) {
                //Deallocate settlement discounts.
                EMCQuery discQuery = new EMCQuery(enumQueryTypes.SELECT, DebtorsTransactionSettlementHistory.class);
                discQuery.addTableAnd(DebtorsTransactions.class.getName(), "creditTransRef", DebtorsTransactionSettlementHistory.class.getName(), "recordID");
                discQuery.addAnd("referenceType", DebtorsTransactionRefTypes.SETTLEMENT_DISC.toString(), DebtorsTransactions.class.getName());
                discQuery.addAnd("sessionId", historyRecord.getSessionId());
                discQuery.addAnd("debitTransRef", historyRecord.getDebitTransRef());
                discQuery.addAnd("systemAllocated", false);

                DebtorsTransactionSettlementHistory discHistory = (DebtorsTransactionSettlementHistory) util.executeSingleResultQuery(discQuery, userData);

                if (discHistory != null) {
                    ret = ret && deallocateSettlement(discHistory, userData);
                }
            }

            //Do not allocate rebates if a settlement discount is being deallocated.
            if (creditTransaction != null && DebtorsTransactionRefTypes.fromString(creditTransaction.getReferenceType()) != DebtorsTransactionRefTypes.SETTLEMENT_DISC) {
                //Deallocate rebates.
                EMCQuery rebateQuery = new EMCQuery(enumQueryTypes.SELECT, DebtorsTransactionSettlementHistory.class);
                rebateQuery.addTableAnd(DebtorsTransactions.class.getName(), "creditTransRef", DebtorsTransactionSettlementHistory.class.getName(), "recordID");
                rebateQuery.addAnd("referenceType", DebtorsTransactionRefTypes.REBATE.toString(), DebtorsTransactions.class.getName());
                rebateQuery.addAnd("sessionId", historyRecord.getSessionId());
                rebateQuery.addAnd("debitTransRef", historyRecord.getDebitTransRef());
                rebateQuery.addAnd("systemAllocated", false);

                DebtorsTransactionSettlementHistory rebateHistory = (DebtorsTransactionSettlementHistory) util.executeSingleResultQuery(rebateQuery, userData);

                if (rebateHistory != null) {
                    ret = ret && deallocateSettlement(rebateHistory, userData);
                }
            }
        }

        return ret;
    }

    /**
     * Deallocates all allocations made using the specified debit transaction.
     * @param creditTransRef Debit transaction record ID.
     * @param userData User data.
     * @return A boolean indicating success.
     * @throws EMCEntityBeanException
     */
    public boolean deallocateDebit(long debitTransRef, EMCUserData userData) throws EMCEntityBeanException {
        boolean ret = true;

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsTransactionSettlementHistory.class);
        query.addAnd("debitTransRef", debitTransRef);
        query.addAnd("systemAllocated", false);

        List<DebtorsTransactionSettlementHistory> historyRecords = (List<DebtorsTransactionSettlementHistory>) util.executeGeneralSelectQuery(query, userData);

        for (DebtorsTransactionSettlementHistory historyRecord : historyRecords) {
            ret = ret && deallocateSettlement(historyRecord, userData);
        }

        return ret;
    }
}
