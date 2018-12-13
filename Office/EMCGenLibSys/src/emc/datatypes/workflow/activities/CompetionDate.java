package emc.datatypes.workflow.activities;

import emc.datatypes.EMCDate;

/**
 *
 * @author wikus
 */
public class CompetionDate extends EMCDate {

    /** Creates a new instance of CompetionDate */
    public CompetionDate() {
        this.setEmcLabel("Completion Date");
        this.setEditable(true);
        this.setColumnWidth(40);
    }
}
