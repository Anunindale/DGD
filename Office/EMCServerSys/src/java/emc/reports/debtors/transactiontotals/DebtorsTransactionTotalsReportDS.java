/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.debtors.transactiontotals;

import java.math.BigDecimal;

/**
 *
 * @author wikus
 */
public class DebtorsTransactionTotalsReportDS {

    private String customrtId;
    private String customerName;
    private String transactionType;
    private String transDate;
    private String reference;
    private BigDecimal amount = new BigDecimal(0);

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomrtId() {
        return customrtId;
    }

    public void setCustomrtId(String customrtId) {
        this.customrtId = customrtId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }
}
