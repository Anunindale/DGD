/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.transactions;

import emc.datatypes.EMCDouble;

/**
 *
 * @author rico
 */
public class OrderedOut extends EMCDouble{
    public OrderedOut(){
        this.setEmcLabel("QtyOut");
        this.setNumberDecimalsDisplay(2);
        this.setAllowSort(false);
    }

}
