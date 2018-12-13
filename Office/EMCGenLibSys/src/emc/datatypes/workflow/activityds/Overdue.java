package emc.datatypes.workflow.activityds;

import emc.datatypes.EMCBoolean;

/**
 * 
 * @author wikus
 */
public class Overdue extends EMCBoolean {

    /** Creates a new instance of Overdue */
    public Overdue() {
        this.setEmcLabel("Overdue");
        this.setEditable(true);
    }
}
