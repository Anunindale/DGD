/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.datasources.inventory;

import emc.datatypes.datasources.DSRelation;
import emc.datatypes.inventory.dimension2.Dimension2;
import emc.entity.inventory.dimensions.InventoryDimension2;

/**
 *
 * @author wikus
 */
public class Dim2DS extends Dimension2{

    public Dim2DS() {
    super();
        DSRelation dsr = new DSRelation();
        dsr.setRelatedTable(InventoryDimension2.class.getName());
        dsr.setRelatedField("dimensionId");
        dsr.setFKField("size");
        dsr.setPKField("dimensionId");
        this.setDsRelation(dsr);
        this.setMandatory(false);
    }

}
