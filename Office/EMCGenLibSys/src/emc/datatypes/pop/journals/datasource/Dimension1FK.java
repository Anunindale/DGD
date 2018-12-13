/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.pop.journals.datasource;

import emc.datatypes.inventory.dimension1.Dimension1;
import emc.entity.inventory.dimensions.InventoryDimension1;

/**
 *
 * @author wikus
 */
public class Dimension1FK extends Dimension1 {

    /** Creates a new instance of Dimension1FK */
    public Dimension1FK() {
        this.setEmcLabel("Config");
        this.setMandatory(false);
        this.setRelatedField("dimensionId");
        this.setRelatedTable(InventoryDimension1.class.getName());
    }
}
