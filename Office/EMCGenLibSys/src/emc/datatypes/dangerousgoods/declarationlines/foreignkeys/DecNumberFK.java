/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.dangerousgoods.declarationlines.foreignkeys;

import emc.datatypes.dangerousgoods.declarationmaster.DecNumber;
import emc.entity.dangerousgoods.DGDeclarationMaster;
import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 *
 * @author asd_admin
 */
public class DecNumberFK extends DecNumber{
    
    public DecNumberFK()
    {
        this.setRelatedTable(DGDeclarationMaster.class.getName());
        this.setRelatedField("decNumber");
        this.setDeleteAction(enumDeleteUpdateOptions.RESTRICT);
        this.setUpdateAction(enumDeleteUpdateOptions.CASCADE);
        this.setNumberSeqAllowed(false);
        this.setMandatory(true);
        this.setEmcLabel("Declaration Number");
    }
    
}
