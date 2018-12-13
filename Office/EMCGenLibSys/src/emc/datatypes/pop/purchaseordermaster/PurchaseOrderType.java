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
public class PurchaseOrderType extends EMCString {
    
    /** Creates a new instance of PurchaseOrderType */
    public PurchaseOrderType() {
        this.setEmcLabel("Type");
        this.setColumnWidth(70);
        this.setMandatory(true);
    }
}
