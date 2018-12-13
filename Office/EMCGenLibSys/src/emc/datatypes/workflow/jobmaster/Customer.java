package emc.datatypes.workflow.jobmaster;

import emc.datatypes.workflow.activities.*;
import emc.datatypes.EMCString;
import emc.entity.sop.SOPCustomers;

/**
 *
 * @author wikus
 */
public class Customer extends EMCString {

    /** Creates a new instance of Customer */
    public Customer() {
        this.setEmcLabel("Customer");
        this.setEditable(true);
        this.setStringSize(50);
        this.setRelatedTable(SOPCustomers.class.getName());
        this.setRelatedField("customerId");
    }
}
