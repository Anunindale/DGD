/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.developertools.bugtracking;

import emc.datatypes.EMCDouble;

/**
 *
 * @author rico
 */
public class TimeTaken extends EMCDouble{
    public TimeTaken(){
    	this.setColumnWidth(39);
        this.setEmcLabel("Time");
    }

}
