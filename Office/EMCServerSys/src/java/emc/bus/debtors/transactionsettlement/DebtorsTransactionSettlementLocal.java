/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.debtors.transactionsettlement;

import emc.entity.debtors.allocationimport.DebtorsAllocationImport;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import java.util.List;
import javax.ejb.Local;

/**
 * @description : Local interface for DebtorsTransactionSettlementBean.
 *
 * @date        : 10 Jun 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
@Local
public interface DebtorsTransactionSettlementLocal extends EMCEntityBeanLocalInterface {

    /**
     * This method is used to populate the DebtorsTransactionSettlement table
     * with data for the active user.  Records can be identified using a
     * combination of the session id returned by this method, and a user id.
     *
     * @param customerId Customer id for which records should be created.
     * @param userData User data.
     * @return The session id for the records that were created.
     * @throws EMCEntityBeanException
     */
    public long populateSettlement(java.lang.String customerId, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    /**
     * Clears settlement records for the specified session.
     * @param sessionId Session id.
     * @param userData User data.
     * @return A boolean indicating success.
     * @throws EMCEntityBeanException
     */
    public boolean clearSettlement(long sessionId, EMCUserData userData) throws EMCEntityBeanException;

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
     * Imports data in the specified list to the specified allocation session.
     * @param allocationImport Allocation import record.
     * @param sessionId Allocation session id.
     * @param importData Data to import.  The first line should contain column headings.
     * @param userData User data.
     * @return A boolean indicating success.
     * @throws EMCEntityBeanException
     */
    public boolean importSettlement(DebtorsAllocationImport allocationImport, long sessionId, List<String> importData, EMCUserData userData) throws EMCEntityBeanException;
}
