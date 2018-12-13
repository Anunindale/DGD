/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.workflow.lines;

/**
 *
 * @author wikus
 */
import emc.datatypes.workflow.activtycategory.*;
import emc.datatypes.EMCString;
import emc.entity.workflow.WorkFlowActivityCate;

public class ActivityCategory extends EMCString {

    /** Creates a new instance of activityCategory */
    public ActivityCategory() {
        this.setEmcLabel("Activity Category");
        this.setColumnWidth(50);
        this.setMandatory(true);
        this.setRelatedTable(WorkFlowActivityCate.class.getName());
        this.setRelatedField("activityCategory");
    }
}
