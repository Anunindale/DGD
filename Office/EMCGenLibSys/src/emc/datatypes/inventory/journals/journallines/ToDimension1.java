/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.journals.journallines;

import emc.datatypes.EMCString;
import emc.entity.inventory.dimensions.InventoryDimension1;
import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 *
 * @author riaan
 */
public class ToDimension1 extends EMCString {

    /** Creates a new instance of ToDimension1 */
    public ToDimension1() {
        this.setEmcLabel("Config");
        this.setColumnWidth(100);
        this.setRelatedField("dimensionId");
        this.setRelatedTable(InventoryDimension1.class.getName());
        this.setDeleteAction(enumDeleteUpdateOptions.RESTRICT);
    }
}