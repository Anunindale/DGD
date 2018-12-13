package emc.datatypes.workflow.joblines;

import emc.datatypes.EMCBoolean;

/**
 *
 * @author wikus
 */
public class OutputFileRequired extends EMCBoolean {

    /** Creates a new instance of OutputFileRequired */
    public OutputFileRequired() {
        this.setEmcLabel("Output File Required");
        this.setMandatory(true);
        this.setEditable(true);
    }
}
