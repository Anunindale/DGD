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
public class Description extends EMCString {

    /** Creates a new instance of Description */
    public Description() {
        this.setEmcLabel("Description");
        this.setStringSize(100);
        this.setLowerCaseAllowed(true);
    }
}
