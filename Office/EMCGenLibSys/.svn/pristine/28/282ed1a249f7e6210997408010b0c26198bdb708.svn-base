/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.crm;

import emc.framework.EMCEntityClass;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "CRMParameters", uniqueConstraints = {@UniqueConstraint(columnNames = {"companyId"})})
public class CRMParameters extends EMCEntityClass {

    private String prospectsWorkFlowId;//default WF can be overwritten by the interestGroup
    private String prospectActRule; //enum that allows logic to be executed to assign act/job to specific employee

    public String getProspectsWorkFlowId() {
        return prospectsWorkFlowId;
    }

    public void setProspectsWorkFlowId(String prospectsWorkFlowId) {
        this.prospectsWorkFlowId = prospectsWorkFlowId;
    }

    /**
     * @return the prospectActRule
     */
    public String getProspectActRule() {
        return prospectActRule;
    }

    /**
     * @param prospectActRule the prospectActRule to set
     */
    public void setProspectActRule(String prospectActRule) {
        this.prospectActRule = prospectActRule;
    }
}
