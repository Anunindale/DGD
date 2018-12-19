/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.dangerousgoods.declarationlines.foreignkeys;

import emc.datatypes.dangerousgoods.declarationmaster.DecNumber;
import emc.entity.dangerousgoods.DGDeclarationLines;
import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 *
 * @author asd_admin
 */
public class DecNumberFK extends DecNumber{
    
    public DecNumberFK()
    {
        this.setRelatedTable(DGDeclarationLines.class.getName());
        this.setRelatedField("decNumber");
        this.setDeleteAction(enumDeleteUpdateOptions.CASCADE);
        this.setUpdateAction(enumDeleteUpdateOptions.CASCADE);
        this.setNumberSeqAllowed(false);
        this.setMandatory(true);
        this.setEmcLabel("Number");
    }
    
}
