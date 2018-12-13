/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.workflow;

import emc.datatypes.workflow.activtytype.foreignkeys.ActivityTypeFK;
import emc.datatypes.workflow.joblines.EmployeeId;
import emc.datatypes.systemwide.Description;
import emc.datatypes.systemwide.LineNo;
import emc.datatypes.workflow.joblines.Manager;
import emc.datatypes.workflow.joblines.Skill;
import emc.datatypes.workflow.joblines.DesignNo;
import emc.datatypes.workflow.jobs.PrimaryIndicator;
import emc.datatypes.workflow.joblines.ActivityCategory;
import emc.datatypes.workflow.joblines.ActivityGroup;
import emc.datatypes.workflow.joblines.ActivityResult;
import emc.datatypes.workflow.joblines.Billable;
import emc.datatypes.workflow.joblines.ClosedBy;
import emc.datatypes.workflow.joblines.ClosedByManager;
import emc.datatypes.workflow.joblines.ClosedDate;
import emc.datatypes.workflow.joblines.CompetionDate;
import emc.datatypes.workflow.joblines.CompletionRules;
import emc.datatypes.workflow.joblines.Duration;
import emc.datatypes.workflow.joblines.ExpectedCompletionDate;
import emc.datatypes.workflow.joblines.ExternalNotes;
import emc.datatypes.workflow.joblines.HoursWorkActual;
import emc.datatypes.workflow.joblines.HoursWorkEstimated;
import emc.datatypes.workflow.joblines.InternalNotes;
import emc.datatypes.workflow.joblines.OutputFilePath;
import emc.datatypes.workflow.joblines.OutputFileRequired;
import emc.datatypes.workflow.joblines.Reference;
import emc.datatypes.workflow.joblines.ReferenceSource;
import emc.datatypes.workflow.joblines.RequiredCompletionDate;
import emc.datatypes.workflow.joblines.StartDate;
import emc.datatypes.workflow.jobs.Status;
import emc.entity.workflow.superclass.WorkFlowSuper;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author Marius-Gert Coetzee
 */
@Entity
@Table(name = "WorkFlowJobLines", uniqueConstraints = {@UniqueConstraint(columnNames = {"designNo", "lineNo", "companyId"})})
public class WorkFlowJobLines extends WorkFlowSuper implements Serializable {

    private String designNo;
    private double lineNo;
    private double nextLineNo;
    private String status;
    private String reference;
    @Temporal(TemporalType.DATE)
    private Date requiredCompletionDate;
    @Temporal(TemporalType.DATE)
    private Date expectedCompletionDate;
    private double hoursWorkEstimated;
    private double hoursWorkActual;
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Temporal(TemporalType.TIME)
    private Date startTime;
    @Temporal(TemporalType.DATE)
    private Date completionDate;
    @Temporal(TemporalType.TIME)
    private Date completionTime;
    private String outputFilePath;
    private String jobResult;
    private String closedBy;
    @Temporal(TemporalType.DATE)
    private Date closedDate;
    @Temporal(TemporalType.TIME)
    private Date closedTime;

    public WorkFlowJobLines() {
    }

    public String getDesignNo() {
        return designNo;
    }

    public void setDesignNo(String designNo) {
        this.designNo = designNo;
    }

    public double getLineNo() {
        return lineNo;
    }

    public void setLineNo(double lineNo) {
        this.lineNo = lineNo;
    }

    public double getNextLineNo() {
        return nextLineNo;
    }

    public void setNextLineNo(double nextLineNo) {
        this.nextLineNo = nextLineNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public double getHoursWorkEstimated() {
        return hoursWorkEstimated;
    }

    public void setHoursWorkEstimated(double hoursWorkEstimated) {
        this.hoursWorkEstimated = hoursWorkEstimated;
    }

    public double getHoursWorkActual() {
        return hoursWorkActual;
    }

    public void setHoursWorkActual(double hoursWorkActual) {
        this.hoursWorkActual = hoursWorkActual;
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

    public Date getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    public Date getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(Date completionTime) {
        this.completionTime = completionTime;
    }

    public String getOutputFilePath() {
        return outputFilePath;
    }

    public void setOutputFilePath(String outputFilePath) {
        this.outputFilePath = outputFilePath;
    }

    public String getClosedBy() {
        return closedBy;
    }

    public void setClosedBy(String closedBy) {
        this.closedBy = closedBy;
    }

    public Date getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(Date closedDate) {
        this.closedDate = closedDate;
    }

    public Date getClosedTime() {
        return closedTime;
    }

    public void setClosedTime(Date closedTime) {
        this.closedTime = closedTime;
    }

    public String getJobResult() {
        return jobResult;
    }

    public void setJobResult(String jobResult) {
        this.jobResult = jobResult;
    }

    public Date getRequiredCompletionDate() {
        return requiredCompletionDate;
    }

    public void setRequiredCompletionDate(Date requiredCompletionDate) {
        this.requiredCompletionDate = requiredCompletionDate;
    }

    public Date getExpectedCompletionDate() {
        return expectedCompletionDate;
    }

    public void setExpectedCompletionDate(Date expectedCompletionDate) {
        this.expectedCompletionDate = expectedCompletionDate;
    }

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();
        toBuild.put("lineNo", new LineNo());
        toBuild.put("nextLineNo", new LineNo());
        toBuild.put("primaryIndicator", new PrimaryIndicator());
        toBuild.put("status", new Status());
        toBuild.put("descriptionOfActivity", new Description());
        toBuild.put("designNo", new DesignNo());
        toBuild.put("activityCategory", new ActivityCategory());
        toBuild.put("activityType", new ActivityTypeFK());
        toBuild.put("skill", new Skill());
        toBuild.put("employeeId", new EmployeeId());
        toBuild.put("managerResponsible", new Manager());
        toBuild.put("activityGroup", new ActivityGroup());
        toBuild.put("referenceSource", new ReferenceSource());
        toBuild.put("reference", new Reference());
        toBuild.put("requiredCompletionDate", new RequiredCompletionDate());
        toBuild.put("expectedCompletionDate", new ExpectedCompletionDate());
        toBuild.put("duration", new Duration());
        toBuild.put("hoursWorkEstimated", new HoursWorkEstimated());
        toBuild.put("hoursWorkActual", new HoursWorkActual());
        toBuild.put("billable", new Billable());
        toBuild.put("startDate", new StartDate());
        toBuild.put("completionDate", new CompetionDate());
        toBuild.put("completionRules", new CompletionRules());
        toBuild.put("outputFileRequired", new OutputFileRequired());
        toBuild.put("outputFilePath", new OutputFilePath());
        toBuild.put("jobResult", new ActivityResult());
        toBuild.put("closedBy", new ClosedBy());
        toBuild.put("closedDate", new ClosedDate());
        toBuild.put("internalNotes", new InternalNotes());
        toBuild.put("externalNotes", new ExternalNotes());
        toBuild.put("closedByManager", new ClosedByManager());
        return toBuild;
    }
}
