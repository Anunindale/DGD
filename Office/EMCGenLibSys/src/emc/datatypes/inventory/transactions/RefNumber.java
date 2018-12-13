package emc.datatypes.inventory.transactions;

import emc.datatypes.EMCString;

/**
 * 
 * @author wikus
 */
public class RefNumber extends EMCString {

    /** Creates a new instance of RefNumber */
    public RefNumber() {
    	this.setColumnWidth(62);
       this.setEmcLabel("Ref");
    }
}
