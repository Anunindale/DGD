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
public class ConnectionDescription extends EMCString {

    public ConnectionDescription() {
        this.setEmcLabel("Connection");
        this.setMandatory(true);
        this.setLowerCaseAllowed(true);
        this.setStringSize(500);
    }
}
