/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.inventory.movestock;

import emc.datatypes.EMCDouble;

/**
 *
 * @author wikus
 */
public class AvailableQty extends EMCDouble {

    public AvailableQty() {
        this.setEmcLabel("Qty Available");
        this.setColumnWidth(40);
    }
}
