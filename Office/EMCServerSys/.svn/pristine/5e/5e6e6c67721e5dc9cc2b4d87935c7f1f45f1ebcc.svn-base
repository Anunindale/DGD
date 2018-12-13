/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.debtors.customerstatement;

import emc.reports.debtors.customeragingdetailed.CustomerStatementDSInterface;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author wikus
 */
public class DebtorsCustomerStatementReportDS implements CustomerStatementDSInterface {

    private String customrtId;
    private String customerName;
    private String transactionDate;
    private Date actualTransactionDate;
    private String transactionReference;
    private String transactionType;
    private BigDecimal amount = new BigDecimal(0);
    private BigDecimal balance = new BigDecimal(0);
    private BigDecimal calculatedBalance = new BigDecimal(0);
    private String agingGroup;
    private String bin1Name;
    private String bin2Name;
    private String bin3Name;
    private String bin4Name;
    private String bin5Name;
    private String bin6Name;
    private String bin7Name;
    private String currentBinName;
    private BigDecimal bin1Value = new BigDecimal(0);
    private BigDecimal bin2Value = new BigDecimal(0);
    private BigDecimal bin3Value = new BigDecimal(0);
    private BigDecimal bin4Value = new BigDecimal(0);
    private BigDecimal bin5Value = new BigDecimal(0);
    private BigDecimal bin6Value = new BigDecimal(0);
    private BigDecimal bin7Value = new BigDecimal(0);
    private BigDecimal currentBinValue = new BigDecimal(0);
    private String pageFooterCaption;
    private String companyPhysicalAddress1;
    private String companyPhysicalAddress2;
    private String companyPhysicalAddress3;
    private String companyPhysicalAddress4;
    private String companyPhysicalAddress5;
    private String companyPhysicalAddressCode;
    private String companyPostalAddress1;
    private String companyPostalAddress2;
    private String companyPostalAddress3;
    private String companyPostalAddress4;
    private String companyPostalAddress5;
    private String companyPostalAddressCode;
    private String companyPhone;
    private String companyFax;
    private String companyRegistration;
    private String companyVATNumber;
    private String customerAddress1;
    private String customerAddress2;
    private String customerAddress3;
    private String customerAddress4;
    private String customerAddress5;
    private String customerAddressCode;
    private String statementDate;
    private String atDate;
    private String agingMode;
    private String customerOrderNumber;
    private String dueDate;
    private BigDecimal debit = new BigDecimal(0);
    private BigDecimal credit = new BigDecimal(0);
    private String bankingInfo;

    //N & L Statement Mod - These fields are used to get and show payments.
    private long debitTransRecordID;

    /** Creates a new instance of DebtorsCustomerStatementReportDS. */
    public DebtorsCustomerStatementReportDS() {
    }

    public String getCompanyFax() {
        return companyFax;
    }

    public void setCompanyFax(String companyFax) {
        this.companyFax = companyFax;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }

    public String getCompanyPhysicalAddress1() {
        return companyPhysicalAddress1;
    }

    public void setCompanyPhysicalAddress1(String companyPhysicalAddress1) {
        this.companyPhysicalAddress1 = companyPhysicalAddress1;
    }

    public String getCompanyPhysicalAddress2() {
        return companyPhysicalAddress2;
    }

    public void setCompanyPhysicalAddress2(String companyPhysicalAddress2) {
        this.companyPhysicalAddress2 = companyPhysicalAddress2;
    }

    public String getCompanyPhysicalAddress3() {
        return companyPhysicalAddress3;
    }

    public void setCompanyPhysicalAddress3(String companyPhysicalAddress3) {
        this.companyPhysicalAddress3 = companyPhysicalAddress3;
    }

    public String getCompanyPhysicalAddress4() {
        return companyPhysicalAddress4;
    }

    public void setCompanyPhysicalAddress4(String companyPhysicalAddress4) {
        this.companyPhysicalAddress4 = companyPhysicalAddress4;
    }

    public String getCompanyPhysicalAddress5() {
        return companyPhysicalAddress5;
    }

