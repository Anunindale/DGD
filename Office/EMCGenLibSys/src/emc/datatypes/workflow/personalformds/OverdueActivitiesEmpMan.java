package emc.datatypes.workflow.personalformds;

import emc.datatypes.EMCString;

/**
 * 
 * @author wikus
 */
public class OverdueActivitiesEmpMan extends EMCString {

    /** Creates a new instance of OverdueActivitiesEmpMan */
    public OverdueActivitiesEmpMan() {
        this.setEmcLabel("Overdue Activities Emp Man");
        this.setEditable(true);
        this.setStringSize(50);
    }
}
