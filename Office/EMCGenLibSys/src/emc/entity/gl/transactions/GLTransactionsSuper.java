/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.gl.transactions;

import emc.framework.EMCEntityClass;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author claudette
 */
@Entity
public class GLTransactionsSuper extends EMCEntityClass {

    private String accountNumber;
    private String transactionSource;
    private String sourceReference;
    private String transactionNumber;
    @Temporal(TemporalType.DATE)
    private Date transactionDate;
    private String text;
    private String foreignCurrency;
    private String vatCode;
    private String analysisCode1;
    private String analysisCode2;
    private String analysisCode3;
    private String analysisCode4;
    private String analysisCode5;
    private String analysisCode6;
    private BigDecimal debit = BigDecimal.ZERO;
    private BigDecimal credit = BigDecimal.ZERO;
    private BigDecimal foreignCurrencyDebit = BigDecimal.ZERO;
    private BigDecimal foreignCurrencyCredit = BigDecimal.ZERO;
    private int groupWeek;
    private String groupPeriod;
    //Processed flag is set by default - all transaction are now processed and
    //consolidated as soon as they are created.
    private boolean processed = true;
    //Populated from journals.  Only applies to detailed transactions and non-grouped
    //summary transactions.
    private String externalReference;

    public GLTransactionsSuper() {
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAnalysisCode1() {
        return analysisCode1;
    }

    public void setAnalysisCode1(String analysisCode1) {
        this.analysisCode1 = analysisCode1;
    }

    public String getAnalysisCode2() {
        return analysisCode2;
    }

    public void setAnalysisCode2(String analysisCode2) {
        this.analysisCode2 = analysisCode2;
    }

    public String getAnalysisCode3() {
        return analysisCode3;
    }

    public void setAnalysisCode3(String analysisCode3) {
        this.analysisCode3 = analysisCode3;
    }

    public String getAnalysisCode4() {
        return analysisCode4;
    }

    public void setAnalysisCode4(String analysisCode4) {
        this.analysisCode4 = analysisCode4;
    }

    public String getForeignCurrency() {
        return foreignCurrency;
    }

    public void setForeignCurrency(String foreignCurrency) {
        this.foreignCurrency = foreignCurrency;
    }

    public String getSourceReference() {
        return sourceReference;
    }

    public void setSourceReference(String sourceReference) {
        this.sourceReference = sourceReference;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public String getTransactionSource() {
        return transactionSource;
    }

    public void setTransactionSource(String transactionSource) {
        this.transactionSource = transactionSource;
    }

    public String getVatCode() {
        return vatCode;
    }

    public void setVatCode(String vatCode) {
        this.vatCode = vatCode;
    }

    public String getAnalysisCode5() {
        return analysisCode5;
    }

    public void setAnalysisCode5(String analysisCode5) {
        this.analysisCode5 = analysisCode5;
    }

    public String getAnalysisCode6() {
        return analysisCode6;
    }

    public void setAnalysisCode6(String analysisCode6) {
        this.analysisCode6 = analysisCode6;
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

    public BigDecimal getForeignCurrencyDebit() {
        return foreignCurrencyDebit;
    }

    public void setForeignCurrencyDebit(BigDecimal foreignCurrencyDebit) {
        this.foreignCurrencyDebit = foreignCurrencyDebit;
    }

    public BigDecimal getForeignCurrencyCredit() {
        return foreignCurrencyCredit;
    }

    public void setForeignCurrencyCredit(BigDecimal foreignCurrencyCredit) {
        this.foreignCurrencyCredit = foreignCurrencyCredit;
    }

    public boolean getProcessed() {
        return isProcessed();
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    public int getGroupWeek() {
        return groupWeek;
    }

    public void setGroupWeek(int groupWeek) {
        this.groupWeek = groupWeek;
    }

    public String getGroupPeriod() {
        return groupPeriod;
    }

    public void setGroupPeriod(String groupPeriod) {
        this.groupPeriod = groupPeriod;
    }

    public boolean isProcessed() {
        return processed;
    }

    public String getExternalReference() {
        return externalReference;
    }

    public void setExternalReference(String externalReference) {
        this.externalReference = externalReference;
    }
}