    public void setCompanyPhysicalAddress5(String companyPhysicalAddress5) {
        this.companyPhysicalAddress5 = companyPhysicalAddress5;
    }

    public String getCompanyPhysicalAddressCode() {
        return companyPhysicalAddressCode;
    }

    public void setCompanyPhysicalAddressCode(String companyPhysicalAddressCode) {
        this.companyPhysicalAddressCode = companyPhysicalAddressCode;
    }

    public String getCompanyPostalAddress1() {
        return companyPostalAddress1;
    }

    public void setCompanyPostalAddress1(String companyPostalAddress1) {
        this.companyPostalAddress1 = companyPostalAddress1;
    }

    public String getCompanyPostalAddress2() {
        return companyPostalAddress2;
    }

    public void setCompanyPostalAddress2(String companyPostalAddress2) {
        this.companyPostalAddress2 = companyPostalAddress2;
    }

    public String getCompanyPostalAddress3() {
        return companyPostalAddress3;
    }

    public void setCompanyPostalAddress3(String companyPostalAddress3) {
        this.companyPostalAddress3 = companyPostalAddress3;
    }

    public String getCompanyPostalAddress4() {
        return companyPostalAddress4;
    }

    public void setCompanyPostalAddress4(String companyPostalAddress4) {
        this.companyPostalAddress4 = companyPostalAddress4;
    }

    public String getCompanyPostalAddress5() {
        return companyPostalAddress5;
    }

    public void setCompanyPostalAddress5(String companyPostalAddress5) {
        this.companyPostalAddress5 = companyPostalAddress5;
    }

    public String getCompanyPostalAddressCode() {
        return companyPostalAddressCode;
    }

    public void setCompanyPostalAddressCode(String companyPostalAddressCode) {
        this.companyPostalAddressCode = companyPostalAddressCode;
    }

    public String getCompanyRegistration() {
        return companyRegistration;
    }

    public void setCompanyRegistration(String companyRegistration) {
        this.companyRegistration = companyRegistration;
    }

    public String getCompanyVATNumber() {
        return companyVATNumber;
    }

    public void setCompanyVATNumber(String companyVATNumber) {
        this.companyVATNumber = companyVATNumber;
    }

    public String getCustomerAddress1() {
        return customerAddress1;
    }

    public void setCustomerAddress1(String customerAddress1) {
        this.customerAddress1 = customerAddress1;
    }

    public String getCustomerAddress2() {
        return customerAddress2;
    }

    public void setCustomerAddress2(String customerAddress2) {
        this.customerAddress2 = customerAddress2;
    }

    public String getCustomerAddress3() {
        return customerAddress3;
    }

    public void setCustomerAddress3(String customerAddress3) {
        this.customerAddress3 = customerAddress3;
    }

    public String getCustomerAddress4() {
        return customerAddress4;
    }

    public void setCustomerAddress4(String customerAddress4) {
        this.customerAddress4 = customerAddress4;
    }

    public String getCustomerAddress5() {
        return customerAddress5;
    }

    public void setCustomerAddress5(String customerAddress5) {
        this.customerAddress5 = customerAddress5;
    }

    public String getCustomerAddressCode() {
        return customerAddressCode;
    }

    public void setCustomerAddressCode(String customerAddressCode) {
        this.customerAddressCode = customerAddressCode;
    }

    public String getStatementDate() {
        return statementDate;
    }

    public void setStatementDate(String statementDate) {
        this.statementDate = statementDate;
    }

    public String getCustomrtId() {
        return customrtId;
    }

