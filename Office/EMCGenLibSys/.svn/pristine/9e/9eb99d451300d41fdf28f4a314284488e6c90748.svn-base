/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.inventory.dimensions.datasource;

import emc.datatypes.datasources.inventory.Dim2DescDS;
import emc.entity.inventory.dimensions.InventoryDimension2GroupSetup;
import java.util.HashMap;

/**
 *
 * @author riaan
 */
public class InventoryDimension2GroupSetupDS extends InventoryDimension2GroupSetup {

    private String description;
    private int sortCode;

    /** Creates a new instance of InventoryDimension2GroupSetupDS. */
    public InventoryDimension2GroupSetupDS() {
        setDataSource(true);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSortCode() {
        return sortCode;
    }

    public void setSortCode(int sortCode) {
        this.sortCode = sortCode;
    }

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();
        toBuild.put("description", new Dim2DescDS());
        return toBuild;
    }
}
