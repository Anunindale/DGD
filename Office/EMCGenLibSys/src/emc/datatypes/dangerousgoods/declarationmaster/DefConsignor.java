/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.dangerousgoods.declarationmaster;

import emc.datatypes.EMCString;

/**
 *
 * @author pj
 */
public class DefConsignor extends EMCString{
    
    public DefConsignor()
    {
        this.setEmcLabel("Default Consignor");
        this.setLowerCaseAllowed(true);
    }
    
}
