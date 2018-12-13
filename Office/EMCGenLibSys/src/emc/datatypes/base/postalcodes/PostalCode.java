/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.base.postalcodes;

import emc.datatypes.EMCString;

/**
 *
 * @author riaan
 */
public class PostalCode extends EMCString {

    /** Creates a new instance of PostalCode */
    public PostalCode() {
        this.setEmcLabel("Postal Code");
        this.setMandatory(true);
    }
}
