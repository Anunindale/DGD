package emc.datatypes.inventory.transactions;

import emc.datatypes.EMCString;

/**
 * 
 * @author wikus
 */
public class TransId extends EMCString {

    /** Creates a new instance of TransId */
    public TransId() {
       this.setEmcLabel("Transaction Id");
       this.setMandatory(true);
    }
}
