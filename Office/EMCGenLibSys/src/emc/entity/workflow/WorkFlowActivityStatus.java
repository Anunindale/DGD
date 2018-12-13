/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.workflow;

import emc.datatypes.systemwide.Description;
import emc.datatypes.workflow.activtystatus.ActivityStatus;
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
@Table(name = "WFActivityStatus",uniqueConstraints={@UniqueConstraint(columnNames={"activityStatus","companyId"})})
public class WorkFlowActivityStatus extends EMCEntityClass implements Serializable {
    private String activityStatus;
    private String description;
    public WorkFlowActivityStatus(){
        
    }

    public String getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(String activityStatus) {
        this.activityStatus = activityStatus;
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
       toBuild.put("activityStatus", new ActivityStatus());
        toBuild.put("description", new Description());
        
        return toBuild;
    }
}
