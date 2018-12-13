/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.debtors.transactions.logic;

import emc.entity.debtors.DebtorsCreditNoteMaster;
import emc.entity.debtors.DebtorsCustomerInvoiceMaster;
import emc.entity.debtors.DebtorsTransactions;
import emc.entity.debtors.transactionsettlement.DebtorsTransactionSettlementHistory;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCUserData;
import javax.ejb.Local;

/**
 * @description : Local interface for DebtorsTransactionLogicBean.
 *
 * @date        : 13 May 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
@Local
public interface DebtorsTransactionLogicLocal {

    /**
     * Posts a Debtors journal.
     *
     * @param journalMaster Journal to post.
     * @param userData User data.
     * @return A boolean indicating whether the record was succesfully posted.
     * @throws EMCEntityBeanException
     */
    public boolean postDebtorsJournal(emc.entity.debtors.journals.DebtorsJournalMaster journalMaster, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    /**
     * This method posts a customer invoice.
     * @param invoiceMaster Invoice to post.
     * @param userData User data.
     * @return A boolean indicating whether the invoice was posted successfully.
     * @throws EMCEntityBeanException
     */
    public boolean postDebtorsCustomerInvoice(DebtorsCustomerInvoiceMaster invoiceMaster, EMCUserData userData) throws EMCEntityBeanException;

     /**
     * This method posts a Credit Note.
     * @param creditNoteMaster Credit Note to post.
     * @param generateNewSTKNumbers Indicates whether new batch numbers should be generated
     * if the credit note is returning goods into stock.
     * @param userData User data.
     * @return A boolean indicating whether the Credit Note was posted successfully.
     * @throws EMCEntityBeanException
     */
    public boolean postDebtorsCreditNote(DebtorsCreditNoteMaster creditNoteMaster, boolean generateNewSTKNumbers, EMCUserData userData) throws EMCEntityBeanException;

    /**
     * Posts a settlement.
     * @param sessionId The session id for which settlement records should be allocated.
     * @param customerId Customer id.  Should be unique per session.  This is used when creating a discount transaction.
     * @param userData User data.
     * @return A boolean indicating success.
     * @throws EMCEntityBeanException
     */
    public boolean allocateSettlement(long sessionId, java.lang.String customerId, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    /**
     * Deallocates the specified settlement from the given DebtorsTransactionSettlementHistory instance.
     * @param settlementHistory Settlement to deallocate.
     * @param userData User data.
     * @return A boolean indicating success.
     * @throws EMCEntityBeanException
     */
    public boolean deallocateSettlement(DebtorsTransactionSettlementHistory settlementHistory, EMCUserData userData) throws EMCEntityBeanException;

    /**
     * Deallocates the specified rebate or settlement discount from the given DebtorsTransactionSettlementHistory instance.
     * @param settlementHistory Settlement to deallocate.
     * @param reverseTransaction The amount on this transaction will be updated when deallocating the specified settlement.
     * @param userData User data.
     * @return A the transaction that was created.
     * @throws EMCEntityBeanException
     */
    public DebtorsTransactions deallocateSettlement(DebtorsTransactionSettlementHistory settlementHistory, DebtorsTransactions reverseTransaction, EMCUserData userData) throws EMCEntityBeanException;
}
