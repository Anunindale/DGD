/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.debtors.display.customerinvoice.resources;

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
 * @author riaan
 */
public class CustomerInvoiceLinesLRM extends EMCLookupRelationManager {

    private String dimCombinations = InventoryItemDimensionCombinations.class.getName();
    private String dimensionTable = InventoryDimensionTable.class.getName();
    private String summary = InventorySummary.class.getName();
    private emcDataRelationManagerUpdate dataManager;

    /** Creates a new instance of CustomerInvoiceLinesLRM.  Takes the field names of the item and dimension fields on the table to relate as parameters. */
    public CustomerInvoiceLinesLRM(emcDataRelationManagerUpdate dataManager) {
        super();

        this.dataManager = dataManager;
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
            } else if (lookupName.equals("warehouse") || lookupName.equals("tableWarehouse")) {
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
        Object value = dataManager.getLastFieldValueAt("itemId");

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
            query.removeAndNotJoin("itemId", dimCombinations);

            if (value != null && !value.equals("")) {
                query.addAnd("itemId", value, dimCombinations);
            }
        }

        lookupsToUpdate = getLookupFromName("dimension3");
        for (EMCLookup lookupToUpdate : lookupsToUpdate) {
            query = lookupToUpdate.getTheQuery();
            query.removeAndNotJoin("itemId", dimCombinations);

            if (value != null && !value.equals("")) {
                query.addAnd("itemId", value, dimCombinations);
            }
        }

        lookupsToUpdate = getLookupFromName("serial");
        for (EMCLookup lookupToUpdate : lookupsToUpdate) {
            query = lookupToUpdate.getTheQuery();
            query.removeAndNotJoin("itemId", summary);

            if (!Functions.checkBlank(value)) {
                query.addAnd("itemId", value, summary);
            }
            lookupToUpdate.setTheQuery(query);
        }

