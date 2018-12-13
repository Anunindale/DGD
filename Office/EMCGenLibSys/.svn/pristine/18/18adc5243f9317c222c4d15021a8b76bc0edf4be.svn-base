/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.gl;

import emc.datatypes.EMCDataType;
import emc.datatypes.gl.financialperiod.EndDate;
import emc.datatypes.gl.financialperiod.NumberOfWeeks;
import emc.datatypes.gl.financialperiod.PeriodId;
import emc.datatypes.gl.financialperiod.PeriodName;
import emc.datatypes.gl.financialperiod.PeriodType;
import emc.datatypes.gl.financialperiod.Quarter;
import emc.datatypes.gl.financialperiod.StartDate;
import emc.datatypes.gl.financialperiod.Status;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author riaan
 */
@Entity
@Table(name = "GLFinancialPeriods", uniqueConstraints = {@UniqueConstraint(columnNames = {"companyId", "periodId"})})
public class GLFinancialPeriods extends EMCEntityClass {

    private String periodId;
    private String periodType;
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Temporal(TemporalType.DATE)
    private Date endDate;
    private int numberOfWeeks;
    private String periodName;
    private int periodQuarter =1;
    private String accountStatus;

    /** Creates a new instance of GLFinancialPeriods. */
    public GLFinancialPeriods() {
    }

    public String getPeriodType() {
        return periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getPeriodQuarter() {
        return periodQuarter;
    }

    public void setPeriodQuarter(int quarter) {
        this.periodQuarter = quarter;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String status) {
        this.accountStatus = status;
    }

    public String getPeriodId() {
        return periodId;
    }

    public void setPeriodId(String periodId) {
        this.periodId = periodId;
    }

    /**
     * @return the numberOfWeeks
     */
    public int getNumberOfWeeks() {
        return numberOfWeeks;
    }

    /**
     * @param numberOfWeeks the numberOfWeeks to set
     */
    public void setNumberOfWeeks(int numberOfWeeks) {
        this.numberOfWeeks = numberOfWeeks;
    }

    /**
     * @return the periodName
     */
    public String getPeriodName() {
        return periodName;
    }

    /**
     * @param periodName the periodName to set
     */
    public void setPeriodName(String periodName) {
        this.periodName = periodName;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("periodId");
        toBuild.add("startDate");
        toBuild.add("endDate");
        toBuild.add("periodQuarter");
        return toBuild;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("periodId", new PeriodId());
        toBuild.put("periodType", new PeriodType());
        toBuild.put("startDate", new StartDate());
        toBuild.put("endDate", new EndDate());
        toBuild.put("periodQuarter", new Quarter());
        toBuild.put("accountStatus", new Status());
        toBuild.put("numberOfWeeks",new NumberOfWeeks());
        toBuild.put("periodName", new PeriodName());
        return toBuild;
    }

    @Override
    public EMCQuery buildQuery() {
        EMCQuery query = super.buildQuery();
        query.addOrderBy("startDate");
        return query;
    }
}
