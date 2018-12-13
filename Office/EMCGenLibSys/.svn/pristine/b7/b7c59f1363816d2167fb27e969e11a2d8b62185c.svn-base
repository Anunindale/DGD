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
public class LocationFKNotManditory extends LocationPK {

    public LocationFKNotManditory() {
        this.setNumberSeqAllowed(false);
        this.setMandatory(false);
        this.setRelatedTable(InventoryLocation.class.getName());
    	this.setColumnWidth(55);
        this.setRelatedField("locationId");
    }
}
