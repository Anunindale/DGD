/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.creditors;

import emc.framework.EMCEntityClass;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author claudette
 */
@Entity
@Table(name = "CreditorsParameters", uniqueConstraints = {@UniqueConstraint(columnNames = {"companyId"})})
public class CreditorsParameters extends EMCEntityClass {

    public CreditorsParameters() {
    }
    private String prospectsWorkFlowId;

    public String getProspectsWorkFlowId() {
        return prospectsWorkFlowId;
    }

    public void setProspectsWorkFlowId(String prospectsWorkFlowId) {
        this.prospectsWorkFlowId = prospectsWorkFlowId;
    }
}
