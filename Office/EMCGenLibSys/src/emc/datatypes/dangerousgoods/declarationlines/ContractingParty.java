/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.dangerousgoods.declarationlines;

import emc.datatypes.EMCString;
import emc.entity.dangerousgoods.DGDContacts;
import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 *
 * @author pj
 */
public class ContractingParty extends EMCString{
    public ContractingParty()
    {
        this.setEmcLabel("Party contracting the operator");
        this.setColumnWidth(37);
        this.setRelatedTable(DGDContacts.class.getName());
        this.setRelatedField("contactNumber");
        this.setDeleteAction(enumDeleteUpdateOptions.RESTRICT);
        this.setUpdateAction(enumDeleteUpdateOptions.CASCADE);
    }
    
}
