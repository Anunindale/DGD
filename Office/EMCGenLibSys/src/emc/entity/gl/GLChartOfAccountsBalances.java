/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.gl;

import emc.datatypes.EMCDataType;
import emc.datatypes.gl.chartofaccounts.SubAccountNumber;
import emc.datatypes.gl.chartofaccounts.foreignkeys.AccountNumberFK;
import emc.datatypes.gl.financialperiod.foreignkeys.PeriodIdFKNM;
import emc.datatypes.gl.journallines.Credit;
import emc.datatypes.gl.journallines.Debit;
import emc.framework.EMCEntityClass;
import java.math.BigDecimal;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "GLChartOfAccountsBalances", uniqueConstraints = {@UniqueConstraint(columnNames = {"companyId", "accountNumber", "periodId"})})
public class GLChartOfAccountsBalances extends EMCEntityClass {

    private String accountNumber;
    private String subAccountNumber;
    private String periodId;
    private BigDecimal debit;
    private BigDecimal credit;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public BigDecimal getDebit() {
        return debit;
    }

    public void setDebit(BigDecimal debit) {
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

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("accountNumber", new AccountNumberFK());
        toBuild.put("subAccountNumber", new SubAccountNumber());
        toBuild.put("periodId", new PeriodIdFKNM());
        toBuild.put("debit", new Debit());
        toBuild.put("credit", new Credit());
        return toBuild;
    }
}
