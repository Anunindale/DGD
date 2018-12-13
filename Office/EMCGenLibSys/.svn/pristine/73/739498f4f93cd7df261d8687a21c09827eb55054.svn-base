/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.inventory.journals.datasource;

import emc.datatypes.base.unitsofmeasure.foreignkeys.UnitOfMeasureFK;
import emc.datatypes.datasources.inventory.itemDescDS;
import emc.datatypes.datasources.inventory.itemPrimaryReferenceDS;
import emc.datatypes.datasources.inventory.itemPrimaryReferenceDSNotManditory;
import emc.entity.inventory.journals.InventoryJournalLines;
import java.util.HashMap;

/**
 *
 * @author riaan
 */
public class InventoryJournalLinesDS extends InventoryJournalLines {

    private String uom;
    private String toUom;
    private String description;
    private String toDescription;
    private String itemToPrimaryReference;
    private String itemPrimaryReference;

    public String getItemPrimaryReference() {
        return itemPrimaryReference;
    }

    public void setItemPrimaryReference(String itemPrimaryReference) {
        this.itemPrimaryReference = itemPrimaryReference;
    }

    public String getItemToPrimaryReference() {
        return itemToPrimaryReference;
    }

    public void setItemToPrimaryReference(String itemToPrimaryReference) {
        this.itemToPrimaryReference = itemToPrimaryReference;
    }
    
    /** Creates a new instance of InventoryJournalLinesDS */
    public InventoryJournalLinesDS() {
        this.setDataSource(true);
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getToDescription() {
        return toDescription;
    }

    public void setToDescription(String toDescription) {
        this.toDescription = toDescription;
    }

    public String getToUom() {
        return toUom;
    }

    public void setToUom(String toUom) {
        this.toUom = toUom;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();
        
        UnitOfMeasureFK uomDT = new UnitOfMeasureFK();
        toBuild.put("uom", uomDT);
        toBuild.put("toUom", uomDT);
        toBuild.put("itemToPrimaryReference", new itemPrimaryReferenceDSNotManditory());
        toBuild.put("itemPrimaryReference", new itemPrimaryReferenceDS());
        toBuild.put("description", new itemDescDS());
        toBuild.put("toDescription", new itemDescDS());
        
        return toBuild;
    }

}
