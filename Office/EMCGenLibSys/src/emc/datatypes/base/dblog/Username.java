/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.base.dblog;

import emc.datatypes.EMCString;

/**
 *
 * @author riaan
 */
public class Username extends EMCString {

    /** Creates a new instance of Username. */
    public Username() {
    	this.setColumnWidth(56);
        this.setEmcLabel("User");
    }
}
