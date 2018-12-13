/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.sop.datasource;

import emc.datatypes.EMCDataType;
import emc.datatypes.sop.garmentrequirementspersales.ItemDescription;
import emc.datatypes.sop.garmentrequirementspersales.ItemReference;
import emc.entity.sop.SOPGarmentRequirementsPerSales;
import java.util.HashMap;

/**
 *
 * @author wikus
 */
public class SOPGarmentRequirementsPerSalesDS extends SOPGarmentRequirementsPerSales {

    private String itemReference;
    private String itemDescription;

    public SOPGarmentRequirementsPerSalesDS() {
        this.setDataSource(true);
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

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("itemReference", new ItemReference());
        toBuild.put("itemDescription", new ItemDescription());
        return toBuild;
    }
}
