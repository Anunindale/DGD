/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.hr.diciplaneryaction;

import emc.datatypes.EMCDate;

/**
 *
 * @author wikus
 */
public class OffenceDate extends EMCDate {

    public OffenceDate() {
        this.setEmcLabel("Offence Date");
    	this.setColumnWidth(80);
        this.setMandatory(true);
    }


}
