/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.itemmaster;

import emc.datatypes.EMCString;
import emc.entity.pop.POPSuppliers;

/**
 *
 * @author riaan
 */
public class PurchaseSupplier extends EMCString { 

    /** Creates a new instance of PurchaseSupplier */
    public PurchaseSupplier() {
        this.setEmcLabel("Supplier");
        this.setRelatedTable(POPSuppliers.class.getName());
        this.setRelatedField("supplierId");
    }
}
