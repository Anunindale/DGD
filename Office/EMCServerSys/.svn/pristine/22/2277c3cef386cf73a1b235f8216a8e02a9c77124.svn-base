/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.server.datehandler.emcbusinesscalendar;

import emc.entity.base.calendar.BaseCalendar;
import emc.entity.base.calendar.BaseCalendarExceptions;
import emc.enums.enumQueryTypes;
import emc.framework.EMCBean;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wikus
 */
public class EMCBusinessCalendarFactory extends EMCBean {

    private static Map<String, EMCBusinessCalendar> calendarWarehouse;

    public EMCBusinessCalendarFactory() {
        if (calendarWarehouse == null) {
            calendarWarehouse = Collections.synchronizedMap(new HashMap<String, EMCBusinessCalendar>());
        }
    }

    public EMCBusinessCalendar getBusinessCalendar(String calendarName, EMCUserData userData) {
        //Check Name Valid
        calendarName = Functions.checkBlank(calendarName) ? "Default_EMCBusinessCalendar" : calendarName;
        //Check Warehouse for Calendar
        EMCBusinessCalendar calendar = calendarWarehouse.get(calendarName);
        if (calendar == null) {
            //Create Calendar if not found in warehouse
            calendar = createNewCalendar(calendarName, userData);
            calendarWarehouse.put(calendarName, calendar);
        }
        //Return Calendar Instance
        return calendar.copyCalendar();
    }

    private EMCBusinessCalendar createNewCalendar(String calendarName, EMCUserData userData) {
        //Create New calendar Instance
        EMCBusinessCalendar calendar = new EMCBusinessCalendar(calendarName);
        //Fetch Base Calendat
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseCalendar.class);
        query.addAnd("calendarId", calendarName);
        query.addAnd("companyId", userData.getCompanyId());
        BaseCalendar emcCalendar = (BaseCalendar) util.executeSingleResultQuery(query, userData);
        if (emcCalendar != null) {
            //Load Work Week
            List<Integer> workWeek = new ArrayList<Integer>();
            if (emcCalendar.isMonday()) {
                workWeek.add(Calendar.MONDAY);
            }
            if (emcCalendar.isTuesday()) {
                workWeek.add(Calendar.TUESDAY);
            }
            if (emcCalendar.isWednesday()) {
                workWeek.add(Calendar.WEDNESDAY);
            }
            if (emcCalendar.isThursday()) {
                workWeek.add(Calendar.THURSDAY);
            }
            if (emcCalendar.isFriday()) {
                workWeek.add(Calendar.FRIDAY);
            }
            if (emcCalendar.isSaturday()) {
                workWeek.add(Calendar.SATURDAY);
            }
            if (emcCalendar.isSunday()) {
                workWeek.add(Calendar.SUNDAY);
            }
            calendar.setWorkWeek(workWeek);
            //Fetch Holidays
            query = new EMCQuery(enumQueryTypes.SELECT, BaseCalendarExceptions.class);
            query.addAnd("calendarId", calendarName);
            query.addField("lineDate");
            query.addField("workHours");
            List<Object[]> holidayDateList = util.executeGeneralSelectQuery(query, userData);
            List<Calendar> workDayExceptions = new ArrayList<Calendar>();
            List<Calendar> nonWorkDayExceptions = new ArrayList<Calendar>();
            Calendar c;
            for (Object[] holidayDate : holidayDateList) {
                c = Calendar.getInstance();
                c.setTime((Date) holidayDate[0]);
                if (calendar.isWeekDay(c)) {
                    if ((Double) holidayDate[1] == 0d) {
                        workDayExceptions.add(c);
                    }
                } else {
                    if ((Double) holidayDate[1] != 0d) {
                        nonWorkDayExceptions.add(c);
                    }
                }
            }
            calendar.setWorkDayExceptions(workDayExceptions);
            calendar.setNonWorkDayExceptions(nonWorkDayExceptions);
        } else {
            Logger.getLogger("emc").log(Level.WARNING, "No calendar found for the name " + calendarName, userData);

            List<Integer> workWeek = new ArrayList<Integer>();
            workWeek.add(Calendar.MONDAY);
            workWeek.add(Calendar.TUESDAY);
            workWeek.add(Calendar.WEDNESDAY);
            workWeek.add(Calendar.THURSDAY);
            workWeek.add(Calendar.FRIDAY);
            calendar.setWorkWeek(workWeek);
        }
        return calendar;
    }

    public void markCalendarAsUpdated(String calendarId) {
        calendarWarehouse.remove(calendarId);
    }
}
