/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.inventory.movestock;

import emc.datatypes.EMCLong;
import emc.entity.inventory.dimensions.InventoryDimensionTable;

/**
 *
 * @author wikus
 */
public class DimensionId extends EMCLong {

    public DimensionId() {
        this.setRelatedTable(InventoryDimensionTable.class.getName());
        this.setRelatedField("recordID");
    }
}