    public void setCustomrtId(String customrtId) {
        this.customrtId = customrtId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionReference() {
        return transactionReference;
    }

    public void setTransactionReference(String transactionReference) {
        this.transactionReference = transactionReference;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getCalculatedBalance() {
        return calculatedBalance;
    }

    public void setCalculatedBalance(BigDecimal calculatedBalance) {
        this.calculatedBalance = calculatedBalance;
    }

    public String getAgingGroup() {
        return agingGroup;
    }

    public void setAgingGroup(String agingGroup) {
        this.agingGroup = agingGroup;
    }

    public String getBin1Name() {
        return bin1Name;
    }

    public void setBin1Name(String bin1Name) {
        this.bin1Name = bin1Name;
    }

    public String getBin2Name() {
        return bin2Name;
    }

    public void setBin2Name(String bin2Name) {
        this.bin2Name = bin2Name;
    }

    public String getBin3Name() {
        return bin3Name;
    }

    public void setBin3Name(String bin3Name) {
        this.bin3Name = bin3Name;
    }

    public String getBin4Name() {
        return bin4Name;
    }

    public void setBin4Name(String bin4Name) {
        this.bin4Name = bin4Name;
    }

    public String getBin5Name() {
        return bin5Name;
    }

    public void setBin5Name(String bin5Name) {
        this.bin5Name = bin5Name;
    }

    public String getBin6Name() {
        return bin6Name;
    }

    public void setBin6Name(String bin6Name) {
        this.bin6Name = bin6Name;
    }

    public String getBin7Name() {
        return bin7Name;
    }

    public void setBin7Name(String bin7Name) {
        this.bin7Name = bin7Name;
    }

    public String getCurrentBinName() {
        return currentBinName;
    }

    public void setCurrentBinName(String currentBinName) {
        this.currentBinName = currentBinName;
    }

    public BigDecimal getBin1Value() {
        return bin1Value;
    }

    public void setBin1Value(BigDecimal bin1Value) {
        this.bin1Value = bin1Value;
    }

    public BigDecimal getBin2Value() {
        return bin2Value;
    }

    public void setBin2Value(BigDecimal bin2Value) {
        this.bin2Value = bin2Value;
    }

    public BigDecimal getBin3Value() {
        return bin3Value;
    }

    public void setBin3Value(BigDecimal bin3Value) {
        this.bin3Value = bin3Value;
    }

    public BigDecimal getBin4Value() {
        return bin4Value;
    }

    public void setBin4Value(BigDecimal bin4Value) {
        this.bin4Value = bin4Value;
    }

    public BigDecimal getBin5Value() {
        return bin5Value;
    }

    public void setBin5Value(BigDecimal bin5Value) {
        this.bin5Value = bin5Value;
    }

    public BigDecimal getBin6Value() {
        return bin6Value;
    }

    public void setBin6Value(BigDecimal bin6Value) {
        this.bin6Value = bin6Value;
    }

    public BigDecimal getBin7Value() {
        return bin7Value;
    }

    public void setBin7Value(BigDecimal bin7Value) {
        this.bin7Value = bin7Value;
    }

    public BigDecimal getCurrentBinValue() {
        return currentBinValue;
    }

    public void setCurrentBinValue(BigDecimal currentBinValue) {
        this.currentBinValue = currentBinValue;
    }

    public String getPageFooterCaption() {
        return pageFooterCaption;
    }

    public void setPageFooterCaption(String pageFooterCaption) {
        this.pageFooterCaption = pageFooterCaption;
    }

    public String getAtDate() {
        return atDate;
    }

    public void setAtDate(String atDate) {
        this.atDate = atDate;
    }

    public String getCustomerOrderNumber() {
        return customerOrderNumber;
    }

    public void setCustomerOrderNumber(String customerOrderNumber) {
        this.customerOrderNumber = customerOrderNumber;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
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

    public String getBankingInfo() {
        return bankingInfo;
    }

    public void setBankingInfo(String bankingInfo) {
        this.bankingInfo = bankingInfo;
    }

    public long getDebitTransRecordID() {
        return debitTransRecordID;
    }

    public void setDebitTransRecordID(long debitTransRecordID) {
        this.debitTransRecordID = debitTransRecordID;
    }

    public Date getActualTransactionDate() {
        return actualTransactionDate;
    }

    public void setActualTransactionDate(Date actualTransactionDate) {
        this.actualTransactionDate = actualTransactionDate;
    }

    public String getAgingMode() {
        return agingMode;
    }

    public void setAgingMode(String agingMode) {
        this.agingMode = agingMode;
    }
}
