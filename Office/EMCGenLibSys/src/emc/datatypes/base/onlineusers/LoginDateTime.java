package emc.datatypes.base.onlineusers;

import emc.datatypes.EMCDate;

/**
 *
 * @author wikus
 */
public class LoginDateTime extends EMCDate {

    /** Creates a new instance of LoginDateTime */
    public LoginDateTime() {
        this.setEmcLabel("Login Date");
        this.setMandatory(true);
    }
}
