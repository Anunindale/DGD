/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.dangerousgoods.vehicles.datasource;

import emc.datatypes.EMCString;

/**
 *
 * @author asd_admin
 */
public class CompanyName extends EMCString{
    
    public CompanyName()
    {
        this.setEmcLabel("Company Name");
        this.setLowerCaseAllowed(true);
    }
    
}
