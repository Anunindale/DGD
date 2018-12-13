/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.workflow.master;

import emc.datatypes.workflow.jobmaster.*;
import emc.datatypes.workflow.joblines.*;
import emc.datatypes.workflow.activitypriority.*;
import emc.datatypes.EMCString;
import emc.entity.base.BaseEmployeeTable;

/**
 *
 * @author riaan
 */
public class ApprovedBy extends EMCString {

    /** Creates a new instance of ApprovedBy */
    public ApprovedBy() {
        this.setEmcLabel("Approved By");
        this.setColumnWidth(80);
        this.setRelatedTable(BaseEmployeeTable.class.getName());
        this.setRelatedField("employeeNumber");
    }
}
