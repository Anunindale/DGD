/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.calendar;

import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import java.util.Date;
import javax.ejb.Local;

/**
 *
 * @author wikus
 */
@Local
public interface BaseCalendarLocal extends EMCEntityBeanLocalInterface {

    public void copyCalendar(String calendarToCopy, String newCalendarName, EMCUserData userData);

    /**
     * Get the work hours for the given day on the given calendar
     * @param calendarId
     * @param day
     * @param userData
     * @return
     */
    @Deprecated
    public double findWorkingHours(String calendarId, int day, EMCUserData userData);

    /**
     * Finds the hours worked for the given calendar
     * @param calendarId The calendar to check
     * @param dateFrom The date wich to start from
     * @param dateTo The date to end at
     * @param checkExceptions Should the hours reflect the exceptions on the calendar
     * @param userData
     * @return the hours worked
     */
    public double findWorkingHours(String calendarId, Date dateFrom, Date dateTo, boolean checkExceptions, EMCUserData userData);

    public double findExceptionHours(String calendarId, String exception, Date dateFrom, Date dateTo, EMCUserData userData);

    public void testCalendar(String cal, Date theDate, int toShift, EMCUserData userData);

    public double findWorkingHours(java.sql.Connection conn, java.lang.String calendarId, java.util.Date dateFrom, java.util.Date dateTo, boolean checkExceptions, emc.framework.EMCUserData userData);
}
