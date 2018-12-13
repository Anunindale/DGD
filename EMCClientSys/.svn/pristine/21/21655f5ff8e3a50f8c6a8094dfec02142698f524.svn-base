/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.creditors.display.creditnote.resources;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.lookup.EMCLookup;
import emc.app.components.lookup.EMCLookupRelationManager;
import emc.entity.inventory.dimensions.InventoryDimensionTable;
import emc.entity.inventory.dimensions.InventoryItemDimensionCombinations;
import emc.entity.inventory.transactions.InventorySummary;
import emc.framework.EMCDebug;
import emc.framework.EMCQuery;
import emc.functions.Functions;
import java.util.List;

/**
 *
 * @author wikus
 */
public class CreditorsCreditNoteLinesLRM extends EMCLookupRelationManager {

    private String dimCombinations = InventoryItemDimensionCombinations.class.getName();
    private String dimensionTable = InventoryDimensionTable.class.getName();
    private String summary = InventorySummary.class.getName();
    private String itemField;
    private String dim1Field;
    private String dim2Field;
    private String dim3Field;
    private String warehouseField;

    /** Creates a new instance of CustomerInvoiceLinesLRM.  Takes the field names of the item and dimension fields on the table to relate as parameters. */
    public CreditorsCreditNoteLinesLRM(String itemField, String dim1Field, String dim2Field, String dim3Field, String warehouseField) {
        super();
        this.itemField = itemField;
        this.dim1Field = dim1Field;
        this.dim2Field = dim2Field;
        this.dim3Field = dim3Field;
        this.warehouseField = warehouseField;
    }

    @Override
    public void doRelation(EMCLookup lookup) {
        String lookupName = getLookupName(lookup);

        if (lookupName != null) {
            if (lookupName.equals("item")) {
                updateItemChanged(lookup);
            } else if (lookupName.equals("dimension1") || lookupName.equals("tableDimension1")) {
                updateDimension1Changed(lookup);
            } else if (lookupName.equals("dimension2") || lookupName.equals("tableDimension2")) {
                updateDimension2Changed(lookup);
            } else if (lookupName.equals("dimension3") || lookupName.equals("tableDimension3")) {
                updateDimension3Changed(lookup);
            } else if (lookupName.equals("warehouse")) {
                updateWarehouseChanged(lookup);
            }
        } else {
            if (EMCDebug.getDebug()) {
                System.out.println("Lookup not mapped");
            }
        }
    }

    /** Updates other lookups when the item lookup is updated */
    private void updateItemChanged(EMCLookup lookup) {
        EMCQuery query = null;
        Object value = lookup.getValue();

        List<EMCLookup> lookupsToUpdate = getLookupFromName("dimension1");
        for (EMCLookup lookupToUpdate : lookupsToUpdate) {
            query = lookupToUpdate.getTheQuery();
            query.removeAndNotJoin("itemId", dimCombinations);

            if (value != null && !value.equals("")) {
                query.addAnd("itemId", value, dimCombinations);
            }
        }

        lookupsToUpdate = getLookupFromName("dimension2");
        for (EMCLookup lookupToUpdate : lookupsToUpdate) {
            query = lookupToUpdate.getTheQuery();
            query.removeAnd("itemId", dimCombinations);

            if (value != null && !value.equals("")) {
                query.addAnd("itemId", value, dimCombinations);
            }
        }

        lookupsToUpdate = getLookupFromName("dimension3");
        for (EMCLookup lookupToUpdate : lookupsToUpdate) {
            query = lookupToUpdate.getTheQuery();
            query.removeAnd("itemId", dimCombinations);

            if (value != null && !value.equals("")) {
                query.addAnd("itemId", value, dimCombinations);
            }
        }

        lookupsToUpdate = getLookupFromName("serial");
        for (EMCLookup lookupToUpdate : lookupsToUpdate) {
            query = lookupToUpdate.getTheQuery();
            query.removeAnd("itemId", summary);

            if (!Functions.checkBlank(value)) {
                query.addAnd("itemId", value, summary);
            }
            lookupToUpdate.setTheQuery(query);
        }

        lookupsToUpdate = getLookupFromName("batch");
        for (EMCLookup lookupToUpdate : lookupsToUpdate) {
            query = lookupToUpdate.getTheQuery();
            query.removeAnd("itemId", summary);

            if (!Functions.checkBlank(value)) {
                query.addAnd("itemId", value, summary);
            }
            lookupToUpdate.setTheQuery(query);
        }
    }

