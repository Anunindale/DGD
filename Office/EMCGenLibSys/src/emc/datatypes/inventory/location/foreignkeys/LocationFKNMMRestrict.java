/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.location.foreignkeys;

import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 *
 * @author riaan
 */
public class LocationFKNMMRestrict extends LocationFKNotManditory {

    /** Creates a new instance of LocationFKNMMRestrict. */
    public LocationFKNMMRestrict() {
        this.setDeleteAction(enumDeleteUpdateOptions.RESTRICT);
        this.setUpdateAction(enumDeleteUpdateOptions.RESTRICT);
    }
}
