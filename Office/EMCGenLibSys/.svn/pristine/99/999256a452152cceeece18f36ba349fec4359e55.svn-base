/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.crm;

import emc.datatypes.EMCDataType;
import emc.datatypes.crm.interestgroup.CRMInterestGroupDescription;
import emc.datatypes.crm.interestgroup.InterestGroup;
import emc.datatypes.workflow.master.foreignkeys.WorkFlowIdFKNM;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author rico
 */
@Entity
@Table(name = "CRMInterestGroup", uniqueConstraints = {@UniqueConstraint(columnNames = {"interestGroupId", "companyId"})})
public class CRMInterestGroup extends EMCEntityClass{
    private String interestGroupId;
    private String description;
    private String workFlowId;

    /**
     * @return the interestGroupId
     */
    public String getInterestGroupId() {
        return interestGroupId;
    }

    /**
     * @param interestGroupId the interestGroupId to set
     */
    public void setInterestGroupId(String interestGroupId) {
        this.interestGroupId = interestGroupId;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the workFlowId
     */
    public String getWorkFlowId() {
        return workFlowId;
    }

    /**
     * @param workFlowId the workFlowId to set
     */
    public void setWorkFlowId(String workFlowId) {
        this.workFlowId = workFlowId;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List fields = super.buildDefaultLookupFieldList();
        fields.add("interestGroupId");
        fields.add("description");
        return fields;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap ret = super.buildFieldList();
        ret.put("interestGroupId",new InterestGroup());
        ret.put("description", new CRMInterestGroupDescription());
        ret.put("workFlowId",new WorkFlowIdFKNM());
        return ret;
    }

}
