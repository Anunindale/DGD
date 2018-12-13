/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.debtors.customerinvoice;

import emc.datatypes.EMCString;

/**
 *
 * @author riaan
 */
public class InventTransId extends EMCString {

    /** Creates a new instance of InventTransId */
    public InventTransId() {
        this.setEmcLabel("Inventory Transaction Id");
        this.setNumberSeqAllowed(true);
        this.setMandatory(true);
    }
}
