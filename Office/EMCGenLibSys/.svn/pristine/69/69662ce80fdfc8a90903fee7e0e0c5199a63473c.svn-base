/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.inventory.dimensions.datasource;

import emc.datatypes.datasources.inventory.Dim3DescDS;
import emc.entity.inventory.dimensions.InventoryDimension3GroupSetup;
import java.util.HashMap;

/**
 *
 * @author riaan
 */
public class InventoryDimension3GroupSetupDS extends InventoryDimension3GroupSetup {

    private String description;
    
    /** Creates a new instance of InventoryDimension3GroupSetupDS. */
    public InventoryDimension3GroupSetupDS() {
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
        toBuild.put("description", new Dim3DescDS());
        return toBuild;
    }
}
