/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.debtors.ageanalysisbyagent;

import java.math.BigDecimal;

/**
 *
 * @author riaan
 */
public class DebtorsAgeAnalysisByAgentReportDS {

    //Rep
    private String rep;
    private String repName;
    //Customer Info
    private String customer;
    private String customerName;
    private String terms;
    private BigDecimal creditLimit;
    private String customerStatus;
    //Ageing
    private String bin1Name;
    private String bin2Name;
    private String bin3Name;
    private String bin4Name;
    private String bin5Name;
    private String bin6Name;
    private String bin7Name;
    private String currentBinName;
    private BigDecimal bin1Value = BigDecimal.ZERO;
    private BigDecimal bin2Value = BigDecimal.ZERO;
    private BigDecimal bin3Value = BigDecimal.ZERO;
    private BigDecimal bin4Value = BigDecimal.ZERO;
    private BigDecimal bin5Value = BigDecimal.ZERO;
    private BigDecimal bin6Value = BigDecimal.ZERO;
    private BigDecimal bin7Value = BigDecimal.ZERO;
    private BigDecimal currentBinValue = BigDecimal.ZERO;
    private BigDecimal totalOutstanding = BigDecimal.ZERO;
    private String atDate;
    private String ageingMode;
    //Outstanding Orders
    private String orderNo;
    private String customerOrderNo;
    private String requiredDate;
    private String status;
    private BigDecimal value = BigDecimal.ZERO;   //Excludes VAT, includes discount.
    private String ordersFrom;
    private String ordersTo;
    private String shipToCustomer;
    private String reference;
    private boolean printSO;

    /** Creates a new instance of DebtorsAgeAnalysisByAgentReportDS. */
    public DebtorsAgeAnalysisByAgentReportDS() {
    }

    public String getBin1Name() {
        return bin1Name;
    }

    public void setBin1Name(String bin1Name) {
        this.bin1Name = bin1Name;
    }

    public BigDecimal getBin1Value() {
        return bin1Value;
    }

    public void setBin1Value(BigDecimal bin1Value) {
        this.bin1Value = bin1Value;
    }

    public String getBin2Name() {
        return bin2Name;
    }

    public void setBin2Name(String bin2Name) {
        this.bin2Name = bin2Name;
    }

    public BigDecimal getBin2Value() {
        return bin2Value;
    }

    public void setBin2Value(BigDecimal bin2Value) {
        this.bin2Value = bin2Value;
    }

    public String getBin3Name() {
        return bin3Name;
    }

    public void setBin3Name(String bin3Name) {
        this.bin3Name = bin3Name;
    }

    public BigDecimal getBin3Value() {
        return bin3Value;
    }

    public void setBin3Value(BigDecimal bin3Value) {
        this.bin3Value = bin3Value;
    }

    public String getBin4Name() {
        return bin4Name;
    }

    public void setBin4Name(String bin4Name) {
        this.bin4Name = bin4Name;
    }

    public BigDecimal getBin4Value() {
        return bin4Value;
    }

    public void setBin4Value(BigDecimal bin4Value) {
        this.bin4Value = bin4Value;
    }

    public String getBin5Name() {
        return bin5Name;
    }

    public void setBin5Name(String bin5Name) {
        this.bin5Name = bin5Name;
    }

    public BigDecimal getBin5Value() {
        return bin5Value;
    }

    public void setBin5Value(BigDecimal bin5Value) {
        this.bin5Value = bin5Value;
    }

    public String getBin6Name() {
        return bin6Name;
    }

    public void setBin6Name(String bin6Name) {
        this.bin6Name = bin6Name;
    }

    public BigDecimal getBin6Value() {
        return bin6Value;
    }

    public void setBin6Value(BigDecimal bin6Value) {
        this.bin6Value = bin6Value;
    }

    public String getBin7Name() {
        return bin7Name;
    }

    public void setBin7Name(String bin7Name) {
        this.bin7Name = bin7Name;
    }

    public BigDecimal getBin7Value() {
        return bin7Value;
    }

    public void setBin7Value(BigDecimal bin7Value) {
        this.bin7Value = bin7Value;
    }

    public String getCurrentBinName() {
        return currentBinName;
    }

    public void setCurrentBinName(String currentBinName) {
        this.currentBinName = currentBinName;
    }

    public BigDecimal getCurrentBinValue() {
        return currentBinValue;
    }

    public void setCurrentBinValue(BigDecimal currentBinValue) {
        this.currentBinValue = currentBinValue;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerOrderNo() {
        return customerOrderNo;
    }

    public void setCustomerOrderNo(String customerOrderNo) {
        this.customerOrderNo = customerOrderNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getRep() {
        return rep;
    }

    public void setRep(String rep) {
        this.rep = rep;
    }

    public String getRepName() {
        return repName;
    }

    public void setRepName(String repName) {
        this.repName = repName;
    }

    public String getRequiredDate() {
        return requiredDate;
    }

    public void setRequiredDate(String requiredDate) {
        this.requiredDate = requiredDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    public String getCustomerStatus() {
        return customerStatus;
    }

    public void setCustomerStatus(String customerStatus) {
        this.customerStatus = customerStatus;
    }

    public BigDecimal getTotalOutstanding() {
        return totalOutstanding;
    }

    public void setTotalOutstanding(BigDecimal totalOutstanding) {
        this.totalOutstanding = totalOutstanding;
    }

    public String getAtDate() {
        return atDate;
    }

    public void setAtDate(String atDate) {
        this.atDate = atDate;
    }

    public String getAgeingMode() {
        return ageingMode;
    }

    public void setAgeingMode(String ageingMode) {
        this.ageingMode = ageingMode;
    }

    public String getOrdersFrom() {
        return ordersFrom;
    }

    public void setOrdersFrom(String ordersFrom) {
        this.ordersFrom = ordersFrom;
    }

    public String getOrdersTo() {
        return ordersTo;
    }

    public void setOrdersTo(String ordersTo) {
        this.ordersTo = ordersTo;
    }

    public String getShipToCustomer() {
        return shipToCustomer;
    }

    public void setShipToCustomer(String shipToCustomer) {
        this.shipToCustomer = shipToCustomer;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public boolean isPrintSO() {
        return printSO;
    }

    public void setPrintSO(boolean printSO) {
        this.printSO = printSO;
    }
}
