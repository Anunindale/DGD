/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.gl.financialperiod;

import emc.datatypes.EMCDate;

/**
 *
 * @author wikus
 */
public class StartDate extends EMCDate{

    public StartDate() {
        this.setEmcLabel("Start Date");
    	this.setColumnWidth(81);
        this.setMandatory(true);
    }

}
