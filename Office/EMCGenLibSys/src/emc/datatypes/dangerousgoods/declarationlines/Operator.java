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
public class Operator extends EMCString{
    public Operator()
    {
        this.setEmcLabel("Operator");
        this.setMandatory(true);
    }
}
