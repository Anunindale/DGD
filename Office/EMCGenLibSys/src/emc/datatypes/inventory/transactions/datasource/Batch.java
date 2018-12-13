package emc.datatypes.inventory.transactions.datasource;

import emc.datatypes.EMCString;
import emc.datatypes.systemwide.BatchInterface;

/**
 * 
 * @author wikus
 */
public class Batch extends EMCString implements BatchInterface {

    /** Creates a new instance of Batch */
    public Batch() {
        this.setEmcLabel("Batch");
        this.setStringSize(20);
        this.setColumnWidth(62);
    }
}
