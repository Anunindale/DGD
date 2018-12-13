/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.pop.costchange;

import emc.datatypes.EMCDouble;

/**
 *
 * @author wikus
 */
public class POPrice extends EMCDouble {

    public POPrice() {
        this.setEmcLabel("PO Price");
        this.setColumnWidth(50);
    }
}
