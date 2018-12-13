/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.workflow.jobs;

import emc.datatypes.EMCString;

/**
 *
 * @author riaan
 */
public class Status extends EMCString { 

    /** Creates a new instance of Status */
    public Status() {
        this.setEmcLabel("Status");
        this.setColumnWidth(20);
    }
}
