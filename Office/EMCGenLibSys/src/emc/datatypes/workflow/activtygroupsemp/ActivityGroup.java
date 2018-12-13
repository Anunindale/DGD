/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.workflow.activtygroupsemp;

/**
 *
 * @author wikus
 */
import emc.datatypes.EMCString;

public class ActivityGroup extends EMCString {

    /** Creates a new instance of ActivityGroup */
    public ActivityGroup() {
        this.setEmcLabel("Activity Group");
        this.setColumnWidth(50);
        this.setMandatory(true);
    }
}
