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
public class orderedReserved extends EMCDouble {

    /** Creates a new instance of physicalTotal */
    public orderedReserved() {
        this.setEmcLabel("O Reserved");
        this.setNumberDecimalsDisplay(2);
        this.setAllowSort(false);
    }
}
