package emc.datatypes.workflow.activitygroupempds;

import emc.datatypes.EMCString;

/**
 * 
 * @author wikus
 */
public class EmployeeName extends EMCString {

    /** Creates a new instance of EmployeeName */
    public EmployeeName() {
        this.setEmcLabel("Employee Name");
        this.setEditable(true);
        this.setStringSize(40);
    }
}
