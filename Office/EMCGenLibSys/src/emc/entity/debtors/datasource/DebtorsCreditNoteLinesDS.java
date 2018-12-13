/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.debtors.datasource;

import emc.datatypes.EMCDataType;
import emc.datatypes.debtors.customerinvoice.ItemReference;
import emc.datatypes.inventory.itemmaster.ItemDescriptionNotEditable;
import emc.entity.debtors.DebtorsCreditNoteLines;
import emc.inventory.ItemReferenceInterface;
import java.util.HashMap;

/**
 *
 * @author riaan
 */
public class DebtorsCreditNoteLinesDS extends DebtorsCreditNoteLines implements ItemReferenceInterface {

    private String itemReference;
    private String itemDescription;

    /** Creates a new instance of DebtorsCreditNoteLinesDS */
    public DebtorsCreditNoteLinesDS() {
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
}
