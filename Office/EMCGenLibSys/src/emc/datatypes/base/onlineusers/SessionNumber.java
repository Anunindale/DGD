/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.base.onlineusers;

import emc.datatypes.EMCString;

/**
 *
 * @author riaan
 */
public class SessionNumber extends EMCString {

    /** Creates a new instance of SessionNumber */
    public SessionNumber() {
        this.setEmcLabel("Session Number");
        this.setColumnWidth(5);
    }
}
