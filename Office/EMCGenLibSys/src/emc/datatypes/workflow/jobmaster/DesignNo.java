package emc.datatypes.workflow.jobmaster;

import emc.datatypes.workflow.joblines.*;
import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class DesignNo extends EMCString {

    /** Creates a new instance of DesignNo */
    public DesignNo() {
        this.setEmcLabel("Design No");
        this.setMandatory(true);
        this.setStringSize(50);
    }
}
