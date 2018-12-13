/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.sop.customers.foreignkeys;


import emc.datatypes.creditors.transactions.CustomerID;
import emc.datatypes.sop.customers.CustomerId;
import emc.entity.base.BaseEmployeeTable;
import emc.entity.sop.SOPCustomers;
import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 *
 * @author riaan
 */
public class CustmerIDFKNotMandatory extends CustomerId {

    /** Creates a new instance of CustomerId */
    public CustmerIDFKNotMandatory() {
        this.setNumberSeqAllowed(false);
        this.setMandatory(false);
        this.setRelatedField("customerId");
        this.setRelatedTable(SOPCustomers.class.getName());
        this.setNumberSeqAllowed(false);
    }
}
