/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.workflow.joblines;

import emc.datatypes.base.employeetable.*;
import emc.datatypes.EMCString;
import emc.entity.base.BaseEmployeeTable;

/**
 *
 * @author rico
 */
public class EmployeeId extends EMCString {
    public EmployeeId(){
        this.setEmcLabel("Employee Responsable");
        this.setColumnWidth(80);
        this.setMandatory(false);
        this.setRelatedTable(BaseEmployeeTable.class.getName());
        this.setRelatedField("employeeNumber");
    }

}
