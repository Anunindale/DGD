/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.dangerousgoods.declarationlines.foreignkeys;

import emc.datatypes.EMCString;
import emc.entity.dangerousgoods.DGDeclarationMaster;
import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 *
 * @author pj
 */
public class CustomerFK extends EMCString{
    public CustomerFK()
    {
        this.setNumberSeqAllowed(false);
        this.setRelatedTable(DGDeclarationMaster.class.getName());
        this.setRelatedField("customer");
        this.setDeleteAction(enumDeleteUpdateOptions.CASCADE);
        this.setUpdateAction(enumDeleteUpdateOptions.CASCADE);
    }
}
