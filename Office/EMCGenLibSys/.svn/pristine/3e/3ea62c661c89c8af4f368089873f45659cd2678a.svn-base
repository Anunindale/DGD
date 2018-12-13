package emc.entity.gl.transactions;

import emc.datatypes.EMCDataType;
import emc.datatypes.gl.analysiscode1.foreignkeys.AnalysisCode1FKNM;
import emc.datatypes.gl.analysiscode2.foreignkeys.AnalysisCode2FKNM;
import emc.datatypes.gl.analysiscode3.foreignkeys.AnalysisCode3FKNM;
import emc.datatypes.gl.analysiscode4.foreignkeys.AnalysisCode4FKNM;
import emc.datatypes.gl.analysiscode5.foreignkeys.AnalysisCode5FKNM;
import emc.datatypes.gl.analysiscode6.foreignkeys.AnalysisCode6FKNM;
import emc.datatypes.gl.transactionperiodsummary.AccountNumber;
import emc.datatypes.gl.transactionperiodsummary.Credit;
import emc.datatypes.gl.transactionperiodsummary.Debit;
import emc.datatypes.gl.transactionperiodsummary.FinancialPeriod;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import java.math.BigDecimal;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/** 
 *
 * @author claudette
 */
@Entity
@Table(name = "GLTransactionPeriodSummary", uniqueConstraints = {@UniqueConstraint(columnNames = {"uniqueKey", "companyId"})})
public class GLTransactionPeriodSummary extends EMCEntityClass {

    private String accountNumber;
    private String financialPeriod;
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
    public GLTransactionPeriodSummary() {
    }

     /**
      * Creates a new instance of GLTransactionPeriodSummary.  Used by enquiry
      * view queries.
      */
    public GLTransactionPeriodSummary(String accountNumber, String financialPeriod,
            String analysisCode1, String analysisCode2, String analysisCode3,
            String analysisCode4, String analysisCode5, String analysisCode6,
            BigDecimal debit, BigDecimal credit) {
        this.accountNumber = accountNumber;
        this.financialPeriod = financialPeriod;
        this.analysisCode1 = analysisCode1;
        this.analysisCode2 = analysisCode2;
        this.analysisCode3 = analysisCode3;
        this.analysisCode4 = analysisCode4;
        this.analysisCode5 = analysisCode5;
        this.analysisCode6 = analysisCode6;
        this.debit = debit;
        this.credit = credit;
    }

    @Override
    public HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("accountNumber", new AccountNumber());
        toBuild.put("financialPeriod", new FinancialPeriod());
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

    @Override
    public EMCQuery buildQuery() {
        EMCQuery query = super.buildQuery();
        query.addOrderBy("financialPeriod");
        query.addOrderBy("accountNumber");
        query.addOrderBy("analysisCode1");
        query.addOrderBy("analysisCode2");
        query.addOrderBy("analysisCode3");
        query.addOrderBy("analysisCode4");
        query.addOrderBy("analysisCode5");
        query.addOrderBy("analysisCode6");
        return query;
    }
    
    public String getAccountNumber() {
        return this.accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getFinancialPeriod() {
        return this.financialPeriod;
    }

    public void setFinancialPeriod(String financialPeriod) {
        this.financialPeriod = financialPeriod;
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