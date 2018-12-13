/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.dimensions.foreignkeys;

import emc.datatypes.inventory.dimension2.Dimension2;
import emc.entity.inventory.dimensions.InventoryDimension2;
import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 *
 * @author riaan
 */
public class Dimension2FK extends Dimension2 {

    /** Creates a new instance of Dimension2FK */
    public Dimension2FK() {
        //this.setMandatory(false);
        this.setRelatedField("dimensionId");
        this.setRelatedTable(InventoryDimension2.class.getName());
    	this.setColumnWidth(59);
        this.setDeleteAction(enumDeleteUpdateOptions.RESTRICT);
    }
}
