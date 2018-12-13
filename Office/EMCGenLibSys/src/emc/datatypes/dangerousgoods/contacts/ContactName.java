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
public class ContactName extends EMCString{
    public ContactName()
    {
        this.setEmcLabel("Contact Name");
        this.setLowerCaseAllowed(true);
        this.setMandatory(true);
    }
}
