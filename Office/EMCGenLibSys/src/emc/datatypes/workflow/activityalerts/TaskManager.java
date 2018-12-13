/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.workflow.activityalerts;

import emc.datatypes.EMCBoolean;

/**
 *
 * @author wikus
 */
public class TaskManager extends EMCBoolean {

    public TaskManager() {
    	this.setColumnWidth(19);
        this.setEmcLabel("T");
    }
}
