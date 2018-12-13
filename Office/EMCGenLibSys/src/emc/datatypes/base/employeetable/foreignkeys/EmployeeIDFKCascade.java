/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.base.employeetable.foreignkeys;

import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 *
 * @author riaan
 */
public class EmployeeIDFKCascade extends EmployeeIdFK {

    /** Creates a new instance of EmployeeIDFKCascade. */
    public EmployeeIDFKCascade() {
        this.setDeleteAction(enumDeleteUpdateOptions.CASCADE);
        this.setUpdateAction(enumDeleteUpdateOptions.CASCADE);
    }
}
