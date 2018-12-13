/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.debtors.journals;

import emc.datatypes.EMCDate;

/**
 *
 * @author riaan
 */
public class LineDate extends EMCDate {

    /** Creates a new instance of LineDate. */
    public LineDate() {
        this.setEmcLabel("Line Date");
    	this.setColumnWidth(78);
        this.setMandatory(true);
    }
}
