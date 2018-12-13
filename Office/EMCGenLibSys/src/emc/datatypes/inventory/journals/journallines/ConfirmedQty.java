/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.journals.journallines;

import emc.datatypes.EMCDouble;

/**
 *
 * @author rico
 */
public class ConfirmedQty extends EMCDouble {
    public ConfirmedQty(){
        this.setEmcLabel("Confirmed Quantity");
        this.setMaxValue(1000000000);
    }
}
