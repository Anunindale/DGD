/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.creditors;

import emc.framework.EMCEntityClass;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "CreditorsOpenTransactions", uniqueConstraints = {@UniqueConstraint(columnNames = {"companyId", "referenceNumber", "referenceType"})})
public class CreditorsOpenTransactions extends EMCEntityClass {

    @Temporal(TemporalType.DATE)
    private Date transactionDate;
    private String description;
    private String transactionSource;
    private BigDecimal debit;
    private BigDecimal credit;
    private String supplierId;
    private BigDecimal vatAmount;
    private String referenceNumber;
    private String referenceType;
    private BigDecimal debitAmountSettled;
    private BigDecimal creditAmountSettled;
    @Temporal(TemporalType.DATE)
    private Date lastSettledDate;
    private BigDecimal balance;
    private String supplierOrderNumber;
    @Temporal(TemporalType.DATE)
    private Date settlementDiscDate;
    private BigDecimal settlementDiscPercentage;
    @Temporal(TemporalType.DATE)
    private Date paymentDueDate;
    private String purchaseOrderId;
    private long refTransRecId;

    public long getRefTransRecId() {
        return refTransRecId;
    }

    public void setRefTransRecId(long refTransRecId) {
        this.refTransRecId = refTransRecId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public BigDecimal getCreditAmountSettled() {
        return creditAmountSettled;
    }

    public void setCreditAmountSettled(BigDecimal creditAmountSettled) {
        this.creditAmountSettled = creditAmountSettled;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getLastSettledDate() {
        return lastSettledDate;
    }

    public void setLastSettledDate(Date lastSettledDate) {
        this.lastSettledDate = lastSettledDate;
    }

    public Date getPaymentDueDate() {
        return paymentDueDate;
    }

    public void setPaymentDueDate(Date paymentDueDate) {
        this.paymentDueDate = paymentDueDate;
    }

    public String getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(String purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
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

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierOrderNumber() {
        return supplierOrderNumber;
    }

    public void setSupplierOrderNumber(String supplierOrderNumber) {
        this.supplierOrderNumber = supplierOrderNumber;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionSource() {
        return transactionSource;
    }

    public void setTransactionSource(String transactionSource) {
        this.transactionSource = transactionSource;
    }

    public BigDecimal getVatAmount() {
        return vatAmount;
    }

    public void setVatAmount(BigDecimal vatAmount) {
        this.vatAmount = vatAmount;
    }
}
