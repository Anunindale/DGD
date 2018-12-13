/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.developertools.bugtracking;

import emc.datatypes.EMCDataType;
import emc.datatypes.base.numbersequences.ModuleId;
import emc.datatypes.developertools.bugtracking.Billable;
import emc.datatypes.developertools.bugtracking.BugId;
import emc.datatypes.developertools.bugtracking.BugNumber;
import emc.datatypes.developertools.bugtracking.BugType;
import emc.datatypes.developertools.bugtracking.Client;
import emc.datatypes.developertools.bugtracking.CompletedDate;
import emc.datatypes.developertools.bugtracking.CoreUpdates;
import emc.datatypes.developertools.bugtracking.Documentation;
import emc.datatypes.developertools.bugtracking.EntityLog;
import emc.datatypes.developertools.bugtracking.EstimateTime;
import emc.datatypes.developertools.bugtracking.Pending;
import emc.datatypes.developertools.bugtracking.PossibleAffected;
import emc.datatypes.developertools.bugtracking.PossibleAffectedData;
import emc.datatypes.developertools.bugtracking.Priority;
import emc.datatypes.developertools.bugtracking.Processed;
import emc.datatypes.developertools.bugtracking.RequiredDate;
import emc.datatypes.developertools.bugtracking.Responsible;
import emc.datatypes.developertools.bugtracking.StartTime;
import emc.datatypes.developertools.bugtracking.TaskSummary;
import emc.datatypes.developertools.bugtracking.TestDescription;
import emc.datatypes.developertools.bugtracking.Tested;
import emc.datatypes.developertools.bugtracking.TestedBy;
import emc.datatypes.developertools.bugtracking.TimeTaken;
import emc.datatypes.developertools.bugtracking.foreignkeys.BugNumberFK;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author riaan
 */
@Entity
@Table(name = "DevBugsTable")
public class DevBugsTable extends EMCEntityClass {

    @Column(length = 20000)
    private String bugId;
    private boolean processed;
    @Temporal(TemporalType.DATE)
    private Date completed;
    @Temporal(TemporalType.DATE)
    private Date requiredDate;
    private String responsible;
    private String bugNumber;
    private String priority;
    private boolean billable;
    private double timeTaken;
    private double estimateTime;
    private boolean pending;
    private String moduleId;
    private boolean tested;
    @Temporal(TemporalType.TIME)
    private Date startTime;
    private String client;
    private String bugType;
    @Column(length = 1000)
    private String testDescription;
    private String testedBy;
    @Column(length = 1000)
    private String possibleAffected;
    @Column(length = 1000)
    private String possibleAffectedData;
    @Column(length = 1000)
    private String documentation;
    @Column(length = 1000)
    private String entityLog;
    @Column(length = 1000)
    private String coreUpdates;
    @Column(length = 255)
    private String summary;
    private BigDecimal quotedHours = BigDecimal.ZERO;
    private String parentTask = "0";

    public DevBugsTable() {
    }

    public String getParentTask() {
        return parentTask;
    }

    public void setParentTask(String parentTask) {
        this.parentTask = parentTask;
    }
    
    public String getBugId() {
        return bugId;
    }

    public void setBugId(String bugId) {
        this.bugId = bugId;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    /**
     * @return the completed
     */
    public Date getCompleted() {
        return completed;
    }

    /**
     * @param completed the completed to set
     */
    public void setCompleted(Date completed) {
        this.completed = completed;
    }

    /**
     * @return the bugNumber
     */
    public String getBugNumber() {
        return bugNumber;
    }

    /**
     * @param bugNumber the bugNumber to set
     */
    public void setBugNumber(String bugNumber) {
        this.bugNumber = bugNumber;
    }

    /**
     * @return the priority
     */
    public String getPriority() {
        return priority;
    }

    /**
     * @param priority the priority to set
     */
    public void setPriority(String priority) {
        this.priority = priority;
    }

    /**
     * @return the requiredDate
     */
    public Date getRequiredDate() {
        return requiredDate;
    }

    /**
     * @param requiredDate the requiredDate to set
     */
    public void setRequiredDate(Date requiredDate) {
        this.requiredDate = requiredDate;
    }

    /**
     * @return the responsible
     */
    public String getResponsible() {
        return responsible;
    }

    /**
     * @param responsible the responsible to set
     */
    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }

