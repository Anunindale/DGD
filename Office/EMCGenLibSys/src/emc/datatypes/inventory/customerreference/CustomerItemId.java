/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.customerreference;

import emc.datatypes.EMCString;

/**
 * @description Data type for the customerItemId field on the InventoryCustomerReference entity.
 *
 * @version     1.0 6 April 2010
 *
 * @author      Riaan Nel
 */
public class CustomerItemId extends EMCString {

    /** Creates a new instance of CustomerItemId */
    public CustomerItemId() {
        this.setEmcLabel("Customer Item Ref");
        this.setMandatory(true);
    }
}
