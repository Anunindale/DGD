/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.reports.inventory.ageing;

import emc.entity.inventory.transactions.InventoryTransactions;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author rico
 */
public class ResolverResult {
    private boolean sucess = true;
    private List<ResolverData> originalTransactions = new ArrayList();
    private List<InventoryTransactions> outTransactions = new ArrayList();
    private List<String> warningMessages = new ArrayList();
    private double left; //convenience
    private List<Long> stack = new ArrayList(); //keep track of the transactions resolved if an in is called again on the
    //same stack - overflow will occur.


    public void setOriginalTransaction(InventoryTransactions oTx,double qty,Date resDate){
        getOriginalTransactions().add(new ResolverData(oTx, qty, resDate));
    }

    public void addWarning(String toAdd){
        getWarningMessages().add(toAdd);
    }

    /**
     * @return the sucess
     */
    public boolean isSucess() {
        return sucess;
    }

    /**
     * @param sucess the sucess to set
     */
    public void setSucess(boolean sucess) {
        this.sucess = sucess;
    }

    /**
     * @return the originalTransactions
     */
    public List<ResolverData> getOriginalTransactions() {
        return originalTransactions;
    }

    /**
     * @return the warningMessages
     */
    public List<String> getWarningMessages() {
        return warningMessages;
    }

    /**
     * @return the left
     */
    public double getLeft() {
        return left;
    }

    /**
     * @param left the left to set
     */
    public void setLeft(double left) {
        this.left = left;
    }

    /**
     * @return the stack
     */
    public List<Long> getStack() {
        return stack;
    }

    /**
     * @return the outTransactions
     */
    public List<InventoryTransactions> getOutTransactions() {
        return outTransactions;
    }

    /**
     * @param outTransactions the outTransactions to set
     */
    public void setOutTransactions(List<InventoryTransactions> outTransactions) {
        this.outTransactions = outTransactions;
    }


    

}
