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
public class Department extends EMCString{
    public Department(){
        this.setEmcLabel("Department");
        this.setRelatedTable("emc.entity.workflow.WorkFlowDepartment");
    	this.setColumnWidth(86);
        this.setRelatedField("department");
    }
}
