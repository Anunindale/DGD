package emc.datatypes.base.companies;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class BankAccount extends EMCString {

    /** Creates a new instance of BankAccount */
    public BankAccount() {
        this.setEmcLabel("Bank Account");
        this.setStringSize(100);
    }
}
