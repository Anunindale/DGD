/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.inventory.safetystock;

import emc.datatypes.EMCBigDecimal;

/**
 *
 * @author wikus
 */
public class Quantity extends EMCBigDecimal {

    public Quantity() {
    	this.setColumnWidth(66);
        this.setEmcLabel("Quantity");
    }
}
