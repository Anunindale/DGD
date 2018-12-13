/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.inventory.customerreference;

import emc.datatypes.inventory.inventoryreference.supplier.*;
import emc.datatypes.systemwide.Description;

/**
 *
 * @author wikus
 */
public class CustomerItemDesc extends Description {

    /** Creates a new instance of SupplierItemDesc */
    public CustomerItemDesc() {
        this.setEmcLabel("Cust. Specification");
        this.setEditable(true);
        this.setColumnWidth(120);
        this.setStringSize(40);
    }
}
