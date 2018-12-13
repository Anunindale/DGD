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
public class PrimaryIndicator extends EMCString { 

    /** Creates a new instance of PrimaryIndicator */
    public PrimaryIndicator() {
        this.setEmcLabel("Primary Indicator");
        this.setColumnWidth(25);
    }
}
