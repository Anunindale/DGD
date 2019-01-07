/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.dangerousgoods.declarationmaster;

import emc.datatypes.EMCString;
import emc.entity.dangerousgoods.DGDContacts;
import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 *
 * @author pj
 */
public class DefOperator extends EMCString{
    
    public DefOperator()
    {
        this.setEmcLabel("Default Operator");
        this.setLowerCaseAllowed(true);
        this.setRelatedTable(DGDContacts.class.getName());
        this.setRelatedField("contactNumber");
        this.setDeleteAction(enumDeleteUpdateOptions.RESTRICT);
        this.setUpdateAction(enumDeleteUpdateOptions.CASCADE);
    }
    
}
