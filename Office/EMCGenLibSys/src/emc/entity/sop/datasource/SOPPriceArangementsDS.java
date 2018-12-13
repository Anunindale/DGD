/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.sop.datasource;

import emc.datatypes.EMCDataType;
import emc.datatypes.datasources.debtors.CustomerDescriptionDS;
import emc.datatypes.datasources.inventory.itemDescDS;
import emc.datatypes.datasources.inventory.itemPrimaryReferenceDS;
import emc.datatypes.inventory.brandgroup.BrandGroupDS;
import emc.datatypes.inventory.itemmaster.ItemDescriptionNotEditable;
import emc.datatypes.sop.pricearangements.CustomerDisplayField;
import emc.datatypes.systemwide.Description;
import emc.entity.sop.SOPPriceArangements;
import emc.inventory.ItemDimensionInterface;
import emc.inventory.ItemReferenceInterface;
import java.util.HashMap;

/**
 *
 * @author wikus
 */
public class SOPPriceArangementsDS extends SOPPriceArangements implements ItemReferenceInterface, ItemDimensionInterface {

    private String itemReference;
    private String itemDescription;
    private String itemBrand;
    private String customerName;
    private String customerDisplayField;

    public SOPPriceArangementsDS() {
        this.setDataSource(true);
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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

    public String getCustomerDisplayField() {
        return customerDisplayField;
    }

    public void setCustomerDisplayField(String customerDisplayField) {
        this.customerDisplayField = customerDisplayField;
    }

    public String getItemBrand() {
        return itemBrand;
    }

    public void setItemBrand(String itemBrand) {
        this.itemBrand = itemBrand;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("itemReference", new itemPrimaryReferenceDS());
        toBuild.put("itemDescription", new itemDescDS());
        toBuild.put("itemBrand", new BrandGroupDS());
        toBuild.put("customerDisplayField", new CustomerDisplayField());
        toBuild.put("customerName", new CustomerDescriptionDS());
        return toBuild;
    }
}
