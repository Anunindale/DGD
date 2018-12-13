/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.workflow;

import emc.datatypes.EMCDataType;
import emc.datatypes.workflow.activityalerts.ActivityCategory;
import emc.datatypes.workflow.activityalerts.Employee;
import emc.datatypes.workflow.activityalerts.Manager;
import emc.datatypes.workflow.activityalerts.TaskManager;
import emc.datatypes.workflow.activtygroups.foreignkey.ActivityGroupFK;
import emc.datatypes.workflow.activtytype.foreignkeys.ActivityTypeFK;
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
@Table(name = "WFActivityAlerts", uniqueConstraints = {@UniqueConstraint(columnNames = {"activityCategory", "activityGroup", "activityType", "companyId"})})
public class WFActivityAlerts extends EMCEntityClass {

    private String activityCategory;
    private String activityGroup;
    private String activityType;
    private boolean manager;
    private boolean employee;
    private boolean taskManager;

    public String getActivityCategory() {
        return activityCategory;
    }

    public void setActivityCategory(String activityCategory) {
        this.activityCategory = activityCategory;
    }

    public String getActivityGroup() {
        return activityGroup;
    }

    public void setActivityGroup(String activityGroup) {
        this.activityGroup = activityGroup;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public boolean isEmployee() {
        return employee;
    }

    public void setEmployee(boolean employee) {
        this.employee = employee;
    }

    public boolean isManager() {
        return manager;
    }

    public void setManager(boolean manager) {
        this.manager = manager;
    }

    public boolean isTaskManager() {
        return taskManager;
    }

    public void setTaskManager(boolean taskManager) {
        this.taskManager = taskManager;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("activityCategory", new ActivityCategory());
        toBuild.put("activityGroup", new ActivityGroupFK());
        toBuild.put("activityType", new ActivityTypeFK());
        toBuild.put("manager", new Manager());
        toBuild.put("employee", new Employee());
        toBuild.put("taskManager", new TaskManager());
        return toBuild;
    }
}
