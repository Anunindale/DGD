/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.workflow.activtytype.foreignkeys;

/**
 *
 * @author wikus
 */
import emc.datatypes.workflow.activtytype.ActivityType;
import emc.entity.workflow.WorkFlowActivityTypes;

public class ActivityTypeFK extends ActivityType {

    /** Creates a new instance of ActivityTypeFK */
    public ActivityTypeFK() {
        this.setRelatedTable(WorkFlowActivityTypes.class.getName());
    	this.setColumnWidth(102);
        this.setRelatedField("activityType");
    }
}
