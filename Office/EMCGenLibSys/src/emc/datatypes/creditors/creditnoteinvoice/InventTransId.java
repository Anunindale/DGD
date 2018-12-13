/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.creditors.creditnoteinvoice;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class InventTransId extends EMCString {

    public InventTransId() {
        this.setEmcLabel("Inventory Transaction Id");
        this.setNumberSeqAllowed(true);
        this.setMandatory(true);
    }
}
