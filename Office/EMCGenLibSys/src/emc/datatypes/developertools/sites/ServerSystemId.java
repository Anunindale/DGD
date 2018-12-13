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
public class ServerSystemId extends EMCString{

    public ServerSystemId() {
        this.setStringSize(255);
        this.setEmcLabel("Server System Id");
        this.setLowerCaseAllowed(true);
    }

}
