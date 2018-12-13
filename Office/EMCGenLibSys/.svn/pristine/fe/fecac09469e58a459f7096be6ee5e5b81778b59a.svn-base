/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.base.calendar;

import emc.datatypes.EMCDataType;
import emc.datatypes.base.calendar.CalendarHour;
import emc.datatypes.base.calendar.CalendarId;
import emc.datatypes.base.calendar.CalendarMin;
import emc.datatypes.base.calendar.DayActive;
import emc.datatypes.base.calendar.WorkHours;
import emc.datatypes.base.calendar.WorkShifts;
import emc.datatypes.base.country.CountryFKNM;
import emc.datatypes.systemwide.Description;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "BaseCalendar", uniqueConstraints = {@UniqueConstraint(columnNames = {"calendarId", "companyId"})})
public class BaseCalendar extends EMCEntityClass {

    private String calendarId;
    private String description;
    private boolean monday;
    private boolean tuesday;
    private boolean wednesday;
    private boolean thursday;
    private boolean friday;
    private boolean saturday;
    private boolean sunday;
    private double stdworkHoursMon;
    private double stdworkHoursTue;
    private double stdworkHoursWed;
    private double stdworkHoursThu;
    private double stdworkHoursFri;
    private double stdworkHoursSat;
    private double stdworkHoursSun;
    private int stdShiftsMon;
    private int stdShiftsTue;
    private int stdShiftsWed;
    private int stdShiftsThu;
    private int stdShiftsFri;
    private int stdShiftsSat;
    private int stdShiftsSun;
    private String country;
    private int minMon;
    private int minTue;
    private int minWed;
    private int minThu;
    private int minsFri;
    private int minSat;
    private int minSun;
    private int hourMon;
    private int hourTue;
    private int hourWed;
    private int hourThu;
    private int hourFri;
    private int hourSat;
    private int hourSun;

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap toBuild = super.buildFieldList();

        toBuild.put("calendarId", new CalendarId());
        toBuild.put("description", new Description());
        toBuild.put("monday", new DayActive());
        toBuild.put("tuesday", new DayActive());
        toBuild.put("wednesday", new DayActive());
        toBuild.put("thursday", new DayActive());
        toBuild.put("friday", new DayActive());
        toBuild.put("saturday", new DayActive());
        toBuild.put("sunday", new DayActive());
        toBuild.put("stdworkHoursMon", new WorkHours());
        toBuild.put("stdworkHoursTue", new WorkHours());
        toBuild.put("stdworkHoursWed", new WorkHours());
        toBuild.put("stdworkHoursThu", new WorkHours());
        toBuild.put("stdworkHoursFri", new WorkHours());
        toBuild.put("stdworkHoursSat", new WorkHours());
        toBuild.put("stdworkHoursSun", new WorkHours());
        toBuild.put("stdShiftsMon", new WorkShifts());
        toBuild.put("stdShiftsTue", new WorkShifts());
        toBuild.put("stdShiftsWed", new WorkShifts());
        toBuild.put("stdShiftsThu", new WorkShifts());
        toBuild.put("stdShiftsFri", new WorkShifts());
        toBuild.put("stdShiftsSat", new WorkShifts());
        toBuild.put("stdShiftsSun", new WorkShifts());
        toBuild.put("country", new CountryFKNM());

        toBuild.put("minMon", new CalendarMin());
        toBuild.put("minTue", new CalendarMin());
        toBuild.put("minWed", new CalendarMin());
        toBuild.put("minThu", new CalendarMin());
        toBuild.put("minsFri", new CalendarMin());
        toBuild.put("minSat", new CalendarMin());
        toBuild.put("minSun", new CalendarMin());

        toBuild.put("hourMon", new CalendarHour());
        toBuild.put("hourTue", new CalendarHour());
        toBuild.put("hourWed", new CalendarHour());
        toBuild.put("hourThu", new CalendarHour());
        toBuild.put("hourFri", new CalendarHour());
        toBuild.put("hourSat", new CalendarHour());
        toBuild.put("hourSun", new CalendarHour());

