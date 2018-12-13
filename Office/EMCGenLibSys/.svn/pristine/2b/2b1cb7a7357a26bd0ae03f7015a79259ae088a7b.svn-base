/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.inventory.datasource;

import emc.datatypes.datasources.inventory.itemPrimaryReferenceDSNotManditory;
import emc.entity.inventory.InventoryItemMaster;
import java.util.HashMap;

/**
 *
 * @author wikus
 */
public class InventoryItemMasterDS extends InventoryItemMaster {
    
    private String subItemPrimaryReference;

    public InventoryItemMasterDS() {
        this.setDataSource(true);
    }

    public String getSubItemPrimaryReference() {
        return subItemPrimaryReference;
    }

    public void setSubItemPrimaryReference(String subItemPrimaryReference) {
        this.subItemPrimaryReference = subItemPrimaryReference;
    }
    
    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();
        toBuild.put("subItemPrimaryReference", new itemPrimaryReferenceDSNotManditory());
        return toBuild;
    }
}
