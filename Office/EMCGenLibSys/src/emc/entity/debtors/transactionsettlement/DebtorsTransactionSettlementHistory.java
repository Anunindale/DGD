/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.debtors.transactionsettlement;

import emc.datatypes.EMCDataType;
import emc.datatypes.debtors.transactionsettlementhistory.AllocatedDate;
import emc.datatypes.debtors.transactionsettlementhistory.CreditSettled;
import emc.datatypes.debtors.transactionsettlementhistory.DebitSettled;
import emc.framework.EMCEntityClass;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @description : This class is used to maintain a history of debtors transaction
 *                settlements.
 *
 * @date        : 28 Jun 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
@Entity
@Table(name = "DebtorsTransactionSettlementHistory")
public class DebtorsTransactionSettlementHistory extends EMCEntityClass {

    private long debitTransRef;
    private long creditTransRef;
    private BigDecimal debitSettled;
    private BigDecimal creditSettled;
    //Transaction date, used for aging.
    @Temporal(TemporalType.DATE)
    private Date debitTransactionDate;
    @Temporal(TemporalType.DATE)
    private Date creditTransactionDate;
    //Created dates of the transaction which were settled.  Used for aging
    @Temporal(TemporalType.DATE)
    private Date debitTransactionCreatedDate;
    @Temporal(TemporalType.DATE)
    private Date creditTransactionCreatedDate;
    //Used for aging
    private String customerId;
    //Used to deallocate discounts when a payment is deallocated
    private long sessionId;
    //System allocations may not be deallocated.
    private boolean systemAllocated;

    /** Creates a new instance of DebtorsTransactionSettlementHistory */
    public DebtorsTransactionSettlementHistory() {
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("creditSettled", new CreditSettled());
        toBuild.put("debitSettled", new DebitSettled());
        toBuild.put("createdDate", new AllocatedDate());

        return toBuild;
    }

    public long getDebitTransRef() {
        return debitTransRef;
    }

    public void setDebitTransRef(long debitTransRef) {
        this.debitTransRef = debitTransRef;
    }

    public long getCreditTransRef() {
        return creditTransRef;
    }

    public void setCreditTransRef(long creditTransRef) {
        this.creditTransRef = creditTransRef;
    }

    public BigDecimal getDebitSettled() {
        return debitSettled;
    }

    public void setDebitSettled(BigDecimal debitSettled) {
        this.debitSettled = debitSettled;
    }

    public BigDecimal getCreditSettled() {
        return creditSettled;
    }

    public void setCreditSettled(BigDecimal creditSettled) {
        this.creditSettled = creditSettled;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Date getDebitTransactionDate() {
        return debitTransactionDate;
    }

    public void setDebitTransactionDate(Date debitTransactionDate) {
        this.debitTransactionDate = debitTransactionDate;
    }

    public Date getCreditTransactionDate() {
        return creditTransactionDate;
    }

    public void setCreditTransactionDate(Date creditTransactionDate) {
        this.creditTransactionDate = creditTransactionDate;
    }

    public Date getDebitTransactionCreatedDate() {
        return debitTransactionCreatedDate;
    }

    public void setDebitTransactionCreatedDate(Date debitTransactionCreatedDate) {
        this.debitTransactionCreatedDate = debitTransactionCreatedDate;
    }

    public Date getCreditTransactionCreatedDate() {
        return creditTransactionCreatedDate;
    }

    public void setCreditTransactionCreatedDate(Date creditTransactionCreatedDate) {
        this.creditTransactionCreatedDate = creditTransactionCreatedDate;
    }

    public long getSessionId() {
        return sessionId;
    }

    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }

    public boolean isSystemAllocated() {
        return systemAllocated;
    }

    public void setSystemAllocated(boolean systemAllocated) {
        this.systemAllocated = systemAllocated;
    }
}
