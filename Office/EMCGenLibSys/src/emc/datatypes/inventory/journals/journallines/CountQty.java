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
public class CountQty extends EMCDouble{
    public CountQty(){
        this.setEmcLabel("Counted Qty");
    	this.setColumnWidth(81);
        this.setMaxValue(1000000000);
    }
}
