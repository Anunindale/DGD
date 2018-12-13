package emc.entity.creditors;

import emc.datatypes.EMCDataType;
import emc.datatypes.creditors.transactions.CreditSettled;
import emc.datatypes.creditors.transactions.CreditTransRef;
import emc.datatypes.creditors.transactions.CreditTransactionCreatedDate;
import emc.datatypes.creditors.transactions.CreditTransactionDate;
import emc.datatypes.creditors.transactions.CustomerID;
import emc.datatypes.creditors.transactions.DebitSettled;
import emc.datatypes.creditors.transactions.DebitTransRef;
import emc.datatypes.creditors.transactions.DebitTransactionCreatedDate;
import emc.datatypes.creditors.transactions.DebitTransactionDate;
import emc.datatypes.creditors.transactions.SessionID;
import emc.datatypes.creditors.transactions.SystemAllocated;
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
@Table(name = "CreditorsTransactionSettlementHistory", uniqueConstraints = {@UniqueConstraint(columnNames = {"companyId"})})
public class CreditorsTransactionSettlementHistory extends EMCEntityClass {

    private long debitTransRef;
    private long creditTransRef;
    private BigDecimal debitSettled;
    private BigDecimal creditSettled;
    @Temporal(TemporalType.DATE)
    private Date debitTransactionDate;
    @Temporal(TemporalType.DATE)
    private Date creditTransactionDate;
    @Temporal(TemporalType.DATE)
    private Date debitTransactionCreatedDate;
    @Temporal(TemporalType.DATE)
    private Date creditTransactionCreatedDate;
    private String customerID;
    private long sessionID;
    private boolean systemAllocated;

    /** Creates a new instance of CreditorsTransactionSettlementHistory. */
    public CreditorsTransactionSettlementHistory() {
    }

    @Override
    public HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("debitTransRef", new DebitTransRef());
        toBuild.put("creditTransRef", new CreditTransRef());
        toBuild.put("debitSettled", new DebitSettled());
        toBuild.put("creditSettled", new CreditSettled());
        toBuild.put("debitTransactionDate", new DebitTransactionDate());
        toBuild.put("creditTransactionDate", new CreditTransactionDate());
        toBuild.put("debitTransactionCreatedDate", new DebitTransactionCreatedDate());
        toBuild.put("creditTransactionCreatedDate", new CreditTransactionCreatedDate());
        toBuild.put("customerID", new CustomerID());
        toBuild.put("sessionID", new SessionID());
        toBuild.put("systemAllocated", new SystemAllocated());
        return toBuild;
    }

    public long getDebitTransRef() {
        return this.debitTransRef;
    }

    public void setDebitTransRef(long debitTransRef) {
        this.debitTransRef = debitTransRef;
    }

    public long getCreditTransRef() {
        return this.creditTransRef;
    }

    public void setCreditTransRef(long creditTransRef) {
        this.creditTransRef = creditTransRef;
    }

    public BigDecimal getDebitSettled() {
        return this.debitSettled;
    }

    public void setDebitSettled(BigDecimal debitSettled) {
        this.debitSettled = debitSettled;
    }

    public BigDecimal getCreditSettled() {
        return this.creditSettled;
    }

    public void setCreditSettled(BigDecimal creditSettled) {
        this.creditSettled = creditSettled;
    }

    public Date getDebitTransactionDate() {
        return this.debitTransactionDate;
    }

    public void setDebitTransactionDate(Date debitTransactionDate) {
        this.debitTransactionDate = debitTransactionDate;
    }

    public Date getCreditTransactionDate() {
        return this.creditTransactionDate;
    }

    public void setCreditTransactionDate(Date creditTransactionDate) {
        this.creditTransactionDate = creditTransactionDate;
    }

    public Date getDebitTransactionCreatedDate() {
        return this.debitTransactionCreatedDate;
    }

    public void setDebitTransactionCreatedDate(Date debitTransactionCreatedDate) {
        this.debitTransactionCreatedDate = debitTransactionCreatedDate;
    }

    public Date getCreditTransactionCreatedDate() {
        return this.creditTransactionCreatedDate;
    }

    public void setCreditTransactionCreatedDate(Date creditTransactionCreatedDate) {
        this.creditTransactionCreatedDate = creditTransactionCreatedDate;
    }

    public String getCustomerID() {
        return this.customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public long getSessionID() {
        return this.sessionID;
    }

    public void setSessionID(long sessionID) {
        this.sessionID = sessionID;
    }

    public boolean getSystemAllocated() {
        return this.systemAllocated;
    }

    public void setSystemAllocated(boolean systemAllocated) {
        this.systemAllocated = systemAllocated;
    }
}
