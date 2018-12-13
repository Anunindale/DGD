/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.base.dbconnection;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class UserName extends EMCString{

    public UserName() {
        this.setEmcLabel("User Name");
        this.setMandatory(true);
        this.setStringSize(1000);
        this.setLowerCaseAllowed(true);
    }

}