        lookupsToUpdate = getLookupFromName("batch");
        for (EMCLookup lookupToUpdate : lookupsToUpdate) {
            query = lookupToUpdate.getTheQuery();
            query.removeAndNotJoin("itemId", summary);

            if (!Functions.checkBlank(value)) {
                query.addAnd("itemId", value, summary);
            }
            lookupToUpdate.setTheQuery(query);
        }
    }

    /** Updates other lookups when the dimension 1 lookup is updated */
    private void updateDimension1Changed(EMCLookup lookup) {
        EMCQuery query = null;
        Object value = dataManager.getLastFieldValueAt("dimension1");
        List<EMCLookup> lookupsToUpdate = getLookupFromName("dimension2");

        for (EMCLookup lookupToUpdate : lookupsToUpdate) {
            query = lookupToUpdate.getTheQuery();
            query.removeAndNotJoin("dimension1Id", dimCombinations);

            if (value != null && !value.equals("")) {
                query.addAnd("dimension1Id", value, dimCombinations);
            }
        }

        lookupsToUpdate = getLookupFromName("dimension3");
        for (EMCLookup lookupToUpdate : lookupsToUpdate) {
            query = lookupToUpdate.getTheQuery();
            query.removeAndNotJoin("dimension1Id", dimCombinations);

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

        query = lookup.getTheQuery();
        query.removeAndNotJoin("itemId", dimCombinations);
        query.removeAndNotJoin("dimension2Id", dimCombinations);
        query.removeAndNotJoin("dimension3Id", dimCombinations);

        value = dataManager.getLastFieldValueAt("itemId");
        if (!Functions.checkBlank(value)) {
            query.addAnd("itemId", value, dimCombinations);
        }

        value = dataManager.getLastFieldValueAt("dimension2");
        if (!Functions.checkBlank(value)) {
            query.addAnd("dimension2Id", value, dimCombinations);
        }

        value = dataManager.getLastFieldValueAt("dimension3");
        if (!Functions.checkBlank(value)) {
            query.addAnd("dimension3Id", value, dimCombinations);
        }
    }

    /** Updates other lookups when the dimension 2 lookup is updated */
    private void updateDimension2Changed(EMCLookup lookup) {
        EMCQuery query = null;
        Object value = dataManager.getLastFieldValueAt("dimension2");
        List<EMCLookup> lookupsToUpdate = getLookupFromName("dimension1");

        for (EMCLookup lookupToUpdate : lookupsToUpdate) {
            query = lookupToUpdate.getTheQuery();
            query.removeAndNotJoin("dimension2Id", dimCombinations);

            if (value != null && !value.equals("")) {
                query.addAnd("dimension2Id", value, dimCombinations);
            }
        }

        lookupsToUpdate = getLookupFromName("dimension3");
        for (EMCLookup lookupToUpdate : lookupsToUpdate) {
            query = lookupToUpdate.getTheQuery();
            query.removeAndNotJoin("dimension2Id", dimCombinations);

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

        query = lookup.getTheQuery();
        query.removeAndNotJoin("itemId", dimCombinations);
        query.removeAndNotJoin("dimension1Id", dimCombinations);
        query.removeAndNotJoin("dimension3Id", dimCombinations);

        value = dataManager.getLastFieldValueAt("itemId");
        if (!Functions.checkBlank(value)) {
            query.addAnd("itemId", value, dimCombinations);
        }

        value = dataManager.getLastFieldValueAt("dimension1");
        if (!Functions.checkBlank(value)) {
            query.addAnd("dimension1Id", value, dimCombinations);
        }

        value = dataManager.getLastFieldValueAt("dimension3");
        if (!Functions.checkBlank(value)) {
            query.addAnd("dimension3Id", value, dimCombinations);
        }

    }

    /** Updates other lookups when the dimension 3 lookup is updated */
    private void updateDimension3Changed(EMCLookup lookup) {
        EMCQuery query = null;
        Object value = dataManager.getLastFieldValueAt("dimension3");
        List<EMCLookup> lookupsToUpdate = getLookupFromName("dimension2");

        for (EMCLookup lookupToUpdate : lookupsToUpdate) {
            query = lookupToUpdate.getTheQuery();
            query.removeAndNotJoin("dimension3Id", dimCombinations);

            if (value != null && !value.equals("")) {
                query.addAnd("dimension3Id", value, dimCombinations);
            }
        }

        lookupsToUpdate = getLookupFromName("dimension1");
        for (EMCLookup lookupToUpdate : lookupsToUpdate) {
            query = lookupToUpdate.getTheQuery();
            query.removeAndNotJoin("dimension3Id", dimCombinations);

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

        query = lookup.getTheQuery();
        query.removeAndNotJoin("itemId", dimCombinations);
        query.removeAndNotJoin("dimension1Id", dimCombinations);
        query.removeAndNotJoin("dimension2Id", dimCombinations);

        value = dataManager.getLastFieldValueAt("itemId");
        if (!Functions.checkBlank(value)) {
            query.addAnd("itemId", value, dimCombinations);
        }

        value = dataManager.getLastFieldValueAt("dimension1");
        if (!Functions.checkBlank(value)) {
            query.addAnd("dimension1Id", value, dimCombinations);
        }

        value = dataManager.getLastFieldValueAt("dimension2");
        if (!Functions.checkBlank(value)) {
            query.addAnd("dimension2Id", value, dimCombinations);
        }

    }

    private void updateWarehouseChanged(EMCLookup lookup) {
        EMCLookup theLookup = getLookupFromName("location").get(0);
        EMCQuery query = theLookup.getTheQuery();
        query.removeAnd("warehouseId");
        query.addAnd("warehouseId", dataManager.getLastFieldValueAt("warehouse"));
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
        }

        lookups = getLookupFromName("dimension2");

        for (EMCLookup lookup : lookups) {
            theQuery = lookup.getTheQuery();
            theQuery.removeAndNotJoin("dimension1Id", dimCombinations);
            theQuery.removeAndNotJoin("dimension3Id", dimCombinations);
            theQuery.removeAndNotJoin("itemId", dimCombinations);
        }

        lookups = getLookupFromName("dimension3");

        for (EMCLookup lookup : lookups) {
            theQuery = lookup.getTheQuery();
            theQuery.removeAndNotJoin("dimension2Id", dimCombinations);
            theQuery.removeAndNotJoin("dimension1Id", dimCombinations);
            theQuery.removeAndNotJoin("itemId", dimCombinations);
        }

        Object value;
        lookups = getLookupFromName("serial");
        for (EMCLookup lookup : lookups) {
            theQuery = lookup.getTheQuery();
            theQuery.removeAndNotJoin("dimension1Id", dimensionTable);
            theQuery.removeAndNotJoin("dimension2Id", dimensionTable);
            theQuery.removeAndNotJoin("dimension3Id", dimensionTable);
            theQuery.removeAndNotJoin("warehouseId", dimensionTable);
            theQuery.removeAndNotJoin("itemId", summary);
        }

        lookups = getLookupFromName("batch");
        for (EMCLookup lookup : lookups) {
            theQuery = lookup.getTheQuery();
            theQuery.removeAndNotJoin("dimension1Id", dimensionTable);
            theQuery.removeAndNotJoin("dimension2Id", dimensionTable);
            theQuery.removeAndNotJoin("dimension3Id", dimensionTable);
            theQuery.removeAndNotJoin("warehouseId", dimensionTable);
            theQuery.removeAndNotJoin("itemId", summary);
        }

        lookups = getLookupFromName("location");
        for (EMCLookup lookup : lookups) {
            theQuery = lookup.getTheQuery();
            theQuery.removeAnd("warehouseId");
        }
    }
}