    /** Updates other lookups when the dimension 1 lookup is updated */
    private void updateDimension1Changed(EMCLookup lookup) {
        EMCQuery query = null;
        Object value = lookup.getValue();
        List<EMCLookup> lookupsToUpdate = getLookupFromName("dimension2");

        for (EMCLookup lookupToUpdate : lookupsToUpdate) {
            query = lookupToUpdate.getTheQuery();
            query.removeAnd("dimension1Id", dimCombinations);

            if (value != null && !value.equals("")) {
                query.addAnd("dimension1Id", value, dimCombinations);
            }
        }

        lookupsToUpdate = getLookupFromName("dimension3");
        for (EMCLookup lookupToUpdate : lookupsToUpdate) {
            query = lookupToUpdate.getTheQuery();
            query.removeAnd("dimension1Id", dimCombinations);

            if (value != null && !value.equals("")) {
                query.addAnd("dimension1Id", value, dimCombinations);
            }
        }

        lookupsToUpdate = getLookupFromName("serial");
        for (EMCLookup lookupToUpdate : lookupsToUpdate) {
            query = lookupToUpdate.getTheQuery();
            query.removeAndNotJoin("dimension1Id", dimensionTable);

            if (!Functions.checkBlank(value)) {
                query.addAnd("dimension1Id", value, dimensionTable);
            }
            lookupToUpdate.setTheQuery(query);
        }

        lookupsToUpdate = getLookupFromName("batch");
        for (EMCLookup lookupToUpdate : lookupsToUpdate) {
            query = lookupToUpdate.getTheQuery();
            query.removeAndNotJoin("dimension1Id", dimensionTable);

            if (!Functions.checkBlank(value)) {
                query.addAnd("dimension1Id", value, dimensionTable);
            }
            lookupToUpdate.setTheQuery(query);
        }

    }

    /** Updates other lookups when the dimension 2 lookup is updated */
    private void updateDimension2Changed(EMCLookup lookup) {
        EMCQuery query = null;
        Object value = lookup.getValue();
        List<EMCLookup> lookupsToUpdate = getLookupFromName("dimension1");

        for (EMCLookup lookupToUpdate : lookupsToUpdate) {
            query = lookupToUpdate.getTheQuery();
            query.removeAnd("dimension2Id", dimCombinations);

            if (value != null && !value.equals("")) {
                query.addAnd("dimension2Id", value, dimCombinations);
            }
        }

        lookupsToUpdate = getLookupFromName("dimension3");
        for (EMCLookup lookupToUpdate : lookupsToUpdate) {
            query = lookupToUpdate.getTheQuery();
            query.removeAnd("dimension2Id", dimCombinations);

            if (value != null && !value.equals("")) {
                query.addAnd("dimension2Id", value, dimCombinations);
            }
        }

        lookupsToUpdate = getLookupFromName("serial");
        for (EMCLookup lookupToUpdate : lookupsToUpdate) {
            query = lookupToUpdate.getTheQuery();
            query.removeAndNotJoin("dimension2Id", dimensionTable);

            if (!Functions.checkBlank(value)) {
                query.addAnd("dimension2Id", value, dimensionTable);
            }
            lookupToUpdate.setTheQuery(query);
        }

        lookupsToUpdate = getLookupFromName("batch");
        for (EMCLookup lookupToUpdate : lookupsToUpdate) {
            query = lookupToUpdate.getTheQuery();
            query.removeAndNotJoin("dimension2Id", dimensionTable);

            if (!Functions.checkBlank(value)) {
                query.addAnd("dimension2Id", value, dimensionTable);
            }
            lookupToUpdate.setTheQuery(query);
        }

    }

