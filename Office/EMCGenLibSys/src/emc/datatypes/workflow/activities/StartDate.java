package emc.datatypes.workflow.activities;

import emc.datatypes.EMCDate;

/**
 *
 * @author wikus
 */
public class StartDate extends EMCDate {

    /** Creates a new instance of StartDate */
    public StartDate() {
        this.setEmcLabel("Start Date");
        this.setEditable(true);
        this.setColumnWidth(40);
    }
}
