package emc.datatypes.workflow.activities;

import emc.datatypes.EMCBoolean;

/**
 *
 * @author wikus
 */
public class Billable extends EMCBoolean {

    /** Creates a new instance of Billable */
    public Billable() {
        this.setEmcLabel("Billable");
        this.setMandatory(true);
        this.setEditable(true);
    }
}
