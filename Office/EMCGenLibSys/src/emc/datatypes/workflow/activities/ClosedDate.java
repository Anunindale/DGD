package emc.datatypes.workflow.activities;

import emc.datatypes.EMCDate;

/**
 *
 * @author wikus
 */
public class ClosedDate extends EMCDate {

    /** Creates a new instance of ClosedDate */
    public ClosedDate() {
        this.setEmcLabel("Closed Date");
        this.setEditable(true);
    }
}
