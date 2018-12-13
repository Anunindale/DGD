/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.inventory.inventoryreference;

import emc.datatypes.EMCString;
import emc.entity.sop.SOPCustomers;

/**
 *
 * @author wikus
 */
public class CustomerNumber extends EMCString {

    /** Creates a new instance of CustomerNumber */
    public CustomerNumber() {
        this.setEmcLabel("Customer No");
        this.setEditable(true);
        this.setStringSize(50);
        this.setRelatedTable(SOPCustomers.class.getName());
        this.setRelatedField("customerId");
    }
}
