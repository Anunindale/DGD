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
public class DecNumber extends EMCString{
    
    public DecNumber()
    {
        this.setNumberSeqAllowed(true);
        this.setMandatory(true);
        this.setEmcLabel("Number");
    }
    
}