    /** Updates other lookups when the dimension 3 lookup is updated */
    private void updateDimension3Changed(EMCLookup lookup) {
        EMCQuery query = null;
        Object value = lookup.getValue();
        List<EMCLookup> lookupsToUpdate = getLookupFromName("dimension2");

        for (EMCLookup lookupToUpdate : lookupsToUpdate) {
            query = lookupToUpdate.getTheQuery();
            query.removeAnd("dimension3Id", dimCombinations);

            if (value != null && !value.equals("")) {
                query.addAnd("dimension3Id", value, dimCombinations);
            }
        }

        lookupsToUpdate = getLookupFromName("dimension1");
        for (EMCLookup lookupToUpdate : lookupsToUpdate) {
            query = lookupToUpdate.getTheQuery();
            query.removeAnd("dimension3Id", dimCombinations);

            if (value != null && !value.equals("")) {
                query.addAnd("dimension3Id", value, dimCombinations);
            }
        }

        lookupsToUpdate = getLookupFromName("serial");
        for (EMCLookup lookupToUpdate : lookupsToUpdate) {
            query = lookupToUpdate.getTheQuery();
            query.removeAndNotJoin("dimension3Id", dimensionTable);

            if (!Functions.checkBlank(value)) {
                query.addAnd("dimension3Id", value, dimensionTable);
            }
            lookupToUpdate.setTheQuery(query);
        }

        lookupsToUpdate = getLookupFromName("batch");
        for (EMCLookup lookupToUpdate : lookupsToUpdate) {
            query = lookupToUpdate.getTheQuery();
            query.removeAndNotJoin("dimension3Id", dimensionTable);

            if (!Functions.checkBlank(value)) {
                query.addAnd("dimension3Id", value, dimensionTable);
            }
            lookupToUpdate.setTheQuery(query);
        }

    }

    private void updateWarehouseChanged(EMCLookup lookup) {
        EMCLookup theLookup = getLookupFromName("location").get(0);
        EMCQuery query = theLookup.getTheQuery();
        query.removeAnd("warehouseId");
        query.addAnd("warehouseId", lookup.getValue());
    }

