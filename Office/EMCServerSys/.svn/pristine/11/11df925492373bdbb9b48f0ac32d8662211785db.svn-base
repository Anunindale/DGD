/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.gl.transactions.logic.posthelpers;

import emc.entity.gl.transactions.GLTransactionsDetail;
import java.util.List;

/**
 *
 * @author riaan
 */
public interface GLTransactionPostHelper {

    /**
     * Adds a transactions to this post helper instance.  All transactions
     * created during posting should be added to the helper instance used
     * to create the transactions.
     * @param transaction
     */
    public void addTransaction(GLTransactionsDetail transaction);

    /** 
     * Returns a list of transactions created during posting.
     * @return A list of transactions created during posting.
     */
    public List<GLTransactionsDetail> getTransactions();
}
