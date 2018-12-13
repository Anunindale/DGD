/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.inventory.datasource;

import emc.datatypes.EMCDataType;
import emc.datatypes.inventory.safetystock.CustomerName;
import emc.datatypes.inventory.safetystock.ItemDescription1;
import emc.datatypes.inventory.safetystock.ItemReference;
import emc.entity.inventory.InventorySafetyStock;
import java.util.HashMap;

/**
 *
 * @author wikus
 */
public class InventorySafetyStockDS extends InventorySafetyStock {

    private String customerName;
    private String itemReference;
    private String itemDescription;

    public InventorySafetyStockDS() {
        this.setDataSource(true);
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getItemReference() {
        return itemReference;
    }

    public void setItemReference(String itemReference) {
        this.itemReference = itemReference;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("customerName", new CustomerName());
        toBuild.put("itemReference", new ItemReference());
        toBuild.put("itemDescription", new ItemDescription1());
        return toBuild;
    }
}
