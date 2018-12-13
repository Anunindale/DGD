/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.debtors.parameters;

import emc.datatypes.inventory.location.foreignkeys.LocationFK;

/**
 *
 * @author riaan
 */
public class DefaultReturnLocation extends LocationFK {

    /** Creates a new instance of DefaultReturnLocation. */
    public DefaultReturnLocation() {
        this.setEmcLabel("Default Return Location");
    }
}
