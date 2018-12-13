/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.workflow.activitypriority;

import emc.datatypes.EMCString;
import emc.entity.base.BaseEmployeeTable;

/**
 *
 * @author riaan
 */
public class Manager extends EMCString {

    /** Creates a new instance of Manager */
    public Manager() {
        this.setEmcLabel("Manager Resp.");
        this.setColumnWidth(70);
        this.setRelatedTable(BaseEmployeeTable.class.getName());
        this.setRelatedField("employeeNumber");
    }
}
