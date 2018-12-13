package emc.entity.gl.transactions;

import emc.datatypes.EMCDataType;
import emc.datatypes.gl.analysiscode1.foreignkeys.AnalysisCode1FKNM;
import emc.datatypes.gl.analysiscode2.foreignkeys.AnalysisCode2FKNM;
import emc.datatypes.gl.analysiscode3.foreignkeys.AnalysisCode3FKNM;
import emc.datatypes.gl.analysiscode4.foreignkeys.AnalysisCode4FKNM;
import emc.datatypes.gl.analysiscode5.foreignkeys.AnalysisCode5FKNM;
import emc.datatypes.gl.analysiscode6.foreignkeys.AnalysisCode6FKNM;
import emc.datatypes.gl.transactiondaysummary.AccountNumber;
import emc.datatypes.gl.transactiondaysummary.Credit;
import emc.datatypes.gl.transactiondaysummary.Debit;
import emc.datatypes.gl.transactiondaysummary.FinancialDate;
import emc.framework.EMCEntityClass;
import java.lang.Override;
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
 * @author claudette
 */
@Entity
@Table(name = "GLTransactionDaySummary", uniqueConstraints = {@UniqueConstraint(columnNames = {"uniqueKey", "companyId"})})
public class GLTransactionDaySummary extends EMCEntityClass {

    private String accountNumber;
    @Temporal(TemporalType.DATE)
    private Date financialDate;
    private String analysisCode1;
    private String analysisCode2;
    private String analysisCode3;
    private String analysisCode4;
    private String analysisCode5;
    private String analysisCode6;
    private BigDecimal debit = BigDecimal.ZERO;
    private BigDecimal credit = BigDecimal.ZERO;
    private String uniqueKey;

    /** Creates a new instance of GLTransactionPeriodSummary. */
    public GLTransactionDaySummary() {
    }

    @Override
    public HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("accountNumber", new AccountNumber());
        toBuild.put("financialDate", new FinancialDate());
        toBuild.put("analysisCode1", new AnalysisCode1FKNM());
        toBuild.put("analysisCode2", new AnalysisCode2FKNM());
        toBuild.put("analysisCode3", new AnalysisCode3FKNM());
        toBuild.put("analysisCode4", new AnalysisCode4FKNM());
        toBuild.put("analysisCode5", new AnalysisCode5FKNM());
        toBuild.put("analysisCode6", new AnalysisCode6FKNM());
        toBuild.put("debit", new Debit());
        toBuild.put("credit", new Credit());

        return toBuild;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Date getFinancialDate() {
        return this.financialDate;
    }

    public void setFinancialDate(Date financialDate) {
        this.financialDate = financialDate;
    }

    public String getAnalysisCode1() {
        return this.analysisCode1;
    }

    public void setAnalysisCode1(String analysisCode1) {
        this.analysisCode1 = analysisCode1;
    }

    public String getAnalysisCode2() {
        return this.analysisCode2;
    }

    public void setAnalysisCode2(String analysisCode2) {
        this.analysisCode2 = analysisCode2;
    }

    public String getAnalysisCode3() {
        return this.analysisCode3;
    }

    public void setAnalysisCode3(String analysisCode3) {
        this.analysisCode3 = analysisCode3;
    }

    public String getAnalysisCode4() {
        return this.analysisCode4;
    }

    public void setAnalysisCode4(String analysisCode4) {
        this.analysisCode4 = analysisCode4;
    }

    public String getAnalysisCode5() {
        return this.analysisCode5;
    }

    public void setAnalysisCode5(String analysisCode5) {
        this.analysisCode5 = analysisCode5;
    }

    public String getAnalysisCode6() {
        return this.analysisCode6;
    }

    public void setAnalysisCode6(String analysisCode6) {
        this.analysisCode6 = analysisCode6;
    }

    public BigDecimal getDebit() {
        return this.debit;
    }

    public void setDebit(BigDecimal debit) {
        this.debit = debit;
    }

    public BigDecimal getCredit() {
        return this.credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public String getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }
}