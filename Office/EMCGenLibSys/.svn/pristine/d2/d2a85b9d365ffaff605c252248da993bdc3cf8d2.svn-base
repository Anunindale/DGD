/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.workflow;

import emc.datatypes.base.employeetable.foreignkeys.EmployeeIdFK;
import emc.datatypes.systemwide.Description;
import emc.datatypes.systemwide.DetailedDescription;
import emc.datatypes.workflow.activities.ActivityGroup;
import emc.datatypes.workflow.activities.ActivityId;
import emc.datatypes.workflow.activities.ActivityResult;
import emc.datatypes.workflow.activities.Billable;
import emc.datatypes.workflow.activities.Category;
import emc.datatypes.workflow.activities.ClosedBy;
import emc.datatypes.workflow.activities.ClosedByManager;
import emc.datatypes.workflow.activities.ClosedDate;
import emc.datatypes.workflow.activities.CompetionDate;
import emc.datatypes.workflow.activities.CompletionRules;
import emc.datatypes.workflow.activities.Customer;
import emc.datatypes.workflow.activities.Duration;
import emc.datatypes.workflow.activities.ElapsedWorkHours;
import emc.datatypes.workflow.activities.ExpectedCompletionDate;
import emc.datatypes.workflow.activities.ExternalNotes;
import emc.datatypes.workflow.activities.HoursWorkActual;
import emc.datatypes.workflow.activities.HoursWorkEstimated;
import emc.datatypes.workflow.activities.InternalNotes;
import emc.datatypes.workflow.activities.OutputFilePath;
import emc.datatypes.workflow.activities.OutputFileRequired;
import emc.datatypes.workflow.activities.PriorityFK;
import emc.datatypes.workflow.activities.Reference;
import emc.datatypes.workflow.activities.ReferenceSource;
import emc.datatypes.workflow.activities.RequiredCompletionDate;
import emc.datatypes.workflow.activities.ScratchPad;
import emc.datatypes.workflow.activities.SkillRequired;
import emc.datatypes.workflow.activities.StartDate;
import emc.datatypes.workflow.activities.Status;
import emc.datatypes.workflow.activities.Type;
import emc.datatypes.workflow.activitypriority.Manager;
import emc.enums.workflow.enumActivitySMSEmailState;
import emc.framework.EMCEntityClass;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import javax.persistence.Column;
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
@Table(name = "WFActivities", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"activityId", "companyId"})})
public class WorkFlowActivity extends EMCEntityClass implements Serializable {

    private String activityId;
    private String employeeNumber;
    private String managerResponsible;
    private String activityGroup;
    private String priority;
    private String type;
    private String category;
    private String status;
    private String referenceSource;
    private String reference;
    private String customer;
    private String description;
    @Column(length = 2000)
    private String detailedDescription;
    private String skillRequired;
    @Temporal(TemporalType.DATE)
    private Date requiredCompletionDate;
    @Temporal(TemporalType.DATE)
    private Date expectedCompletionDate;
    private double elapsedWorkHours;
    private double hoursWorkEstimated;
    private double hoursWorkActual;
    private boolean billable;
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Temporal(TemporalType.TIME)
    private Date startTime;
    @Temporal(TemporalType.DATE)
    private Date completionDate;
    @Temporal(TemporalType.TIME)
    private Date completionTime;
    private String completionRules;
    private boolean outputFileRequired;
    private String outputFilePath;
    private String activityResult;
    private String closedBy;
    @Temporal(TemporalType.DATE)
    private Date closedDate;
    @Temporal(TemporalType.TIME)
    private Date closedTime;
    private String internalNotes;
    private String externalNotes;
    @Column(length = 2000)
    private String scratchPad;
    @Column(name = "duration")
    private double duration;
    @Column(name = "closedByManager")
    private boolean closedByManager;
    private String sourceTable;
    private long sourceRecordId;
    //Email & SMS
    private String emailStatus = enumActivitySMSEmailState.NONE.toString();
    private String smsStatus = enumActivitySMSEmailState.NONE.toString();
    private String emailTemplate;
    private String smsTemplate;
    //Points To Record in BaseQueryTable
    private String emailRecipientQueryName;
    private String smsRecipientQueryName;
    private boolean autoCompleteActivity;
    //Activity Group Sending
    private boolean emailActivityGroup;
    private boolean smsActivityGroup;
    //For SMS & Email Testing
    @Column(length = 2000)
    private String templateQuery;
    @Column(length = 2000)
    private String recipientQuery;

