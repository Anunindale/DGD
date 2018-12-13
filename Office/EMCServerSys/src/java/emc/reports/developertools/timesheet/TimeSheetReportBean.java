/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.developertools.timesheet;

import emc.entity.developertools.bugtracking.DevBugsTable;
import emc.framework.EMCReportBean;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class TimeSheetReportBean extends EMCReportBean implements TimeSheetReportLocal {

    @Override
    public List<Object> manipulateQueryResult(List<Object> queryResult, java.util.Map<java.lang.String, java.lang.Object> paramMap, EMCUserData userData) {
        DevBugsTable bugsrecord;
        TimeSheetReportDS ds;
        List<Object> retList = new ArrayList<Object>();
        Calendar cal = Calendar.getInstance();
        for (Object o : queryResult) {
            bugsrecord = (DevBugsTable) o;
            ds = new TimeSheetReportDS();
            ds.setCompletedDate(Functions.date2String(bugsrecord.getCompleted()));
            ds.setDuration(bugsrecord.getTimeTaken());
            if (!isBlank(bugsrecord.getStartTime())) {
                ds.setEndTime(Functions.date2String(bugsrecord.getStartTime(), "HH:mm"));
                cal.setTime(bugsrecord.getStartTime());
                cal.add(Calendar.HOUR_OF_DAY, new Double(Math.floor(ds.getDuration())).intValue());
                cal.add(Calendar.MINUTE, new Double((ds.getDuration() - Math.floor(ds.getDuration())) * 60).intValue());
                ds.setEndTime(Functions.date2String(cal.getTime(), "HH:mm"));
            }
            ds.setDescription(bugsrecord.getBugId());
            ds.setClient(bugsrecord.getClient());
            ds.setBillable(bugsrecord.isBillable());
            retList.add(ds);
        }
        return retList;
    }
}
