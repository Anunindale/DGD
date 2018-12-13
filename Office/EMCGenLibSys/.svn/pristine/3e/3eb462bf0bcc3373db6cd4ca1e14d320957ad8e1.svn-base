/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.pop.planning.datasource;

import emc.datatypes.EMCDataType;
import emc.datatypes.pop.datasource.plannedpurchaseorders.ItemDescription;
import emc.datatypes.pop.datasource.plannedpurchaseorders.ItemReference;
import emc.datatypes.pop.planning.ItemGroupFK;
import emc.entity.pop.planning.POPPlannedPurchaseOrders;
import emc.inventory.ItemReferenceInterface;
import java.util.HashMap;

/**
 *
 * @author wikus
 */
public class POPPlannedPurchaseOrdersDS extends POPPlannedPurchaseOrders implements ItemReferenceInterface {

    private String itemReference;
    private String itemDescription;
    private String itemGroup;

    public POPPlannedPurchaseOrdersDS() {
        this.setDataSource(true);
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("itemReference", new ItemReference());
        toBuild.put("itemDescription", new ItemDescription());
        toBuild.put("itemGroup", new ItemGroupFK());

        return toBuild;
    }

    public String getItemReference() {
        return itemReference;
    }

    public void setItemReference(String itemReference) {
        this.itemReference = itemReference;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemGroup() {
        return itemGroup;
    }

    public void setItemGroup(String itemGroup) {
        this.itemGroup = itemGroup;
    }
}
