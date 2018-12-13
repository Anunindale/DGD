/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.dimension1.lines;

import emc.datatypes.datasources.DSRelation;
import emc.datatypes.datasources.inventory.Dim3DescDSNE;
import emc.entity.inventory.dimensions.InventoryDimension3;

/**
 *
 * @author riaan
 */
public class Dim3Desc extends Dim3DescDSNE{

    /** Creates a new instance of Dim3Desc */
    public Dim3Desc() {
        DSRelation dsr = new DSRelation();
        dsr.setRelatedTable(InventoryDimension3.class.getName());
        dsr.setRelatedField("description");
        dsr.setFKField("dimensionId");
        dsr.setPKField("dimension3Id");
        this.setDsRelation(dsr);
    }
}
