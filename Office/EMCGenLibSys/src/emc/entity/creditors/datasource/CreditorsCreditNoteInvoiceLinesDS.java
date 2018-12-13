/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.creditors.datasource;

import emc.datatypes.EMCDataType;
import emc.datatypes.creditors.creditnoteinvoice.ItemDescription;
import emc.datatypes.creditors.creditnoteinvoice.ItemReference;
import emc.entity.creditors.CreditorsCreditNoteInvoiceLines;
import emc.inventory.ItemReferenceInterface;
import java.util.HashMap;

/**
 *
 * @author wikus
 */
public class CreditorsCreditNoteInvoiceLinesDS extends CreditorsCreditNoteInvoiceLines implements ItemReferenceInterface {

    private String itemReference;
    private String itemDescription;

    public CreditorsCreditNoteInvoiceLinesDS() {
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

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("itemReference", new ItemReference());
        toBuild.put("itemDescription", new ItemDescription());
        return toBuild;
    }
}
