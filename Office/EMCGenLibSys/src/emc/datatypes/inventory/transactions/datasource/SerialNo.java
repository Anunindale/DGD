package emc.datatypes.inventory.transactions.datasource;

import emc.datatypes.EMCString;
import emc.datatypes.systemwide.SerialNoInterface;

/**
 * 
 * @author wikus
 */
public class SerialNo extends EMCString implements SerialNoInterface {

    /** Creates a new instance of SerialNo */
    public SerialNo() {
        this.setEmcLabel("Serial No");
        this.setStringSize(20);
        this.setColumnWidth(50);
    }
}
