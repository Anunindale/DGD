/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.creditors.creditnoteinvoice;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class Comments extends EMCString {

    public Comments() {
        this.setEmcLabel("Comments");
        this.setStringSize(1000);
        this.setLowerCaseAllowed(true);
    }
}
