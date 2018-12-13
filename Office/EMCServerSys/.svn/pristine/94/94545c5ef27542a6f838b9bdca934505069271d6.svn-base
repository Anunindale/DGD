/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.developertools.timesheet;

import emc.bus.developertools.bugtracking.DevBugsTableLocal;
import emc.entity.developertools.DevTimeSheet;
import emc.entity.developertools.bugtracking.DevBugsTable;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.helpers.devtools.DevTimeSheetTaskHelper;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class DevTimeSheetBean extends EMCEntityBean implements DevTimeSheetLocal {

    @EJB
    private DevBugsTableLocal taskBean;

    @Override
    public List<DevBugsTable> fetchPosibleMatchingTasks(DevTimeSheet timeSheet, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DevBugsTable.class);
        if (timeSheet.getTaskRecordId() == 0) {
            query.addAnd("summary", timeSheet.getTaskSummary());
            //query.addAnd("responsible", timeSheet.getEmployeeId());
        } else {
            query.addAnd("recordID", timeSheet.getTaskRecordId());
        }
        query.addAnd("completed", null, EMCQueryConditions.IS_NULL);

        return util.executeGeneralSelectQuery(query, userData);
    }

    @Override
    public long saveTask(DevBugsTable task, EMCUserData userData) throws EMCEntityBeanException {
        if (task.getRecordID() == 0) {
            taskBean.insert(task, userData);
        } else {
            taskBean.update(task, userData);
        }

        return task.getRecordID();
    }

    @Override
    public boolean completeTask(DevTimeSheet timeSheet, DevTimeSheetTaskHelper helper, EMCUserData userData) throws EMCEntityBeanException {
        updateTaskTime(timeSheet.getTaskRecordId(), userData);

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DevBugsTable.class);
        query.addAnd("recordID", timeSheet.getTaskRecordId());
        DevBugsTable task = (DevBugsTable) util.executeSingleResultQuery(query, userData);

        if (task == null) {
            Logger.getLogger("emc").log(Level.SEVERE, "Failed to find task for time sheet.");
            return false;
        }

        task.setCompleted(timeSheet.getWorkDate());
        task.setBillable(helper.isBillable());
        task.setTested(true);
        task.setTestedBy(helper.getTestedBy());
        task.setTestDescription(helper.getTest());
        task.setCoreUpdates(helper.getCoreUpdates());
        task.setPossibleAffected(helper.getAffected());
        task.setPossibleAffectedData(helper.getAffectedData());
        task.setEntityLog(helper.getEntityLog());
        task.setDocumentation(helper.getDocumentation());
        taskBean.update(task, userData);

        return true;
    }

    @Override
    public Object insert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        DevTimeSheet timeSheet = (DevTimeSheet) super.insert(iobject, userData);

        updateTaskTime(timeSheet.getTaskRecordId(), userData);

        return timeSheet;
    }

    @Override
    public Object update(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        DevTimeSheet timeSheet = (DevTimeSheet) super.update(uobject, userData);

        updateTaskTime(timeSheet.getTaskRecordId(), userData);

        return timeSheet;
    }

    public boolean updateTaskTime(long taskRecordID, EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DevBugsTable.class);
        query.addAnd("recordID", taskRecordID);
        DevBugsTable task = (DevBugsTable) util.executeSingleResultQuery(query, userData);

        if (task == null) {
            Logger.getLogger("emc").log(Level.SEVERE, "Failed to find task for time sheet.");
            return false;
        }

        query = new EMCQuery(enumQueryTypes.SELECT, DevTimeSheet.class);
        query.addAnd("taskRecordId", taskRecordID);
        query.addField("workStartTime");
        query.addField("workEndTime");
        query.addOrderBy("workDate");
        query.addOrderBy("workStartTime");
        query.addOrderBy("workEndTime");

        List<Object[]> allTimeSheets = util.executeGeneralSelectQuery(query, userData);

        Date startTime = null;
        double timeTaken = 0;

        for (Object[] selectedTimeSheet : allTimeSheets) {
            if (startTime == null) {
                startTime = (Date) selectedTimeSheet[0];
            }

            timeTaken += dateHandler.calculateTimeDiffInHourse((Date) selectedTimeSheet[0], (Date) selectedTimeSheet[1], userData);
        }

        task.setStartTime(startTime);
        task.setTimeTaken(timeTaken);
        taskBean.update(task, userData);

        return true;
    }

    @Override
    public Date getLastTaskEndTime(Date atDate, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DevTimeSheet.class);
        query.addAnd("workDate", atDate);
        query.addFieldAggregateFunction("workEndTime", "MAX");

        Date endTime = (Date) util.executeSingleResultQuery(query, userData);

        if (endTime == null) {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, 8);
            cal.set(Calendar.MINUTE, 30);

            endTime = cal.getTime();
        }

        return endTime;
    }

}
