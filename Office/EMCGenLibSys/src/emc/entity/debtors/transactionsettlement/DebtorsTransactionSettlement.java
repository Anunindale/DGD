/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.debtors.transactionsettlement;

import emc.datatypes.EMCDataType;
import emc.datatypes.debtors.transactions.ReferenceNumber;
import emc.datatypes.debtors.transactionsettlement.Credit;
import emc.datatypes.debtors.transactionsettlement.CreditAmountSettled;
import emc.datatypes.debtors.transactionsettlement.CreditBalance;
import emc.datatypes.debtors.transactionsettlement.CustomerOrderNumber;
import emc.datatypes.debtors.transactionsettlement.DebitAmountSettled;
import emc.datatypes.debtors.transactionsettlement.DebitBalance;
import emc.datatypes.debtors.transactionsettlement.DiscAvailable;
import emc.datatypes.debtors.transactionsettlement.DiscTaken;
import emc.datatypes.debtors.transactionsettlement.OriginalCredit;
import emc.datatypes.debtors.transactionsettlement.Rebate;
import emc.datatypes.debtors.transactionsettlement.ReferenceType;
import emc.datatypes.debtors.transactionsettlement.Tick;
import emc.datatypes.debtors.transactionsettlement.TransactionDate;
import emc.framework.EMCEntityClass;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @description : This class is used to temporarily hold records while allocating
 *                debit/credit transactions in the Debtors module.
 *
 * @date        : 27 May 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
@Entity
@Table(name = "DebtorsTransactionSettlement")
public class DebtorsTransactionSettlement extends EMCEntityClass {

    private long sessionId;
    private long debtorsTransRef;
    private long transVersionNumber;
    private String referenceNumber;
    private String referenceType;
    private String customerOrderNumber;
    //Not transaction total.  Transaction total - settled on transaction.
    private BigDecimal debit = new BigDecimal(0);
    private BigDecimal credit = new BigDecimal(0);
    private BigDecimal discAvail = new BigDecimal(0);
    private BigDecimal discTaken = new BigDecimal(0);
    private BigDecimal rebate = new BigDecimal(0);
    //Amounts for this settlement only; not total settled on transaction.
    private BigDecimal debitAmountSettled = new BigDecimal(0);
    private BigDecimal creditAmountSettled = new BigDecimal(0);
    private BigDecimal debitBalance = new BigDecimal(0);
    private BigDecimal creditBalance = new BigDecimal(0);
    //Original transaction credit amount
    private BigDecimal originalCredit;
    @Temporal(TemporalType.DATE)
    private Date transactionDate;
    private boolean tick;
    //Used to reduce selections when calculating discounts.
    @Temporal(TemporalType.DATE)
    private Date settlementDiscDate;
    private BigDecimal settlementDiscPercentage;
    //Indicates whether a user has changed the amount on a line.
    private boolean userChangedAmount;

    /** Creates a new instance of DebtorsTransactionSettlement */
    public DebtorsTransactionSettlement() {
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("tick", new Tick());
        toBuild.put("referenceNumber", new ReferenceNumber());
        toBuild.put("referenceType", new ReferenceType());
        toBuild.put("debitBalance", new DebitBalance());
        toBuild.put("creditBalance", new CreditBalance());
        toBuild.put("debitAmountSettled", new DebitAmountSettled());
        toBuild.put("creditAmountSettled", new CreditAmountSettled());
        //toBuild.put("debit", new Debit());
        toBuild.put("credit", new Credit());
        toBuild.put("discAvail", new DiscAvailable());
        toBuild.put("discTaken", new DiscTaken());
        toBuild.put("customerOrderNumber", new CustomerOrderNumber());
        toBuild.put("originalCredit", new OriginalCredit());
        toBuild.put("rebate", new Rebate());
        toBuild.put("transactionDate", new TransactionDate());

        return toBuild;
    }

    public long getSessionId() {
        return sessionId;
    }

    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }

    public long getDebtorsTransRef() {
        return debtorsTransRef;
    }

    public void setDebtorsTransRef(long debtorsTransRef) {
        this.debtorsTransRef = debtorsTransRef;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(String referenceType) {
        this.referenceType = referenceType;
    }

    public BigDecimal getDebit() {
        return debit;
    }

    public void setDebit(BigDecimal debit) {
        this.debit = debit;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public BigDecimal getDiscAvail() {
        return discAvail;
    }

    public void setDiscAvail(BigDecimal discAvail) {
        this.discAvail = discAvail;
    }

    public BigDecimal getDiscTaken() {
        return discTaken;
    }

    public void setDiscTaken(BigDecimal discTaken) {
        this.discTaken = discTaken;
    }

    public BigDecimal getDebitAmountSettled() {
        return debitAmountSettled;
    }

    public void setDebitAmountSettled(BigDecimal debitAmountSettled) {
        this.debitAmountSettled = debitAmountSettled;
    }

    public BigDecimal getCreditAmountSettled() {
        return creditAmountSettled;
    }

    public void setCreditAmountSettled(BigDecimal creditAmountSettled) {
        this.creditAmountSettled = creditAmountSettled;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public boolean isTick() {
        return tick;
    }

    public void setTick(boolean tick) {
        this.tick = tick;
    }

    public BigDecimal getDebitBalance() {
        return debitBalance;
    }

    public void setDebitBalance(BigDecimal debitBalance) {
        this.debitBalance = debitBalance;
    }

    public BigDecimal getCreditBalance() {
        return creditBalance;
    }

    public void setCreditBalance(BigDecimal creditBalance) {
        this.creditBalance = creditBalance;
    }

    public long getTransVersionNumber() {
        return transVersionNumber;
    }

    public void setTransVersionNumber(long transVersionNumber) {
        this.transVersionNumber = transVersionNumber;
    }

    public boolean isUserChangedAmount() {
        return userChangedAmount;
    }

    public void setUserChangedAmount(boolean userChangedAmount) {
        this.userChangedAmount = userChangedAmount;
    }

    public String getCustomerOrderNumber() {
        return customerOrderNumber;
    }

    public void setCustomerOrderNumber(String customerOrderNumber) {
        this.customerOrderNumber = customerOrderNumber;
    }

    public BigDecimal getOriginalCredit() {
        return originalCredit;
    }

    public void setOriginalCredit(BigDecimal originalCredit) {
        this.originalCredit = originalCredit;
    }

    public BigDecimal getRebate() {
        return rebate;
    }

    public void setRebate(BigDecimal rebate) {
        this.rebate = rebate;
    }

    public BigDecimal getSettlementDiscPercentage() {
        return settlementDiscPercentage;
    }

    public void setSettlementDiscPercentage(BigDecimal settlementDiscPercentage) {
        this.settlementDiscPercentage = settlementDiscPercentage;
    }

    public Date getSettlementDiscDate() {
        return settlementDiscDate;
    }

    public void setSettlementDiscDate(Date settlementDiscDate) {
        this.settlementDiscDate = settlementDiscDate;
    }
}
