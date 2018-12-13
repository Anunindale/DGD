/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.base.timedoperations;

import emc.datatypes.EMCDataType;
import emc.datatypes.base.timedoperations.IdleHours;
import emc.datatypes.base.timedoperations.IdleMins;
import emc.datatypes.base.timedoperations.IdleSecs;
import emc.datatypes.base.timedoperations.LastExecutedStartDate;
import emc.datatypes.base.timedoperations.LastExecutedEndDate;
import emc.datatypes.base.timedoperations.LastExecutedEndTime;
import emc.datatypes.base.timedoperations.LastExecutedStartTime;
import emc.datatypes.base.timedoperations.Module;
import emc.datatypes.base.timedoperations.OperationId;
import emc.datatypes.base.timedoperations.StartDate;
import emc.datatypes.base.timedoperations.StartTime;
import emc.datatypes.base.timedoperations.Status;
import emc.enums.base.timedoperations.EnumTimedOperationStatus;
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
 * @author rico
 */
@Entity
@Table(name = "BaseTimedOperations", uniqueConstraints = {@UniqueConstraint(columnNames = {"operationEnumId", "companyId"})})
public class BaseTimedOperations extends EMCEntityClass {

    private String theModule;
    private String operationEnumId;
    @Temporal(TemporalType.DATE)
    private Date startExecutionDate;
    @Temporal(TemporalType.TIME)
    private Date startExecutionTime;
    private long idleDurationHour;
    private long idleDurationMin;
    private long idleDurationSec;
    private String currentStatus = EnumTimedOperationStatus.CREATED.toString();
    @Temporal(TemporalType.DATE)
    private Date lastExecutedDate;
    @Temporal(TemporalType.TIME)
    private Date lastExecutedTime;
    @Temporal(TemporalType.DATE)
    private Date lastExecutedEndDate;
    @Temporal(TemporalType.TIME)
    private Date lastExecutedEndTime;
    private Boolean fireOnStartup;

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public long getIdleDurationHour() {
        return idleDurationHour;
    }

    public void setIdleDurationHour(long idleDurationHour) {
        this.idleDurationHour = idleDurationHour;
    }

    public long getIdleDurationMin() {
        return idleDurationMin;
    }

    public void setIdleDurationMin(long idleDurationMin) {
        this.idleDurationMin = idleDurationMin;
    }

    public long getIdleDurationSec() {
        return idleDurationSec;
    }

    public void setIdleDurationSec(long idleDurationSec) {
        this.idleDurationSec = idleDurationSec;
    }

    public Date getLastExecutedDate() {
        return lastExecutedDate;
    }

    public void setLastExecutedDate(Date lastExecutedDate) {
        this.lastExecutedDate = lastExecutedDate;
    }

    public Date getLastExecutedEndDate() {
        return lastExecutedEndDate;
    }

    public void setLastExecutedEndDate(Date lastExecutedEndDate) {
        this.lastExecutedEndDate = lastExecutedEndDate;
    }

    public Date getLastExecutedEndTime() {
        return lastExecutedEndTime;
    }

    public void setLastExecutedEndTime(Date lastExecutedEndTime) {
        this.lastExecutedEndTime = lastExecutedEndTime;
    }

    public Date getLastExecutedTime() {
        return lastExecutedTime;
    }

    public void setLastExecutedTime(Date lastExecutedTime) {
        this.lastExecutedTime = lastExecutedTime;
    }

    public String getOperationEnumId() {
        return operationEnumId;
    }

    public void setOperationEnumId(String operationEnumId) {
        this.operationEnumId = operationEnumId;
    }

    public Date getStartExecutionDate() {
        return startExecutionDate;
    }

    public void setStartExecutionDate(Date startExecutionDate) {
        this.startExecutionDate = startExecutionDate;
    }

    public Date getStartExecutionTime() {
        return startExecutionTime;
    }

    public void setStartExecutionTime(Date startExecutionTime) {
        this.startExecutionTime = startExecutionTime;
    }

    public String getTheModule() {
        return theModule;
    }

    public void setTheModule(String theModule) {
        this.theModule = theModule;
    }

    public Boolean getFireOnStartup() {
        return fireOnStartup == null ? false : fireOnStartup;
    }

    public void setFireOnStartup(Boolean fireOnStartup) {
        this.fireOnStartup = fireOnStartup;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("theModule", new Module());
        toBuild.put("operationEnumId", new OperationId());
        toBuild.put("startExecutionDate", new StartDate());
        toBuild.put("startExecutionTime", new StartTime());
        toBuild.put("idleDurationHour", new IdleHours());
        toBuild.put("idleDurationMin", new IdleMins());
        toBuild.put("idleDurationSec", new IdleSecs());
        toBuild.put("currentStatus", new Status());
        toBuild.put("lastExecutedDate", new LastExecutedStartDate());
        toBuild.put("lastExecutedTime", new LastExecutedStartTime());
        toBuild.put("lastExecutedEndDate", new LastExecutedEndDate());
        toBuild.put("lastExecutedEndTime", new LastExecutedEndTime());
        return toBuild;
    }
}
