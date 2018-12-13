/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.developertools.sites;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class UserName extends EMCString {

    public UserName() {
        this.setEmcLabel("User Name");
        this.setLowerCaseAllowed(true);
        this.setStringSize(500);
    }
}
