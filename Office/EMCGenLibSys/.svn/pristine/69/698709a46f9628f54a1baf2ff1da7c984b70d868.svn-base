/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.inventory.dimensions.datasource;

import emc.datatypes.datasources.inventory.Dim1DescDS;
import emc.entity.inventory.dimensions.InventoryDimension1GroupSetup;
import java.util.HashMap;

/**
 *
 * @author riaan
 */
public class InventoryDimension1GroupSetupDS extends InventoryDimension1GroupSetup {

    private String description;
    
    /** Creates a new instance of InventoryDimension1GroupSetupDS. */
    public InventoryDimension1GroupSetupDS() {
        setDataSource(true);
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();
        toBuild.put("description", new Dim1DescDS());
        return toBuild;
    }
}