    /**
     * @return the billable
     */
    public boolean isBillable() {
        return billable;
    }

    /**
     * @param billable the billable to set
     */
    public void setBillable(boolean billable) {
        this.billable = billable;
    }

    /**
     * @return the timeTaken
     */
    public double getTimeTaken() {
        return timeTaken;
    }

    /**
     * @param timeTaken the timeTaken to set
     */
    public void setTimeTaken(double timeTaken) {
        this.timeTaken = timeTaken;
    }

    public double getEstimateTime() {
        return estimateTime;
    }

    public void setEstimateTime(double estimateTime) {
        this.estimateTime = estimateTime;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public boolean isPending() {
        return pending;
    }

    public void setPending(boolean pending) {
        this.pending = pending;
    }

    public boolean isTested() {
        return tested;
    }

    public void setTested(boolean tested) {
        this.tested = tested;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getBugType() {
        return bugType;
    }

    public void setBugType(String bugType) {
        this.bugType = bugType;
    }

    public String getTestDescription() {
        return testDescription;
    }

    public void setTestDescription(String testDescription) {
        this.testDescription = testDescription;
    }

    public String getTestedBy() {
        return testedBy;
    }

    public void setTestedBy(String testedBy) {
        this.testedBy = testedBy;
    }

    public String getPossibleAffected() {
        return possibleAffected;
    }

    public void setPossibleAffected(String possibleAffected) {
        this.possibleAffected = possibleAffected;
    }

    public String getEntityLog() {
        return entityLog;
    }

    public void setEntityLog(String entityLog) {
        this.entityLog = entityLog;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public BigDecimal getQuotedHours() {
        return quotedHours;
    }

    public void setQuotedHours(BigDecimal quotedHours) {
        this.quotedHours = quotedHours;
    }

    public String getPossibleAffectedData() {
        return possibleAffectedData;
    }

    public void setPossibleAffectedData(String possibleAffectedData) {
        this.possibleAffectedData = possibleAffectedData;
    }

    public String getDocumentation() {
        return documentation;
    }

    public void setDocumentation(String documentation) {
        this.documentation = documentation;
    }

    public String getCoreUpdates() {
        return coreUpdates;
    }

    public void setCoreUpdates(String coreUpdates) {
        this.coreUpdates = coreUpdates;
    }

    @Override
    public HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("bugId", new BugId());
        toBuild.put("processed", new Processed());
        toBuild.put("bugNumber", new BugNumber());
        toBuild.put("completed", new CompletedDate());
        toBuild.put("priority", new Priority());
        toBuild.put("requiredDate", new RequiredDate());
        toBuild.put("responsible", new Responsible());
        toBuild.put("timeTaken", new TimeTaken());
        toBuild.put("estimateTime", new EstimateTime());
        toBuild.put("billable", new Billable());
        toBuild.put("moduleId", new ModuleId());
        toBuild.put("tested", new Tested());
        toBuild.put("pending", new Pending());
        toBuild.put("startTime", new StartTime());
        toBuild.put("client", new Client());
        toBuild.put("bugType", new BugType());
        toBuild.put("testDescription", new TestDescription());
        toBuild.put("testedBy", new TestedBy());
        toBuild.put("possibleAffected", new PossibleAffected());
        toBuild.put("entityLog", new EntityLog());
        toBuild.put("summary", new TaskSummary());
        toBuild.put("coreUpdates", new CoreUpdates());
        toBuild.put("documentation", new Documentation());
        toBuild.put("possibleAffectedData", new PossibleAffectedData());
        toBuild.put("parentTask", new BugNumberFK());

        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("bugNumber");
        toBuild.add("summary");
        toBuild.add("responsible");
        return toBuild;
    }

    @Override
    public EMCQuery buildQuery() {
        EMCQuery query = super.buildQuery();

        query.addAnd("completed", null);

        return query;
    }
}
