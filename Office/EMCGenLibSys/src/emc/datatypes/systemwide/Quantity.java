/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.systemwide;

import emc.datatypes.EMCDouble;

/**
 *
 * @author riaan
 */
public class Quantity extends EMCDouble {

    /** Creates a new instance of Quantity. */
    public Quantity() {
        this.setEmcLabel("Qty");
        this.setColumnWidth(40);
    }
}
