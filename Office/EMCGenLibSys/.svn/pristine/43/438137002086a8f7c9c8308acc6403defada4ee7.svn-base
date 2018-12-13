/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.inventory.dimensions.datasource;

import emc.datatypes.EMCDataType;
import emc.datatypes.inventory.dimension1.lines.Dim3Desc;
import emc.entity.inventory.dimensions.lines.InventoryDimension1Lines;
import java.util.HashMap;

/**
 *
 * @author riaan
 */
public class InventoryDimension1LinesDS extends InventoryDimension1Lines {

    private String dim3Desc;

    /** Creates a new instance of InventoryDimension1LinesDS */
    public InventoryDimension1LinesDS() {
        this.setDataSource(true);
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("dim3Desc", new Dim3Desc());

        return toBuild;
    }

    public String getDim3Desc() {
        return dim3Desc;
    }

    public void setDim3Desc(String dim3Desc) {
        this.dim3Desc = dim3Desc;
    }
}
