/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.base.parameters;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class FTPServer extends EMCString {

    public FTPServer() {
        this.setEmcLabel("FTP Server");
        this.setLowerCaseAllowed(true);
        this.setStringSize(1000);
    }
}
