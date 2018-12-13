/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.inventory.location;

import emc.datatypes.EMCString;
import emc.datatypes.systemwide.LocationInterface;

/**
 *
 * @author wikus
 */
public class LocationPK extends EMCString implements LocationInterface {

    public LocationPK() {
        this.setColumnWidth(60);
        this.setEmcLabel("Location");
        this.setMandatory(true);
    }
}
