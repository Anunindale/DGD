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
public class Employee extends EMCBoolean {

    public Employee() {
    	this.setColumnWidth(18);
        this.setEmcLabel("E");
    }
}
