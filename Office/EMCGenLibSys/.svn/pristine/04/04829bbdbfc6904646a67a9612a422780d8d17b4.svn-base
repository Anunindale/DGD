package emc.entity.debtors;

import emc.datatypes.debtors.customertransactionssummary.TransactionDate;
import emc.datatypes.debtors.transactions.Credit;
import emc.datatypes.debtors.transactions.CreditAmountSettled;
import emc.datatypes.debtors.transactions.CustomerID;
import emc.datatypes.debtors.transactions.Debit;
import emc.datatypes.debtors.transactions.DebitAmountSettled;
import emc.datatypes.debtors.transactions.Description;
import emc.datatypes.debtors.transactions.LastSettledDate;
import emc.datatypes.debtors.transactions.PaymentDueDate;
import emc.datatypes.debtors.transactions.ReferenceNumber;
import emc.datatypes.debtors.transactions.ReferenceType;
import emc.datatypes.debtors.transactions.SettlementDiscDate;
import emc.datatypes.debtors.transactionsettlement.TransactionSource;
import emc.enums.emcquery.EMCQueryConditions;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
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
 * @Author wikus
 */
@Entity
@Table(name = "DebtorsTransactions", uniqueConstraints = {@UniqueConstraint(columnNames = {"referenceNumber", "referenceType", "companyId"})})
public class DebtorsTransactions extends EMCEntityClass {

    @Temporal(TemporalType.DATE)
    private Date transactionDate;
    private String transactionSource;
    private String description;
    private BigDecimal debit = BigDecimal.ZERO;
    private BigDecimal credit = BigDecimal.ZERO;
    private String referenceNumber;
    private String referenceType;
    private BigDecimal debitAmountSettled = BigDecimal.ZERO;
    private BigDecimal creditAmountSettled = BigDecimal.ZERO;
    private String customerId;
    private BigDecimal vatAmount = BigDecimal.ZERO;
    @Temporal(TemporalType.DATE)
    private Date lastSettledDate;
    //Not normalized.  Stored to make calculations easier.  This field is calculated.
    private BigDecimal balance;
    private String customerOrderNumber;
    //Not normalized.  Used on allocation to elimate the need to select invoices.
    @Temporal(TemporalType.DATE)
    private Date settlementDiscDate;
    private BigDecimal settlementDiscPercentage = BigDecimal.ZERO;
    //Not normalized.  Used on statement to eliminate the need to select invoices.
    @Temporal(TemporalType.DATE)
    private Date paymentDueDate;
    private String salesOrderNo;
    private String salesRep;

    /** Creates a new instance of DebtorsTransactions. */
    public DebtorsTransactions() {
    }

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();

        toBuild.put("transactionDate", new TransactionDate());
        toBuild.put("transactionSource", new TransactionSource());
        toBuild.put("customerId", new CustomerID());
        toBuild.put("description", new Description());
        toBuild.put("referenceNumber", new ReferenceNumber());
        toBuild.put("referenceType", new ReferenceType());
        toBuild.put("debitAmountSettled", new DebitAmountSettled());
        toBuild.put("creditAmountSettled", new CreditAmountSettled());
        toBuild.put("debit", new Debit());
        toBuild.put("credit", new Credit());
        toBuild.put("lastSettledDate", new LastSettledDate());
        toBuild.put("paymentDueDate", new PaymentDueDate());
        toBuild.put("settlementDiscDate", new SettlementDiscDate());

        return toBuild;
    }

    @Override
    public EMCQuery buildQuery() {
        EMCQuery query = super.buildQuery();

        query.addAnd("balance", 0, EMCQueryConditions.NOT);

        return query;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTransactionSource() {
        return transactionSource;
    }

    public void setTransactionSource(String transactionSource) {
        this.transactionSource = transactionSource;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(String referenceType) {
        this.referenceType = referenceType;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
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

    public BigDecimal getVatAmount() {
        return vatAmount;
    }

    public void setVatAmount(BigDecimal vatAmount) {
        this.vatAmount = vatAmount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Date getLastSettledDate() {
        return lastSettledDate;
    }

    public void setLastSettledDate(Date lastSettledDate) {
        this.lastSettledDate = lastSettledDate;
    }

    public String getCustomerOrderNumber() {
        return customerOrderNumber;
    }

    public void setCustomerOrderNumber(String customerOrderNumber) {
        this.customerOrderNumber = customerOrderNumber;
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

    public Date getPaymentDueDate() {
        return paymentDueDate;
    }

    public void setPaymentDueDate(Date paymentDueDate) {
        this.paymentDueDate = paymentDueDate;
    }

    public String getSalesOrderNo() {
        return salesOrderNo;
    }

    public void setSalesOrderNo(String salesOrderNo) {
        this.salesOrderNo = salesOrderNo;
    }

    public String getSalesRep() {
        return salesRep;
    }

    public void setSalesRep(String salesRep) {
        this.salesRep = salesRep;
    }
}
