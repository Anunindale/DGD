/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.base.dblogsetup;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class ActionValue extends EMCString {

    public ActionValue() {
        this.setEmcLabel("Value");
        this.setLowerCaseAllowed(true);
        this.setStringSize(1000);
    }

}
