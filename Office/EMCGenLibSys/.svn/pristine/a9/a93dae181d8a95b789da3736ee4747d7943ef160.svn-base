/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.inventory.accessgroupemployees;

import emc.datatypes.EMCString;
import emc.entity.base.BaseEmployeeTable;

/**
 *
 * @author wikus
 */
public class EmployeeIdFK extends EMCString {

    /** Creates a new instance of UserIdFK */
    public EmployeeIdFK() {
        this.setEmcLabel("Employee");
        this.setColumnWidth(50);
        this.setMandatory(true);
        this.setRelatedTable(BaseEmployeeTable.class.getName());
        this.setRelatedField("employeeNumber");
    }
}