    @Override
    public void doRowChanged(emcDataRelationManagerUpdate drm) {

        EMCQuery theQuery = null;
        List<EMCLookup> lookups = getLookupFromName("dimension1");

        for (EMCLookup lookup : lookups) {
            theQuery = lookup.getTheQuery();
            theQuery.removeAndNotJoin("dimension2Id", dimCombinations);
            theQuery.removeAndNotJoin("dimension3Id", dimCombinations);
            theQuery.removeAndNotJoin("itemId", dimCombinations);

            Object value = drm.getFieldValueAt(drm.getLastRowAccessed(), itemField);

            if (value != null && !value.toString().equals("")) {
                theQuery.addAnd("itemId", value, dimCombinations);
            }

            value = drm.getFieldValueAt(drm.getLastRowAccessed(), dim2Field);
            if (value != null && !value.toString().equals("")) {
                theQuery.addAnd("dimension2Id", value, dimCombinations);
            }

            value = drm.getFieldValueAt(drm.getLastRowAccessed(), dim3Field);
            if (value != null && !value.toString().equals("")) {
                theQuery.addAnd("dimension3Id", value, dimCombinations);
            }
        }

        lookups = getLookupFromName("dimension2");

        for (EMCLookup lookup : lookups) {
            theQuery = lookup.getTheQuery();
            theQuery.removeAndNotJoin("dimension1Id", dimCombinations);
            theQuery.removeAndNotJoin("dimension3Id", dimCombinations);
            theQuery.removeAndNotJoin("itemId", dimCombinations);

            Object value = drm.getFieldValueAt(drm.getLastRowAccessed(), itemField);

            if (value != null && !value.toString().equals("")) {
                theQuery.addAnd("itemId", value, dimCombinations);
            }

            value = drm.getFieldValueAt(drm.getLastRowAccessed(), dim1Field);
            if (value != null && !value.toString().equals("")) {
                theQuery.addAnd("dimension1Id", value, dimCombinations);
            }

            value = drm.getFieldValueAt(drm.getLastRowAccessed(), dim3Field);
            if (value != null && !value.toString().equals("")) {
                theQuery.addAnd("dimension3Id", value, dimCombinations);
            }
        }

        lookups = getLookupFromName("dimension3");

        for (EMCLookup lookup : lookups) {
            theQuery = lookup.getTheQuery();
            theQuery.removeAndNotJoin("dimension2Id", dimCombinations);
            theQuery.removeAndNotJoin("dimension1Id", dimCombinations);
            theQuery.removeAndNotJoin("itemId", dimCombinations);

            Object value = drm.getFieldValueAt(drm.getLastRowAccessed(), itemField);

            if (value != null && !value.toString().equals("")) {
                theQuery.addAnd("itemId", value, dimCombinations);
            }

            value = drm.getFieldValueAt(drm.getLastRowAccessed(), dim2Field);
            if (value != null && !value.toString().equals("")) {
                theQuery.addAnd("dimension2Id", value, dimCombinations);
            }

            value = drm.getFieldValueAt(drm.getLastRowAccessed(), dim1Field);
            if (value != null && !value.toString().equals("")) {
                theQuery.addAnd("dimension1Id", value, dimCombinations);
            }
        }

        Object value;
        lookups = getLookupFromName("serial");
        for (EMCLookup lookup : lookups) {
            theQuery = lookup.getTheQuery();
            theQuery.removeAndNotJoin("dimension1Id", dimensionTable);
            theQuery.removeAndNotJoin("dimension2Id", dimensionTable);
            theQuery.removeAndNotJoin("dimension3Id", dimensionTable);
            theQuery.removeAndNotJoin("warehouseId", dimensionTable);
            theQuery.removeAnd("itemId", summary);

            value = drm.getFieldValueAt(drm.getLastRowAccessed(), itemField);
            if (!Functions.checkBlank(value)) {
                theQuery.addAnd("itemId", value, summary);
            }

            value = drm.getFieldValueAt(drm.getLastRowAccessed(), dim1Field);
            if (!Functions.checkBlank(value)) {
                theQuery.addAnd("dimension1Id", value, dimensionTable);
            }

            value = drm.getFieldValueAt(drm.getLastRowAccessed(), dim2Field);
            if (!Functions.checkBlank(value)) {
                theQuery.addAnd("dimension2Id", value, dimensionTable);
            }

            value = drm.getFieldValueAt(drm.getLastRowAccessed(), dim3Field);
            if (!Functions.checkBlank(value)) {
                theQuery.addAnd("dimension3Id", value, dimensionTable);
            }

            value = drm.getFieldValueAt(drm.getLastRowAccessed(), warehouseField);
            if (!Functions.checkBlank(value)) {
                theQuery.addAnd("warehouseId", value, dimensionTable);
            }
        }

        lookups = getLookupFromName("batch");
        for (EMCLookup lookup : lookups) {
            theQuery = lookup.getTheQuery();
            theQuery.removeAndNotJoin("dimension1Id", dimensionTable);
            theQuery.removeAndNotJoin("dimension2Id", dimensionTable);
            theQuery.removeAndNotJoin("dimension3Id", dimensionTable);
            theQuery.removeAndNotJoin("warehouseId", dimensionTable);
            theQuery.removeAnd("itemId", summary);

            value = drm.getFieldValueAt(drm.getLastRowAccessed(), itemField);
            if (!Functions.checkBlank(value)) {
                theQuery.addAnd("itemId", value, summary);
            }

            value = drm.getFieldValueAt(drm.getLastRowAccessed(), dim1Field);
            if (!Functions.checkBlank(value)) {
                theQuery.addAnd("dimension1Id", value, dimensionTable);
            }

            value = drm.getFieldValueAt(drm.getLastRowAccessed(), dim2Field);
            if (!Functions.checkBlank(value)) {
                theQuery.addAnd("dimension2Id", value, dimensionTable);
            }

            value = drm.getFieldValueAt(drm.getLastRowAccessed(), dim3Field);
            if (!Functions.checkBlank(value)) {
                theQuery.addAnd("dimension3Id", value, dimensionTable);
            }

            value = drm.getFieldValueAt(drm.getLastRowAccessed(), warehouseField);
            if (!Functions.checkBlank(value)) {
                theQuery.addAnd("warehouseId", value, dimensionTable);
            }
        }

        lookups = getLookupFromName("location");
        for (EMCLookup lookup : lookups) {
            theQuery = lookup.getTheQuery();
            theQuery.removeAnd("warehouseId");
            theQuery.addAnd("warehouseId", drm.getFieldValueAt(drm.getLastRowAccessed(), warehouseField));
        }
    }
}
