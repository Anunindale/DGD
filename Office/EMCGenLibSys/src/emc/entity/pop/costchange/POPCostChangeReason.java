/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.pop.costchange;

import emc.datatypes.EMCDataType;
import emc.datatypes.pop.costchange.ReasonId;
import emc.datatypes.systemwide.Description;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "POPCostChangeReason", uniqueConstraints = {@UniqueConstraint(columnNames = {"reasonId", "companyId"})})
public class POPCostChangeReason extends EMCEntityClass{
    
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
        HashMap toBuild = super.buildFieldList();
        
        toBuild.put("reasonId", new ReasonId());
        toBuild.put("description", new Description());
        
        return toBuild;
    }

    
}
