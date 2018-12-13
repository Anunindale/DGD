/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.gl;

import emc.datatypes.EMCDataType;
import emc.datatypes.gl.analysiscode1.foreignkeys.AnalysisCode1FKNM;
import emc.datatypes.gl.analysiscode2.foreignkeys.AnalysisCode2FKNM;
import emc.datatypes.gl.analysiscode3.foreignkeys.AnalysisCode3FKNM;
import emc.datatypes.gl.analysiscode4.foreignkeys.AnalysisCode4FKNM;
import emc.datatypes.gl.analysiscode5.foreignkeys.AnalysisCode5FKNM;
import emc.datatypes.gl.analysiscode6.foreignkeys.AnalysisCode6FKNM;
import emc.datatypes.gl.chartofaccounts.SubAccountNumber;
import emc.datatypes.gl.chartofaccounts.foreignkeys.AccountNumberFK;
import emc.datatypes.gl.financialperiod.foreignkeys.PeriodIdFKNM;
import emc.datatypes.gl.journallines.Credit;
import emc.datatypes.gl.journallines.Debit;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "GLChartOfAccountsAnalysisBalances", uniqueConstraints = {@UniqueConstraint(columnNames = {"companyId", "accountNumber", "periodId"})})
public class GLChartOfAccountsAnalysisBalances extends EMCEntityClass {

    private String accountNumber;
    private String subAccountNumber;
    private String periodId;
    private String analysisCode1;
    private String analysisCode2;
    private String analysisCode3;
    private String analysisCode4;
    private String analysisCode5;
    private String analysisCode6;
    private double debit;
    private double credit;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public double getDebit() {
        return debit;
    }

    public void setDebit(double debit) {
        this.debit = debit;
    }

    public String getPeriodId() {
        return periodId;
    }

    public void setPeriodId(String periodId) {
        this.periodId = periodId;
    }

    public String getSubAccountNumber() {
        return subAccountNumber;
    }

    public void setSubAccountNumber(String subAccountNumber) {
        this.subAccountNumber = subAccountNumber;
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

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("accountNumber", new AccountNumberFK());
        toBuild.put("subAccountNumber", new SubAccountNumber());
        toBuild.put("periodId", new PeriodIdFKNM());
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
}
