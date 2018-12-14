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
public class DefOperator extends EMCString{
    
    public DefOperator()
    {
        this.setEmcLabel("Default Operator");
        this.setLowerCaseAllowed(true);
    }
    
}
