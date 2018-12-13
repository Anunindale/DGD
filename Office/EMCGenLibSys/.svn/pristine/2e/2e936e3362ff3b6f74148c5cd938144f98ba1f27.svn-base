/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.inventory.brandgroup;

import emc.datatypes.EMCString;
import emc.datatypes.datasources.DSRelation;
import emc.entity.inventory.InventoryItemMaster;

/**
 *
 * @author wikus
 */
public class BrandGroupDS extends EMCString{

    public BrandGroupDS() { 
        this.setEmcLabel("Brand");
        DSRelation dsr = new DSRelation();
        dsr.setRelatedTable(InventoryItemMaster.class.getName());
        dsr.setRelatedField("brandGroup");
        dsr.setFKField("itemId");
        dsr.setPKField("itemId");
    	this.setColumnWidth(50);
        this.setDsRelation(dsr);
    }
    
}
