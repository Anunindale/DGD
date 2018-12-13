/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.inventory.itemranges;

import emc.datatypes.EMCString;

/**
 *
 * @author riaan
 */
public class ItemRangeId extends EMCString {

    /** Creates a new instance of ItemRangeId */
    public ItemRangeId() {
        this.setEmcLabel("Item Range");
        this.setColumnWidth(50);
        this.setMandatory(true);
        this.setEditable(true);
    }
}
