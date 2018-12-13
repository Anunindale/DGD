/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.base.calendar;

import emc.datatypes.EMCDataType;
import emc.datatypes.base.calendar.CalendarHour;
import emc.datatypes.base.calendar.CalendarIdFK;
import emc.datatypes.base.calendar.CalendarMin;
import emc.datatypes.base.calendar.ExceptionTypeFK;
import emc.datatypes.base.calendar.LineDate;
import emc.datatypes.base.calendar.PercOfTarget;
import emc.datatypes.base.calendar.WorkHours;
import emc.datatypes.base.calendar.WorkShifts;
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
@Table(name = "BaseCalendarExceptions", uniqueConstraints = {@UniqueConstraint(columnNames = {"calendarId", "lineDate", "companyId"})})
public class BaseCalendarExceptions extends EMCEntityClass {

    private String calendarId;
    @Temporal(TemporalType.DATE)
    private Date lineDate;
    private boolean repeatYearly;
    private double workHours;
    private int numberOfShifts;
    private String type;
    private int hours;
    private int minutes;
    private double percentageOfTarget;

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap toBuild = super.buildFieldList();

        toBuild.put("calendarId", new CalendarIdFK());
        toBuild.put("workHours", new WorkHours());
        toBuild.put("numberOfShifts", new WorkShifts());
        toBuild.put("type", new ExceptionTypeFK());
        toBuild.put("lineDate", new LineDate());
        toBuild.put("hours", new CalendarHour());
        toBuild.put("minutes", new CalendarMin());
        toBuild.put("percentageOfTarget", new PercOfTarget());

        return toBuild;
    }

    public String getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(String calendarId) {
        this.calendarId = calendarId;
    }

    public Date getLineDate() {
        return lineDate;
    }

    public void setLineDate(Date lineDate) {
        this.lineDate = lineDate;
    }

    public int getNumberOfShifts() {
        return numberOfShifts;
    }

    public void setNumberOfShifts(int numberOfShifts) {
        this.numberOfShifts = numberOfShifts;
    }

    public boolean isRepeatYearly() {
        return repeatYearly;
    }

    public void setRepeatYearly(boolean repeatYearly) {
        this.repeatYearly = repeatYearly;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getWorkHours() {
        return workHours;
    }

    public void setWorkHours(double workHours) {
        this.workHours = workHours;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hour) {
        this.hours = hour;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public double getPercentageOfTarget() {
        return percentageOfTarget;
    }

    public void setPercentageOfTarget(double percentageOfTarget) {
        this.percentageOfTarget = percentageOfTarget;
    }
}

