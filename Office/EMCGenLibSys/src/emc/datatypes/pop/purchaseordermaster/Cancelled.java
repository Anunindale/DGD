/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.pop.purchaseordermaster;

import emc.datatypes.EMCBoolean;

/**
 *
 * @author riaan
 */
public class Cancelled extends EMCBoolean {

    /** Creates a new instance of Cancelled. */
    public Cancelled() {
        this.setEditable(false);
        this.setEmcLabel("C");
        this.setColumnWidth(3);
    }
}
