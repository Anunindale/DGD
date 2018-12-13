package emc.datatypes.workflow.activities;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class ActivityResult extends EMCString {

    /** Creates a new instance of ActivityResult */
    public ActivityResult() {
        this.setEmcLabel("Activity Result");
        this.setEditable(true);
        this.setStringSize(1000);
    }
}
