/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.systemwide;

import emc.datatypes.EMCString;

/**
 *
 * @author riaan
 */
public class Name extends EMCString {

    /** Creates a new instance of Name */
    public Name() {
        this.setEmcLabel("Name");
        this.setColumnWidth(30);
        this.setStringSize(40);
        this.setLowerCaseAllowed(true);
    }
}
