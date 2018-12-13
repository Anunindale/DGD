/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.workflow;

import emc.datatypes.systemwide.Description;
import emc.datatypes.workflow.master.ApprovedBy;
import emc.datatypes.workflow.master.ApprovedDate;
import emc.datatypes.workflow.master.WorkFlowId;
import emc.framework.EMCEntityClass;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
@Table(name = "WorkFlowMaster", uniqueConstraints = {@UniqueConstraint(columnNames = {"workFlowId", "companyId"})})
public class WorkFlowMaster extends EMCEntityClass implements Serializable {

    private String workFlowId;
    private String workFlowDescription;
    private String approvedBy;
    @Temporal(TemporalType.DATE)
    private Date approvedDate;
    private String sourceTable;

    public WorkFlowMaster() {
    }

    public String getWorkFlowId() {
        return workFlowId;
    }

    public void setWorkFlowId(String workFlowId) {
        this.workFlowId = workFlowId;
    }

    public String getWorkFlowDescription() {
        return workFlowDescription;
    }

    public void setWorkFlowDescription(String workFlowDescription) {
        this.workFlowDescription = workFlowDescription;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
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

        toBuild.put("workFlowDescription", new Description());
        toBuild.put("workFlowId", new WorkFlowId());
        toBuild.put("approvedBy", new ApprovedBy());
        toBuild.put("approvedDate", new ApprovedDate());

        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("workFlowId");
        toBuild.add("workFlowDescription");
        return toBuild;
    }
}
