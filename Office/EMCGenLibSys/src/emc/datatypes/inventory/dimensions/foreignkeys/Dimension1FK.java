/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.dimensions.foreignkeys;

import emc.datatypes.inventory.dimension1.Dimension1;
import emc.entity.inventory.dimensions.InventoryDimension1;
import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 *
 * @author riaan
 */
public class Dimension1FK extends Dimension1 {

    /** Creates a new instance of Dimension1FK */
    public Dimension1FK() {
        //this.setMandatory(false);
        this.setRelatedField("dimensionId");
        this.setRelatedTable(InventoryDimension1.class.getName());
    	this.setColumnWidth(50);
        this.setDeleteAction(enumDeleteUpdateOptions.RESTRICT);
    }
}
