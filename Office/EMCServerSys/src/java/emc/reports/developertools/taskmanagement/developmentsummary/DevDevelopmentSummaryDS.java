/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.developertools.taskmanagement.developmentsummary;

/**
 *
 * @author wikus
 */
public class DevDevelopmentSummaryDS {

    private String currentDate;
    private String client;
    private String requiredDate;
    private double estimatedHours;
    private String requirement;
    private String affectedAreas;
    private String affectedData;
    private String developer;
    private double actualHours;
    private String entityLogs;
    private String testing;
    private String documentation;
    private String billable;
    private String taskNo;
    private String completedDate;
    private String coreUpdates;

    public String getAffectedAreas() {
        return this.affectedAreas;
    }

    public void setAffectedAreas(String affectedAreas) {
        this.affectedAreas = affectedAreas;
    }

    public String getAffectedData() {
        return this.affectedData;
    }

    public void setAffectedData(String affectedData) {
        this.affectedData = affectedData;
    }

    public String getClient() {
        return this.client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getCurrentDate() {
        return this.currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getDeveloper() {
        return this.developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public double getEstimatedHours() {
        return this.estimatedHours;
    }

    public void setEstimatedHours(double estimatedHours) {
        this.estimatedHours = estimatedHours;
    }

    public String getRequiredDate() {
        return this.requiredDate;
    }

    public void setRequiredDate(String requiredDate) {
        this.requiredDate = requiredDate;
    }

    public String getRequirement() {
        return this.requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public double getActualHours() {
        return this.actualHours;
    }

    public void setActualHours(double actualHours) {
        this.actualHours = actualHours;
    }

    public String getDocumentation() {
        return this.documentation;
    }

    public void setDocumentation(String documentation) {
        this.documentation = documentation;
    }

    public String getEntityLogs() {
        return this.entityLogs;
    }

    public void setEntityLogs(String entityLogs) {
        this.entityLogs = entityLogs;
    }

    public String getTesting() {
        return this.testing;
    }

    public void setTesting(String testing) {
        this.testing = testing;
    }

    public String getBillable() {
        return this.billable;
    }

    public void setBillable(String billable) {
        this.billable = billable;
    }

    public String getTaskNo() {
        return this.taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    public String getCompletedDate() {
        return this.completedDate;
    }

    public void setCompletedDate(String completedDate) {
        this.completedDate = completedDate;
    }

    public String getCoreUpdates() {
        return this.coreUpdates;
    }

    public void setCoreUpdates(String coreUpdates) {
        this.coreUpdates = coreUpdates;
    }

}
