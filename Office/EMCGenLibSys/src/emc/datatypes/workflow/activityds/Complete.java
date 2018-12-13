package emc.datatypes.workflow.activityds;

import emc.datatypes.EMCBoolean;

/**
 * 
 * @author wikus
 */
public class Complete extends EMCBoolean {

    /** Creates a new instance of Complete */
    public Complete() {
        this.setEmcLabel("Completed");
        this.setEditable(true);
    }
}