        return toBuild;
    }

    public String getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(String calendarId) {
        this.calendarId = calendarId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFriday() {
        return friday;
    }

    public void setFriday(boolean friday) {
        this.friday = friday;
    }

    public boolean isMonday() {
        return monday;
    }

    public void setMonday(boolean monday) {
        this.monday = monday;
    }

    public boolean isSaturday() {
        return saturday;
    }

    public void setSaturday(boolean saturday) {
        this.saturday = saturday;
    }

    public int getStdShiftsFri() {
        return stdShiftsFri;
    }

    public void setStdShiftsFri(int stdShiftsFri) {
        this.stdShiftsFri = stdShiftsFri;
    }

    public int getStdShiftsMon() {
        return stdShiftsMon;
    }

    public void setStdShiftsMon(int stdShiftsMon) {
        this.stdShiftsMon = stdShiftsMon;
    }

    public int getStdShiftsSat() {
        return stdShiftsSat;
    }

    public void setStdShiftsSat(int stdShiftsSat) {
        this.stdShiftsSat = stdShiftsSat;
    }

    public int getStdShiftsSun() {
        return stdShiftsSun;
    }

    public void setStdShiftsSun(int stdShiftsSun) {
        this.stdShiftsSun = stdShiftsSun;
    }

    public int getStdShiftsWed() {
        return stdShiftsWed;
    }

    public void setStdShiftsWed(int stdShiftsWed) {
        this.stdShiftsWed = stdShiftsWed;
    }

    public double getStdworkHoursFri() {
        return stdworkHoursFri;
    }

    public void setStdworkHoursFri(double stdworkHoursFri) {
        this.stdworkHoursFri = stdworkHoursFri;
    }

    public double getStdworkHoursMon() {
        return stdworkHoursMon;
    }

    public void setStdworkHoursMon(double stdworkHoursMon) {
        this.stdworkHoursMon = stdworkHoursMon;
    }

    public double getStdworkHoursSat() {
        return stdworkHoursSat;
    }

    public void setStdworkHoursSat(double stdworkHoursSat) {
        this.stdworkHoursSat = stdworkHoursSat;
    }

    public double getStdworkHoursWed() {
        return stdworkHoursWed;
    }

    public void setStdworkHoursWed(double stdworkHoursWed) {
        this.stdworkHoursWed = stdworkHoursWed;
    }

    public boolean isSunday() {
        return sunday;
    }

    public void setSunday(boolean sunday) {
        this.sunday = sunday;
    }

    public boolean isThursday() {
        return thursday;
    }

    public void setThursday(boolean thursday) {
        this.thursday = thursday;
    }

    public boolean isTuesday() {
        return tuesday;
    }

    public void setTuesday(boolean tuesday) {
        this.tuesday = tuesday;
    }

    public boolean isWednesday() {
        return wednesday;
    }

    public void setWednesday(boolean wednesday) {
        this.wednesday = wednesday;
    }

    public int getStdShiftsThu() {
        return stdShiftsThu;
    }

    public void setStdShiftsThu(int stdShiftsThu) {
        this.stdShiftsThu = stdShiftsThu;
    }

    public int getStdShiftsTue() {
        return stdShiftsTue;
    }

    public void setStdShiftsTue(int stdShiftsTue) {
        this.stdShiftsTue = stdShiftsTue;
    }

    public double getStdworkHoursSun() {
        return stdworkHoursSun;
    }

    public void setStdworkHoursSun(double stdworkHoursSun) {
        this.stdworkHoursSun = stdworkHoursSun;
    }

    public double getStdworkHoursThu() {
        return stdworkHoursThu;
    }

    public void setStdworkHoursThu(double stdworkHoursThu) {
        this.stdworkHoursThu = stdworkHoursThu;
    }

    public double getStdworkHoursTue() {
        return stdworkHoursTue;
    }

    public void setStdworkHoursTue(double stdworkHoursTue) {
        this.stdworkHoursTue = stdworkHoursTue;
    }

    public int getHourFri() {
        return hourFri;
    }

    public void setHourFri(int hourFri) {
        this.hourFri = hourFri;
    }

    public int getHourMon() {
        return hourMon;
    }

    public void setHourMon(int hourMon) {
        this.hourMon = hourMon;
    }

    public int getHourSat() {
        return hourSat;
    }

    public void setHourSat(int hourSat) {
        this.hourSat = hourSat;
    }

    public int getHourSun() {
        return hourSun;
    }

    public void setHourSun(int hourSun) {
        this.hourSun = hourSun;
    }

    public int getHourThu() {
        return hourThu;
    }

    public void setHourThu(int hourThu) {
        this.hourThu = hourThu;
    }

    public int getHourTue() {
        return hourTue;
    }

    public void setHourTue(int hourTue) {
        this.hourTue = hourTue;
    }

    public int getHourWed() {
        return hourWed;
    }

    public void setHourWed(int hourWed) {
        this.hourWed = hourWed;
    }

    public int getMinMon() {
        return minMon;
    }

    public void setMinMon(int minMon) {
        this.minMon = minMon;
    }

    public int getMinSat() {
        return minSat;
    }

    public void setMinSat(int minSat) {
        this.minSat = minSat;
    }

    public int getMinSun() {
        return minSun;
    }

    public void setMinSun(int minSun) {
        this.minSun = minSun;
    }

    public int getMinThu() {
        return minThu;
    }

    public void setMinThu(int minThu) {
        this.minThu = minThu;
    }

    public int getMinTue() {
        return minTue;
    }

    public void setMinTue(int minTue) {
        this.minTue = minTue;
    }

    public int getMinWed() {
        return minWed;
    }

    public void setMinWed(int minWed) {
        this.minWed = minWed;
    }

    public int getMinsFri() {
        return minsFri;
    }

    public void setMinsFri(int minsFri) {
        this.minsFri = minsFri;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("calendarId");
        toBuild.add("description");
        return toBuild;
    }
}
