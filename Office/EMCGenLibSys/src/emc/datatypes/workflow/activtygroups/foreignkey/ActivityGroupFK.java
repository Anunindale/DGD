/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.workflow.activtygroups.foreignkey;

import emc.datatypes.workflow.activities.ActivityGroup;
import emc.entity.workflow.WorkFlowActivityGroups;

/**
 *
 * @author wikus
 */
public class ActivityGroupFK extends ActivityGroup{

    public ActivityGroupFK() {
        this.setRelatedTable(WorkFlowActivityGroups.class.getName());
        this.setRelatedField("activityGroup");
    	this.setColumnWidth(98);
        this.setMandatory(true);
    }

}
