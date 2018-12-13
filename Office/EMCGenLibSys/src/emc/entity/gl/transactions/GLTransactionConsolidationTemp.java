/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.gl.transactions;

import emc.framework.EMCEntityClass;
import java.math.BigDecimal;
import javax.persistence.Entity;

/**
 *
 * @author riaan
 */
@Entity
public class GLTransactionConsolidationTemp extends EMCEntityClass {

    private long containerTransactionRef;
    private BigDecimal debit;
    private BigDecimal credit;
    //Record IDs of updated summary records
    private long periodSummaryRef;
    private long daySummaryRef;
    private long transactionSummaryRef;

    /**
     * Creates a new instance of GLTransactionConsolidationTemp.
     */
    public GLTransactionConsolidationTemp() {
        
    }

    public long getContainerTransactionRef() {
        return containerTransactionRef;
    }

    public void setContainerTransactionRef(long containerTransactionRef) {
        this.containerTransactionRef = containerTransactionRef;
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

    public long getPeriodSummaryRef() {
        return periodSummaryRef;
    }

    public void setPeriodSummaryRef(long periodSummaryRef) {
        this.periodSummaryRef = periodSummaryRef;
    }

    public long getDaySummaryRef() {
        return daySummaryRef;
    }

    public void setDaySummaryRef(long daySummaryRef) {
        this.daySummaryRef = daySummaryRef;
    }

    public long getTransactionSummaryRef() {
        return transactionSummaryRef;
    }

    public void setTransactionSummaryRef(long transactionSummaryRef) {
        this.transactionSummaryRef = transactionSummaryRef;
    }
}
