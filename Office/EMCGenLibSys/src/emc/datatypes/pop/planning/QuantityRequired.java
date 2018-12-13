/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.pop.planning;

import emc.datatypes.EMCBigDecimal;

/**
 *
 * @author wikus
 */
public class QuantityRequired extends EMCBigDecimal {

    public QuantityRequired() {
    	this.setColumnWidth(60);
        this.setEmcLabel("Quantity");
    }
}
