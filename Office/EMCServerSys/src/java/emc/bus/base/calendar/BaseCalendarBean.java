/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.calendar;

import emc.entity.base.calendar.BaseCalendar;
import emc.entity.base.calendar.BaseCalendarExceptions;
import emc.entity.base.calendar.BaseCalendarShifts;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.messages.ServerBaseMessageEnum;
import emc.tables.EMCTable;
import java.sql.Connection;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class BaseCalendarBean extends EMCEntityBean implements BaseCalendarLocal {

    @EJB
    private BaseCalendarShiftsLocal shiftsBean;
    @EJB
    private BaseCalendarExceptionsLocal exceptionsBean;

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        boolean ret = (Boolean) super.validateField(fieldNameToValidate, theRecord, userData);
        if (ret) {
            BaseCalendar record = (BaseCalendar) theRecord;
            if (fieldNameToValidate.equals("monday")) {
                if (!record.isMonday()) {
                    record.setStdShiftsMon(0);
                    record.setStdworkHoursMon(0);
                    record.setHourMon(0);
                    record.setMinMon(0);
                    return record;
                }
            }
            if (fieldNameToValidate.equals("tuesday")) {
                if (!record.isTuesday()) {
                    record.setStdShiftsTue(0);
                    record.setStdworkHoursTue(0);
                    record.setHourTue(0);
                    record.setMinTue(0);
                    return record;
                }
            }
            if (fieldNameToValidate.equals("wednesday")) {
                if (!record.isWednesday()) {
                    record.setStdShiftsWed(0);
                    record.setStdworkHoursWed(0);
                    record.setHourWed(0);
                    record.setMinWed(0);
                    return record;
                }
            }
            if (fieldNameToValidate.equals("thursday")) {
                if (!record.isThursday()) {
                    record.setStdShiftsThu(0);
                    record.setStdworkHoursThu(0);
                    record.setHourThu(0);
                    record.setMinThu(0);
                    return record;
                }
            }
            if (fieldNameToValidate.equals("friday")) {
                if (!record.isFriday()) {
                    record.setStdShiftsFri(0);
                    record.setStdworkHoursFri(0);
                    record.setHourFri(0);
                    record.setMinsFri(0);
                    return record;
                }
            }
            if (fieldNameToValidate.equals("saturday")) {
                if (!record.isSaturday()) {
                    record.setStdShiftsSat(0);
                    record.setStdworkHoursSat(0);
                    record.setHourSat(0);
                    record.setMinSat(0);
                    return record;
                }
            }

            if (fieldNameToValidate.equals("sunday")) {
                if (!record.isSunday()) {
                    record.setStdShiftsSun(0);
                    record.setStdworkHoursSun(0);
                    record.setHourSun(0);
                    record.setMinSun(0);
                    return record;
                }
            }
        }
        return ret;
    }

    @Override
    public boolean doUpdateValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        return super.doUpdateValidation(doHoursSave((BaseCalendar) vobject), userData);
    }

    @Override
    public boolean doInsertValidation(EMCTable vobject, EMCUserData userData) {
        return super.doInsertValidation(doHoursSave((BaseCalendar) vobject), userData);
    }

    @Override
    public Object insert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        BaseCalendar calendar = (BaseCalendar) iobject;
        if (!isBlank(calendar.getCalendarId())) {
            dateHandler.markCalendarAsUpdated(calendar.getCalendarId(), userData);
        }
        return super.insert(iobject, userData);
    }

    @Override
    public Object update(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        BaseCalendar calendar = (BaseCalendar) uobject;
        if (!isBlank(calendar.getCalendarId())) {
            dateHandler.markCalendarAsUpdated(calendar.getCalendarId(), userData);
        }
        return super.update(uobject, userData);
    }

    @Override
    public void copyCalendar(String calendarToCopy, String newCalendarName, EMCUserData userData) {
        //Calendar
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseCalendar.class.getName());
        query.addAnd("calendarId", calendarToCopy);
        query.addAnd("companyId", userData.getCompanyId());
        BaseCalendar calendar = (BaseCalendar) util.executeSingleResultQuery(query, userData);
        calendar = (BaseCalendar) doClone(calendar, userData);
        calendar.setCalendarId(newCalendarName);
        try {
            super.insert(calendar, userData);
        } catch (EMCEntityBeanException ebx) {
            logMessage(Level.SEVERE, ServerBaseMessageEnum.CALCOPYFAIL, userData);
            //Logger.getLogger("emc").log(Level.SEVERE, "Failed To copy calendar. Could not insert new calendar.", userData);
            return;
        }

        //Exceptions
        query = new EMCQuery(enumQueryTypes.SELECT, BaseCalendarExceptions.class.getName());
        query.addAnd("calendarId", calendarToCopy);
        query.addAnd("companyId", userData.getCompanyId());
        List theList = util.executeGeneralSelectQuery(query, userData);
        BaseCalendarExceptions exception;
        for (Object ox : theList) {
            exception = (BaseCalendarExceptions) ox;
            exception = (BaseCalendarExceptions) doClone(exception, userData);
            exception.setCalendarId(newCalendarName);
            try {
                exceptionsBean.insert(exception, userData);
            } catch (EMCEntityBeanException ebx) {
                logMessage(Level.SEVERE, ServerBaseMessageEnum.EXCOPYFAIL, userData);
                //Logger.getLogger("emc").log(Level.SEVERE, "Failed To copy calendar. Could not insert new exception.", userData);
                return;
            }
        }

        //Shifts
        query = new EMCQuery(enumQueryTypes.SELECT, BaseCalendarShifts.class.getName());
        query.addAnd("calendarId", calendarToCopy);
        query.addAnd("companyId", userData.getCompanyId());
        theList = util.executeGeneralSelectQuery(query, userData);
        BaseCalendarShifts shift;
        for (Object os : theList) {
            shift = (BaseCalendarShifts) os;
            shift = (BaseCalendarShifts) doClone(shift, userData);
            shift.setCalendarId(newCalendarName);
            try {
                shiftsBean.insert(shift, userData);
            } catch (EMCEntityBeanException ebx) {
                logMessage(Level.SEVERE, ServerBaseMessageEnum.SHIFTCOPYFAIL, userData);
                //Logger.getLogger("emc").log(Level.SEVERE, "Failed To copy calendar. Could not insert new shift.", userData);
                return;
            }
        }
    }

    /**
     * Finds the hours worked for the given calendar
     *
     * @param calendarId The calendar to check
     * @param dateFrom The date wich to start from
     * @param dateTo The date to end at
     * @param checkExceptions Should the hours reflect the exceptions on the
     * calendar
     * @param userData
     * @return the hours worked
     */
    @Override
    public double findWorkingHours(String calendarId, Date dateFrom, Date dateTo, boolean checkExceptions, EMCUserData userData) {
        return findWorkingHours(null, calendarId, dateFrom, dateTo, checkExceptions, userData);
    }

    @Override
    public double findWorkingHours(Connection conn, String calendarId, Date dateFrom, Date dateTo, boolean checkExceptions, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseCalendar.class.getName());
        query.addAnd("calendarId", calendarId);
        query.addField("stdworkHoursMon");
        query.addField("stdworkHoursTue");
        query.addField("stdworkHoursWed");
        query.addField("stdworkHoursThu");
        query.addField("stdworkHoursFri");
        query.addField("stdworkHoursSat");
        query.addField("stdworkHoursSun");
        List<Object[]> calDataList;

        if (conn == null) {
            calDataList = util.executeGeneralSelectQuery(query, userData);
        } else {
            try {
                calDataList = util.exJDBCFieldReadQuery(conn, query, userData);
            } catch (EMCEntityBeanException ex) {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to execute JDBC Query. Could not calculate the working hours.", userData);
                return 0d;
            }
        }

        if (calDataList == null || calDataList.isEmpty()) {
            Logger.getLogger("emc").log(Level.SEVERE, "Failed to find the calendar " + calendarId + ". Could not calculate the working hours.", userData);
            return 0d;
        }

        Object[] cal = calDataList.get(0);
        double workingHours = 0;
        Calendar javaCal = Calendar.getInstance();
        javaCal.setTime(dateFrom);
        int day;

        for (int i = 0; i < Functions.calculateDayDiff(dateFrom, dateTo) + 1; i++) {
            day = javaCal.get(Calendar.DAY_OF_WEEK);
            switch (day) {
                case 2://Mon
                    workingHours += (Double) cal[0];
                    break;
                case 3://Tue
                    workingHours += (Double) cal[1];
                    break;
                case 4://Wed
                    workingHours += (Double) cal[2];
                    break;
                case 5://Thu
                    workingHours += (Double) cal[3];
                    break;
                case 6://Fri
                    workingHours += (Double) cal[4];
                    break;
                case 7://Sat
                    workingHours += (Double) cal[5];
                    break;
                case 1://Sun
                    workingHours += (Double) cal[6];
                    break;
            }
            javaCal.add(Calendar.DATE, 1);
        }

        if (checkExceptions) {
            query = new EMCQuery(enumQueryTypes.SELECT, BaseCalendarExceptions.class.getName());
            query.addAnd("calendarId", calendarId);
            query.addAndCommaSeperated("lineDate", Functions.date2String(dateFrom) + "~" + Functions.date2String(dateTo), BaseCalendarExceptions.class.getName(), EMCQueryConditions.EQUALS);
            query.addField("lineDate");
            query.addField("workHours");
            List<Object[]> exceptionList;

            if (conn == null) {
                exceptionList = util.executeGeneralSelectQuery(query, userData);
            } else {
                try {
                    exceptionList = util.exJDBCFieldReadQuery(conn, query, userData);
                } catch (EMCEntityBeanException ex) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Failed to execute JDBC Query. Could not calculate the working hours.", userData);
                    return 0d;
                }
            }

            if (exceptionList == null) {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to find the calendar exceptions for calender" + calendarId + ". Could not calculate the working hours.", userData);
                return 0d;
            }

            for (Object[] exc : exceptionList) {
                javaCal.setTime((Date) exc[0]);
                day = javaCal.get(Calendar.DAY_OF_WEEK);
                switch (day) {
                    case 2://Mon
                        workingHours -= (Double) cal[0];
                        break;
                    case 3://Tue
                        workingHours -= (Double) cal[1];
                        break;
                    case 4://Wed
                        workingHours -= (Double) cal[2];
                        break;
                    case 5://Thu
                        workingHours -= (Double) cal[3];
                        break;
                    case 6://Fri
                        workingHours -= (Double) cal[4];
                        break;
                    case 7://Sat
                        workingHours -= (Double) cal[5];
                        break;
                    case 1://Sun
                        workingHours -= (Double) cal[6];
                        break;
                }
                workingHours += (Double) exc[1];
            }
        }
        return workingHours;

    }

    @Override
    public double findExceptionHours(String calendarId, String exception, Date dateFrom, Date dateTo, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseCalendarExceptions.class);
        query.addAnd("calendarId", calendarId);
        query.addAnd("type", exception);
        query.addAndCommaSeperated("lineDate", Functions.date2String(dateFrom) + "~" + Functions.date2String(dateTo), BaseCalendarExceptions.class.getName(), EMCQueryConditions.EQUALS);
        query.addFieldAggregateFunction("workHours", "SUM");
        query.addGroupBy(calendarId);
        Double d = (Double) util.executeSingleResultQuery(query, userData);
        if (d == null) {
            d = 0d;
        }
        return d;
    }

    /**
     * Get the work hours for the given day on the given calendar
     *
     * @param calendarId
     * @param day
     * @param userData
     * @return
     */
    @Deprecated
    @Override
    public double findWorkingHours(String calendarId, int day, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseCalendar.class.getName());
        query.addAnd("calendarId", calendarId);
        BaseCalendar cal = (BaseCalendar) util.executeSingleResultQuery(query, userData);
        switch (day) {
            case 2:
                return cal.getStdworkHoursMon();
            case 3:
                return cal.getStdworkHoursTue();
            case 4:
                return cal.getStdworkHoursWed();
            case 5:
                return cal.getStdworkHoursThu();
            case 6:
                return cal.getStdworkHoursFri();
            case 7:
                return cal.getStdworkHoursSat();
            case 1:
                return cal.getStdworkHoursSun();
            default:
                return 0;
        }
    }

    private BaseCalendar doHoursSave(BaseCalendar record) {
        record.setHourMon(record.getHourMon() + (record.getMinMon() / 60));
        record.setMinMon((record.getMinMon() % 60));
        record.setStdworkHoursMon(record.getHourMon() + (new Integer(record.getMinMon()).doubleValue() / 60));

        record.setHourTue(record.getHourTue() + (record.getMinTue() / 60));
        record.setMinTue((record.getMinTue() % 60));
        record.setStdworkHoursTue(record.getHourTue() + (new Integer(record.getMinTue()).doubleValue() / 60));

        record.setHourWed(record.getHourWed() + (record.getMinWed() / 60));
        record.setMinWed((record.getMinWed() % 60));
        record.setStdworkHoursWed(record.getHourWed() + (new Integer(record.getMinWed()).doubleValue() / 60));

        record.setHourThu(record.getHourThu() + (record.getMinThu() / 60));
        record.setMinThu((record.getMinThu() % 60));
        record.setStdworkHoursThu(record.getHourThu() + (new Integer(record.getMinThu()).doubleValue() / 60));

        record.setHourFri(record.getHourFri() + (record.getMinsFri() / 60));
        record.setMinsFri((record.getMinsFri() % 60));
        record.setStdworkHoursFri(record.getHourFri() + (new Integer(record.getMinsFri()).doubleValue() / 60));

        record.setHourSat(record.getHourSat() + (record.getMinSat() / 60));
        record.setMinSat((record.getMinSat() % 60));
        record.setStdworkHoursSat(record.getHourSat() + (new Integer(record.getMinSat()).doubleValue() / 60));

        record.setHourSun(record.getHourSun() + (record.getMinSun() / 60));
        record.setMinSun((record.getMinSun() % 60));
        record.setStdworkHoursSun(record.getHourSun() + (new Integer(record.getMinSun()).doubleValue() / 60));

        return record;
    }

    @Override
    public void testCalendar(String cal, Date theDate, int toShift, EMCUserData userData) {
        Logger.getLogger("emc").log(Level.INFO, Functions.date2String(dateHandler.calculateEndDate(cal, theDate, toShift, userData)), userData);
    }
}
