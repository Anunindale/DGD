/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.workflow.activities;

import emc.datatypes.workflow.activitypriority.Priority;
import emc.entity.workflow.WorkFlowActivityPriority;

/**
 *
 * @author rico
 */
public class PriorityFK extends Priority{
    public PriorityFK(){
        this.setMandatory(false);
        this.setRelatedTable(WorkFlowActivityPriority.class.getName());
        this.setRelatedField("activityPriority");
    }

}
