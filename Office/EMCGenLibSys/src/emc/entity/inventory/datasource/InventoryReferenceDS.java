/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.inventory.datasource;

import emc.datatypes.datasources.inventory.itemPrimaryReferenceDSNotManditory;
import emc.entity.inventory.InventoryReference;
import java.util.HashMap;

/**
 *
 * @author wikus
 */
public class InventoryReferenceDS extends InventoryReference{
    
    private String itemPrimaryReference;
    private String itemDesc;

    public InventoryReferenceDS() {
        this.setDataSource(true);
    }

    public String getItemPrimaryReference() {
        return itemPrimaryReference;
    }

    public void setItemPrimaryReference(String itemPrimaryReference) {
        this.itemPrimaryReference = itemPrimaryReference;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }
    
    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();
        toBuild.put("itemPrimaryReference", new itemPrimaryReferenceDSNotManditory());
        return toBuild;
    }
}
