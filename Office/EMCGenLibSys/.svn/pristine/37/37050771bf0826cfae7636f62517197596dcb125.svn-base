/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.inventory.dimensions.datasource;

import emc.datatypes.datasources.inventory.Dim1DescDS;
import emc.entity.inventory.dimensions.InventoryDimension1;
import java.util.HashMap;

/**
 *
 * @author riaan
 */
public class InventoryItemDimension1SetupDS extends InventoryDimension1 {

    /** Creates a new instance of InventoryItemDimension1SetupDS. */
    public InventoryItemDimension1SetupDS() {
        setDataSource(true);
    }

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();
        toBuild.put("description", new Dim1DescDS());
        return toBuild;
    }
}
