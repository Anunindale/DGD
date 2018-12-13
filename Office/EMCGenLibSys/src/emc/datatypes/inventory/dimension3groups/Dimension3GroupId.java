/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.dimension3groups;

import emc.datatypes.EMCString;

/**
 *
 * @author riaan
 */
public class Dimension3GroupId extends EMCString {

    /** Creates a new instance of Dimension3GroupId */
    public Dimension3GroupId() {
        this.setMandatory(true);
        this.setEmcLabel("Colour Group Id");
    }
}