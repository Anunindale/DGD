/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.itemdimensiongroups;

import emc.datatypes.EMCString;

/**
 *
 * @author riaan
 */
public class ItemDimensionGroupId extends EMCString {

    /** Creates a new instance of ItemDimensionGroupId */
    public ItemDimensionGroupId() {
        this.setMandatory(true);
        this.setColumnWidth(100);
        this.setEmcLabel("Dimension Group");
    }
}
