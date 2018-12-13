/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.safetystock;

import emc.datatypes.EMCDate;

/**
 *
 * @author wikus
 */
public class ToDate extends EMCDate{

    public ToDate() {
        this.setEmcLabel("To Date");
    	this.setColumnWidth(87);
        this.setMandatory(true);
    }

}
