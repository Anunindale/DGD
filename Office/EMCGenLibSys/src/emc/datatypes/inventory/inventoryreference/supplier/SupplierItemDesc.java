/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.inventory.inventoryreference.supplier;

import emc.datatypes.systemwide.Description;

/**
 *
 * @author wikus
 */
public class SupplierItemDesc extends Description {

    /** Creates a new instance of SupplierItemDesc */
    public SupplierItemDesc() {
        this.setEmcLabel("Supp. Specification");
        this.setEditable(true);
        this.setColumnWidth(120);
        this.setStringSize(40);
    }
}
