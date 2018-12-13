/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.developertools.bugtracking;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class Documentation extends EMCString {

    public Documentation() {
        this.setEmcLabel("Documentation");
        this.setLowerCaseAllowed(true);
        this.setStringSize(1000);
    }
}
