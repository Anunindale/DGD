/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.gl.transactions.logic.posthelpers;

import emc.entity.gl.transactions.GLTransactionsDetail;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author riaan
 */
public abstract class AbstractGLTransactionPostHelper implements GLTransactionPostHelper {

    private List<GLTransactionsDetail> transactions = new ArrayList<GLTransactionsDetail>();
    
    /**
     * Creates a new instance of AbstractGLTransactionPostHelper.
     */
    public AbstractGLTransactionPostHelper() {
        
    }


     /**
     * Adds a transactions to this post helper instance.  All transactions
     * created during posting should be added to the helper instance used
     * to create the transactions.
     * @param transaction
     */
    public void addTransaction(GLTransactionsDetail transaction) {
        this.transactions.add(transaction);
    }

    /**
     * Returns a list of transactions created during posting.
     * @return A list of transactions created during posting.
     */
    public List<GLTransactionsDetail> getTransactions() {
        return this.transactions;
    }

}
