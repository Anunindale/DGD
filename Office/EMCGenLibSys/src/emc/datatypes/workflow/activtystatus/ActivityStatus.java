/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.workflow.activtystatus;

/**
 *
 * @author wikus
 */
import emc.datatypes.EMCString;

public class ActivityStatus extends EMCString {

    /** Creates a new instance of ActivityStatus */
    public ActivityStatus() {
        this.setEmcLabel("Activity Status");
        this.setColumnWidth(50);
        this.setMandatory(true);
    }
}
