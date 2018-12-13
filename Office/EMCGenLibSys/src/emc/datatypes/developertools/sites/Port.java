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
public class Port extends EMCString {

    public Port() {
        this.setEmcLabel("Port");
        this.setLowerCaseAllowed(true);
        this.setStringSize(255);
    }
}
