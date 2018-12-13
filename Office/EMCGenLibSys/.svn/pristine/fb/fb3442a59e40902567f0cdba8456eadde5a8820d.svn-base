/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.workflow.activtygroupsemp;

/**
 *
 * @author wikus
 */
import emc.datatypes.workflow.employeeskills.*;
import emc.datatypes.EMCString;
import emc.entity.base.BaseEmployeeTable;

public class EmployeeNumber extends EMCString {

    /** Creates a new instance of Employee */
    public EmployeeNumber() {
        this.setEmcLabel("Employee");
        this.setColumnWidth(50);
        this.setMandatory(true);
        this.setRelatedTable(BaseEmployeeTable.class.getName());
        this.setRelatedField("employeeNumber");
    }
}
