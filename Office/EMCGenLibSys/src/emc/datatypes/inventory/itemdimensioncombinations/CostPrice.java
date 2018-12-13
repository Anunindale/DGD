/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.itemdimensioncombinations;

import emc.datatypes.EMCDouble;

/**
 *
 * @author riaan
 */
public class CostPrice extends EMCDouble {

    /** Creates a new instance of CostPrice */
    public CostPrice() {
        this.setEmcLabel("Cost Price");
        this.setColumnWidth(50);
    }
}
