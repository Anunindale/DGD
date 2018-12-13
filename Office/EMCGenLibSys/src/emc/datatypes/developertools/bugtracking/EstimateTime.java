/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.developertools.bugtracking;

import emc.datatypes.EMCDouble;

/**
 *
 * @author wikus
 */
public class EstimateTime extends EMCDouble{
    public EstimateTime(){
    	this.setColumnWidth(51);
        this.setEmcLabel("Estimate");
    }

}
