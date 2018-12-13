package emc.datatypes.workflow.jobmaster;

import emc.datatypes.workflow.lines.*;
import emc.datatypes.EMCString;

/**
 * 
 * @author wikus
 */
public class WorkFlowId extends EMCString {

    /** Creates a new instance of WorkFlowId */
    public WorkFlowId() {
        this.setEmcLabel("Work Flow Id");
        this.setMandatory(true);
        this.setStringSize(50);
    }
}
