/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.base.employeetable;

import emc.datatypes.EMCString;

/**
 *
 * @author rico
 */
public class EmployeeType extends EMCString{
    public EmployeeType(){
        this.setRelatedField("employeeType");
        this.setRelatedTable("emc.entity.workflow.WorkFlowEmployeeType");
        this.setEmcLabel("Employee Type");
    }

}
