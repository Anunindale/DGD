/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.pop.journals.datasource;

import emc.datatypes.inventory.dimension2.Dimension2;
import emc.entity.inventory.dimensions.InventoryDimension2;

/**
 *
 * @author wikus
 */
public class Dimension2FK extends Dimension2 {

    /** Creates a new instance of Dimension2FK */
    public Dimension2FK() {
        this.setEmcLabel("Size");
        this.setMandatory(false);
        this.setRelatedField("dimensionId");
        this.setRelatedTable(InventoryDimension2.class.getName());

    }
}
