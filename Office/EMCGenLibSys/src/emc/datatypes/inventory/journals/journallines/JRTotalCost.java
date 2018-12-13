/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.journals.journallines;

import emc.datatypes.systemwide.Quantity;

/**
 *
 * @author rico
 */
public class JRTotalCost extends Quantity {
    public JRTotalCost(){
        this.setEmcLabel("Amount");
        this.setMaxValue(10e12);
        this.setMinValue(-10e12);
    }

}
