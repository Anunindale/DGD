/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.gl;

import emc.datatypes.EMCDataType;
import emc.datatypes.gl.allocationrules.foreignkeys.AllocationRulesFKNM;
import emc.datatypes.gl.analysiscode1.foreignkeys.AnalysisCode1FKNM;
import emc.datatypes.gl.analysiscode2.foreignkeys.AnalysisCode2FKNM;
import emc.datatypes.gl.analysiscode3.foreignkeys.AnalysisCode3FKNM;
import emc.datatypes.gl.analysiscode4.foreignkeys.AnalysisCode4FKNM;
import emc.datatypes.gl.analysisrules.foreignkeys.AnalysisRulesFKNM;
import emc.datatypes.gl.analysiscode6.foreignkeys.AnalysisCode6FKNM;
import emc.datatypes.gl.analysiscode5.foreignkeys.AnalysisCode5FKNM;
import emc.datatypes.gl.chartofaccounts.AccountClosed;
import emc.datatypes.gl.chartofaccounts.AccountNumber;
import emc.datatypes.gl.chartofaccounts.AccountType;
import emc.datatypes.gl.chartofaccounts.Description;
import emc.datatypes.gl.chartofaccounts.GroupCompanyAccount;
import emc.datatypes.gl.chartofaccounts.GroupRules;
import emc.datatypes.gl.chartofaccounts.RecordType;
import emc.datatypes.gl.chartofaccounts.RestrictedUse;
import emc.datatypes.gl.chartofaccounts.VATCode;
import emc.datatypes.gl.currency.foreignkeys.CurrencyFK;
import emc.datatypes.gl.securityrules.foreignkeys.SecurityRulesFKNM;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author riaan
 */
@Entity
@Table(name = "GLChartOfAccounts", uniqueConstraints = {@UniqueConstraint(columnNames = {"companyId", "accountNumber"})})
public class GLChartOfAccounts extends EMCEntityClass {

    private String accountNumber;
    private String description;
    private String recordType;
    private String accountType;
    private String currency;
    private String vatCode;
    private String groupCompanyAccount;
    private boolean restrictedUse;
    private boolean locked;
    private boolean accountClosed;
    private String analysisCode1;
    private String analysisCode2;
    private String analysisCode3;
    private String analysisCode4;
    private String analysisCode5;
    private String analysisCode6;
    private String securityRules;
    private String analysisRules;
    private String allocationRules;
    private String groupRules;

    /** Creates a new instance of GLChartOfAccounts */
    public GLChartOfAccounts() {
    }

    public boolean isAccountClosed() {
        return accountClosed;
    }

    public void setAccountClosed(boolean accountClosed) {
        this.accountClosed = accountClosed;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAllocationRules() {
        return allocationRules;
    }

    public void setAllocationRules(String allocationRules) {
        this.allocationRules = allocationRules;
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

    public String getAnalysisRules() {
        return analysisRules;
    }

    public void setAnalysisRules(String analysisRules) {
        this.analysisRules = analysisRules;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    public boolean isRestrictedUse() {
        return restrictedUse;
    }

    public void setRestrictedUse(boolean restrictedUse) {
        this.restrictedUse = restrictedUse;
    }

    public String getSecurityRules() {
        return securityRules;
    }

    public void setSecurityRules(String securityRules) {
        this.securityRules = securityRules;
    }

    public String getVatCode() {
        return vatCode;
    }

    public void setVatCode(String vatCode) {
        this.vatCode = vatCode;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("accountNumber", new AccountNumber());
        toBuild.put("description", new Description());
        toBuild.put("recordType", new RecordType());
        toBuild.put("currency", new CurrencyFK());
        toBuild.put("vatCode", new VATCode());
        toBuild.put("groupCompanyAccount", new GroupCompanyAccount());
        toBuild.put("restrictedUse", new RestrictedUse());
        toBuild.put("accountClosed", new AccountClosed());
        toBuild.put("analysisCode1", new AnalysisCode1FKNM());
        toBuild.put("analysisCode2", new AnalysisCode2FKNM());
        toBuild.put("analysisCode3", new AnalysisCode3FKNM());
        toBuild.put("analysisCode4", new AnalysisCode4FKNM());
        toBuild.put("analysisCode5", new AnalysisCode5FKNM());
        toBuild.put("analysisCode6", new AnalysisCode6FKNM());
        toBuild.put("securityRules", new SecurityRulesFKNM());
        toBuild.put("analysisRules", new AnalysisRulesFKNM());
        toBuild.put("allocationRules", new AllocationRulesFKNM());
        toBuild.put("groupRules", new GroupRules());
        toBuild.put("accountType", new AccountType());
        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("accountNumber");
        toBuild.add("description");
        return toBuild;
    }

    @Override
    public EMCQuery buildQuery() {
        EMCQuery query = super.buildQuery();

        query.addOrderBy("accountNumber");

        return query;
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

    public String getGroupCompanyAccount() {
        return groupCompanyAccount;
    }

    public void setGroupCompanyAccount(String groupCompanyAccount) {
        this.groupCompanyAccount = groupCompanyAccount;
    }

    public String getGroupRules() {
        return groupRules;
    }

    public void setGroupRules(String groupRules) {
        this.groupRules = groupRules;
    }
}
