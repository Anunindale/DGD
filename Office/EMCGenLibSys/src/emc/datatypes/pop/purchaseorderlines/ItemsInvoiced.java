/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.pop.purchaseorderlines;

import emc.datatypes.systemwide.PositiveDouble;

/**
 *
 * @author riaan
 */
public class ItemsInvoiced extends PositiveDouble {

    /** Creates a new instance of ItemsInvoiced */
    public ItemsInvoiced() {
        this.setEmcLabel("Items Invoiced");
        this.setNumberDecimalsDisplay(2);
    }
}
