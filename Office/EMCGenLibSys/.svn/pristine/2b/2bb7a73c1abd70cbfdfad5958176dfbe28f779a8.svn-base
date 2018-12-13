/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.workflow;

import emc.datatypes.systemwide.Description;
import emc.datatypes.workflow.employeetypes.EmployeeTypes;
import emc.framework.EMCEntityClass;
import java.io.Serializable;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author riaan
 */

@Entity
@Table(name = "WorkFlowEmployeeType", uniqueConstraints = {@UniqueConstraint(columnNames = {"employeeType", "companyId"})})
public class WorkFlowEmployeeType extends EMCEntityClass implements Serializable {
    
    private String employeeType;
    private String description;

    public WorkFlowEmployeeType() {
        
    }
    
    public String getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(String employeeType) {
        this.employeeType = employeeType;
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
       toBuild.put("employeeType", new EmployeeTypes());
        toBuild.put("description", new Description());
        
        return toBuild;
    }
}
