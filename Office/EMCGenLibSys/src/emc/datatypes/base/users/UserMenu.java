package emc.datatypes.base.users;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class UserMenu extends EMCString {

    /** Creates a new instance of UserMenu */
    public UserMenu() {
        this.setEmcLabel("User Menu");
        this.setEditable(true);
        this.setStringSize(1000);
    }
}
