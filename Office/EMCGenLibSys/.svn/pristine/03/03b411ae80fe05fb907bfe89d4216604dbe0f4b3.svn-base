/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.workflow;

import emc.datatypes.systemwide.Description;
import emc.datatypes.workflow.activitypriority.Priority;
import emc.framework.EMCEntityClass;
import java.io.Serializable;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author rico
 */
@Entity
@Table(name = "WFActivityPrior",uniqueConstraints={@UniqueConstraint(columnNames={"activityPriority","companyId"})})
public class WorkFlowActivityPriority extends EMCEntityClass implements Serializable {
    private String activityPriority;
    private String description;
    public WorkFlowActivityPriority(){
        
    }

    public String getActivityPriority() {
        return activityPriority;
    }

    public void setActivityPriority(String activityPriority) {
        this.activityPriority = activityPriority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
     @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();
        toBuild.put("activityPriority", new Priority());
        toBuild.put("description", new Description());
        
        return toBuild;
    }
}
