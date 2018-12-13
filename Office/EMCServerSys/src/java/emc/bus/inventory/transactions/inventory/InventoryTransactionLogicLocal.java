/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.transactions.inventory;

import emc.bus.inventory.transactions.EMCStockException;
import emc.bus.inventory.transactions.TransactionHelper;
import emc.entity.inventory.journals.InventoryJournalLines;
import emc.entity.inventory.movestock.InventoryMoveStockMaster;
import emc.entity.inventory.picking.InventoryPickingListLines;
import emc.entity.inventory.picking.InventoryPickingListMaster;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCUserData;
import emc.tables.EMCTable;
import javax.ejb.Local;

/**
 *
 * @author wikus
 */
@Local
public interface InventoryTransactionLogicLocal {

    /**
     * Posts a journal line
     * @param theLine
     * @param userData
     * @return
     * @throws emc.bus.inventory.transactions.EMCStockException
     */
    public EMCTable postJournalLine(InventoryJournalLines theLine, TransactionHelper th, EMCUserData userData) throws EMCStockException;

    /**
     * Post journal Master
     * @param iJournalMast
     * @param userData
     * @return
     */
    public emc.tables.EMCTable postInventJournal(emc.entity.inventory.journals.InventoryJournalMaster iJournalMast, boolean actualPosting, emc.framework.EMCUserData userData) throws emc.bus.inventory.transactions.EMCStockException;

    /**
     * Moves Stock Around
     * @param MSMaster
     * @param userData
     * @return
     * @throws emc.bus.inventory.transactions.EMCStockException
     */
    public EMCTable postMoveStockLine(InventoryMoveStockMaster MSMaster, EMCUserData userData) throws EMCStockException;

    public emc.tables.EMCTable postPickingList(emc.entity.inventory.picking.InventoryPickingListLines lineToPick, emc.bus.inventory.transactions.TransactionHelper txH, emc.framework.EMCUserData userData) throws emc.bus.inventory.transactions.EMCStockException;

    /**
     * Re-reserves the stock no the pick list line with the new values
     * @param lineToPick
     * @param txH
     * @param userData
     * @return
     * @throws emc.bus.inventory.transactions.EMCStockException
     * @throws emc.framework.EMCEntityBeanException
     */
    public emc.tables.EMCTable postReReservePickList(emc.entity.inventory.picking.rereservepicklist.InventoryReReservePickList lineToPick, emc.bus.inventory.transactions.TransactionHelper txH, emc.framework.EMCUserData userData) throws emc.bus.inventory.transactions.EMCStockException, emc.framework.EMCEntityBeanException;

    /**
     * Moves Stock Around - Overloaded version to allow move to different warehouse as well.
     * @param MSMaster
     * @param warehouse
     * @param userData
     * @return
     * @throws emc.bus.inventory.transactions.EMCStockException
     */
    public emc.tables.EMCTable postMoveStockLine(emc.entity.inventory.movestock.InventoryMoveStockMaster MSMaster, java.lang.String toWarehouse, emc.framework.EMCUserData userData) throws emc.bus.inventory.transactions.EMCStockException;

    /**
     * Reserves stock against a movement journal line.
     * @param journalLine Journal line to reserve stock against.
     * @param register Register record to use when reserving stock.  May be null.
     * @param orderTx Ordered transaction.
     * @param userData User data.
     * @return The journal line that has been reserved.
     */
    //public InventoryJournalLines reserveMovementJournalLine(InventoryJournalLines journalLine, InventoryRemoveRegister register, InventoryTransactions orderTx, EMCUserData userData) throws EMCStockException;
    /**
     * Unreserves stock against a movement journal line.
     * @param journalLine Journal line to reserve stock against.
     * @param register Register record to use when unreserving stock.
     * @param orderAgain Indicates whether unreserved stock should be addded back to an ordered transaction.
     * @param userData User data.
     * @return The journal line that has been unreserved.
     */
    //public InventoryJournalLines unreserveMovementJournalLine(InventoryJournalLines journalLine, InventoryRemoveRegister register, boolean orderAgain, EMCUserData userData) throws EMCStockException;
    /**
     * Cancels (unreserves) the specified Picking List line.
     * @param pickingListMaster Picking List master to which the line belongs.
     * @param pickingListLine Line to cancel.
     * @param userData User data.
     * @return The line that has been cancelled.
     * @throws EMCEntityBeanException, EMCStockException
     */
    public EMCTable cancelPickingListLine(InventoryPickingListMaster pickingListMaster, InventoryPickingListLines pickingListLine, EMCUserData userData) throws EMCEntityBeanException, EMCStockException;

    public EMCTable postMoveReservedStockLine(InventoryMoveStockMaster MSMaster, EMCUserData userData) throws EMCStockException;
}
