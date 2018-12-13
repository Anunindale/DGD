package emc.datatypes.workflow.jobmaster;

import emc.datatypes.workflow.activities.*;
import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class StartedBy extends EMCString {

    /** Creates a new instance of StartedBy */
    public StartedBy() {
        this.setEmcLabel("Started By");
        this.setEditable(true);
        this.setStringSize(50);
    }
}
