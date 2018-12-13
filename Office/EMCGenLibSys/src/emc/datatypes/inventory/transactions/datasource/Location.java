package emc.datatypes.inventory.transactions.datasource;

import emc.datatypes.EMCString;
import emc.datatypes.systemwide.LocationInterface;

/**
 * 
 * @author wikus
 */
public class Location extends EMCString implements LocationInterface {

    /** Creates a new instance of Location */
    public Location() {
        this.setEmcLabel("Location");
        this.setStringSize(60);
    }
}
