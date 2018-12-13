/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.debtors;

import emc.framework.EMCEntityClass;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * @description : Entity class to represent open Debtors transactions.
 *
 * @date        : 18 May 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
@Entity
@Table(name = "DebtorsOpenTransactions", uniqueConstraints = {@UniqueConstraint(columnNames = {"referenceNumber", "referenceType", "companyId"})})
public class DebtorsOpenTransactions extends EMCEntityClass {

    private long debtorsTransRef;

    //Payment date/due date.
    @Temporal(TemporalType.DATE)
    private Date transactionDate;

    private String referenceType;
    private String referenceNumber;
    private BigDecimal credit = new BigDecimal(0);
    private BigDecimal debit = new BigDecimal(0);
    private BigDecimal debitAmountSettled = new BigDecimal(0);
    private BigDecimal creditAmountSettled = new BigDecimal(0);
    private String customerId;
    //Not normalized.  Stored to make calculations easier.
    private BigDecimal balance = new BigDecimal(0);

    /** Creates a new instance of DebtorsOpenTransactions */
    public DebtorsOpenTransactions() {
    }

    public long getDebtorsTransRef() {
        return debtorsTransRef;
    }

    public void setDebtorsTransRef(long debtorsTransRef) {
        this.debtorsTransRef = debtorsTransRef;
    }

    public String getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(String referenceType) {
        this.referenceType = referenceType;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
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

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
