/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.workflow;

import emc.datatypes.base.employeeaccessgroup.foreignkeys.AccessGroupFK;
import emc.datatypes.systemwide.Description;
import emc.datatypes.workflow.departments.Departments;
import emc.framework.EMCEntityClass;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author riaan
 */
@Entity
@Table(name = "WorkFlowDepartment", uniqueConstraints = {@UniqueConstraint(columnNames = {"department", "companyId"})})
public class WorkFlowDepartment extends EMCEntityClass implements Serializable {

    private String department;
    private String description;
    private String accessGroup;

    public WorkFlowDepartment() {
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAccessGroup() {
        return accessGroup;
    }

    public void setAccessGroup(String accessGroup) {
        this.accessGroup = accessGroup;
    }

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();
        toBuild.put("department", new Departments());
        toBuild.put("description", new Description());
        toBuild.put("accessGroup", new AccessGroupFK());
        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("department");
        toBuild.add("description");
        return toBuild;
    }
}
