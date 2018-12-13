package emc.datatypes.workflow.joblines;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class OutputFilePath extends EMCString {

    /** Creates a new instance of OutputFilePath */
    public OutputFilePath() {
        this.setEmcLabel("Output File Path");
        this.setStringSize(256);
        this.setEditable(true);
    }
}
