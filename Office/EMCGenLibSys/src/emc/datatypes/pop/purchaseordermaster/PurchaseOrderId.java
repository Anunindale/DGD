/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.pop.purchaseordermaster;

import emc.datatypes.EMCString;

/**
 *
 * @author riaan
 */
public class PurchaseOrderId extends EMCString {

    /** Creates a new instance of PurchaseOrderId */
    public PurchaseOrderId() {
        this.setColumnWidth(35);
        this.setEmcLabel("Purchase Order");
        this.setMandatory(true);
        this.setNumberSeqAllowed(true);
    }
}
