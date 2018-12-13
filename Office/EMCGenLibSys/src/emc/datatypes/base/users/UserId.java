/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.base.users;

import emc.datatypes.EMCString;

/**
 *
 * @author riaan
 */
public class UserId extends EMCString {

    /** Creates a new instance of UserId */
    public UserId() {
        this.setEmcLabel("User Id");
        this.setMandatory(true);
        this.setStringSize(10);
    }
}
