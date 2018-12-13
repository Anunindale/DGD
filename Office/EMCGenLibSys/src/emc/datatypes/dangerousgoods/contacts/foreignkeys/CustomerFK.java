/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.dangerousgoods.contacts.foreignkeys;

import emc.datatypes.sop.customers.CustomerId;
import emc.entity.sop.SOPCustomers;
import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 *
 * @author pj
 */
public class CustomerFK extends CustomerId {
    
    public CustomerFK()
    {
        this.setEmcLabel("Customer");
        this.setNumberSeqAllowed(false);
        this.setRelatedTable(SOPCustomers.class.getName());
        this.setRelatedField("customerId");
        this.setDeleteAction(enumDeleteUpdateOptions.CLEARFIELD);
        this.setUpdateAction(enumDeleteUpdateOptions.CASCADE);
    }       
}
