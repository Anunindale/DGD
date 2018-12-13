/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.journals.journallines;

import emc.datatypes.EMCString;
import emc.entity.inventory.dimensions.InventoryDimension2;
import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 *
 * @author riaan
 */
public class ToDimension2 extends EMCString {

    /** Creates a new instance of ToDimension2 */
    public ToDimension2() {
        this.setEmcLabel("Size");
        this.setColumnWidth(100);
        this.setRelatedField("dimensionId");
        this.setRelatedTable(InventoryDimension2.class.getName());
        this.setDeleteAction(enumDeleteUpdateOptions.RESTRICT);
    }
}
