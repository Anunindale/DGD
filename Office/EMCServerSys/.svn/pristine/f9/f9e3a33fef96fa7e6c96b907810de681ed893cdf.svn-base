/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.calendar;

import emc.entity.base.calendar.BaseCalendar;
import emc.entity.base.calendar.BaseCalendarExceptions;
import emc.entity.base.calendar.BaseCalendarShifts;
import emc.enums.base.calendar.WeekDays;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.math.EMCMath;
import emc.messages.ServerBaseMessageEnum;
import emc.tables.EMCTable;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class BaseCalendarShiftsBean extends EMCEntityBean implements BaseCalendarShiftsLocal {

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        boolean ret = (Boolean) super.validateField(fieldNameToValidate, theRecord, userData);
        if (ret) {
            BaseCalendarShifts record = (BaseCalendarShifts) theRecord;
            if (fieldNameToValidate.equals("dayOfWeek") && !isBlank(record.getDayOfWeek())) {
                if (record.getShiftDate() != null) {
                    ret = false;
                    logMessage(Level.SEVERE, ServerBaseMessageEnum.SHIFTDAYERR, userData);
                    //Logger.getLogger("emc").log(Level.SEVERE, "The day can't be specified if a date is specified.", userData);
                }
            }
            if (fieldNameToValidate.equals("shiftDate") && record.getShiftDate() != null) {
                if (!isBlank(record.getDayOfWeek())) {
                    ret = false;
                    logMessage(Level.SEVERE, ServerBaseMessageEnum.SHIFTDATEERR, userData);
                    //Logger.getLogger("emc").log(Level.SEVERE, "The date can't be specified if a day is specified.", userData);
                }
            }
        }
        return ret;
    }

    @Override
    public boolean doInsertValidation(EMCTable vobject, EMCUserData userData) {
        boolean ret = super.doInsertValidation(vobject, userData);
        if (ret) {
            BaseCalendarShifts record = (BaseCalendarShifts) vobject;
//            ret = ret && checkNumberShifts(record, false, userData);
            ret = ret && checkSiftHours(record, userData);
        }
        return ret;
    }

    @Override
    public boolean doUpdateValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        boolean ret = super.doInsertValidation(vobject, userData);
        if (ret) {
            BaseCalendarShifts record = (BaseCalendarShifts) vobject;
//            ret = checkNumberShifts(record, true, userData);
            ret = ret && checkSiftHours(record, userData);
        }
        return ret;
    }

    private boolean checkNumberShifts(BaseCalendarShifts record, boolean isUpdate, EMCUserData userData) {
        EMCQuery query = null;
        if (!isBlank(record.getDayOfWeek())) {
            query = new EMCQuery(enumQueryTypes.SELECT, BaseCalendarShifts.class.getName());
            query.addTableAnd(BaseCalendar.class.getName(), "calendarId", BaseCalendarShifts.class.getName(), "calendarId");
            query.addAnd("calendarId", record.getCalendarId());
            query.addAnd("companyId", userData.getCompanyId());
            query.addAnd("dayOfWeek", record.getDayOfWeek(), BaseCalendarShifts.class.getName());
            query.addGroupBy(BaseCalendar.class.getName(), "calendarId");
            query.addFieldAggregateFunction("calendarId", BaseCalendarShifts.class.getName(), "COUNT");
            switch (WeekDays.fromString(record.getDayOfWeek())) {
                case MONDAY:
                    query.addField("stdShiftsMon", BaseCalendar.class.getName());
                    break;
                case TUESDAY:
                    query.addField("stdShiftsTue", BaseCalendar.class.getName());
                    break;
                case WEDNESDAY:
                    query.addField("stdShiftsWed", BaseCalendar.class.getName());
                    break;
                case THURSDAY:
                    query.addField("stdShiftsThu", BaseCalendar.class.getName());
                    break;
                case FRIDAY:
                    query.addField("stdShiftsFri", BaseCalendar.class.getName());
                    break;
                case SATURDAY:
                    query.addField("stdShiftsSat", BaseCalendar.class.getName());
                    break;
                case SUNDAY:
                    query.addField("stdShiftsSun", BaseCalendar.class.getName());
                    break;
            }
        } else if (!isBlank(record.getShiftDate())) {
            query = new EMCQuery(enumQueryTypes.SELECT, BaseCalendarShifts.class.getName());
            query.addTableAnd(BaseCalendarExceptions.class.getName(), "shiftDate", BaseCalendarShifts.class.getName(), "lineDate");
            query.addAnd("calendarId", BaseCalendarShifts.class.getName(), EMCQueryConditions.EQUALS, "calendarId", BaseCalendarExceptions.class.getName());
            query.addAnd("calendarId", record.getCalendarId(), BaseCalendarShifts.class.getName());
            query.addFieldAggregateFunction("shiftNumber", BaseCalendarShifts.class.getName(), "COUNT");
            query.addField("numberOfShifts", BaseCalendarExceptions.class.getName());
            query.addGroupBy(BaseCalendarExceptions.class.getName(), "lineDate");
        } else {
            logMessage(Level.SEVERE, ServerBaseMessageEnum.NOSHIFTDATE, userData);
            //Logger.getLogger("emc").log(Level.SEVERE, "You need to select a day or a date at least.", userData);
            return false;
        }
        Object o = util.executeSingleResultQuery(query.toString(), userData);
        if (o != null) {
            Object[] oArray = (Object[]) o;
            int count = Integer.parseInt(oArray[0].toString());
            double allowed = Double.valueOf(oArray[1].toString());
            if (isUpdate ? count > allowed : count == allowed) {
                logMessage(Level.SEVERE, ServerBaseMessageEnum.ALLSHIFTCAP, userData);
                //Logger.getLogger("emc").log(Level.SEVERE, "All the shifts for the selected day has already been captured", userData);
                return false;
            }
        }
        return true;
    }

    private boolean checkSiftHours(BaseCalendarShifts record, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseCalendarShifts.class.getName());
        query.addAnd("calendarId", record.getCalendarId());
        query.addAnd("recordID", record.getRecordID(), EMCQueryConditions.NOT);
        query.addField("shiftStartTime");
        query.addField("shiftEndTime");
        if (!isBlank(record.getDayOfWeek())) {
            query.addAnd("dayOfWeek", record.getDayOfWeek());
            List theList = util.executeGeneralSelectQuery(query, userData);
            Date fromD = record.getShiftStartTime();
            Date toD = record.getShiftEndTime();
            theList.add(new Object[]{Time.valueOf(fromD.getHours() + ":" + fromD.getMinutes() + ":00"), Time.valueOf(toD.getHours() + ":" + toD.getMinutes() + ":00")});
            double duration = 0d;
            for (Object o : theList) {
                Object[] oArray = (Object[]) o;
                duration += calculateDuration((Time) oArray[0], (Time) oArray[1]);
            }
            query = new EMCQuery(enumQueryTypes.SELECT, BaseCalendar.class.getName());
            query.addAnd("calendarId", record.getCalendarId());
            switch (WeekDays.fromString(record.getDayOfWeek())) {
                case MONDAY:
                    query.addField("stdworkHoursMon", BaseCalendar.class.getName());
                    break;
                case TUESDAY:
                    query.addField("stdworkHoursTue", BaseCalendar.class.getName());
                    break;
                case WEDNESDAY:
                    query.addField("stdworkHoursWed", BaseCalendar.class.getName());
                    break;
                case THURSDAY:
                    query.addField("stdworkHoursThu", BaseCalendar.class.getName());
                    break;
                case FRIDAY:
                    query.addField("stdworkHoursFri", BaseCalendar.class.getName());
                    break;
                case SATURDAY:
                    query.addField("stdworkHoursSat", BaseCalendar.class.getName());
                    break;
                case SUNDAY:
                    query.addField("stdworkHoursSun", BaseCalendar.class.getName());
                    break;
            }
            Object o = util.executeSingleResultQuery(query, userData);
            if (o == null) {
                logMessage(Level.WARNING, ServerBaseMessageEnum.NOCALFOUND, userData);
                //Logger.getLogger("emc").log(Level.WARNING, "No calendar found.", userData);
                return false;
            } else {
                double total = (Double) o;
                if (util.compareDouble(duration, total) > 0) {
                    logMessage(Level.WARNING, ServerBaseMessageEnum.OVERTOT, userData, getTimeToDisplay(total), getTimeToDisplay(duration));
                    //Logger.getLogger("emc").log(Level.WARNING, "There are only " + getTimeToDisplay(total) + "to work on that day and your shifts amount to " + getTimeToDisplay(duration) + ".", userData);
                    return false;
                }
            }
        } else if (!isBlank(record.getShiftDate())) {
            query.addAnd("shiftDate", record.getShiftDate());
            List theList = util.executeGeneralSelectQuery(query, userData);
            Date fromD = record.getShiftStartTime();
            Date toD = record.getShiftEndTime();
            theList.add(new Object[]{Time.valueOf(fromD.getHours() + ":" + fromD.getMinutes() + ":00"), Time.valueOf(toD.getHours() + ":" + toD.getMinutes() + ":00")});
            double duration = 0d;
            for (Object o : theList) {
                Object[] oArray = (Object[]) o;
                duration += calculateDuration((Time) oArray[0], (Time) oArray[1]);
            }
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
            query = new EMCQuery(enumQueryTypes.SELECT, BaseCalendarExceptions.class.getName());
            query.addAnd("calendarId", record.getCalendarId());
            query.addAnd("lineDate", f.format(record.getShiftDate()));
            query.addField("workHours");
            Object o = util.executeSingleResultQuery(query, userData);
            if (o == null) {
                Logger.getLogger("emc").log(Level.WARNING, "No exception found for this date. Please create the exception first.", userData);
                return false;
            } else {
                double total = (Double) o;
                if (util.compareDouble(duration, total) > 0) {
                    Logger.getLogger("emc").log(Level.WARNING, "There are only " + getTimeToDisplay(total) + "to work on that day and your shifts amount to " + getTimeToDisplay(duration) + ".", userData);
                    return false;
                }
            }
        } else {
            Logger.getLogger("emc").log(Level.SEVERE, "You need to select a day or a date at least.", userData);
            return false;
        }
        return true;
    }

    private double calculateDuration(Time from, Time to) {
        double duration = 0d;
        double fromH = from.getHours();
        double fromM = from.getMinutes();
        double toH = to.getHours();
        double toM = to.getMinutes();
        duration += toH - fromH;
        if (duration < 0) {
            duration = duration + 24;
        }
        duration += (toM - fromM) / 60;
        return duration;
    }

    private String getTimeToDisplay(double time) {
        int hours = new Double(Math.floor(time)).intValue();
        int minutes = new Double(new EMCMath().round((time - Math.floor(time)) * 60, 0)).intValue();
        return hours + " hours and " + minutes + " minutes ";
    }
}
