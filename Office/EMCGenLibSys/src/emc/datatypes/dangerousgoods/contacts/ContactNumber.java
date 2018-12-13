/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.dangerousgoods.contacts;

import emc.datatypes.EMCString;

/**
 *
 * @author pj
 */
public class ContactNumber extends EMCString {

    public ContactNumber()
    {
        this.setEmcLabel("Contact Number");
        this.setMandatory(true);
        this.setNumberSeqAllowed(true);
    }
}
