/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.pop.purchasepostlines;

import emc.datatypes.inventory.location.foreignkeys.LocationFK;
import emc.datatypes.inventory.location.foreignkeys.LocationFKNotManditory;

/**
 * @description : Data type for standardLocation on POPPurchasePostLines.
 *
 * @date        : 28 May 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class StandardLocation extends LocationFKNotManditory {

    /** Creates a new instance of StandardLocation */
    public StandardLocation() {
        setColumnWidth(5);
    }
}
