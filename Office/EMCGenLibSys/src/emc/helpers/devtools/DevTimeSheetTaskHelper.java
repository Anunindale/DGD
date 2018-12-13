/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.helpers.devtools;

import java.util.Date;

/**
 *
 * @author wikus
 */
public class DevTimeSheetTaskHelper {

    //Creation
    private String client;
    private String priority;
    private String module;
    private String type;
    private Date requiredDate;
    private String parentTask;
    //Completion
    private boolean billable;
    private String testedBy;
    private String test;
    private String coreUpdates;
    private String affected;
    private String affectedData;
    private String entityLog;
    private String documentation;

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getParentTask() {
        return parentTask;
    }

    public void setParentTask(String parentTask) {
        this.parentTask = parentTask;
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

    public String getAffected() {
        return affected;
    }

    public void setAffected(String affected) {
        this.affected = affected;
    }

    public boolean isBillable() {
        return billable;
    }

    public void setBillable(boolean billable) {
        this.billable = billable;
    }

    public String getEntityLog() {
        return entityLog;
    }

    public void setEntityLog(String entityLog) {
        this.entityLog = entityLog;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getTestedBy() {
        return testedBy;
    }

    public void setTestedBy(String testedBy) {
        this.testedBy = testedBy;
    }

    public Date getRequiredDate() {
        return requiredDate;
    }

    public void setRequiredDate(Date requiredDate) {
        this.requiredDate = requiredDate;
    }

    public String getAffectedData() {
        return affectedData;
    }

    public void setAffectedData(String affectedData) {
        this.affectedData = affectedData;
    }

    public String getCoreUpdates() {
        return coreUpdates;
    }

    public void setCoreUpdates(String coreUpdates) {
        this.coreUpdates = coreUpdates;
    }

    public String getDocumentation() {
        return documentation;
    }

    public void setDocumentation(String documentation) {
        this.documentation = documentation;
    }
}
