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
public class Password extends EMCString {

    public Password() {
        this.setEmcLabel("Password");
        this.setLowerCaseAllowed(true);
        this.setStringSize(500);
    }
}
