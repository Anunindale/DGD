/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.inventory.itemmaster;

import emc.datatypes.EMCBigDecimal;

/**
 *
 * @author Owner
 */
public class SafetyStock extends EMCBigDecimal {

    public SafetyStock() {
        this.setEmcLabel("Safety Stock");
        this.setMandatory(false);
    }
}
