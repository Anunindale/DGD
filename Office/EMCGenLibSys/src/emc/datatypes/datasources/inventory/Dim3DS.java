/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.datasources.inventory;

import emc.datatypes.datasources.DSRelation;
import emc.datatypes.inventory.dimension3.Dimension3;
import emc.entity.inventory.dimensions.InventoryDimension3;

/**
 *
 * @author wikus
 */
public class Dim3DS extends Dimension3{

    public Dim3DS() {
    super();
        DSRelation dsr = new DSRelation();
        dsr.setRelatedTable(InventoryDimension3.class.getName());
        dsr.setRelatedField("dimensionId");
        dsr.setFKField("colour");
        dsr.setPKField("dimensionId");
        this.setDsRelation(dsr);
        this.setMandatory(false);
    }

}
