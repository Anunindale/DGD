/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.workflow;

import emc.datatypes.systemwide.Description;
import emc.datatypes.workflow.jobmaster.CompletedDate;
import emc.datatypes.workflow.jobmaster.Customer;
import emc.datatypes.workflow.jobmaster.DesignNo;
import emc.datatypes.workflow.jobmaster.ExternalNotes;
import emc.datatypes.workflow.jobmaster.InternalNotes;
import emc.datatypes.workflow.jobmaster.Item;
import emc.datatypes.workflow.jobmaster.Manager;
import emc.datatypes.workflow.jobmaster.StartedBy;
import emc.datatypes.workflow.jobmaster.StartedDate;
import emc.datatypes.workflow.jobmaster.Status;
import emc.datatypes.workflow.jobmaster.TargetCompletionDate;
import emc.datatypes.workflow.jobmaster.WorkFlowId;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
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
@Table(name = "WFJobMaster", uniqueConstraints = {@UniqueConstraint(columnNames = {"designNo", "companyId"})})
public class WorkFlowJobMaster extends EMCEntityClass implements Serializable {

    private String designNo;
    private String workFlowId;
    private String description;
    private String customer;
    private String item;
    private String startedBy;
    @Temporal(TemporalType.DATE)
    private Date startedDate;
    @Temporal(TemporalType.DATE)
    private Date targetCompletionDate;
    @Temporal(TemporalType.DATE)
    private Date completed;
    private String managerResponsible;
    private String status;
    private String internalNotes;
    private String externalNotes;
    private String sourceTable;
    private long sourceRecordId;

    public WorkFlowJobMaster() {
    }

    public String getDesignNo() {
        return designNo;
    }

    public void setDesignNo(String designNo) {
        this.designNo = designNo;
    }

    public String getWorkFlowId() {
        return workFlowId;
    }

    public void setWorkFlowId(String workFlowId) {
        this.workFlowId = workFlowId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getStartedBy() {
        return startedBy;
    }

    public void setStartedBy(String startedBy) {
        this.startedBy = startedBy;
    }

    public Date getTargetCompletionDate() {
        return targetCompletionDate;
    }

    public void setTargetCompletionDate(Date targetCompletionDate) {
        this.targetCompletionDate = targetCompletionDate;
    }

    public String getManagerResponsible() {
        return managerResponsible;
    }

    public void setManagerResponsible(String managerResponsible) {
        this.managerResponsible = managerResponsible;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Date getStartedDate() {
        return startedDate;
    }

    public void setStartedDate(Date startedDate) {
        this.startedDate = startedDate;
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

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();

        toBuild.put("description", new Description());
        toBuild.put("designNo", new DesignNo());
        toBuild.put("workFlowId", new WorkFlowId());
        toBuild.put("customer", new Customer());
        toBuild.put("item", new Item());
        toBuild.put("startedBy", new StartedBy());
        toBuild.put("startedDate", new StartedDate());
        toBuild.put("targetCompletionDate", new TargetCompletionDate());
        toBuild.put("managerResponsible", new Manager());
        toBuild.put("status", new Status());
        toBuild.put("internalNotes", new InternalNotes());
        toBuild.put("externalNotes", new ExternalNotes());
        toBuild.put("completed", new CompletedDate());

        return toBuild;
    }

    @Override
    public EMCQuery buildQuery() {
        EMCQuery query = super.buildQuery();

        query.addAnd("status", null);

        return query;
    }

    public Date getCompleted() {
        return completed;
    }

    public void setCompleted(Date completed) {
        this.completed = completed;
    }
}
