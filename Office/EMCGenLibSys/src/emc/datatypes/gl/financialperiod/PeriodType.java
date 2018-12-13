/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.gl.financialperiod;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class PeriodType extends EMCString{

    public PeriodType() {
    	this.setColumnWidth(81);
        this.setEmcLabel("Type");
    }

}
