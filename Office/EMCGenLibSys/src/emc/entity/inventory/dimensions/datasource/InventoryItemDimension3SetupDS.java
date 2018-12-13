/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.inventory.dimensions.datasource;

import emc.datatypes.datasources.inventory.Dim3DescDS;
import emc.entity.inventory.dimensions.InventoryDimension3;
import java.util.HashMap;

/**
 *
 * @author riaan
 */
public class InventoryItemDimension3SetupDS extends InventoryDimension3 {

    /** Creates a new instance of InventoryItemDimension3SetupDS. */
    public InventoryItemDimension3SetupDS() {
        setDataSource(true);
    }

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();
        toBuild.put("description", new Dim3DescDS());
        return toBuild;
    }
}

