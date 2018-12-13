package emc.datatypes.inventory.transactions;

import emc.datatypes.EMCString;

/**
 * 
 * @author wikus
 */
public class Direction extends EMCString {

    /** Creates a new instance of Direction */
    public Direction() {
        this.setEmcLabel("D");
        this.setMandatory(true);
        this.setEditable(true);
        this.setStringSize(10);
        this.setColumnWidth(17);
    }
}
