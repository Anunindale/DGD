/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.inventory.transactions.datasource;

import emc.datatypes.datasources.inventory.itemDescDS;
import emc.datatypes.datasources.inventory.itemPrimaryReferenceDSNotManditory;
import emc.entity.inventory.transactions.*;
import java.util.HashMap;

/**
 *
 * @author rico
 */
public class InventorySummaryDS extends InventorySummary {

    private String itemPrimaryReference;
    private String description;
    private String unitOfMeasure;
    private String displayColumns;

    public InventorySummaryDS() {
        this.setDataSource(true);
    }

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();
        toBuild.put("itemPrimaryReference", new itemPrimaryReferenceDSNotManditory());
        toBuild.put("description", new itemDescDS());

        return toBuild;
    }

    public String getItemPrimaryReference() {
        return itemPrimaryReference;
    }

    public void setItemPrimaryReference(String itemPrimaryReference) {
        this.itemPrimaryReference = itemPrimaryReference;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public String getDisplayColumns() {
        return displayColumns;
    }

    public void setDisplayColumns(String displayColumns) {
        this.displayColumns = displayColumns;
    }
}
