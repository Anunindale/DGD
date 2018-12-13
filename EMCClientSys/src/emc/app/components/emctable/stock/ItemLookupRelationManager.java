/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.app.components.emctable.stock;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.EMCLookup;
import emc.app.components.lookup.EMCLookupRelationManager;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.dimensions.InventoryItemDimensionCombinations;
import emc.framework.EMCDebug;
import emc.framework.EMCQuery;
import java.util.List;

/**
 * This class is used to manage lookup relations in association with a StockDRM
 * 
 * @author riaan
 */
public class ItemLookupRelationManager extends EMCLookupRelationManager {

    private String dimCombinations = InventoryItemDimensionCombinations.class.getName();
    private String itemMaster = InventoryItemMaster.class.getName();
    
    private String itemField;
    private String dim1Field;
    private String dim2Field;
    private String dim3Field;
    
    private StockDRM drm;
    
    /** Creates a new instance of ItemLookupRelationManager.  If a lookup is null, it will not be added. 
     *  This constructor also sets the lookup relation manager on the data relation manager.
     */
    public ItemLookupRelationManager(StockDRM drm, ItemLookupRelationManagerParameters params) {
        super();
        
        this.drm = drm;
        
        drm.addItemLookupRelationManager(this);
        
        this.itemField = params.getItemField();
        this.dim1Field = params.getDim1Field();
        this.dim2Field = params.getDim2Field();
        this.dim3Field = params.getDim3Field();
                
        if (params.getFormItemLookup() != null) {
            addLookup(params.getFormItemLookup(), itemField);
        }
        
        if (params.getTableItemLookup() != null) {
            addLookup(params.getTableItemLookup(), itemField);
        }
        
        if (params.getFormDim1Lookup() != null) {
            addLookup(params.getFormDim1Lookup(), dim1Field);
        }
        
        if (params.getFormDim2Lookup() != null) {
            addLookup(params.getFormDim2Lookup(), dim2Field);
        }
        
        if (params.getFormDim3Lookup() != null) {
            addLookup(params.getFormDim3Lookup(), dim3Field);
        }

        initializeLookups();

        //Only update table dimension lookups after calling initialize lookups
        if (params.getTableDim1Lookup() != null) {
            addLookup(params.getTableDim1Lookup(), dim1Field);
        }
        
        if (params.getTableDim2Lookup() != null) {
            addLookup(params.getTableDim2Lookup(), dim2Field);
        }
        
        if (params.getTableDim3Lookup() != null) {
            addLookup(params.getTableDim3Lookup(), dim3Field);
        }
    }
    
    @Override
    public void doRelation(EMCLookup lookup) {
        String lookupName = getLookupName(lookup);
        
        if (lookupName != null) {
            //Item field will be handled by DRM, after item id is retrieved.
            /*if (lookupName.equals(itemField)) {
                updateItemChanged(lookup);
            } else */ if (lookupName.equals(dim1Field)) {
                updateDimension1Changed(lookup);
            } else if (lookupName.equals(dim2Field)) {
                updateDimension2Changed(lookup);
            } else if (lookupName.equals(dim3Field)) {
                updateDimension3Changed(lookup);
            }
            
            //System.out.println("Lookup - " + lookupName + " query - " + lookup.getTheQuery());
        } else {
            if (EMCDebug.getDebug()) {
                System.out.println("Lookup not mapped");
            }
        }
    }
    
    /** Updates other lookups when the item lookup is updated.  Uses the value in the item field in the data relation manager instead of the lookup value, so null may be passed to this method. */
    private void updateItemChanged(EMCLookup lookup) {
        if (lookup instanceof EMCLookupJTableComponent) {
            //Data relation manager will can update after setting field value.
            return;
        }
        
        EMCQuery query = null;
        //We need item id
        Object value = drm.getLastFieldValueAt(itemField);
        
        List<EMCLookup> lookupsToUpdate = getLookupFromName(dim1Field);
        for (EMCLookup lookupToUpdate : lookupsToUpdate) {
            query = lookupToUpdate.getTheQuery();
            query.removeAndNotJoin("itemId", dimCombinations);

            if (value != null && !value.equals("")) {
                query.addAnd("itemId", value, dimCombinations);
            }
            
            lookupToUpdate.setTheQuery(query);
        }

        lookupsToUpdate = getLookupFromName(dim2Field);
        for (EMCLookup lookupToUpdate : lookupsToUpdate) {
            query = lookupToUpdate.getTheQuery();
            query.removeAnd("itemId", dimCombinations);

            if (value != null && !value.equals("")) {
                query.addAnd("itemId", value, dimCombinations);
            }
            lookupToUpdate.setTheQuery(query);
        }

        lookupsToUpdate = getLookupFromName(dim3Field);
        for (EMCLookup lookupToUpdate : lookupsToUpdate) {
            query = lookupToUpdate.getTheQuery();
            query.removeAnd("itemId", dimCombinations);

            if (value != null && !value.equals("")) {
                query.addAnd("itemId", value, dimCombinations);
            }
            lookupToUpdate.setTheQuery(query);
        }
    }

