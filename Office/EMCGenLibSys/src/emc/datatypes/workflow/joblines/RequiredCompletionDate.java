package emc.datatypes.workflow.joblines;

import emc.datatypes.EMCDate;

/**
 *
 * @author wikus
 */
public class RequiredCompletionDate extends EMCDate {

    /** Creates a new instance of RequiredCompletionDate */
    public RequiredCompletionDate() {
        this.setEmcLabel("Required Completion Date");
        this.setEditable(true);
    }
}
