package emc.datatypes.workflow.joblines;

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
