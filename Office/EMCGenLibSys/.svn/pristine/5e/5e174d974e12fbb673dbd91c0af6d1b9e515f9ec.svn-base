/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.helpers.debtors;

import java.math.BigDecimal;
import java.util.List;

/**
 * @description : This helper class is used to transfer customer balance data
 *                between the client and server.
 *
 * @date        : 21 Jun 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class DebtorsCustomerBalanceHelper {

    private BigDecimal currentBalance;
    private BigDecimal postDatedPaymentTotal;
    private BigDecimal unallocatedCredits = BigDecimal.ZERO;
    private BigDecimal openPickingListValue = BigDecimal.ZERO;
    private BigDecimal openASOValue = BigDecimal.ZERO;
    private BigDecimal creditsBalance = BigDecimal.ZERO;

    private List<DebtorsAgingHelper> agingList;
    
    /** Creates a new instance of DebtorsCustomerBalanceHelper */
    public DebtorsCustomerBalanceHelper() {

    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    public BigDecimal getPostDatedPaymentTotal() {
        return postDatedPaymentTotal;
    }

    public void setPostDatedPaymentTotal(BigDecimal postDatedPaymentTotal) {
        this.postDatedPaymentTotal = postDatedPaymentTotal;
    }

    public BigDecimal getUnallocatedCredits() {
        return unallocatedCredits;
    }

    public void setUnallocatedCredits(BigDecimal unallocatedCredits) {
        this.unallocatedCredits = unallocatedCredits;
    }

    public List<DebtorsAgingHelper> getAgingList() {
        return agingList;
    }

    public void setAgingList(List<DebtorsAgingHelper> agingList) {
        this.agingList = agingList;
    }

    public BigDecimal getOpenPickingListValue() {
        return openPickingListValue;
    }

    public void setOpenPickingListValue(BigDecimal openPickingListValue) {
        this.openPickingListValue = openPickingListValue;
    }

    public BigDecimal getOpenASOValue() {
        return openASOValue;
    }

    public void setOpenASOValue(BigDecimal openASOValue) {
        this.openASOValue = openASOValue;
    }

    public BigDecimal getCreditsBalance() {
        return creditsBalance;
    }

    public void setCreditsBalance(BigDecimal creditsBalance) {
        this.creditsBalance = creditsBalance;
    }
    
}
