/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.developertools;

import java.math.BigDecimal;

/**
 *
 * @author wikus
 */
public class BugListReportDS {

    private String bugNo;
    private String bugDesc;
    private String assignedTo;
    private String requiredDate;
    private String priotity;
    private double estimatedTime;
    private String completedDate;
    private double completedTime;
    private String bugSummary;
    private BigDecimal quotedHours;
    private String taskType;

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getBugDesc() {
        return bugDesc;
    }

    public void setBugDesc(String bugDesc) {
        this.bugDesc = bugDesc;
    }

    public String getBugNo() {
        return bugNo;
    }

    public void setBugNo(String bugNo) {
        this.bugNo = bugNo;
    }

    public String getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(String completedDate) {
        this.completedDate = completedDate;
    }

    public double getCompletedTime() {
        return completedTime;
    }

    public void setCompletedTime(double completedTime) {
        this.completedTime = completedTime;
    }

    public double getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(double estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public String getPriotity() {
        return priotity;
    }

    public void setPriotity(String priotity) {
        this.priotity = priotity;
    }

    public String getRequiredDate() {
        return requiredDate;
    }

    public void setRequiredDate(String requiredDate) {
        this.requiredDate = requiredDate;
    }

    public String getBugSummary() {
        return bugSummary;
    }

    public void setBugSummary(String bugSummary) {
        this.bugSummary = bugSummary;
    }

    public BigDecimal getQuotedHours() {
        return quotedHours;
    }

    public void setQuotedHours(BigDecimal quotedHours) {
        this.quotedHours = quotedHours;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }
}
