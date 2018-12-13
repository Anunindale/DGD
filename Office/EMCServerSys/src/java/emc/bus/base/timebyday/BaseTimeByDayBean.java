/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.timebyday;

import emc.entity.base.BaseTimeByDay;
import emc.entity.gl.GLFinancialPeriods;
import emc.enums.base.calendar.Months;
import emc.enums.base.calendar.WeekDays;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class BaseTimeByDayBean extends EMCEntityBean implements BaseTimeByDayLocal {

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        boolean ret = (Boolean) super.validateField(fieldNameToValidate, theRecord, userData);
        if (ret) {
            BaseTimeByDay record = (BaseTimeByDay) theRecord;
            if (fieldNameToValidate.equals("actualDate")) {
                if (!isBlank(record.getActualDate())) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(record.getActualDate());
                    record.setDayOfWeek(WeekDays.fromId(cal.get(Calendar.DAY_OF_WEEK)).toString());
                    record.setDayOfMonth(cal.get(Calendar.DAY_OF_MONTH));
                    record.setMonthOfYear(Months.fromId(cal.get(Calendar.MONTH)).toString());
                    record.setTheYear(cal.get(Calendar.YEAR));
                    record.setWeekOfYear(cal.get(Calendar.WEEK_OF_YEAR));
                    findFinancialPeriod(record, userData);
                    return record;
                }
            }
            if (fieldNameToValidate.equals("financialPeriod")) {
                if (!isBlank(record.getFinancialPeriod())) {
                    if (ret = validateFinancialPeriod(record, userData)) {
                        return record;
                    }
                }
            }
        }
        return ret;
    }

    private void findFinancialPeriod(BaseTimeByDay record, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, GLFinancialPeriods.class);
        query.addAnd("startDate", Functions.date2String(record.getActualDate()), EMCQueryConditions.LESS_THAN_EQ);
        query.addAnd("endDate", Functions.date2String(record.getActualDate()), EMCQueryConditions.GREATER_THAN_EQ);
        GLFinancialPeriods financialPeriod = (GLFinancialPeriods) util.executeSingleResultQuery(query, userData);
        if (financialPeriod != null) {
            record.setFinancialPeriod(financialPeriod.getPeriodId());
            record.setQuarter(financialPeriod.getPeriodQuarter());
        }
    }

    private boolean validateFinancialPeriod(BaseTimeByDay record, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, GLFinancialPeriods.class);
        query.addAnd("periodId", record.getFinancialPeriod());
        GLFinancialPeriods financialPeriod = (GLFinancialPeriods) util.executeSingleResultQuery(query, userData);
        if (record.getActualDate().before(financialPeriod.getStartDate()) || record.getActualDate().after(financialPeriod.getEndDate())) {
            Logger.getLogger("emc").log(Level.SEVERE, "The selected date doen not fall into the selected financial period.", userData);
            return false;
        } else {
            record.setQuarter(financialPeriod.getPeriodQuarter());
            return true;
        }
    }

    public void populateTimeByDays(Date fromDate, Date toDate, EMCUserData userData) throws EMCEntityBeanException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fromDate);
        BaseTimeByDay record;
        while (cal.getTime().before(toDate)) {
            record = new BaseTimeByDay();
            record.setActualDate(cal.getTime());
            record.setDayOfWeek(WeekDays.fromId(cal.get(Calendar.DAY_OF_WEEK)).toString());
            record.setDayOfMonth(cal.get(Calendar.DAY_OF_MONTH));
            record.setMonthOfYear(Months.fromId(cal.get(Calendar.MONTH)).toString());
            record.setTheYear(cal.get(Calendar.YEAR));
            record.setWeekOfYear(cal.get(Calendar.WEEK_OF_YEAR));
            findFinancialPeriod(record, userData);
            this.insert(record, userData);
            cal.add(Calendar.DATE, 1);
        }
        Logger.getLogger("emc").log(Level.INFO, "Times by day generated.", userData);
    }

    public BaseTimeByDay findTimeByDayRecord(Date theDate, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseTimeByDay.class);
        query.addAnd("actualDate", Functions.date2String(theDate));
        return (BaseTimeByDay) util.executeSingleResultQuery(query, userData);
    }

    public BaseTimeByDay findTimeByDayRecord(long recordID, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseTimeByDay.class);
        query.addAnd("recordID", recordID);
        return (BaseTimeByDay) util.executeSingleResultQuery(query, userData);
    }
}
