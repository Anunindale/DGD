/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */ 

package emc.datatypes.base.employeetable.foreignkeys;

import emc.datatypes.base.employeetable.EmployeeId;
import emc.entity.base.BaseEmployeeTable;

/**
 *
 * @author riaan
 */
public class EmployeeIdFK extends EmployeeId {

    /** Creates a new instance of EmployeeIdFKNotMandatory */
    public EmployeeIdFK() {
        this.setNumberSeqAllowed(false);
        this.setRelatedField("employeeNumber");
        this.setRelatedTable(BaseEmployeeTable.class.getName());
    } 
}
