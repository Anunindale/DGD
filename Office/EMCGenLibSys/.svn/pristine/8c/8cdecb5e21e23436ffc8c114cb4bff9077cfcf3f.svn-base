/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.pop.cancelledpurchaseorders.datasource;

import emc.entity.pop.cancelledpurchaseorders.POPCancelledPurchaseOrderLines;
import emc.inventory.ItemReferenceInterface;

/**
 *
 * @author wikus
 */
public class POPCancelledPurchaseOrderLinesDS extends POPCancelledPurchaseOrderLines implements ItemReferenceInterface {

    private String itemReference;
    private String itemDescription;

    public POPCancelledPurchaseOrderLinesDS() {
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
}
