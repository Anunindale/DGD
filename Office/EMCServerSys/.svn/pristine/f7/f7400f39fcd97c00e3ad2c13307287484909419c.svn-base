/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.server.datehandler;

import emc.bus.base.parameters.BaseParametersLocal;
import emc.constants.systemConstants;
import emc.entity.base.BaseParameters;
import emc.framework.EMCUserData;
import emc.server.datehandler.emcbusinesscalendar.EMCBusinessCalendar;
import emc.server.datehandler.emcbusinesscalendar.EMCBusinessCalendarFactory;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class EMCDateHandler extends EMCBusinessCalendarFactory implements EMCDateHandlerLocal {

    @EJB
    private BaseParametersLocal baseParametersBean;
    /**
     * All minutes have this many milliseconds except the last minute of the day on a day defined with
     * a leap second.
     */
    public static final long MILLISECS_PER_MINUTE = 60 * 1000;
    /**
     * Number of milliseconds per hour, except when a leap second is inserted.
     */
    public static final long MILLISECS_PER_HOUR = 60 * MILLISECS_PER_MINUTE;
    /**
     * Number of leap seconds per day expect on
     * <BR/>1. days when a leap second has been inserted, e.g. 1999 JAN  1.
     * <BR/>2. Daylight-savings "spring forward" or "fall back" days.
     */
    protected static final long MILLISECS_PER_DAY = 24 * MILLISECS_PER_HOUR;

    /**
     * Returns the system date format.  This method should be kept in sync with
     * the date format specified on the emc.constants.systemConstants class.
     * @return System date format.
     */
    public String systemDateFormat() {
        return "yyyy/MM/dd";
    }

    /**
     * Takes a date and return a string with only the date based on systemConstants.
     */
    public String date2String(Date x) {
        SimpleDateFormat dateformat = new SimpleDateFormat(systemDateFormat());
        return dateformat.format(x);
    }

    /**
     * Calculates the end date for a certian task agains the given calendar.
     * @param calendarId The calendar to use for the calculations
     * @param startDate The date the task starts
     * @param days The amount of days the task will take
     * @param userData
     * @return The data the task will be finished.
     */
    @Override
    public Date calculateEndDate(String calendarId, Date startDate, int daysToMove, EMCUserData userData) {
        if (daysToMove != 0) {
            EMCBusinessCalendar calendar = super.getBusinessCalendar(calendarId, userData);
            Calendar initial = new GregorianCalendar(TimeZone.getTimeZone(systemConstants.getTimeZone()));
            if (startDate != null) {
                initial.setTime(startDate);
            }
            if (daysToMove > 0) {
                for (int daysMoved = 0; daysMoved < daysToMove; daysMoved++) {
                    initial.add(Calendar.DATE, 1);
                    if (!calendar.isWorkingDay(initial)) {
                        daysMoved--;
                    }
                }
            } else {
                for (int daysMoved = daysToMove; daysMoved < 0; daysMoved++) {
                    initial.add(Calendar.DATE, -1);
                    if (!calendar.isWorkingDay(initial)) {
                        daysMoved--;
                    }
                }
            }
            return initial.getTime();
        } else {
            return startDate;
        }
    }

    /**
     * Calculatates the end result od adding X a,ount of hourse to the givent date. Only woring days are counted.
     * @param calendarId
     * @param startTime
     * @param hours
     * @param userData
     * @return
     */
    public Date calculateEndTime(String calendarId, Date startTime, int hours, EMCUserData userData) {
        if (hours != 0) {
            EMCBusinessCalendar calendar = super.getBusinessCalendar(calendarId, userData);
            Calendar initial = new GregorianCalendar(TimeZone.getTimeZone(systemConstants.getTimeZone()));

            if (startTime != null) {
                initial.setTime(startTime);
            }

            if (hours > 0) {
                long toMoveMillis = hours * 60 * 60 * 1000;
                long movedMillis = 0;

                Calendar midnightCal;

                while (movedMillis != toMoveMillis) {
                    midnightCal = (Calendar) initial.clone();
                    midnightCal.set(Calendar.HOUR_OF_DAY, 0);
                    midnightCal.set(Calendar.MINUTE, 0);
                    midnightCal.set(Calendar.SECOND, 0);
                    midnightCal.set(Calendar.MILLISECOND, 0);
                    midnightCal.add(Calendar.DATE, 1);

                    if (toMoveMillis - movedMillis >= midnightCal.getTimeInMillis() - initial.getTimeInMillis()) {
                        movedMillis += midnightCal.getTimeInMillis() - initial.getTimeInMillis();
                        initial = (Calendar) midnightCal.clone();
                        while (!calendar.isWorkingDay(initial)) {
                            initial.add(Calendar.DATE, 1);
                        }
                    } else {
                        initial.setTimeInMillis(initial.getTimeInMillis() + (toMoveMillis - movedMillis));
                        movedMillis += toMoveMillis - movedMillis;
                    }
                }
            } //else {
//                for (int daysMoved = daysToMove; daysMoved < 0; daysMoved++) {
//                    initial.add(Calendar.DATE, -1);
//                    if (!calendar.isWorkingDay(initial)) {
//                        daysMoved--;
//                    }
//                }
//            }
            return initial.getTime();
        } else {
            return startTime;
        }
    }

    @Deprecated
    /**
     * This method will work subtract the specified number of working days from the
     * specified end date.
     * @param calendarId The calendar to use for the calculation.
     * @param endDate End date.
     * @param days Number of working days to go back.
     * @param userData User data.
     * @return A date which is equal to the specified end date, minus the
     * specified number of days.
     */
    public Date calculateStartDate(String calendarId, Date endDate, int days, EMCUserData userData) {
        if (days != 0) {
            EMCBusinessCalendar calendar = super.getBusinessCalendar(calendarId, userData);
            Calendar initial = new GregorianCalendar(TimeZone.getTimeZone(systemConstants.getTimeZone()));
            if (endDate != null) {
                initial.setTime(endDate);
            }
            int daysRemaining = days;
            while (daysRemaining > 0) {
                if (calendar.isWorkingDay(initial)) {
                    daysRemaining--;
                }
                initial.add(Calendar.DATE, -1);
            }
            return initial.getTime();
        } else {
            return endDate;
        }
    }

    @Override
    public int calculateDateDiffInBusinussDays(String calendarId, Date startDate, Date endDate, EMCUserData userData) {
        //From
        Calendar fromCal = new GregorianCalendar(TimeZone.getTimeZone(systemConstants.getTimeZone()));
        if (startDate != null) {
            fromCal.setTime(startDate);
        }
        //To
        Calendar toCal = new GregorianCalendar(TimeZone.getTimeZone(systemConstants.getTimeZone()));
        if (endDate != null) {
            toCal.setTime(endDate);
        }
        //Businuss Cal
        EMCBusinessCalendar businussCal = super.getBusinessCalendar(calendarId, userData);
        //Calculate
        int days = 0;
        while (fromCal.before(toCal)) {
            if (businussCal.isWorkingDay(fromCal)) {
                days++;
            }
            fromCal.add(Calendar.DATE, 1);
            if (fromCal.equals(toCal)) {
                if (businussCal.isWorkingDay(fromCal)) {
                    days++;
                }
            }
        }
        return days;
    }

    @Override
    public boolean isWorkingDay(String calendarId, Calendar cal, EMCUserData userData) {
        EMCBusinessCalendar businessCalendar = super.getBusinessCalendar(calendarId, userData);
        return businessCalendar.isWorkingDay(cal);
    }

    /**
     * Create a date with the set values
     * @param year
     * @param month is zero based
     * @param day
     * @param hour
     * @param minute
     * @param second
     * @return
     */
    @Override
    public Date buildDate(int year, int month, int day, int hour, int minute, int second) {
        Calendar cal = new GregorianCalendar(TimeZone.getTimeZone(systemConstants.getTimeZone()));
        cal.set(year, month, day, hour, minute, second);
        return cal.getTime();
    }

    /**
     * Compares two dates, ignoring time values.
     * @param date Date to compare.
     * @param otherDate Other date to compare.
     * @param userData User data.
     * @return An int indicating whether date is greater than, less than or equal to otherDate.
     */
    @Override
    public int compareDatesIgnoreTime(Date date, Date otherDate, EMCUserData userData) {
        Calendar cal = new GregorianCalendar(TimeZone.getTimeZone(systemConstants.getTimeZone()));
        cal.setTime(date);
        cal = removeTime(cal, userData);

        Calendar otherCalendar = new GregorianCalendar(TimeZone.getTimeZone(systemConstants.getTimeZone()));
        otherCalendar.setTime(otherDate);
        otherCalendar = removeTime(otherCalendar, userData);

        return cal.compareTo(otherCalendar);
    }

    /**
     * Gets the difference in days between two dates.
     * DO NOT USE THIS METHOD IF DATE HAS TIME IN AS WELL IT ONLY CATERS FOR DATES NOT TIME
     * @param firstDate
     * @param secondDate
     * @param userData
     * @return
     */
    @Override
    public long getDateDiffInDays(Date firstDate, Date secondDate, EMCUserData userData) {
        return (secondDate.getTime() - firstDate.getTime()) / MILLISECS_PER_DAY;
    }

    /**
     * Adds or subtracts number of days from date given.
     * THIS METHOD WILL keep the time of the date passed to it.
     * @param dateToAddTo the date to start at
     * @param daysToAdd or subtract pass in negative number to return
     * @return
     */
    @Override
    public long addToDate(Date dateToAddTo, int daysToAdd) {
        return (dateToAddTo.getTime() + daysToAdd * MILLISECS_PER_DAY);
    }

    /**
     * Takes a date and return a string with only the Time based on systemConstants + seconds.
     */
    @Override
    public String date2TimeStringSeconds(Date x) {
        SimpleDateFormat dateformat = new SimpleDateFormat(systemConstants.systemTimeFormatWithSeconds());
        return dateformat.format(x);
    }

    /**
     * Returns a string with specified dateformat at time now
     */
    @Override
    public String nowDateString(String dateFormat) {
        Calendar cal = new GregorianCalendar(TimeZone.getTimeZone(systemConstants.getTimeZone()));
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setTimeZone(TimeZone.getTimeZone(systemConstants.getTimeZone()));
        return sdf.format(cal.getTime());
    }

    /**
     * Returns the current date + time as a Date
     */
    @Override
    public Date nowDate() {
        Calendar cal = new GregorianCalendar(TimeZone.getTimeZone(systemConstants.getTimeZone()));
        return cal.getTime();
    }

    /**
     * Returns a Calendar instance using the date settings from Base parameters.
     * @param userData User data.  If no Base parameters are set up, this method
     * will return a Calender instance using the default settings.
     * @return A Calendar instance using the date settings from Base parameters.
     */
    @Override
    public Calendar getCalendarWithEMCSettings(EMCUserData userData) {
        BaseParameters parameters = baseParametersBean.getBaseParameters(userData);
        return getCalendarWithEMCSettings(parameters, userData);
    }

    /**
     * Returns a Calendar instance using the date settings from Base parameters.
     * @param parameters Base Parameters. (parameters is null, this method will return a Calender instance using the default settings.
     * @param userData EMC User data.
     * @return A Calendar instance using the date settings from Base parameters.
     * If no Base parameters are passes, this method will return a Calender instance using the default settings.
     */
    @Override
    public Calendar getCalendarWithEMCSettings(BaseParameters parameters, EMCUserData userData) {
        Calendar cal = Calendar.getInstance();

        if (parameters != null) {
            cal.setFirstDayOfWeek(parameters.getFirstDayOfWeek());
            cal.setMinimalDaysInFirstWeek(parameters.getMinDaysInFirstWeek());
        }

        return cal;
    }

    /**
     * Takes a date and return a string based on the dateFormat passed in.
     */
    public String date2String(Date x, String dateFormat) {
        SimpleDateFormat dateformat = new SimpleDateFormat(dateFormat);
        return dateformat.format(x);
    }

    /**
     * Returns a SQL date representation of the specified date.  This String can
     * be used in SQL queries.
     * @param date Date to convert to String.
     * @return A SQL date representation of the specified date.
     */
    public String date2SQLString(Date date) {
        return date2String(date, "yyyy-MM-dd");
    }

    /**
     * Returns the String value of the month of the date passed in eg. January, February, March
     * @param monthDate :- The Date who's month needs to be resolved
     * @return :- The String value of the month eg. January, February, March
     */
    @Override
    public String getMonthName(Date monthDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(monthDate);
        switch (cal.get(Calendar.MONTH)) {
            case Calendar.JANUARY:
                return "January";
            case Calendar.FEBRUARY:
                return "February";
            case Calendar.MARCH:
                return "March";
            case Calendar.APRIL:
                return "April";
            case Calendar.MAY:
                return "May";
            case Calendar.JUNE:
                return "June";
            case Calendar.JULY:
                return "July";
            case Calendar.AUGUST:
                return "August";
            case Calendar.SEPTEMBER:
                return "September";
            case Calendar.OCTOBER:
                return "October";
            case Calendar.NOVEMBER:
                return "November";
            case Calendar.DECEMBER:
                return "December";
            default:
                return "Month " + cal.get(Calendar.MONTH);
        }
    }

    @Override
    public void markCalendarAsUpdated(String calendarId, EMCUserData userData) {
        super.markCalendarAsUpdated(calendarId);
    }

    /**
     * Returns the start date of the week in which the specified date falls.
     * @param dayInWeek Date in week.
     * @param userData User data.
     * @return The start date of the week in which the specified date falls.
     */
    public Date getFirstDateOfWeek(Date dayInWeek, EMCUserData userData) {
        Calendar cal = getCalendarWithEMCSettings(userData);

        cal.setTime(dayInWeek);

        while (cal.get(Calendar.DAY_OF_WEEK) != cal.getFirstDayOfWeek()) {
            cal.add(Calendar.DATE, -1);
        }

        return cal.getTime();
    }

    /**
     * Returns the week number of the week that the specified date falls in, as
     * per EMC calendar settings.
     * @param date Date.
     * @param userData User data.
     * @return The week number of the week that the specified date falls in.
     */
    public int getWeekNumber(Date date, EMCUserData userData) {
        Calendar cal = getCalendarWithEMCSettings(userData);
        cal.setTime(date);

        return cal.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * Clears the time fields (hour, minute, second, millisecond) on the specified
     * calendar.
     * @param calendar Calendar.
     * @param userData Clear
     */
    @Override
    public void clearTimeFields(Calendar calendar, EMCUserData userData) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    /**
     * Sets all Time Fields to 0
     * @param calendar The calendar that should be changed
     * @param userData EMCUser Data
     * @return Calendar with all Time Fields set to 0
     */
    public Calendar removeTime(Calendar calendar, EMCUserData userData) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    /**
     * Get start and end dates for the weeks in the year .
     * @param fromDate Date to start calculating.
     * @param toDate Date to calculating up to.
     * @param useWeekOfYear Map Dates to 'Calander Week Of Year'.
     * @param param Base Parameters.
     * @param userData EMC User Data.
     * @return LinkedHashMap<Integer, Date[]> A Map with the week (Integer;) as key and date range (Date[]; 0 - Start; 1 - End) as Value.
     * The Map will be ordered  Start Date - End Date
     */
    @Override
    public LinkedHashMap<Integer, Date[]> getWeekRanges(Date fromDate, Date toDate, boolean useWeekOfYear, BaseParameters param, EMCUserData userData) {
        //From
        Calendar fromCal = getCalendarWithEMCSettings(userData);
        fromCal.setTime(fromDate);
        //Set to First Day Of This Week
        while (fromCal.get(Calendar.DAY_OF_WEEK) != param.getFirstDayOfWeek()) {
            fromCal.add(Calendar.DATE, -1);
        }

        //To
        Calendar toCal = getCalendarWithEMCSettings(userData);
        toCal.setTime(toDate);
        //Set to First Day Of Next Week
        while (toCal.get(Calendar.DAY_OF_WEEK) != param.getFirstDayOfWeek()) {
            toCal.add(Calendar.DATE, 1);
        }
        //Set Last Day Of This Week
        toCal.add(Calendar.DATE, -1);

        //Calculations
        Calendar calcCal = getCalendarWithEMCSettings(userData);
        calcCal.setTime(fromDate);
        //Set to First Day Of This Week
        while (calcCal.get(Calendar.DAY_OF_WEEK) != param.getFirstDayOfWeek()) {
            calcCal.add(Calendar.DATE, -1);
        }

        LinkedHashMap<Integer, Date[]> weekDateRangeMap = new LinkedHashMap<Integer, Date[]>();
        Date[] weekDateRange;
        Date weekStartDate;
        Date weekEndDate;
        Integer week;
        if (useWeekOfYear) {
            week = calcCal.get(Calendar.WEEK_OF_YEAR);
        } else {
            week = 1;
        }

        while (calcCal.before(toCal)) {
            //Set Week Start
            weekStartDate = (Date) calcCal.getTime().clone();

            //Find Week End
            //Increase Week
            calcCal.add(Calendar.WEEK_OF_YEAR, 1);
            
            //Set Last Day Of This Week
            calcCal.add(Calendar.DATE, -1);
            //Set Week End
            weekEndDate = (Date) calcCal.getTime().clone();

            //Set Week Range
            weekDateRange = new Date[2];
            weekDateRange[0] = weekStartDate;
            weekDateRange[1] = weekEndDate;

            //Set Range In Map
            weekDateRangeMap.put(week, weekDateRange);

            //Increase Week
            week++;
            if (useWeekOfYear && week > calcCal.getActualMaximum(Calendar.WEEK_OF_YEAR)) {
                week = 1;
            }
            //Set to First Day Of Next Week
            calcCal.add(Calendar.DATE, 1);
        }

        return weekDateRangeMap;
    }

    /**
     * Returns the Map Entry for the week the given dates falls in
     * @param theDate The Date to check
     * @param calendarWeeks A Map of <Integer, Date[]> (see getCalendarWeeks)
     * @param userData EMC User Data
     * @return Map.Entry<Integer, Date[]> from the Map passed to this method
     */
    @Override
    public Map.Entry<Integer, Date[]> getWeekRangeForDate(Date theDate, Map<Integer, Date[]> calendarWeeks, EMCUserData userData) {
        Set<Map.Entry<Integer, Date[]>> mapEntrySet = calendarWeeks.entrySet();

        for (Map.Entry<Integer, Date[]> entry : mapEntrySet) {
            if (compareDatesIgnoreTime(theDate, entry.getValue()[0], userData) >= 0 && compareDatesIgnoreTime(theDate, entry.getValue()[1], userData) <= 0) {
                return entry;
            }
        }

        return null;
    }

    public Date[] getFromToDateRange(Map<Integer, Date[]> weekRangeMap, EMCUserData userData) {
        Date[] fromToRange = new Date[2];

        Iterator<Integer> keysIterator = weekRangeMap.keySet().iterator();
        Integer key = null;
        Date[] weekRange;
        boolean setFrom = true;


        while (keysIterator.hasNext()) {
            key = keysIterator.next();
            weekRange = weekRangeMap.get(key);

            if (setFrom) {
                fromToRange[0] = weekRange[0];
                setFrom = false;
            }

            fromToRange[1] = weekRange[1];
        }

        return fromToRange;
    }

    @Override
    public double calculateTimeDiffInHourse(Date startDate, Date endDate, EMCUserData userData) {
        //From
        Calendar fromCal = new GregorianCalendar(TimeZone.getTimeZone(systemConstants.getTimeZone()));
        if (startDate != null) {
            fromCal.setTime(startDate);
        }
        //To
        Calendar toCal = new GregorianCalendar(TimeZone.getTimeZone(systemConstants.getTimeZone()));
        if (endDate != null) {
            toCal.setTime(endDate);
        }

        //Calculate
        int min = 0;

        while (fromCal.before(toCal)) {
            fromCal.add(Calendar.MINUTE, 1);
            min++;
        }

        double hours = (double)min / (double)60;

        return hours;
    }
}
