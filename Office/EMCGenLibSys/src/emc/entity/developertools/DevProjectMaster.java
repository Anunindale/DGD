/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.developertools;

import emc.datatypes.EMCDataType;
import emc.datatypes.developertools.projects.ProjectName;
import emc.datatypes.sop.customers.foreignkeys.CustomerIdFK;
import emc.datatypes.systemwide.Description;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "DevProjectMaster", uniqueConstraints = {@UniqueConstraint(columnNames = {"customerId", "projectName", "companyId"})})
public class DevProjectMaster extends EMCEntityClass {

    private String customerId;
    private String projectName;
    private String projectDescription;

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("customerId", new CustomerIdFK());
        toBuild.put("projectName", new ProjectName());
        toBuild.put("projectDescription", new Description());
        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("projectName");
        toBuild.add("projectDescription");
        return toBuild;
    }
}
