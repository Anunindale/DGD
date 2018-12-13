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
public class DriverClass extends EMCString{

    public DriverClass() {
        this.setEmcLabel("Driver");
        this.setMandatory(true);
        this.setStringSize(1000);
        this.setLowerCaseAllowed(true);
    }

}
