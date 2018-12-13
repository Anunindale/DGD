/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.workflow.superclass;

import emc.framework.EMCEntityClass;
import javax.persistence.Column;
import javax.persistence.Entity;

/**
 *
 * @author wikus
 */
@Entity
public class WorkFlowSuper extends EMCEntityClass {

    private String primaryIndicator;
    private String activityCategory;
    private String activityType;
    private String skill;
    private String employeeId;
    private String managerResponsible;
    private String activityGroup;
    private String referenceSource;
    private String descriptionOfActivity;
    private double duration;
    private boolean billable;
    private String completionRules;
    private boolean outputFileRequired;
    private String internalNotes;
    private String externalNotes;
    @Column(name = "closedByManager")
    private boolean closedByManager;
    private double leadTime;
    //Email
    private boolean sendEmail;
    private String emailTemplate;
    //Points To Record in BaseQueryTable
    private String emailRecipientQueryName;
    private boolean sendActivityGroupEmail;
    private String activityGroupEmailTemplate;
    private boolean autoCompleteEmailActivity;
    //SMS
    private boolean sendSms;
    private String smsTemplate;
    //Points To Record in BaseQueryTable
    private String smsRecipientQueryName;
    private boolean sendActivityGroupSms;
    private String activityGroupSmsTemplate;
    private boolean autoCompleteSMSActivity;

    public WorkFlowSuper() {
    }

    public String getActivityCategory() {
        return activityCategory;
    }

    public void setActivityCategory(String activityCategory) {
        this.activityCategory = activityCategory;
    }

    public String getActivityGroup() {
        return activityGroup;
    }

    public void setActivityGroup(String activityGroup) {
        this.activityGroup = activityGroup;
    }

    public String getActivityGroupEmailTemplate() {
        return activityGroupEmailTemplate;
    }

    public void setActivityGroupEmailTemplate(String activityGroupEmailTemplate) {
        this.activityGroupEmailTemplate = activityGroupEmailTemplate;
    }

    public String getActivityGroupSmsTemplate() {
        return activityGroupSmsTemplate;
    }

    public void setActivityGroupSmsTemplate(String activityGroupSmsTemplate) {
        this.activityGroupSmsTemplate = activityGroupSmsTemplate;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public boolean isAutoCompleteEmailActivity() {
        return autoCompleteEmailActivity;
    }

    public void setAutoCompleteEmailActivity(boolean autoCompleteEmailActivity) {
        this.autoCompleteEmailActivity = autoCompleteEmailActivity;
    }

    public boolean isAutoCompleteSMSActivity() {
        return autoCompleteSMSActivity;
    }

    public void setAutoCompleteSMSActivity(boolean autoCompleteSMSActivity) {
        this.autoCompleteSMSActivity = autoCompleteSMSActivity;
    }

    public boolean isBillable() {
        return billable;
    }

    public void setBillable(boolean billable) {
        this.billable = billable;
    }

    public boolean getClosedByManager() {
        return closedByManager;
    }

    public void setClosedByManager(boolean closedByManager) {
        this.closedByManager = closedByManager;
    }

    public String getCompletionRules() {
        return completionRules;
    }

    public void setCompletionRules(String completionRules) {
        this.completionRules = completionRules;
    }

    public String getDescriptionOfActivity() {
        return descriptionOfActivity;
    }

    public void setDescriptionOfActivity(String descriptionOfActivity) {
        this.descriptionOfActivity = descriptionOfActivity;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
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

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getExternalNotes() {
        return externalNotes;
    }

    public void setExternalNotes(String externalNotes) {
        this.externalNotes = externalNotes;
    }

    public String getInternalNotes() {
        return internalNotes;
    }

    public void setInternalNotes(String internalNotes) {
        this.internalNotes = internalNotes;
    }

    public double getLeadTime() {
        return leadTime;
    }

    public void setLeadTime(double leadTime) {
        this.leadTime = leadTime;
    }

    public String getManagerResponsible() {
        return managerResponsible;
    }

    public void setManagerResponsible(String managerResponsible) {
        this.managerResponsible = managerResponsible;
    }

    public boolean isOutputFileRequired() {
        return outputFileRequired;
    }

    public void setOutputFileRequired(boolean outputFileRequired) {
        this.outputFileRequired = outputFileRequired;
    }

    public String getPrimaryIndicator() {
        return primaryIndicator;
    }

    public void setPrimaryIndicator(String primaryIndicator) {
        this.primaryIndicator = primaryIndicator;
    }

    public String getReferenceSource() {
        return referenceSource;
    }

    public void setReferenceSource(String referenceSource) {
        this.referenceSource = referenceSource;
    }

    public boolean isSendActivityGroupEmail() {
        return sendActivityGroupEmail;
    }

    public void setSendActivityGroupEmail(boolean sendActivityGroupEmail) {
        this.sendActivityGroupEmail = sendActivityGroupEmail;
    }

    public boolean isSendActivityGroupSms() {
        return sendActivityGroupSms;
    }

    public void setSendActivityGroupSms(boolean sendActivityGroupSms) {
        this.sendActivityGroupSms = sendActivityGroupSms;
    }

    public boolean isSendEmail() {
        return sendEmail;
    }

    public void setSendEmail(boolean sendEmail) {
        this.sendEmail = sendEmail;
    }

    public boolean isSendSms() {
        return sendSms;
    }

    public void setSendSms(boolean sendSms) {
        this.sendSms = sendSms;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
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
}
