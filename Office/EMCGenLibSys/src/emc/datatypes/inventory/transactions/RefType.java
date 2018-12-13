package emc.datatypes.inventory.transactions;

import emc.datatypes.EMCString;

/**
 * 
 * @author wikus
 */
public class RefType extends EMCString {

    /** Creates a new instance of RefType */
    public RefType() {
        this.setEmcLabel("Ref Type");
    	this.setColumnWidth(32);
        this.setMandatory(true);
    }
}
