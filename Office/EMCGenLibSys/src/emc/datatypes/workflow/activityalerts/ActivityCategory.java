/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.workflow.activityalerts;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class ActivityCategory extends EMCString {

    public ActivityCategory() {
        this.setEmcLabel("Category");
    	this.setColumnWidth(320);
        this.setMandatory(true);
    }

}
