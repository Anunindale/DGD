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
public class EmailAddress extends EMCString {

    /** Creates a new instance of EmailAddress */
    public EmailAddress() {
        this.setEmcLabel("Email Address");
        this.setColumnWidth(100);
        this.setStringSize(100);
        this.setLowerCaseAllowed(true);
        this.setMandatory(false);
    }
}
