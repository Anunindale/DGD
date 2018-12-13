package emc.datatypes.workflow.activities;

import emc.datatypes.EMCDate;

/**
 *
 * @author wikus
 */
public class ExpectedCompletionDate extends EMCDate {

    /** Creates a new instance of ExpectedCompletionDate */
    public ExpectedCompletionDate() {
        this.setEmcLabel("Expected Completion Date");
        this.setEditable(true);
    }
}
