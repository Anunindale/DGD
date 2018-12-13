/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.base.dbshowlog;

import emc.datatypes.EMCString;

/**
 *
 * @author asd_admin
 */
public class SessionId extends EMCString {

    public SessionId() {
        this.setEmcLabel("Session Id");
        this.setLowerCaseAllowed(true);
        this.setStringSize(10000);
    }
}