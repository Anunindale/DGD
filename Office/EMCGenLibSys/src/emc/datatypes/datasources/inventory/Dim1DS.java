/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.datasources.inventory;

import emc.datatypes.datasources.DSRelation;
import emc.datatypes.inventory.dimension1.Dimension1;
import emc.entity.inventory.dimensions.InventoryDimension1;

/**
 *
 * @author wikus
 */
public class Dim1DS extends Dimension1{

    public Dim1DS() {
    super();
        DSRelation dsr = new DSRelation();
        dsr.setRelatedTable(InventoryDimension1.class.getName());
        dsr.setRelatedField("dimensionId");
        dsr.setFKField("config");
        dsr.setPKField("dimensionId");
        this.setDsRelation(dsr);
        this.setMandatory(false);
    }

}
