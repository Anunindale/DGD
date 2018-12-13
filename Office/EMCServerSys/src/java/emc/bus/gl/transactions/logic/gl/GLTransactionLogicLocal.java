/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.gl.transactions.logic.gl;

import emc.entity.gl.transactions.GLTransactionsDetail;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCUserData;
import javax.ejb.Local;

/**
 *
 * @author riaan
 */
@Local
public interface GLTransactionLogicLocal {

    /**
     * Consolidates GL transactions from the GLTransactionsDetail table into the
     * GLTransactionsSummary table.
     * @param userData User data.
     * @return A boolean indicating success.
     * @throws EMCEntityBeanException
     */
    public boolean doGLConsolidation(EMCUserData userData) throws EMCEntityBeanException;

    /**
     * This method posts transactions for a GL journal line.
     * @param postHelper Helper class containing information required for posting.
     * @param userData User data.
     * @return A boolean indicating success.
     * @throws emc.bus.gl.transactions.logic.GLTransactionException
     */
    public boolean postGLJournal(emc.bus.gl.transactions.logic.posthelpers.gl.GLJournalPostHelper postHelper, emc.framework.EMCUserData userData) throws emc.bus.gl.transactions.logic.GLTransactionException;

    /**
     * Resets all detail transactions to the unprocessed state, clears the
     * transaction summary, period summary and day summary tables and then
     * reprocesses detail transactions.
     * @param userData User data.
     * @return A boolean indicating success.
     * @throws EMCEntityBeanException
     */
    public boolean regenerateSummaryTables(emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    /**
     * This method update the transaction summary tables with the totals reflected
     * on the specified transaction.
     * @param callingContainerTransKey Transaction key of the container transaction
     * from which this method is called.  Used to roll back changes.
     * @param transaction GL transaction to consolidate.
     * @param userData User data.
     * @return A boolean indicating that the operation was successful.
     * @throws EMCEntityBeanException
     */
    @javax.ejb.TransactionAttribute(value = javax.ejb.TransactionAttributeType.REQUIRES_NEW)
    public boolean consolidateTransaction(final Object callingContainerTransKey, GLTransactionsDetail transaction, emc.framework.EMCUserData userData) throws EMCEntityBeanException;
}
