package emc.datatypes.base.companies;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class BankBranch extends EMCString {

    /** Creates a new instance of BankBranch */
    public BankBranch() {
        this.setEmcLabel("Bank Branch");
        this.setStringSize(100);
    }
}
