/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.crm;

import emc.datatypes.EMCDataType;
import emc.datatypes.crm.prospectclosereason.CRMReasonDescription;
import emc.datatypes.crm.prospectclosereason.ReasonId;
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
@Table(name = "CRMProspectCloseReason", uniqueConstraints = {@UniqueConstraint(columnNames = {"reasonId", "companyId"})})
public class CRMProspectCloseReason extends EMCEntityClass {

    private String reasonId;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReasonId() {
        return reasonId;
    }

    public void setReasonId(String reasonId) {
        this.reasonId = reasonId;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("reasonId", new ReasonId());
        toBuild.put("description", new CRMReasonDescription());
        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("reasonId");
        toBuild.add("description");
        return toBuild;
    }
}
