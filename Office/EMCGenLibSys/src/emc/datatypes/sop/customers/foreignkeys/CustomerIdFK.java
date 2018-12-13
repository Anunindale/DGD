/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.sop.customers.foreignkeys;

import emc.datatypes.sop.customers.CustomerId;
import emc.entity.sop.SOPCustomers;
import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 *
 * @author riaan
 */
public class CustomerIdFK extends CustomerId {

    /** Creates a new instance of CustomerId */
    public CustomerIdFK() {
        this.setRelatedField("customerId");
    	this.setColumnWidth(62);
        this.setRelatedTable(SOPCustomers.class.getName());
        this.setNumberSeqAllowed(false);
        this.setUpdateAction(enumDeleteUpdateOptions.CASCADE);
        //Prevent deletion of customers that have been used.
        this.setDeleteAction(enumDeleteUpdateOptions.RESTRICT);
    }
}
