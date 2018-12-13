package emc.datatypes.workflow.joblines;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class CompletionRules extends EMCString {

    /** Creates a new instance of CompletionRules */
    public CompletionRules() {
        this.setEmcLabel("Completion Rules");
        this.setEditable(true);
        this.setStringSize(1000);
    }
}
