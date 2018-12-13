package emc.datatypes.workflow.personalformds;

import emc.datatypes.EMCString;

/**
 * 
 * @author wikus
 */
public class OverdueActivitiesEmp extends EMCString {

    /** Creates a new instance of OverdueActivitiesEmp */
    public OverdueActivitiesEmp() {
        this.setEmcLabel("Overdue Activities Emp");
        this.setEditable(true);
        this.setStringSize(50);
    }
}
