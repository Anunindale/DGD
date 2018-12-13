package emc.entity.base;

import emc.datatypes.EMCDataType;
import emc.datatypes.base.timebyday.ActualDate;
import emc.datatypes.base.timebyday.DayOfMonth;
import emc.datatypes.base.timebyday.DayOfWeek;
import emc.datatypes.base.timebyday.MonthOfYear;
import emc.datatypes.base.timebyday.Quarter;
import emc.datatypes.base.timebyday.TheYear;
import emc.datatypes.base.timebyday.WeekOfYear;
import emc.datatypes.gl.financialperiod.foreignkeys.PeriodIdFKNM;
import emc.framework.EMCEntityClass;
import java.util.Date;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "BaseTimeByDay", uniqueConstraints = {@UniqueConstraint(columnNames = {"actualDate", "companyId"})})
public class BaseTimeByDay extends EMCEntityClass {

    @Temporal(TemporalType.DATE)
    private Date actualDate;
    private String dayOfWeek;
    private String monthOfYear;
    private int theYear;
    private int dayOfMonth;
    private int weekOfYear;
    private int quarter;
    private String financialPeriod;

    public Date getActualDate() {
        return actualDate;
    }

    public void setActualDate(Date actualDate) {
        this.actualDate = actualDate;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getFinancialPeriod() {
        return financialPeriod;
    }

    public void setFinancialPeriod(String financialPeriod) {
        this.financialPeriod = financialPeriod;
    }

    public String getMonthOfYear() {
        return monthOfYear;
    }

    public void setMonthOfYear(String monthOfYear) {
        this.monthOfYear = monthOfYear;
    }

    public int getQuarter() {
        return quarter;
    }

    public void setQuarter(int quarter) {
        this.quarter = quarter;
    }

    public int getTheYear() {
        return theYear;
    }

    public void setTheYear(int theYear) {
        this.theYear = theYear;
    }

    public int getWeekOfYear() {
        return weekOfYear;
    }

    public void setWeekOfYear(int weekOfYear) {
        this.weekOfYear = weekOfYear;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("actualDate", new ActualDate());
        toBuild.put("dayOfWeek", new DayOfWeek());
        toBuild.put("monthOfYear", new MonthOfYear());
        toBuild.put("theYear", new TheYear());
        toBuild.put("dayOfMonth", new DayOfMonth());
        toBuild.put("weekOfYear", new WeekOfYear());
        toBuild.put("quarter", new Quarter());
        toBuild.put("financialPeriod", new PeriodIdFKNM());
        return toBuild;
    }
}
