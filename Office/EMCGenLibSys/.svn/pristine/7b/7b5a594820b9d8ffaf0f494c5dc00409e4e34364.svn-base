/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.workflow; 

import emc.datatypes.workflow.activtygroupsemp.ActivityGroup;
import emc.datatypes.workflow.activtygroupsemp.EmployeeNumber;
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
@Table(name = "WFActivityGroupEmp",uniqueConstraints={@UniqueConstraint(columnNames={"activityGroup","employeeNumber","companyId"})})
public class WorkFlowActivityGroupEmp extends EMCEntityClass implements Serializable {
    
    private String activityGroup;
    private String employeeNumber;
    
    public WorkFlowActivityGroupEmp(){
        
    }

    public String getActivityGroup() {
        return activityGroup;
    }

    public void setActivityGroup(String activityGroup) {
        this.activityGroup = activityGroup;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }
    
    @Override
     public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();
        toBuild.put("activityGroup", new ActivityGroup());
        toBuild.put("employeeNumber", new EmployeeNumber());
        
        return toBuild;
    }

}
