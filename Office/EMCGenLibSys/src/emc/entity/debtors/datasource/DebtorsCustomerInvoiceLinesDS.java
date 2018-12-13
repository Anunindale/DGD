/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.debtors.datasource;

import emc.datatypes.EMCDataType;
import emc.datatypes.debtors.customerinvoice.ItemReference;
import emc.datatypes.inventory.itemmaster.ItemDescriptionNotEditable;
import emc.entity.debtors.DebtorsCustomerInvoiceLines;
import emc.helpers.debtors.DebtorsCreditHeldLinesDSIF;
import emc.inventory.ItemReferenceInterface;
import java.math.BigDecimal;
import java.util.HashMap;

/**
 *
 * @author riaan
 */
public class DebtorsCustomerInvoiceLinesDS extends DebtorsCustomerInvoiceLines implements ItemReferenceInterface, DebtorsCreditHeldLinesDSIF {

    private String itemReference;
    private String itemDescription;
    private BigDecimal totalHeld;
    private boolean vatIncluded;

    /** Creates a new instance of DebtorsCustomerInvoiceLinesDS */
    public DebtorsCustomerInvoiceLinesDS() {
        this.setDataSource(true);
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("itemReference", new ItemReference());
        toBuild.put("itemDescription", new ItemDescriptionNotEditable());

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
}
