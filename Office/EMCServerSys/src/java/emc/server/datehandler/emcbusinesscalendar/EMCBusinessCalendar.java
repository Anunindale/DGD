/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.server.datehandler.emcbusinesscalendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author wikus
 */
public class EMCBusinessCalendar {

    private String calendarName;
    private List<Calendar> workDayExceptions;
    private List<Calendar> nonWorkDayExceptions;
    private List<Integer> workWeek;

    public EMCBusinessCalendar(String calendarName) {
        this.calendarName = calendarName;
        this.workDayExceptions = new ArrayList<Calendar>();
        this.nonWorkDayExceptions = new ArrayList<Calendar>();
        this.workWeek = new ArrayList<Integer>();
    }

    protected void setNonWorkDayExceptions(List<Calendar> nonWorkDayExceptions) {
        this.nonWorkDayExceptions = nonWorkDayExceptions;
    }

    protected void setWorkDayExceptions(List<Calendar> workDayExceptions) {
        this.workDayExceptions = workDayExceptions;
    }

    protected void setWorkWeek(List<Integer> workWeek) {
        this.workWeek = workWeek;
    }

    public boolean isWorkingDay(Calendar date) {
        Calendar copyDate = setTimeFieldToZero((Calendar) date.clone());
        if (!workWeek.contains(copyDate.get(Calendar.DAY_OF_WEEK))) {
            for (Calendar holiday : nonWorkDayExceptions) {
                holiday = setTimeFieldToZero(holiday);
                if (holiday.getTime().equals(copyDate.getTime())) {
                    return true;
                }
            }
            return false;
        }
        for (Calendar holiday : workDayExceptions) {
            holiday = setTimeFieldToZero(holiday);
            if (holiday.getTime().equals(copyDate.getTime())) {
                return false;
            }
        }
        return true;
    }

    public boolean isNonWorkingDay(Calendar date) {
        Calendar copyDate = setTimeFieldToZero((Calendar) date.clone());
        if (workWeek.contains(copyDate.get(Calendar.DAY_OF_WEEK))) {
            for (Calendar holiday : workDayExceptions) {
                holiday = setTimeFieldToZero(holiday);
                if (holiday.getTime().equals(copyDate.getTime())) {
                    return true;
                }
            }
            return false;
        }
        for (Calendar holiday : nonWorkDayExceptions) {
            holiday = setTimeFieldToZero(holiday);
            if (holiday.getTime().equals(copyDate.getTime())) {
                return false;
            }
        }
        return true;
    }

    public boolean isWeekDay(Calendar date) {
        Calendar copyDate = setTimeFieldToZero((Calendar) date.clone());
        if (workWeek.contains(copyDate.get(Calendar.DAY_OF_WEEK))) {
            return true;
        }
        return false;
    }

    public boolean isWeekEndDay(Calendar date) {
        Calendar copyDate = setTimeFieldToZero((Calendar) date.clone());
        if (workWeek.contains(copyDate.get(Calendar.DAY_OF_WEEK))) {
            return false;
        }
        return true;
    }

    protected EMCBusinessCalendar copyCalendar() {
        EMCBusinessCalendar calendar = new EMCBusinessCalendar(this.calendarName);
        calendar.setWorkWeek(this.workWeek);
        calendar.setNonWorkDayExceptions(this.nonWorkDayExceptions);
        calendar.setWorkDayExceptions(this.workDayExceptions);
        return calendar;
    }

    private Calendar setTimeFieldToZero(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " : " + calendarName;
    }
}
