/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.sop.datasource;

import emc.datatypes.EMCDataType;
import emc.datatypes.sop.salesorderlines.ItemReference;
import emc.entity.sop.SOPSalesOrderLines;
import emc.helpers.debtors.DebtorsCreditHeldLinesDSIF;
import emc.inventory.ItemDimensionInterface;
import emc.inventory.ItemReferenceInterface;
import java.math.BigDecimal;
import java.util.HashMap;

/**
 *
 * @author wikus
 */
public class SOPSalesOrderLinesDS extends SOPSalesOrderLines implements ItemReferenceInterface, ItemDimensionInterface, DebtorsCreditHeldLinesDSIF {

    private String itemReference;
    private String itemDescription;
    private BigDecimal totalHeld;
    private boolean vatIncluded;

    public SOPSalesOrderLinesDS() {
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

    public BigDecimal getTotalHeld() {
        return totalHeld;
    }

    public void setTotalHeld(BigDecimal totalHeld) {
        this.totalHeld = totalHeld;
    }

    public boolean isVATIncluded() {
        return vatIncluded;
    }

    public void setVATIncluded(boolean vatIncluded) {
        this.vatIncluded = vatIncluded;
    }
    
    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("itemReference", new ItemReference());
        return toBuild;
    }
}
