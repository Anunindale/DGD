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
public class Company extends EMCString{
    
    public Company()
    {
        this.setEmcLabel("Company");
        this.setLowerCaseAllowed(true);
        this.setMandatory(true);
    }
    
}
