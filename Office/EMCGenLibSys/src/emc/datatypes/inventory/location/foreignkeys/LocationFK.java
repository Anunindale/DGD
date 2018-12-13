/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.inventory.location.foreignkeys;

import emc.datatypes.inventory.location.LocationPK;
import emc.entity.inventory.InventoryLocation;

/**
 *
 * @author wikus
 */
public class LocationFK extends LocationPK {

    public LocationFK() {
        this.setNumberSeqAllowed(false);
        this.setRelatedTable(InventoryLocation.class.getName());
        this.setRelatedField("locationId");
        
    }
}
