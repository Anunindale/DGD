package emc.datatypes.inventory.journals.journallines;

import emc.datatypes.inventory.transactions.datasource.*;
import emc.datatypes.EMCString;
import emc.datatypes.systemwide.SerialNoInterface;

/**
 * 
 * @author wikus
 */
public class SerialNumber extends EMCString implements SerialNoInterface {

    /** Creates a new instance of SerialNo */
    public SerialNumber() {
        this.setEmcLabel("Serial No");
    	this.setColumnWidth(35);
        this.setStringSize(20);
    }
}
