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
public class QtyOnHand extends EMCDouble{
    public QtyOnHand(){
        this.setEmcLabel("Count QOH");
    	this.setColumnWidth(91);
        this.setMaxValue(1000000000);
    }
}
