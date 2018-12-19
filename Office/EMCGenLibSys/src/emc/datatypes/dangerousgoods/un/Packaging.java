/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.dangerousgoods.un;

import emc.datatypes.EMCString;

/**
 *
 * @author pj
 */
public class Packaging extends EMCString {
    public Packaging()
    {
        this.setEmcLabel("Quantity and packaging type");
        this.setLowerCaseAllowed(true);
    }
}
