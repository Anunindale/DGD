/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.inventory.dimensions.datasource;

import emc.datatypes.datasources.inventory.Dim2DescDS;
import emc.entity.inventory.dimensions.InventoryDimension2;
import java.util.HashMap;

/**
 *
 * @author riaan
 */
public class InventoryItemDimension2SetupDS extends InventoryDimension2 {

    /** Creates a new instance of InventoryItemDimension2SetupDS. */
    public InventoryItemDimension2SetupDS() {
        setDataSource(true);
    }

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();
        toBuild.put("description", new Dim2DescDS());
        return toBuild;
    }
}