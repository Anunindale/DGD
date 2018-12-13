/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.workflow.workflow;

import emc.datatypes.EMCString;

/**
 *
 * @author riaan
 */
public class WorkFlowDescription extends EMCString { 

    /** Creates a new instance of WorkFlowDescription */
    public WorkFlowDescription() {
        this.setEmcLabel("Priority");
        this.setColumnWidth(100);
        this.setStringSize(100);
    }
}
