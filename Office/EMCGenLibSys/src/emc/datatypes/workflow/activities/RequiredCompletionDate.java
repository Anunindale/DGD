package emc.datatypes.workflow.activities;

import emc.datatypes.EMCDate;

/**
 *
 * @author wikus
 */
public class RequiredCompletionDate extends EMCDate {

    /** Creates a new instance of RequiredCompletionDate */
    public RequiredCompletionDate() {
        this.setEmcLabel("Req Completion Date");
        this.setEditable(true);
        this.setColumnWidth(40);
    }
}
