/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.inventory.itemgroup;

import emc.datatypes.EMCDouble;

/**
 *
 * @author wikus
 */
public class InventoryCostingScrap extends EMCDouble {

    public InventoryCostingScrap() {
        this.setEmcLabel("Costing Scrap %");
        this.setNumberDecimalsDisplay(2);
        this.setNumberDecimalsInput(2);
    }
}
