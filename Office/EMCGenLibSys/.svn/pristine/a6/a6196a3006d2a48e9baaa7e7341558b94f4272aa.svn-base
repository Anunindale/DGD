/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.base.calendar;

import emc.datatypes.EMCDataType;
import emc.datatypes.base.calendar.CalendarIdFK;
import emc.datatypes.base.calendar.ExceptionEndTime;
import emc.datatypes.base.calendar.ExceptionStartTime;
import emc.datatypes.base.calendar.LineDateNM;
import emc.datatypes.base.calendar.WeekDay;
import emc.datatypes.base.calendar.WorkShifts;
import emc.framework.EMCEntityClass;
import java.util.Date;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "BaseCalendarShifts")
public class BaseCalendarShifts extends EMCEntityClass {

    private String calendarId;
    @Temporal(TemporalType.DATE)
    private Date shiftDate;
    @Temporal(TemporalType.TIME)
    private Date shiftStartTime;
    @Temporal(TemporalType.TIME)
    private Date shiftEndTime;
    private int shiftNumber;
    private String dayOfWeek;
    
    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap toBuild = super.buildFieldList();
        
        toBuild.put("calendarId", new CalendarIdFK());
        toBuild.put("dayOfWeek", new WeekDay());
        toBuild.put("shiftDate", new LineDateNM());
        toBuild.put("shiftNumber", new WorkShifts());
        toBuild.put("shiftStartTime", new ExceptionStartTime());
        toBuild.put("shiftEndTime", new ExceptionEndTime());

        return toBuild;
    }

    public String getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(String calandarId) {
        this.calendarId = calandarId;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Date getShiftDate() {
        return shiftDate;
    }

    public void setShiftDate(Date shiftDate) {
        this.shiftDate = shiftDate;
    }

    public Date getShiftEndTime() {
        return shiftEndTime;
    }

    public void setShiftEndTime(Date shiftEndTime) {
        this.shiftEndTime = shiftEndTime;
    }

    public int getShiftNumber() {
        return shiftNumber;
    }

    public void setShiftNumber(int shiftNumber) {
        this.shiftNumber = shiftNumber;
    }

    public Date getShiftStartTime() {
        return shiftStartTime;
    }

    public void setShiftStartTime(Date shiftStartTime) {
        this.shiftStartTime = shiftStartTime;
    }
}
