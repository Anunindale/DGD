package emc.datatypes.base.users;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class UserPassword extends EMCString {

    /** Creates a new instance of UserPassword */
    public UserPassword() {
        this.setEmcLabel("User Password");
        this.setMandatory(true);
        this.setStringSize(50);
    }
}
