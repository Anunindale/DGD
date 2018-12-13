package emc.datatypes.workflow.activities;

import emc.datatypes.EMCString;

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
    }
}
