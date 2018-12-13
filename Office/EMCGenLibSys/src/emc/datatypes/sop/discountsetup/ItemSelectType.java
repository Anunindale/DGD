/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.sop.discountsetup;

import emc.datatypes.EMCString;

/**
 *
 * @author riaan
 */
public class ItemSelectType extends EMCString {

    /** Creates a new instance of ItemSelectType. */
    public ItemSelectType() {
        this.setEmcLabel("Item Sel. Type");
        this.setMandatory(true);
    }
}
