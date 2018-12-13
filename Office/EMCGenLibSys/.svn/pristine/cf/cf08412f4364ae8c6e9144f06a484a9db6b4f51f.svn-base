/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.workflow.datasource;

import emc.datatypes.datasources.base.employeeNameDS;
import emc.entity.workflow.WorkFlowActivityGroupEmp;
import java.util.HashMap;

/**
 *
 * @author riaan
 */
public class WorkFlowActivityGroupEmpDS extends WorkFlowActivityGroupEmp {
    
    private String employeeName;
    
    /** Creates a new instance of WorkFlowActivityGroupEmpDS */
    public WorkFlowActivityGroupEmpDS() {
        this.setDataSource(true);
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
    
    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();
        
        toBuild.put("employeeName", new employeeNameDS());
        
        return toBuild;
    }

}
