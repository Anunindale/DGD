/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.reporttools;

import emc.bus.base.parameters.BaseParametersLocal;
import emc.entity.base.BaseParameters;
import emc.entity.base.reporttools.BaseReportOrderTable;
import emc.entity.base.reporttools.BaseReportScheduling;
import emc.entity.base.reporttools.BaseReportUserQueryTable;
import emc.entity.base.reporttools.BaseReportWhereTable;
import emc.enums.base.reporttools.EnumReportScheduleRepeat;
import emc.enums.base.reporttools.EnumReportScheduleStatus;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.reporttools.ReportQueryEntityToEMCQueryConverter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class BaseReportSchedulingBean extends EMCEntityBean implements BaseReportSchedulingLocal {

    @EJB
    private BaseParametersLocal baseParamBean;

    @Override
    public boolean addScheduledReport(String reportId, String reportSelection, Date startDate, Date startTime, String repeatSchedule, String reportFormClassName, EMCUserData userData) throws EMCEntityBeanException {
        BaseReportScheduling schedule = new BaseReportScheduling();
        schedule.setReportId(reportId);
        schedule.setReportUser(userData.getUserName());
        schedule.setReportSelection(reportSelection);
        schedule.setStartDate(startDate);
        schedule.setStartTime(startTime);
        schedule.setRepeatSchedule(repeatSchedule);
        schedule.setReportFormClassName(reportFormClassName);
        schedule.setExecutionStatus(EnumReportScheduleStatus.WAITING.toString());

        insert(schedule, userData);

        return true;
    }

    @Override
    public BaseReportScheduling checkReportSchedule(EMCUserData userData) throws EMCEntityBeanException {
        BaseParameters param = baseParamBean.getBaseParameters(userData);

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseReportScheduling.class);
        query.addAnd("executionStatus", EnumReportScheduleStatus.WAITING.toString());
        List<BaseReportScheduling> scheduledReports = util.executeGeneralSelectQuery(query, userData);

        Calendar nowCal = dateHandler.getCalendarWithEMCSettings(param, userData);
        nowCal.add(Calendar.MINUTE, 1);

        Calendar cal = dateHandler.getCalendarWithEMCSettings(param, userData);
        Calendar dateCal = dateHandler.getCalendarWithEMCSettings(param, userData);
        Calendar timeCal = dateHandler.getCalendarWithEMCSettings(param, userData);

        boolean firstTimeExecution = false;

        for (BaseReportScheduling schedule : scheduledReports) {
            if (schedule.getLastExecutedDate() == null || schedule.getLastExecutedTime() == null) {
                dateCal.setTime(schedule.getStartDate());
                timeCal.setTime(schedule.getStartTime());

                firstTimeExecution = true;
            } else {
                if (schedule.getRepeatSchedule().equals(EnumReportScheduleRepeat.ONCE.toString())) {
                    //Report Already Executed Once
                    continue;
                }

                dateCal.setTime(schedule.getLastExecutedDate());
                timeCal.setTime(schedule.getLastExecutedTime());
            }

            cal.set(Calendar.YEAR, dateCal.get(Calendar.YEAR));
            cal.set(Calendar.MONTH, dateCal.get(Calendar.MONTH));
            cal.set(Calendar.DATE, dateCal.get(Calendar.DATE));
            cal.set(Calendar.HOUR, timeCal.get(Calendar.HOUR));
            cal.set(Calendar.MINUTE, timeCal.get(Calendar.MINUTE));
            cal.set(Calendar.SECOND, timeCal.get(Calendar.SECOND));

            if (!firstTimeExecution) {
                switch (EnumReportScheduleRepeat.fromString(schedule.getRepeatSchedule())) {
                    case ONCE:
                        //Do Nothing
                        break;
                    case HOURLY:
                        cal.add(Calendar.HOUR, 1);
                        break;
                    case DAILY:
                        cal.add(Calendar.DATE, 1);
                        break;
                    case WEEKLY:
                        cal.add(Calendar.DATE, 7);
                        break;
                    case MONTHLY:
                        cal.add(Calendar.MONTH, 1);
                        break;
                }
            }

            if (cal.before(nowCal)) {
                schedule.setExecutionStatus(EnumReportScheduleStatus.BUSY.toString());
                schedule = (BaseReportScheduling) update(schedule, userData);
                return schedule;
            }
        }

        return null;
    }

    @Override
    public EMCQuery buildScheduleSelectionQuery(String reportId, String userId, String userSelection, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseReportUserQueryTable.class);
        query.addAnd("reportId", reportId);
        query.addAnd("createdBy", userId);
        query.addAnd("userRecordName", userSelection);
        BaseReportUserQueryTable userQueryTable = (BaseReportUserQueryTable) util.executeSingleResultQuery(query, userData);

        query = new EMCQuery(enumQueryTypes.SELECT, BaseReportWhereTable.class);
        query.addAnd("reportQueryId", userQueryTable.getRecordID());
        List<BaseReportWhereTable> whereTableList = util.executeGeneralSelectQuery(query, userData);

        query = new EMCQuery(enumQueryTypes.SELECT, BaseReportOrderTable.class);
        query.addAnd("reportQueryId", userQueryTable.getRecordID());
        List<BaseReportOrderTable> orderTableList = util.executeGeneralSelectQuery(query, userData);

        ReportQueryEntityToEMCQueryConverter queryBuilder = new ReportQueryEntityToEMCQueryConverter();
        EMCQuery reportQuery = queryBuilder.convertEntitiesToQuery(userQueryTable, whereTableList, orderTableList);

        return reportQuery;
    }

    @Override
    public boolean updateReportSchedule(BaseReportScheduling schedule, boolean success, EMCUserData userData) throws EMCEntityBeanException {
        if (success) {
            schedule.setLastExecutedDate(Functions.nowDate());
            schedule.setLastExecutedTime(Functions.nowDate());
            schedule.setExecutionStatus(EnumReportScheduleStatus.WAITING.toString());
        } else {
            schedule.setExecutionStatus(EnumReportScheduleStatus.ERROR.toString());
        }

        update(schedule, userData);

        return true;
    }

    @Override
    public boolean logForScheduledReports(String reportId, String queryName, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseReportScheduling.class);
        query.addAnd("reportId", reportId);
        query.addAnd("reportSelection", queryName);

        if (util.exists(query, userData)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean logForScheduledReports(long userRecordId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseReportUserQueryTable.class);
        query.addAnd("recordID", userRecordId);
        query.addField("reportId");
        query.addField("userRecordName");

        Object[] selected = (Object[]) util.executeSingleResultQuery(query, userData);

        if (selected != null) {
            return logForScheduledReports((String) selected[0], (String) selected[1], userData);
        } else {
            return false;
        }
    }
}
