/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.itemclassification.itembatch;

import emc.datatypes.EMCString;

/**
 *
 * @author riaan
 */
public class ItemBatchId extends EMCString {

    /** Creates a new instance of ItemBatchId */
    public ItemBatchId() {
        this.setEmcLabel("Item Batch");
        this.setMandatory(true);
        this.setColumnWidth(50);
    }
}
