package emc.datatypes.workflow.activities;

import emc.datatypes.EMCString;
import emc.entity.workflow.WorkFlowActivityCate;

/**
 *
 * @author wikus
 */
public class Category extends EMCString {

    /** Creates a new instance of Category */
    public Category() {
        this.setEmcLabel("Category");
        this.setEditable(true);
        this.setStringSize(50);
        this.setRelatedTable(WorkFlowActivityCate.class.getName());
        this.setRelatedField("activityCategory");
    }
}
