/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.inventory.datasource;

import emc.datatypes.datasources.inventory.itemDescDS;
import emc.datatypes.datasources.inventory.itemPrimaryReferenceDS;
import emc.entity.inventory.InventoryBarcodes;
import java.util.HashMap;

/**
 *
 * @author wikus
 */
public class InventoryBarcodesDS extends InventoryBarcodes{
    
    private String itemPrimaryReference;
    private String itemDesc;

    public InventoryBarcodesDS() {
        this.setDataSource(true);
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public String getItemPrimaryReference() {
        return itemPrimaryReference;
    }

    public void setItemPrimaryReference(String itemPrimaryReference) {
        this.itemPrimaryReference = itemPrimaryReference;
    }

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();
        
        toBuild.put("itemPrimaryReference", new itemPrimaryReferenceDS());
        toBuild.put("itemDesc", new itemDescDS());
        
        return toBuild;
    }

}
