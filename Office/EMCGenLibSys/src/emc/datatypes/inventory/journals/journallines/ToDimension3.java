/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.journals.journallines;

import emc.datatypes.EMCString;
import emc.entity.inventory.dimensions.InventoryDimension3;
import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 *
 * @author riaan
 */
public class ToDimension3 extends EMCString {

    /** Creates a new instance of ToDimension3 */
    public ToDimension3() {
        this.setEmcLabel("Colour");
        this.setColumnWidth(100);
        this.setRelatedField("dimensionId");
        this.setRelatedTable(InventoryDimension3.class.getName());
        this.setDeleteAction(enumDeleteUpdateOptions.RESTRICT);
    }
}