    public WorkFlowActivity() {
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getManagerResponsible() {
        return managerResponsible;
    }

    public void setManagerResponsible(String managerResponsible) {
        this.managerResponsible = managerResponsible;
    }

    public String getActivityGroup() {
        return activityGroup;
    }

    public void setActivityGroup(String activityGroup) {
        this.activityGroup = activityGroup;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReferenceSource() {
        return referenceSource;
    }

    public void setReferenceSource(String referenceSource) {
        this.referenceSource = referenceSource;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSkillRequired() {
        return skillRequired;
    }

    public void setSkillRequired(String skillRequired) {
        this.skillRequired = skillRequired;
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

    public boolean isBillable() {
        return billable;
    }

    public void setBillable(boolean billable) {
        this.billable = billable;
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

    public String getCompletionRules() {
        return completionRules;
    }

    public void setCompletionRules(String completionRules) {
        this.completionRules = completionRules;
    }

    public boolean isOutputFileRequired() {
        return outputFileRequired;
    }

    public void setOutputFileRequired(boolean outputFileRequired) {
        this.outputFileRequired = outputFileRequired;
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

    public String getInternalNotes() {
        return internalNotes;
    }

    public void setInternalNotes(String internalNotes) {
        this.internalNotes = internalNotes;
    }

    public String getExternalNotes() {
        return externalNotes;
    }

    public void setExternalNotes(String externalNotes) {
        this.externalNotes = externalNotes;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getActivityResult() {
        return activityResult;
    }

    public void setActivityResult(String activityResult) {
        this.activityResult = activityResult;
    }

    public String getDetailedDescription() {
        return detailedDescription;
    }

    public void setDetailedDescription(String detailedDescription) {
        this.detailedDescription = detailedDescription;
    }

    public double getElapsedWorkHours() {
        return elapsedWorkHours;
    }

    public void setElapsedWorkHours(double elapsedWorkHours) {
        this.elapsedWorkHours = elapsedWorkHours;
    }

    public String getScratchPad() {
        return scratchPad;
    }

    public void setScratchPad(String scratchPad) {
        this.scratchPad = scratchPad;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public boolean getClosedByManager() {
        return closedByManager;
    }

    public void setClosedByManager(boolean closedByManager) {
        this.closedByManager = closedByManager;
    }

    public long getSourceRecordId() {
        return sourceRecordId;
    }

    public void setSourceRecordId(long sourceRecordId) {
        this.sourceRecordId = sourceRecordId;
    }

    public String getSourceTable() {
        return sourceTable;
    }

    public void setSourceTable(String sourceTable) {
        this.sourceTable = sourceTable;
    }

    public String getEmailStatus() {
        return emailStatus;
    }

    public void setEmailStatus(String emailStatus) {
        this.emailStatus = emailStatus;
    }

    public String getSmsStatus() {
        return smsStatus;
    }

    public void setSmsStatus(String smsStatus) {
        this.smsStatus = smsStatus;
    }

    public boolean isAutoCompleteActivity() {
        return autoCompleteActivity;
    }

    public void setAutoCompleteActivity(boolean autoCompleteActivity) {
        this.autoCompleteActivity = autoCompleteActivity;
    }

    public String getEmailRecipientQueryName() {
        return emailRecipientQueryName;
    }

    public void setEmailRecipientQueryName(String emailRecipientQueryName) {
        this.emailRecipientQueryName = emailRecipientQueryName;
    }

    public String getEmailTemplate() {
        return emailTemplate;
    }

    public void setEmailTemplate(String emailTemplate) {
        this.emailTemplate = emailTemplate;
    }

    public String getSmsRecipientQueryName() {
        return smsRecipientQueryName;
    }

    public void setSmsRecipientQueryName(String smsRecipientQueryName) {
        this.smsRecipientQueryName = smsRecipientQueryName;
    }

    public String getSmsTemplate() {
        return smsTemplate;
    }

    public void setSmsTemplate(String smsTemplate) {
        this.smsTemplate = smsTemplate;
    }

    public String getRecipientQuery() {
        return recipientQuery;
    }

    public void setRecipientQuery(String recipientQuery) {
        this.recipientQuery = recipientQuery;
    }

    public String getTemplateQuery() {
        return templateQuery;
    }

    public void setTemplateQuery(String templateQuery) {
        this.templateQuery = templateQuery;
    }

    public boolean isEmailActivityGroup() {
        return emailActivityGroup;
    }

    public void setEmailActivityGroup(boolean emailActivityGroup) {
        this.emailActivityGroup = emailActivityGroup;
    }

    public boolean isSmsActivityGroup() {
        return smsActivityGroup;
    }

    public void setSmsActivityGroup(boolean smsActivityGroup) {
        this.smsActivityGroup = smsActivityGroup;
    }

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();

        toBuild.put("employeeNumber", new EmployeeIdFK());
        toBuild.put("managerResponsible", new Manager());
        toBuild.put("priority", new PriorityFK());
        toBuild.put("description", new Description());
        toBuild.put("detailedDescription", new DetailedDescription());
        toBuild.put("activityId", new ActivityId());
        toBuild.put("activityGroup", new ActivityGroup());
        toBuild.put("type", new Type());
        toBuild.put("category", new Category());
        toBuild.put("status", new Status());
        toBuild.put("referenceSource", new ReferenceSource());
        toBuild.put("reference", new Reference());
        toBuild.put("customer", new Customer());
        toBuild.put("skillRequired", new SkillRequired());
        toBuild.put("requiredCompletionDate", new RequiredCompletionDate());
        toBuild.put("expectedCompletionDate", new ExpectedCompletionDate());
        toBuild.put("elapsedWorkHours", new ElapsedWorkHours());
        toBuild.put("hoursWorkEstimated", new HoursWorkEstimated());
        toBuild.put("hoursWorkActual", new HoursWorkActual());
        toBuild.put("billable", new Billable());
        toBuild.put("startDate", new StartDate());
        toBuild.put("completionDate", new CompetionDate());
        toBuild.put("completionRules", new CompletionRules());
        toBuild.put("outputFileRequired", new OutputFileRequired());
        toBuild.put("outputFilePath", new OutputFilePath());
        toBuild.put("activityResult", new ActivityResult());
        toBuild.put("closedBy", new ClosedBy());
        toBuild.put("closedDate", new ClosedDate());
        toBuild.put("internalNotes", new InternalNotes());
        toBuild.put("externalNotes", new ExternalNotes());
        toBuild.put("scratchPad", new ScratchPad());
        toBuild.put("duration", new Duration());
        toBuild.put("closedByManager", new ClosedByManager());

        return toBuild;
    }
}
