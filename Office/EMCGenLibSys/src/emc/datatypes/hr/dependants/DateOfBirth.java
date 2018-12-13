/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.hr.dependants;

import emc.datatypes.EMCDate;

/**
 *
 * @author wikus
 */
public class DateOfBirth extends EMCDate{

    public DateOfBirth() {
        this.setEmcLabel("Date Of Birth");
    	this.setColumnWidth(73);
        //this.setMandatory(true);
    }


}
