/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.inventory.picking.datasources;

import emc.datatypes.EMCDataType;
import emc.datatypes.datasources.inventory.itemDescDS;
import emc.datatypes.inventory.pickinglist.ItemReference;
import emc.entity.inventory.picking.InventoryPickingListLines;
import emc.inventory.ItemReferenceInterface;
import java.util.HashMap;

/**
 *
 * @author wikus
 */
public class InventoryPickingListLinesDS extends InventoryPickingListLines implements ItemReferenceInterface {

    private String itemReference;
    private String itemDescription;
    private String orderId;

    public InventoryPickingListLinesDS() {
        this.setDataSource(true);
    }
    
    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemReference() {
        return itemReference;
    }

    public void setItemReference(String itemReference) {
        this.itemReference = itemReference;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    
    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("itemReference", new ItemReference());
        toBuild.put("itemDescription", new itemDescDS());
        return toBuild;
    }
}
