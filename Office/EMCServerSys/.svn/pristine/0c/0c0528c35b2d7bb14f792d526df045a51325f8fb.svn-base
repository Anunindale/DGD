/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.transactions;

import emc.entity.inventory.transactions.InventoryTransactions;
import emc.enums.inventory.transactions.InventoryTransactionDirection;
import emc.enums.inventory.transactions.InventoryTransactionStatus;
import emc.enums.inventory.transactions.InventoryTransactionTypes;
import emc.enums.inventory.transactions.InventoryTransactionsRefType;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author riaan
 */
@Local
public interface InventoryTransactionsLocal extends EMCEntityBeanLocalInterface {

    public long findTransRecordId(String transId, EMCUserData userData);

    public String findTransId(long transRecordId, EMCUserData userData);

    /** This method returns all transactions matching the given parameters.  All parameters are optional.  Those with null values will be excluded from the query generated to select transactions.
     *
     * @param refNumber Ref number on transactions to find.
     * @param refType Ref type of transactions to find.
     * @param type Type of transactions to find.
     * @param status Status of transactions to find.
     * @param direction Direction of transactions to find.
     * @param transId Trans id of transactions to find.
     * @param userData User data.
     * @return A list of transactions matching the specified criteria.
     */
    public java.util.List<emc.entity.inventory.transactions.InventoryTransactions> findTransactions(java.lang.String refNumber, emc.enums.inventory.transactions.InventoryTransactionsRefType refType, emc.enums.inventory.transactions.InventoryTransactionTypes type, emc.enums.inventory.transactions.InventoryTransactionStatus status, emc.enums.inventory.transactions.InventoryTransactionDirection direction, java.lang.String transId, emc.framework.EMCUserData userData);

    /**
     * Used to set the stockCloseDate. No transactions may be inserted before this date.
     * The bean reads this date from the stock settlement table.
     * The stock settelment routine set this during processing
     * To force a refresh read from the table pass in null for toSet.
     * @param toSet
     * @param userData
     */
    public void setStockCloseDate(Date toSet, EMCUserData userData);

    /**
     * Returns a list of transactions matching the specified criteria.  Null parameters may be passed to this method - they will be ignored.  (0 for dimension id)
     * @param refNumber Reference number.
     * @param itemId Item id.
     * @param dimensionId Dimension id.
     * @param transId Transaction id.
     * @param refType Reference type.
     * @param type Transaction type.
     * @param direction Transaction direction.
     * @param status Transaction status.
     * @param userData User data.
     * @return A list of transactions matching the specified criteria.
     */
    public List<InventoryTransactions> findTransactions(String refNumber, String itemId, long dimensionId, String transId, InventoryTransactionsRefType refType, InventoryTransactionTypes type, InventoryTransactionDirection direction, InventoryTransactionStatus status, EMCUserData userData);

    /** Returns the transaction with the specified record id. */
    public emc.entity.inventory.transactions.InventoryTransactions findTransaction(long transactionRecordId, emc.framework.EMCUserData userData);

    public void doUpdate(Object dobject, EMCUserData userData);

    /**
     * Returns the last stock close date for the company specified in userData.
     * @param userData User data.
     * @return The last stock close date for the company specified in userData.
     *          If no stock close has been done, this method will return null.
     */
    public Date getLastStockCloseDate(EMCUserData userData);
}
