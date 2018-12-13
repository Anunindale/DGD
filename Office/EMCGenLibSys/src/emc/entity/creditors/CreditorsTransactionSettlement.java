/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.creditors;

import emc.datatypes.EMCDataType;
import emc.datatypes.creditors.transactions.Credit;
import emc.datatypes.creditors.transactions.CreditAmountSettled;
import emc.datatypes.creditors.transactions.DebitAmountSettled;
import emc.datatypes.creditors.transactions.ReferenceNumber;
import emc.datatypes.creditors.transactions.ReferenceType;
import emc.datatypes.creditors.transactionsettlement.SupplierOrderNumber;
import emc.datatypes.creditors.transactions.TransactionDate;
import emc.datatypes.creditors.transactionsettlement.CreditBalance;
import emc.datatypes.creditors.transactionsettlement.DebitBalance;
import emc.datatypes.creditors.transactionsettlement.DiscAvailable;
import emc.datatypes.creditors.transactionsettlement.DiscTaken;
import emc.datatypes.creditors.transactionsettlement.OriginalCredit;
import emc.datatypes.creditors.transactionsettlement.Rebate;
import emc.datatypes.debtors.transactionsettlement.Tick;
import emc.framework.EMCEntityClass;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Owner
 */
@Entity
@Table(name = "CreditorsTransactionSettlement")
public class CreditorsTransactionSettlement extends EMCEntityClass {

    private long sessionID;
    private long creditorsTransRef;
    private long transVersionNumber;
    private String referenceNumber;
    private String referenceType;
    private String supplierOrderNumber;
    //Not transaction total.  Transaction total - settled on transaction.
    private BigDecimal credit = new BigDecimal(0);
    private BigDecimal debit = new BigDecimal(0);
    private BigDecimal discAvail = new BigDecimal(0);
    private BigDecimal discTaken = new BigDecimal(0);
    private BigDecimal rebate = new BigDecimal(0);
    //Amounts for this settlement only; not total settled on transaction.
    private BigDecimal creditAmountSettled = new BigDecimal(0);
    private BigDecimal debitAmountSettled = new BigDecimal(0);
    private BigDecimal creditBalance = new BigDecimal(0);
    private BigDecimal debitBalance = new BigDecimal(0);
    //Original transaction credit amount
    private BigDecimal originalCredit = new BigDecimal(0);
    @Temporal(TemporalType.DATE)
    private Date transactionDate;
    private boolean tick;
    //Used to reduce selections when calculating discounts.
    @Temporal(TemporalType.DATE)
    private Date settlementDiscDate;
    private BigDecimal settlementDiscPercentage;
    //Indicates whether a user has changed the amount on a line.
    private boolean userChangedAmount;

    public CreditorsTransactionSettlement() {
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
        toBuild.put("supplierOrderNumber", new SupplierOrderNumber());
        toBuild.put("originalCredit", new OriginalCredit());
        toBuild.put("rebate", new Rebate());
        toBuild.put("transactionDate", new TransactionDate());

        return toBuild;
    }

    public long getSessionID() {
        return sessionID;
    }

    public void setSessionID(long sessionID) {
        this.sessionID = sessionID;
    }

    public long getCreditorsTransRef() {
        return creditorsTransRef;
    }

    public void setCreditorsTransRef(long creditorsTransRef) {
        this.creditorsTransRef = creditorsTransRef;
    }

    public long getTransVersionNumber() {
        return transVersionNumber;
    }

    public void setTransVersionNumber(long transVersionNumber) {
        this.transVersionNumber = transVersionNumber;
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

    public String getSupplierOrderNumber() {
        return supplierOrderNumber;
    }

    public void setSupplierOrderNumber(String supplierOrderNumber) {
        this.supplierOrderNumber = supplierOrderNumber;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public BigDecimal getDebit() {
        return debit;
    }

    public void setDebit(BigDecimal debit) {
        this.debit = debit;
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

    public BigDecimal getRebate() {
        return rebate;
    }

    public void setRebate(BigDecimal rebate) {
        this.rebate = rebate;
    }

    public BigDecimal getCreditAmountSettled() {
        return creditAmountSettled;
    }

    public void setCreditAmountSettled(BigDecimal creditAmountSettled) {
        this.creditAmountSettled = creditAmountSettled;
    }

    public BigDecimal getDebitAmountSettled() {
        return debitAmountSettled;
    }

    public void setDebitAmountSettled(BigDecimal debitAmountSettled) {
        this.debitAmountSettled = debitAmountSettled;
    }

    public BigDecimal getCreditBalance() {
        return creditBalance;
    }

    public void setCreditBalance(BigDecimal creditBalance) {
        this.creditBalance = creditBalance;
    }

    public BigDecimal getDebitBalance() {
        return debitBalance;
    }

    public void setDebitBalance(BigDecimal debitBalance) {
        this.debitBalance = debitBalance;
    }

    public BigDecimal getOriginalCredit() {
        return originalCredit;
    }

    public void setOriginalCredit(BigDecimal originalCredit) {
        this.originalCredit = originalCredit;
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

    public Date getSettlementDiscDate() {
        return settlementDiscDate;
    }

    public void setSettlementDiscDate(Date settlementDiscDate) {
        this.settlementDiscDate = settlementDiscDate;
    }

    public BigDecimal getSettlementDiscPercentage() {
        return settlementDiscPercentage;
    }

    public void setSettlementDiscPercentage(BigDecimal settlementDiscPercentage) {
        this.settlementDiscPercentage = settlementDiscPercentage;
    }

    public boolean isUserChangedAmount() {
        return userChangedAmount;
    }

    public void setUserChangedAmount(boolean userChangedAmount) {
        this.userChangedAmount = userChangedAmount;
    }
}
