/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.base.mailsetup;

import emc.datatypes.EMCString;

/**
 *
 * @author rico
 */
public class ReturnMailAddress extends EMCString {

    public ReturnMailAddress() {
        this.setEmcLabel("Return Mail Address");
        this.setLowerCaseAllowed(true);
        this.setColumnWidth(125);
        this.setStringSize(100);
        this.setMandatory(true);
    }
}
