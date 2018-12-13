/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.workflow.activitypriority;

import emc.datatypes.EMCString;

/**
 *
 * @author riaan
 */
public class Priority extends EMCString { 

    /** Creates a new instance of Priority */
    public Priority() {
        this.setEmcLabel("Priority");
        this.setColumnWidth(60);
        this.setMandatory(true);
    }
}
