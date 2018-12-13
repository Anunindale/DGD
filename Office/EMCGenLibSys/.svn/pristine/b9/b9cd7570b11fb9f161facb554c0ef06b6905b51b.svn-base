/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.dimensions.foreignkeys;

import emc.datatypes.inventory.dimension3.Dimension3;
import emc.entity.inventory.dimensions.InventoryDimension3;
import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 *
 * @author riaan
 */
public class Dimension3FK extends Dimension3 {

    /** Creates a new instance of Dimension3FK */
    public Dimension3FK() {
        //this.setMandatory(false);
        this.setRelatedField("dimensionId");
        this.setRelatedTable(InventoryDimension3.class.getName());
    	this.setColumnWidth(65);
        this.setDeleteAction(enumDeleteUpdateOptions.RESTRICT);
    }
}
