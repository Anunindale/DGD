/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.debtors.transactionsettlement;

import emc.datatypes.EMCDataType;
import emc.datatypes.debtors.settlementdiscounthistory.DiscountOverridden;
import emc.framework.EMCEntityClass;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @description : This class maintains a history of the settlement discounts allocated
 *                during Debtors transaction settlements.
 *
 * @date        : 28 Jun 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
@Entity
@Table(name = "DebtorsSettlementDiscHistory")
public class DebtorsSettlementDiscHistory extends EMCEntityClass {

    private String invoiceNo;
    private BigDecimal discountAmount;
    //Record ID of discount transaction.
    private long discountTransactionRef;
    //Transaction date, used for aging.
    @Temporal(TemporalType.DATE)
    private Date transactionDate;
    //Created date of the transaction to which this discount is being applied.  Used for aging
    @Temporal(TemporalType.DATE)
    private Date transactionCreatedDate;
    //Used for aging
    private String customerId;
    //Indicates whether the amount discounted was changed by the user.
    private boolean discountOverridden;
    @Temporal(TemporalType.DATE)
    private Date discountDate;

    /** Creates a new instance of DebtorsSettlementDiscHistory */
    public DebtorsSettlementDiscHistory() {

    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("discountOverridden", new DiscountOverridden());

        return toBuild;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public long getDiscountTransactionRef() {
        return discountTransactionRef;
    }

    public void setDiscountTransactionRef(long discountTransactionRef) {
        this.discountTransactionRef = discountTransactionRef;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Date getTransactionCreatedDate() {
        return transactionCreatedDate;
    }

    public void setTransactionCreatedDate(Date transactionCreatedDate) {
        this.transactionCreatedDate = transactionCreatedDate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public boolean isDiscountOverridden() {
        return discountOverridden;
    }

    public void setDiscountOverridden(boolean discountOverridden) {
        this.discountOverridden = discountOverridden;
    }

    public Date getDiscountDate() {
        return discountDate;
    }

    public void setDiscountDate(Date discountDate) {
        this.discountDate = discountDate;
    }
}
