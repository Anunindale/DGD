/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.dimension1groups;

import emc.datatypes.EMCString;

/**
 *
 * @author riaan
 */
public class Dimension1GroupId extends EMCString {

    /** Creates a new instance of Dimension1GroupId */
    public Dimension1GroupId() {
        this.setMandatory(true);
        this.setEmcLabel("Config Group Id");
    }
}