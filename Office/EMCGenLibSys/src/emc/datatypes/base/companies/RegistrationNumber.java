package emc.datatypes.base.companies;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class RegistrationNumber extends EMCString {

    /** Creates a new instance of RegistrationNumber */
    public RegistrationNumber() {
        this.setEmcLabel("Reg No");
        this.setStringSize(50);
        this.setMandatory(true);
    }
}
