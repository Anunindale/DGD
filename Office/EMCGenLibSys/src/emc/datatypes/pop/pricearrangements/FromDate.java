package emc.datatypes.pop.pricearrangements;

import emc.datatypes.EMCDate;

/** 
*
* @author riaan
*/
public class FromDate extends EMCDate{

    /** Creates a new instance of FromDate. */
    public FromDate() {
    	this.setColumnWidth(67);
        this.setEmcLabel("From Date");
        this.setMandatory(true);
    }
}
