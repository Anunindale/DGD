package emc.datatypes.workflow.activities;

import emc.datatypes.EMCBoolean;

/**
 *
 * @author wikus
 */
public class ClosedByManager extends EMCBoolean {

    /** Creates a new instance of ClosedByManager */
    public ClosedByManager() {
        this.setEmcLabel("Closed By Manager");
        this.setEditable(true);
    }
}
