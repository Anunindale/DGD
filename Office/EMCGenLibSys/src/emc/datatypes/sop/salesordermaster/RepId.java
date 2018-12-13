/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.sop.salesordermaster;

import emc.datatypes.sop.repcommission.RepIdFK;

/**
 *
 * @author riaan
 */
public class RepId extends RepIdFK {

    /** Creates a new instance of RepId. */
    public RepId() {
        this.setEmcLabel("Rep");
    	this.setColumnWidth(33);
        this.setMandatory(false);
        
    }
}
