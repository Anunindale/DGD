/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.datasources.inventory;

import emc.datatypes.datasources.DSRelation;
import emc.datatypes.inventory.dimension1.Dimension1Description;
import emc.entity.inventory.dimensions.InventoryDimension1;

/**
 *
 * @author wikus
 */
public class Dim1DescDS extends Dimension1Description{
    
    public Dim1DescDS() {
        super();
        DSRelation dsr = new DSRelation();
        dsr.setRelatedTable(InventoryDimension1.class.getName());
        dsr.setRelatedField("description");
        dsr.setFKField("dimensionId");
        dsr.setPKField("dimensionId");
        this.setDsRelation(dsr);
    }

}
