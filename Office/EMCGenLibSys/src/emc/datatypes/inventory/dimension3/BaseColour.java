/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.inventory.dimension3;

import emc.datatypes.EMCString;
import emc.entity.inventory.dimensions.InventoryDimension3;

/**
 *
 * @author wikus
 */
public class BaseColour extends EMCString {

    /** Creates a new instance of BaseColour */
    public BaseColour() {
        this.setEmcLabel("Base Colour");
        this.setRelatedTable(InventoryDimension3.class.getName());
        this.setRelatedField("dimensionId");
        this.setEditable(true);
        this.setStringSize(10);
        this.setColumnWidth(50);
    }
}
