package emc.datatypes.base.onlineusers;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class UserName extends EMCString {

    /** Creates a new instance of UserName */
    public UserName() {
        this.setEmcLabel("User Name");
        this.setMandatory(true);
        this.setStringSize(100);
    }
}
