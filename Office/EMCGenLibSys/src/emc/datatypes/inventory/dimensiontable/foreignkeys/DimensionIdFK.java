/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.dimensiontable.foreignkeys;

import emc.datatypes.EMCLong;
import emc.entity.inventory.dimensions.InventoryDimensionTable;

/**
 * @Name         : DimensionIdFK
 *
 * @Date         : 08 Jun 2009
 * 
 * @Description  : Foreign key datatype for dimension id.
 *
 * @author       : riaan
 */
public class DimensionIdFK extends EMCLong {

    /** Creates a new instance of DimensionIdFK. */
    public DimensionIdFK() {
        this.setEmcLabel("Dim Id");
        this.setRelatedTable(InventoryDimensionTable.class.getName());
        this.setRelatedField("recordID");
    }
}
