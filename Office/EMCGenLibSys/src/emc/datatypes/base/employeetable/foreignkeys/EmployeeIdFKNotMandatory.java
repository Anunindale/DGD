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
public class EmployeeIdFKNotMandatory extends EmployeeId {

    /** Creates a new instance of EmployeeIdFKNotMandatory */
    public EmployeeIdFKNotMandatory() {
        this.setNumberSeqAllowed(false);
        this.setMandatory(false);
        this.setRelatedField("employeeNumber");
        this.setRelatedTable(BaseEmployeeTable.class.getName());
    }
}
