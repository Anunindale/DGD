/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.base.reporttools;

import emc.datatypes.EMCDataType;
import emc.datatypes.base.reporttools.scheduling.RepeatSchedule;
import emc.datatypes.base.reporttools.scheduling.ReportId;
import emc.datatypes.base.reporttools.scheduling.ReportSelection;
import emc.datatypes.base.reporttools.scheduling.ReportUser;
import emc.datatypes.base.reporttools.scheduling.StartDate;
import emc.datatypes.base.reporttools.scheduling.StartTime;
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
@Table(name = "BaseReportScheduling", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"reportId", "reportUser", "reportSelection", "companyId"})})
public class BaseReportScheduling extends EMCEntityClass {

    private String reportId;
    private String reportUser;
    private String reportSelection;
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Temporal(TemporalType.TIME)
    private Date startTime;
    private String repeatSchedule;
    private String reportFormClassName;
    private String executionStatus;
    //
    @Temporal(TemporalType.DATE)
    private Date lastExecutedDate;
    @Temporal(TemporalType.TIME)
    private Date lastExecutedTime;

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getReportUser() {
        return reportUser;
    }

    public void setReportUser(String reportUser) {
        this.reportUser = reportUser;
    }

    public String getReportSelection() {
        return reportSelection;
    }

    public void setReportSelection(String reportSelection) {
        this.reportSelection = reportSelection;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getRepeatSchedule() {
        return repeatSchedule;
    }

    public void setRepeatSchedule(String repeatSchedule) {
        this.repeatSchedule = repeatSchedule;
    }

    public String getReportFormClassName() {
        return reportFormClassName;
    }

    public void setReportFormClassName(String reportFormClassName) {
        this.reportFormClassName = reportFormClassName;
    }

    public String getExecutionStatus() {
        return executionStatus;
    }

    public void setExecutionStatus(String executionStatus) {
        this.executionStatus = executionStatus;
    }

    public Date getLastExecutedDate() {
        return lastExecutedDate;
    }

    public void setLastExecutedDate(Date lastExecutedDate) {
        this.lastExecutedDate = lastExecutedDate;
    }

    public Date getLastExecutedTime() {
        return lastExecutedTime;
    }

    public void setLastExecutedTime(Date lastExecutedTime) {
        this.lastExecutedTime = lastExecutedTime;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("reportId", new ReportId());
        toBuild.put("reportUser", new ReportUser());
        toBuild.put("reportSelection", new ReportSelection());
        toBuild.put("startDate", new StartDate());
        toBuild.put("startTime", new StartTime());
        toBuild.put("repeatSchedule", new RepeatSchedule());

        return toBuild;
    }
}