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
public class Port extends EMCString{

    public Port() {
        this.setEmcLabel("Port");
        this.setMandatory(true);
        this.setStringSize(1000);
        this.setLowerCaseAllowed(true);
    }

}
