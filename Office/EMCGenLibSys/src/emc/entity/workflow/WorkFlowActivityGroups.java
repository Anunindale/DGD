/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.workflow;

import emc.datatypes.systemwide.Description;
import emc.datatypes.workflow.activtygroups.ActivityGroups;
import emc.framework.EMCEntityClass;
import java.io.Serializable;
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
@Table(name = "WFActivityGroup", uniqueConstraints = {@UniqueConstraint(columnNames = {"activityGroup", "companyId"})})
public class WorkFlowActivityGroups extends EMCEntityClass implements Serializable {

    private String activityGroup;
    private String description;

    public WorkFlowActivityGroups() {
    }

    public String getActivityGroup() {
        return activityGroup;
    }

    public void setActivityGroup(String activityGroup) {
        this.activityGroup = activityGroup;
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
        toBuild.put("activityGroup", new ActivityGroups());
        toBuild.put("description", new Description());

        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("activityGroup");
        toBuild.add("description");
        return toBuild;
    }
}
