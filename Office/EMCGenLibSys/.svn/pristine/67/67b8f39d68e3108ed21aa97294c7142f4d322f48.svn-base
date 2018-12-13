package emc.datatypes.workflow.activities;

import emc.datatypes.EMCString;
import emc.entity.workflow.WorkFlowActivityStatus;

/**
 *
 * @author wikus
 */
public class Status extends EMCString {

    /** Creates a new instance of Status */
    public Status() {
        this.setEmcLabel("Status");
        this.setEditable(true);
        this.setStringSize(50);
        this.setRelatedTable(WorkFlowActivityStatus.class.getName());
        this.setRelatedField("activityStatus");
    }
}
