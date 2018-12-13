/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.sop.datasource;

import emc.datatypes.EMCDataType;
import emc.datatypes.sop.discountsetup.datasource.CustomerField;
import emc.datatypes.sop.discountsetup.datasource.ItemDescription;
import emc.datatypes.sop.discountsetup.datasource.ItemField;
import emc.datatypes.sop.discountsetup.datasource.ItemReference;
import emc.entity.sop.SOPDiscountSetup;
import emc.inventory.ItemReferenceInterface;
import java.util.HashMap;

/**
 *
 * @author riaan
 */
public class SOPDiscountSetupDS extends SOPDiscountSetup implements ItemReferenceInterface {

    private String customerField;
    private String itemField;
    private String itemReference;
    private String itemDescription;

    /** Creates a new instance of SOPDiscountSetupDS. */
    public SOPDiscountSetupDS() {
        this.setDataSource(true);
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("customerField", new CustomerField());
        toBuild.put("itemField", new ItemField());
        toBuild.put("itemReference", new ItemReference());
        toBuild.put("itemDescription", new ItemDescription());

        return toBuild;
    }

    public String getCustomerField() {
        return customerField;
    }

    public void setCustomerField(String customerField) {
        this.customerField = customerField;
    }

    public String getItemField() {
        return itemField;
    }

    public void setItemField(String itemField) {
        this.itemField = itemField;
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
}
