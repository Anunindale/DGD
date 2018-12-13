/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.inventory.datasource;

import emc.datatypes.EMCDataType;
import emc.datatypes.inventory.consolidatedpickinglistlines.datasource.ItemReference;
import emc.entity.inventory.consolidatedpickinglist.InventoryConsolidatedPLLines;
import emc.inventory.ItemReferenceInterface;
import java.util.HashMap;

/**
 *
 * @author riaan
 */
public class InventoryConsolidatedPLLinesDS extends InventoryConsolidatedPLLines implements ItemReferenceInterface {

    private String itemReference;
    private String itemDescription;

    public InventoryConsolidatedPLLinesDS() {
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
        return toBuild;
    }
}
