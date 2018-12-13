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
public class UserId extends EMCString {

    /**
     * Creates a new instance of UniqueIdentifier.
     */
    public UserId() {
        this.setEmcLabel("User");
        this.setColumnWidth(96);
    }
}