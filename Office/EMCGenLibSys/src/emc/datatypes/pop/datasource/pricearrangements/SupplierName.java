package emc.datatypes.pop.datasource.pricearrangements;

import emc.datatypes.EMCString;

/** 
*
* @author riaan
*/
public class SupplierName extends EMCString{

    /** Creates a new instance of SupplierName. */
    public SupplierName() {
        this.setEmcLabel("Name");
    	this.setColumnWidth(105);
        this.setEditable(false);
    }
}
