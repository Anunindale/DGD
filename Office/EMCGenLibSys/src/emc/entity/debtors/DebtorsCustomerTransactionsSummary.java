/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.debtors;

import emc.datatypes.EMCDataType;
import emc.datatypes.debtors.customertransactionssummary.AmountExclVAT;
import emc.datatypes.debtors.customertransactionssummary.LastSettledDate;
import emc.datatypes.debtors.customertransactionssummary.ShipToCustomer;
import emc.datatypes.debtors.customertransactionssummary.Source;
import emc.datatypes.debtors.customertransactionssummary.SourceRef;
import emc.datatypes.debtors.customertransactionssummary.TotalInclVAT;
import emc.datatypes.debtors.customertransactionssummary.TotalSettledAmount;
import emc.datatypes.debtors.customertransactionssummary.TransClosed;
import emc.datatypes.debtors.customertransactionssummary.TransactionDate;
import emc.datatypes.debtors.customertransactionssummary.TransactionType;
import emc.datatypes.debtors.customertransactionssummary.VAT;
import emc.datatypes.debtors.transactions.TransactionNo;
import emc.datatypes.sop.customers.foreignkeys.CustomerIdFK;
import emc.datatypes.systemwide.Description;
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
 * @author riaan
 */
@Entity
@Table(name = "DebtorsCustomerTransactionsSummary")
public class DebtorsCustomerTransactionsSummary extends EMCEntityClass {

    private String customerNo;
    private String shipToCustomer;
    private String transactionNo;
    private String transactionType;
    private String transactionDescription;
    @Temporal(TemporalType.DATE)
    private Date transactionDate;
    private String source;
    private String sourceRefId;
    private BigDecimal amountExclVAT;
    private BigDecimal vat;
    private BigDecimal totalInclVAT;
    private boolean transClosed;
    private String settlementId;
    @Temporal(TemporalType.DATE)
    private Date lastSettledDate;
    private BigDecimal totalSettledAmount;

    /** Creates a new instance of DebtorsCustomerTransactionsSummary */
    public DebtorsCustomerTransactionsSummary() {

    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("customerNo", new CustomerIdFK());
        toBuild.put("shipToCustomer", new ShipToCustomer());
        toBuild.put("transactionNo", new TransactionNo());
        toBuild.put("transactionType", new TransactionType());
        toBuild.put("transactionDescription", new Description());
        toBuild.put("transactionDate", new TransactionDate());
        toBuild.put("source", new Source());
        toBuild.put("sourceRefId", new SourceRef());
        toBuild.put("amountExclVAT", new AmountExclVAT());
        toBuild.put("vat", new VAT());
        toBuild.put("totalInclVAT", new TotalInclVAT());
        toBuild.put("transClosed", new TransClosed());
        toBuild.put("lastSettledDate", new LastSettledDate());
        toBuild.put("totalSettledAmount", new TotalSettledAmount());

        return toBuild;
    }

    public BigDecimal getAmountExclVAT() {
        return amountExclVAT;
    }

    public void setAmountExclVAT(BigDecimal amountExclVAT) {
        this.amountExclVAT = amountExclVAT;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public Date getLastSettledDate() {
        return lastSettledDate;
    }

    public void setLastSettledDate(Date lastSettledDate) {
        this.lastSettledDate = lastSettledDate;
    }

    public String getSettlementId() {
        return settlementId;
    }

    public void setSettlementId(String settlementId) {
        this.settlementId = settlementId;
    }

    public String isShipToCustomer() {
        return shipToCustomer;
    }

    public void setShipToCustomer(String shipToCustomer) {
        this.shipToCustomer = shipToCustomer;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourceRefId() {
        return sourceRefId;
    }

    public void setSourceRefId(String sourceRefId) {
        this.sourceRefId = sourceRefId;
    }

    public BigDecimal getTotalInclVAT() {
        return totalInclVAT;
    }

    public void setTotalInclVAT(BigDecimal totalInclVAT) {
        this.totalInclVAT = totalInclVAT;
    }

    public BigDecimal getTotalSettledAmount() {
        return totalSettledAmount;
    }

    public void setTotalSettledAmount(BigDecimal totalSettledAmount) {
        this.totalSettledAmount = totalSettledAmount;
    }

    public boolean isTransClosed() {
        return transClosed;
    }

    public void setTransClosed(boolean transClosed) {
        this.transClosed = transClosed;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionDescription() {
        return transactionDescription;
    }

    public void setTransactionDescription(String transactionDescription) {
        this.transactionDescription = transactionDescription;
    }

    public String getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getVat() {
        return vat;
    }

    public void setVat(BigDecimal vat) {
        this.vat = vat;
    }

}
