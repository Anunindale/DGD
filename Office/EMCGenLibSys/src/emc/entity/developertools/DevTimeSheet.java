/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.developertools;

import emc.datatypes.EMCDataType;
import emc.datatypes.base.employeetable.foreignkeys.EmployeeIdFK;
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
@Table(name="DevTimeSheet")
public class DevTimeSheet extends EMCEntityClass {

    private String employeeId;
    private long taskRecordId;
    private String taskSummary;
    private String taskDetail;
    @Temporal(TemporalType.DATE)
    private Date workDate;
    @Temporal(TemporalType.TIME)
    private Date workStartTime;
    @Temporal(TemporalType.TIME)
    private Date workEndTime;

    public DevTimeSheet() {
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public long getTaskRecordId() {
        return taskRecordId;
    }

    public void setTaskRecordId(long taskRecordId) {
        this.taskRecordId = taskRecordId;
    }

    public String getTaskDetail() {
        return taskDetail;
    }

    public void setTaskDetail(String taskDetail) {
        this.taskDetail = taskDetail;
    }

    public String getTaskSummary() {
        return taskSummary;
    }

    public void setTaskSummary(String taskSummary) {
        this.taskSummary = taskSummary;
    }

    public Date getWorkDate() {
        return workDate;
    }

    public void setWorkDate(Date workDate) {
        this.workDate = workDate;
    }

    public Date getWorkEndTime() {
        return workEndTime;
    }

    public void setWorkEndTime(Date workEndTime) {
        this.workEndTime = workEndTime;
    }

    public Date getWorkStartTime() {
        return workStartTime;
    }

    public void setWorkStartTime(Date workStartTime) {
        this.workStartTime = workStartTime;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
         HashMap<String, EMCDataType> toBuild = super.buildFieldList();
         toBuild.put("employeeId", new EmployeeIdFK());
         return toBuild;
    }


}
