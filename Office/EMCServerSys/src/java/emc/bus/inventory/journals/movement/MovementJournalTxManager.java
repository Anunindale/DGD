/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.journals.movement;

import emc.entity.inventory.transactions.InventoryTransactions;
import emc.framework.EMCUserData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @name : MovementJournalTxManager
 *
 * @description : This class is used to manage transactions during the movement
 * journal line post process.
 *
 * @date : 18 Jan 2010
 *
 * @author : riaan
 */
public class MovementJournalTxManager {

    private MovementTxManagerHelper orderTransaction;
    private List<MovementTxManagerHelper> reservedTransactions = new ArrayList<MovementTxManagerHelper>();
    //If any stock should be unreserved, the quantity to be unreserved for
    //each transactions should be stored in this map.  They key should consist
    //of a concatenation of the item and dimension id.
    private Map<String, Double> quantitiesToUnreserve;

    /**
     * Creates a new instance of MovementJournalTxManager.
     */
    public MovementJournalTxManager() {
    }

    public List<InventoryTransactions> getTransactionsToInsert() {
        List<InventoryTransactions> ret = new ArrayList<InventoryTransactions>();

        if (this.orderTransaction.status == MovementJournalTxManagerStatus.INSERT) {
            ret.add(orderTransaction.trans);
        }

        for (MovementTxManagerHelper resTrans : this.reservedTransactions) {
            if (resTrans.status == MovementJournalTxManagerStatus.INSERT) {
                ret.add(resTrans.trans);
            }
        }

        return ret;
    }

    public List<InventoryTransactions> getTransactionsToUpdate() {
        List<InventoryTransactions> ret = new ArrayList<InventoryTransactions>();

        if (this.orderTransaction.status == MovementJournalTxManagerStatus.UPDATE) {
            ret.add(orderTransaction.trans);
        }

        for (MovementTxManagerHelper resTrans : this.reservedTransactions) {
            if (resTrans.status == MovementJournalTxManagerStatus.UPDATE) {
                ret.add(resTrans.trans);
            }
        }

        return ret;
    }

    public List<InventoryTransactions> getTransactionsToDelete() {
        List<InventoryTransactions> ret = new ArrayList<InventoryTransactions>();

        if (this.orderTransaction.status == MovementJournalTxManagerStatus.DELETE) {
            ret.add(orderTransaction.trans);
        }

        for (MovementTxManagerHelper resTrans : this.reservedTransactions) {
            if (resTrans.status == MovementJournalTxManagerStatus.DELETE) {
                ret.add(resTrans.trans);
            }
        }

        return ret;
    }

    public void setOrderedTrans(InventoryTransactions orderedTransaction) {
        this.orderTransaction = new MovementTxManagerHelper(orderedTransaction);
    }

    public void addReservedTrans(InventoryTransactions reservedTransaction) {
        this.reservedTransactions.add(new MovementTxManagerHelper(reservedTransaction));
    }

    public void setTransactionStatus(InventoryTransactions transaction, double unreserveQty, MovementJournalTxManagerStatus status) {
        if (transaction == this.orderTransaction.trans) {
            this.orderTransaction.status = status;
        } else {
            for (MovementTxManagerHelper helper : this.reservedTransactions) {
                if (transaction.getRecordID() == helper.trans.getRecordID()) {
                    helper.status = status;
                    if (status == MovementJournalTxManagerStatus.DELETE) {
                        helper.unreserveQty = unreserveQty < 0 ? unreserveQty * -1 : unreserveQty;
                    }
                    return;
                }
            }
        }
    }

    public MovementJournalTxManagerStatus getTransactionStatus(InventoryTransactions transaction) {
        if (transaction == this.orderTransaction.trans) {
            return this.orderTransaction.status;
        } else {
            for (MovementTxManagerHelper helper : this.reservedTransactions) {
                if (transaction.getRecordID() == helper.trans.getRecordID()) {
                    return helper.status;
                }
            }
        }

        return null;
    }

    public double getTransactionUnreservedQty(InventoryTransactions transaction) {
        for (MovementTxManagerHelper helper : this.reservedTransactions) {
            if (transaction.getRecordID() == helper.trans.getRecordID()) {
                return helper.unreserveQty;
            }
        }

        return 0;
    }

    public InventoryTransactions getReservedTransaction(String itemId, long dimId, EMCUserData userData) {
        for (MovementTxManagerHelper helper : reservedTransactions) {
            InventoryTransactions trans = helper.trans;

            if (trans.getItemId().equals(trans.getItemId()) && trans.getItemDimId() == dimId) {
                return trans;
            }
        }

        return null;
    }

    public List<InventoryTransactions> getReservedTransactions() {
        List<InventoryTransactions> ret = new ArrayList<InventoryTransactions>();

        for (MovementTxManagerHelper txHelper : reservedTransactions) {
            ret.add(txHelper.trans);
        }

        return ret;
    }

    public InventoryTransactions getOrderedTransaction() {
        if (this.orderTransaction == null) {
            return null;
        } else {
            return this.orderTransaction.trans;
        }
    }

    /**
     * This method is used to keep track of the number of items to be unreserved
     * against a particular transaction. This information is required when there
     * are multiple register lines with the same dimensions, and when the
     * dimensions on a register line are changed.
     *
     * @param transaction Transaction against which stock should be unreserved.
     * @param quantity Quantity to unreserve.
     */
    public void setQuantityToUnreserve(InventoryTransactions transaction, Double quantity) {
        String key = transaction.getItemId() + transaction.getItemDimId();
        if (quantitiesToUnreserve == null) {
            quantitiesToUnreserve = new HashMap<String, Double>();
        }
        quantitiesToUnreserve.put(key, quantity);
    }

    /**
     * Returns the number of item to be unreserved against a specific
     * transaction.
     *
     * @param transaction Transaction to check.
     * @return The number of items to be unreserved against the specified
     * transaction.
     */
    public double getQuantityToUnreserve(InventoryTransactions transaction) {
        String key = transaction.getItemId() + transaction.getItemDimId();
        if (quantitiesToUnreserve == null || !quantitiesToUnreserve.containsKey(key)) {
            return 0d;
        } else {
            return quantitiesToUnreserve.get(key);
        }
    }

    //This class is used to map a transaction status to a transaction.
    private class MovementTxManagerHelper {

        MovementJournalTxManagerStatus status = MovementJournalTxManagerStatus.IGNORE;
        InventoryTransactions trans;
        double unreserveQty = 0;

        /**
         * Creates a new instance of MovementTxManagerHelper.
         */
        public MovementTxManagerHelper(InventoryTransactions trans) {
            this.trans = trans;
        }
    }
}
