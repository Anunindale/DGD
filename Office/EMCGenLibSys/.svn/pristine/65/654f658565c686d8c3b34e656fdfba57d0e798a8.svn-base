/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.datasources.inventory;

import emc.datatypes.datasources.DSRelation;
import emc.datatypes.systemwide.Description;
import emc.entity.inventory.dimensions.InventoryDimension3;

/**
 *
 * @author wikus
 */
public class Dim3DescDS extends Description{
    
    public Dim3DescDS() {
        super();
        DSRelation dsr = new DSRelation();
        dsr.setRelatedTable(InventoryDimension3.class.getName());
        dsr.setRelatedField("description");
        dsr.setFKField("dimensionId");
        dsr.setPKField("dimensionId");
        this.setDsRelation(dsr);
    }

}