    /** Updates other lookups when the dimension 1 lookup is updated */
    private void updateDimension1Changed(EMCLookup lookup) {
        EMCQuery query = null;
        Object value = lookup.getValue();       
        List<EMCLookup> lookupsToUpdate = getLookupFromName(dim2Field);
        
        for (EMCLookup lookupToUpdate : lookupsToUpdate) {
            query = lookupToUpdate.getTheQuery();
            query.removeAnd("dimension1Id", dimCombinations);

            if (value != null && !value.equals("")) {
                query.addAnd("dimension1Id", value, dimCombinations);
            }
            
            lookupToUpdate.setTheQuery(query);
        }

        lookupsToUpdate = getLookupFromName(dim3Field);
        for (EMCLookup lookupToUpdate : lookupsToUpdate) {
            query = lookupToUpdate.getTheQuery();
            query.removeAnd("dimension1Id", dimCombinations);

            if (value != null && !value.equals("")) {
                query.addAnd("dimension1Id", value, dimCombinations);
            }
            
            lookupToUpdate.setTheQuery(query);
        }
    }
    
    /** Updates other lookups when the dimension 2 lookup is updated */
    private void updateDimension2Changed(EMCLookup lookup) {
        EMCQuery query = null;
        Object value = lookup.getValue();
        List<EMCLookup> lookupsToUpdate = getLookupFromName(dim1Field);
        
        for (EMCLookup lookupToUpdate : lookupsToUpdate) {
            query = lookupToUpdate.getTheQuery();
            query.removeAnd("dimension2Id", dimCombinations);

            if (value != null && !value.equals("")) {
                query.addAnd("dimension2Id", value, dimCombinations);
            }
            
            lookupToUpdate.setTheQuery(query);
        }

        lookupsToUpdate = getLookupFromName(dim3Field);
        for (EMCLookup lookupToUpdate : lookupsToUpdate) {
            query = lookupToUpdate.getTheQuery();
            query.removeAnd("dimension2Id", dimCombinations);

            if (value != null && !value.equals("")) {
                query.addAnd("dimension2Id", value, dimCombinations);
            }
            
            lookupToUpdate.setTheQuery(query);
        }
    }
    
    /** Updates other lookups when the dimension 3 lookup is updated */
    private void updateDimension3Changed(EMCLookup lookup) {
        EMCQuery query = null;
        Object value = lookup.getValue();
        List<EMCLookup> lookupsToUpdate = getLookupFromName(dim2Field);
        
        for (EMCLookup lookupToUpdate : lookupsToUpdate) {
            query = lookupToUpdate.getTheQuery();
            query.removeAnd("dimension3Id", dimCombinations);
            
            if (value != null && !value.equals("")) {
                query.addAnd("dimension3Id", value, dimCombinations);
            }
            
            System.out.println("Dimension 2 Query - " + query);
            
            lookupToUpdate.setTheQuery(query);
        }

        lookupsToUpdate = getLookupFromName(dim1Field);
        for (EMCLookup lookupToUpdate : lookupsToUpdate) {
            query = lookupToUpdate.getTheQuery();
            query.removeAnd("dimension3Id", dimCombinations);

            if (value != null && !value.equals("")) {
                query.addAnd("dimension3Id", value, dimCombinations);
            }
            
            lookupToUpdate.setTheQuery(query);
        }
    }

    @Override
    public void doRowChanged(emcDataRelationManagerUpdate drm) {

        EMCQuery theQuery = null;
        List<EMCLookup> lookups = getLookupFromName(dim1Field);

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
            
            lookup.setTheQuery(theQuery);
        } 
        
        lookups = getLookupFromName(dim2Field);

        for (EMCLookup lookup : lookups) {
            theQuery = lookup.getTheQuery();
            theQuery.removeAndNotJoin("dimension1Id", dimCombinations);
            theQuery.removeAndNotJoin("dimension3Id", dimCombinations);
            theQuery.removeAndNotJoin("itemId", dimCombinations);
            
            Object value = drm.getFieldValueAt(drm.getLastRowAccessed(),itemField);
            
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
            
            lookup.setTheQuery(theQuery);
        } 
        
        lookups = getLookupFromName(dim3Field);

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
            
            lookup.setTheQuery(theQuery);
        }
    }
    
    /** This public method is used by the Stock Data Relation Manager to update lookups related to the item lookup, after item id has been set on server. */
    public void fireItemUpdated() {
        this.updateItemChanged(null);
    }
}
