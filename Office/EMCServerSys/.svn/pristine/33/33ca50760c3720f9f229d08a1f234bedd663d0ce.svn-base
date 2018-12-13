/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.debtors.transactions;

import emc.entity.debtors.DebtorsTransactions;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import java.math.BigDecimal;
import java.util.Date;
import javax.ejb.Local;

/**
 *
 * @author wikus
 */
@Local
public interface DebtorsTransactionsLocal extends EMCEntityBeanLocalInterface {

    public BigDecimal calculateReferenceBallance(java.lang.String refNumber, emc.framework.EMCUserData userData);

    public BigDecimal calculateTotalBallance(emc.framework.EMCUserData userData);
 
    /**
     * Deletes the debtors open transactions corresponding to the specified transaction.
     * @param trans Transaction.
     * @param userData User data.
     * @return A boolean indicating success.
     * @throws EMCEntityBeanException
     */
    public boolean deleteOpenTransaction(DebtorsTransactions trans, EMCUserData userData) throws EMCEntityBeanException;

    /**
     * Selects and returns the transaction with the specified record ID.
     * @param transRecordID Transaction record ID.
     * @param userData User data.
     * @return A DebtorsTransaction instance, or null, if none is found.
     */
    public DebtorsTransactions getTransaction(long transRecordID, EMCUserData userData);

    /**
     * Sets the last closed date for the company identified by the specified userData instance.
     * @param closedDate Closed date.
     * @param userData User data.
     */
    public void setLastClosedDate(Date closedDate, EMCUserData userData);
    /**
     * Loads the last closed date for the company identified by the specified userData instance.
     * @param userData User data.
     */
    public void loadLastClosedDate(EMCUserData userData);

    /** This is a temporary method used to calculate due dates on DEBIT transactions without due dates. */
    public boolean calculateDatesAndDisc(EMCUserData userData) throws EMCEntityBeanException;
}
