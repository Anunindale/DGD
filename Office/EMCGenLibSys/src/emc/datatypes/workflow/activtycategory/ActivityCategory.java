/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.workflow.activtycategory;

/**
 *
 * @author wikus
 */
import emc.datatypes.EMCString;

public class ActivityCategory extends EMCString {

    /** Creates a new instance of activityCategory */
    public ActivityCategory() {
        this.setEmcLabel("Activity Category");
        this.setColumnWidth(50);
        this.setMandatory(true);
    }
}
