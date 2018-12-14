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
public class Consignee extends EMCString{
    public Consignee()
    {
        this.setEmcLabel("Consignee");
        this.setMandatory(true);
    }
}
