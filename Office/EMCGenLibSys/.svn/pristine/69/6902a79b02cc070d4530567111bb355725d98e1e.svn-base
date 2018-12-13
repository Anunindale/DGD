/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.inventory.inventoryreference;

import emc.datatypes.EMCString;
import emc.entity.pop.POPSuppliers;

/**
 *
 * @author wikus
 */
public class SupplierNumber extends EMCString {

    /** Creates a new instance of SupplierNumber */
    public SupplierNumber() {
        this.setEmcLabel("Supplier No");
        this.setRelatedTable(POPSuppliers.class.getName());
        this.setRelatedField("supplierId");
        this.setEditable(true);
        this.setStringSize(50);
    }
}
