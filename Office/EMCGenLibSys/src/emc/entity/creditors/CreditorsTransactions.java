package emc.entity.creditors;

import emc.datatypes.EMCDataType;
import emc.datatypes.creditors.transactions.Balance;
import emc.datatypes.creditors.transactions.Credit;
import emc.datatypes.creditors.transactions.CreditAmountSettled;
import emc.datatypes.creditors.transactions.Debit;
import emc.datatypes.creditors.transactions.DebitAmountSettled;
import emc.datatypes.creditors.transactions.Description;
import emc.datatypes.creditors.transactions.LastSettledDate;
import emc.datatypes.creditors.transactions.PaymentDueDate;
import emc.datatypes.creditors.transactions.ReferenceNumber;
import emc.datatypes.creditors.transactions.ReferenceType;
import emc.datatypes.creditors.transactions.SettlementDiscDate;
import emc.datatypes.creditors.transactions.SettlementDiscPercentage;
import emc.datatypes.creditors.transactions.SupplierId;
import emc.datatypes.creditors.transactions.SupplierOrderNumber;
import emc.datatypes.creditors.transactions.TransactionDate;
import emc.datatypes.creditors.transactions.TransactionSource;
import emc.datatypes.creditors.transactions.VatAmount;
import emc.framework.EMCEntityClass;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/** 
 *
 * @author Owner
 */
@Entity
@Table(name = "CreditorsTransactions", uniqueConstraints = {@UniqueConstraint(columnNames = {"companyId", "referenceNumber", "referenceType"})})
public class CreditorsTransactions extends EMCEntityClass {

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

    /** Creates a new instance of CreditorsTransactions. */
    public CreditorsTransactions() {
    }

    @Override
    public HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("transactionDate", new TransactionDate());
        toBuild.put("description", new Description());
        toBuild.put("transactionSource", new TransactionSource());
        toBuild.put("debit", new Debit());
        toBuild.put("credit", new Credit());
        toBuild.put("supplierId", new SupplierId());
        toBuild.put("vatAmount", new VatAmount());
        toBuild.put("referenceNumber", new ReferenceNumber());
        toBuild.put("referenceType", new ReferenceType());
        toBuild.put("debitAmountSettled", new DebitAmountSettled());
        toBuild.put("creditAmountSettled", new CreditAmountSettled());
        toBuild.put("lastSettledDate", new LastSettledDate());
        toBuild.put("balance", new Balance());
        toBuild.put("supplierOrderNumber", new SupplierOrderNumber());
        toBuild.put("settlementDiscDate", new SettlementDiscDate());
        toBuild.put("settlementDiscPercentage", new SettlementDiscPercentage());
        toBuild.put("paymentDueDate", new PaymentDueDate());
        return toBuild;
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
