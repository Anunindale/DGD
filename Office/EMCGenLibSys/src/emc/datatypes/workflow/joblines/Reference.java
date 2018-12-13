package emc.datatypes.workflow.joblines;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class Reference extends EMCString {

    /** Creates a new instance of Reference */
    public Reference() {
        this.setEmcLabel("Reference");
        this.setEditable(true);
        this.setStringSize(50);
    }
}
