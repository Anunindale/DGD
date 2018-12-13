/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.parameters;

import emc.datatypes.EMCBoolean;

/**
 * @description Data type for the allowDim1NotOnGroup on the InventoryParameters entity.
 *
 * @version     1.0 6 April 2010
 *
 * @author      Riaan Nel
 */
public class AllowDim1NotOnGroup extends EMCBoolean {

    /** Creates a new instance of AllowDim1NotOnGroup */
    public AllowDim1NotOnGroup() {
        this.setEmcLabel("Allow Non-Group Item Configuarations");
    }
}
