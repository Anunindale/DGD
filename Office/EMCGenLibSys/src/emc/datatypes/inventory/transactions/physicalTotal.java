/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.inventory.transactions;

import emc.datatypes.EMCDouble;

/**
 *
 * @author wikus
 */
public class physicalTotal extends EMCDouble {

    /** Creates a new instance of physicalTotal */
    public physicalTotal() {
        this.setEmcLabel("PhyQty");
        this.setNumberDecimalsDisplay(2);
        this.setAllowSort(false);
        this.setMaxValue(1000000000);
    }
}
