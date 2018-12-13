/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.reporttools;

import emc.framework.EMCEntityBeanLocalInterface;
import javax.ejb.Local;

/**
 *
 * @author wikus
 */
@Local
public interface BaseReportSchedulingLocal extends EMCEntityBeanLocalInterface {

    public boolean addScheduledReport(java.lang.String reportId, java.lang.String reportSelection, java.util.Date startDate, java.util.Date startTime, java.lang.String repeatSchedule, java.lang.String reportFormClassName, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    public emc.entity.base.reporttools.BaseReportScheduling checkReportSchedule(emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    public emc.framework.EMCQuery buildScheduleSelectionQuery(java.lang.String reportId, java.lang.String userId, java.lang.String userSelection, emc.framework.EMCUserData userData);

    public boolean updateReportSchedule(emc.entity.base.reporttools.BaseReportScheduling schedule, boolean success, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    public boolean logForScheduledReports(java.lang.String reportId, java.lang.String queryName, emc.framework.EMCUserData userData);

    public boolean logForScheduledReports(long userRecordId, emc.framework.EMCUserData userData);
}
