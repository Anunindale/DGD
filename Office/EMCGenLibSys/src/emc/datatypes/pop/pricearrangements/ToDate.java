package emc.datatypes.pop.pricearrangements;

import emc.datatypes.EMCDate;

/** 
*
* @author riaan
*/
public class ToDate extends EMCDate{

    /** Creates a new instance of ToDate. */
    public ToDate() {
    	this.setColumnWidth(71);
        this.setEmcLabel("To Date");
        this.setMandatory(true);
    }
}
