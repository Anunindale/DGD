package emc.datatypes.inventory.transactions.datasource;

import emc.datatypes.EMCString;
import emc.datatypes.systemwide.WarehouseInterface;

/**
 * 
 * @author wikus
 */
public class Warehouse extends EMCString implements WarehouseInterface {

    /** Creates a new instance of Warehouse */
    public Warehouse() {
        this.setEmcLabel("Warehouse");
        this.setStringSize(20);
    }
}
