/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.dangerousgoods.declarationlines;

import emc.datatypes.EMCString;

/**
 *
 * @author pj
 */
public class ContractingParty extends EMCString{
    public ContractingParty()
    {
        this.setEmcLabel("Party contracting the operator");
        this.setColumnWidth(37);
    }
    
}
