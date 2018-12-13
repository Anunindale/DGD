/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.base.journals.superclass;

import emc.datatypes.EMCDate;

/**
 * @description : Data type for enteredDate on Debtors
 *
 * @date        : 03 May 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class EnteredDate extends EMCDate {

    /** Creates a new instance of EnteredDate */
    public EnteredDate() {
    	this.setColumnWidth(88);
        this.setEmcLabel("Entered Date");
    }
}
