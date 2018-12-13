/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.server.datehandler;

import emc.entity.base.BaseParameters;
import emc.framework.EMCUserData;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.ejb.Local;

/**
 *
 * @author wikus
 */
@Local
public interface EMCDateHandlerLocal {

    /**
     * Calculates the end date for a sertian task agains the given calendar.
     * @param calendarId The calendar to use for the calculations
     * @param startDate The date the task starts
     * @param days The amount of days the task will take
     * @param userData
     * @return The data the task will be finished.
     */
    public Date calculateEndDate(String calendarId, Date startDate, int days, EMCUserData userData);

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
    public Date calculateStartDate(String calendarId, Date endDate, int days, EMCUserData userData);

    /**
     * Compares two dates, ignoring time values.
     * @param date Date to compare.
     * @param otherDate Other date to compare.
     * @param userData User data.
     * @return An int indicating whether date is greater than (1), less than(-1) or equal(0) to otherDate.
     */
    public int compareDatesIgnoreTime(java.util.Date date, java.util.Date otherDate, emc.framework.EMCUserData userData);

    /**
     * Clears the time fields (hour, minute, second, millisecond) on the specified
     * calendar.
     * @param calendar Calendar.
     * @param userData Clear
     */
    public void clearTimeFields(Calendar calendar, EMCUserData userData);

    /**
     * Gets the difference in days between two dates.
     * DO NOT USE THIS METHOD IF DATE HAS TIME IN AS WELL IT ONLY CATERS FOR DATES NOT TIME
     * @param firstDate
     * @param secondDate
     * @param userData
     * @return
     */
    public long getDateDiffInDays(java.util.Date firstDate, java.util.Date secondDate, emc.framework.EMCUserData userData);

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
    public java.util.Date buildDate(int year, int month, int day, int hour, int minute, int second);

    /**
     * Adds or subtracts number of days from date given.
     * THIS METHOD WILL keep the time of the date passed to it.
     * @param dateToAddTo the date to start at
     * @param daysToAdd or subtract pass in negative number to return
     * @return
     */
    public long addToDate(java.util.Date dateToAddTo, int daysToAdd);

    /**
     * Takes a date and return a string with only the Time based on systemConstants + seconds.
     */
    public String date2TimeStringSeconds(Date x);

    /**
     * Returns a string with specified dateformat at time now
     */
    public String nowDateString(String dateFormat);

    /**
     * Returns the current date + time as a Date
     */
    public Date nowDate();

    public int calculateDateDiffInBusinussDays(java.lang.String calendarId, java.util.Date startDate, java.util.Date endDate, emc.framework.EMCUserData userData);

    public boolean isWorkingDay(java.lang.String calendarId, java.util.Calendar cal, emc.framework.EMCUserData userData);

    /**
     * Returns a Calendar instance using the date settings from Base parameters.
     * @param userData User data.  If no Base parameters are set up, this method
     * will return a Calender instance using the default settings.
     * @return A Calendar instance using the date settings from Base parameters.
     */
    public java.util.Calendar getCalendarWithEMCSettings(emc.framework.EMCUserData userData);

    /**
     * Returns the String value of the month of the date passed in eg. January, February, March
     * @param monthDate :- The Date who's month needs to be resolved
     * @return :- The String value of the month eg. January, February, March
     */
    public java.lang.String getMonthName(java.util.Date monthDate);

    public void markCalendarAsUpdated(java.lang.String calendarId, emc.framework.EMCUserData userData);

    /**
     * Takes a date and return a string based on the dateFormat passed in.
     */
    public String date2String(Date x, String dateFormat);

    /**
     * Returns a SQL date representation of the specified date.  This String can
     * be used in SQL queries.
     * @param date Date to convert to String.
     * @return A SQL date representation of the specified date.
     */
    public String date2SQLString(Date date);

    /**
     * Returns the system date format.  This method should be kept in sync with
     * the date format specified on the emc.constants.systemConstants class.
     * @return System date format.
     */
    public String systemDateFormat();

    /**
     * Takes a date and return a string with only the date based on systemConstants.
     */
    public String date2String(Date x);

    /**
     * Returns the start date of the week in which the specified date falls.
     * @param dayInWeek Date in week.
     * @param userData User data.
     * @return The start date of the week in which the specified date falls.
     */
    public Date getFirstDateOfWeek(Date dayInWeek, EMCUserData userData);

    /**
     * Returns the week number of the week that the specified date falls in, as
     * per EMC calendar settings.
     * @param date Date.
     * @param userData User data.
     * @return The week number of the week that the specified date falls in.
     */
    public int getWeekNumber(Date date, EMCUserData userData);

    /**
     * Calculatates the end result od adding X a,ount of hourse to the givent date. Only woring days are counted.
     * @param calendarId
     * @param startTime
     * @param hours
     * @param userData
     * @return
     */
    public java.util.Date calculateEndTime(java.lang.String calendarId, java.util.Date startTime, int hours, emc.framework.EMCUserData userData);

    /**
     * Returns a Calendar instance using the date settings from Base parameters.
     * @param parameters Base Parameters. (parameters is null, this method will return a Calender instance using the default settings.
     * @param userData EMC User data.
     * @return A Calendar instance using the date settings from Base parameters.
     * If no Base parameters are passes, this method will return a Calender instance using the default settings.
     */
    public java.util.Calendar getCalendarWithEMCSettings(emc.entity.base.BaseParameters parameters, emc.framework.EMCUserData userData);

    /**
     * Sets all Time Fields to 0
     * @param calendar The calendar that should be changed
     * @param userData EMCUser Data
     * @return Calendar with all Time Fields set to 0
     */
    public Calendar removeTime(Calendar calendar, EMCUserData userData);

    /**
     * Get start and end dates for the weeks in the year .
     * @param fromDate Date to start calculating.
     * @param toDate Date to calculating up to.
     * @param useYearWeekAsKey Map Dates to 'Calander Week Of Year'.
     * @param param Base Parameters.
     * @param userData EMC User Data.
     * @return LinkedHashMap<Integer, Date[]> A Map with the week (Integer;) as key and date range (Date[]; 0 - Start; 1 - End) as Value.
     * The Map will be ordered  Start Date - End Date
     */
    public LinkedHashMap<Integer, Date[]> getWeekRanges(Date fromDate, Date toDate, boolean useYearWeekAsKey, BaseParameters param, EMCUserData userData);

    /**
     * Returns the Map Entry for the week the given dates falls in
     * @param theDate The Date to check
     * @param calendarWeeks A Map of <Integer, Date[]> (see getCalendarWeeks)
     * @param userData EMC User Data
     * @return Map.Entry<Integer, Date[]> from the Map passed to this method
     */
    public Map.Entry<Integer, Date[]> getWeekRangeForDate(Date theDate, Map<Integer, Date[]> calendarWeeks, EMCUserData userData);

    public Date[] getFromToDateRange(Map<Integer, Date[]> weekRangeMap, EMCUserData userData);

    public double calculateTimeDiffInHourse(Date startDate, Date endDate, EMCUserData userData);
}
