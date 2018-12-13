/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.pop.purchaseorderlines;

import emc.datatypes.EMCDouble;

/**
 *
 * @author riaan
 */
public class NetAmount extends EMCDouble { 

    /** Creates a new instance of NetAmount */
    public NetAmount() {
        this.setEmcLabel("Net Amount");
        this.setColumnWidth(50);
        this.setEditable(true);
    }
}
