/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.inventory.display.itemmaster;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.lookup.EMCLookup;
import emc.app.components.lookup.EMCLookupRelationManager;
import emc.entity.inventory.dimensions.InventoryItemDimensionCombinations;
import emc.framework.EMCDebug;
import emc.framework.EMCQuery;
import java.util.List;

/**
 *
 * @author riaan
 */
public class ItemMasterLookupRelationManager extends EMCLookupRelationManager {

    private String dimCombinations = InventoryItemDimensionCombinations.class.getName();
    
    @Override
    public void doRelation(EMCLookup lookup) {
        String lookupName = getLookupName(lookup);
        
        if (lookupName != null) {
            if (lookupName.equals("item")) {
                updateItemChanged(lookup);
            } else if (lookupName.equals("dimension1")) {
                updateDimension1Changed(lookup);
            } else if (lookupName.equals("dimension2")) {
                updateDimension2Changed(lookup);
            } else if (lookupName.equals("dimension3")) {
                updateDimension3Changed(lookup);
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
            
            Object value = drm.getFieldValueAt(drm.getLastRowAccessed(), "planningSubstituteItem");
            
            if (value != null && !value.toString().equals("")) {
                theQuery.addAnd("itemId", value, dimCombinations);
            }
            
            value = drm.getFieldValueAt(drm.getLastRowAccessed(), "planningSubstituteDimension2");
            if (value != null && !value.toString().equals("")) {
                theQuery.addAnd("dimension2Id", value, dimCombinations);
            }
            
            value = drm.getFieldValueAt(drm.getLastRowAccessed(), "planningSubstituteDimension3");
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
            
            Object value = drm.getFieldValueAt(drm.getLastRowAccessed(), "planningSubstituteItem");
            
            if (value != null && !value.toString().equals("")) {
                theQuery.addAnd("itemId", value, dimCombinations);
            }
            
            value = drm.getFieldValueAt(drm.getLastRowAccessed(), "planningSubstituteDimension1");
            if (value != null && !value.toString().equals("")) {
                theQuery.addAnd("dimension1Id", value, dimCombinations);
            }
            
            value = drm.getFieldValueAt(drm.getLastRowAccessed(), "planningSubstituteDimension3");
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
            
            Object value = drm.getFieldValueAt(drm.getLastRowAccessed(), "planningSubstituteItem");
            
            if (value != null && !value.toString().equals("")) {
                theQuery.addAnd("itemId", value, dimCombinations);
            }
            
            value = drm.getFieldValueAt(drm.getLastRowAccessed(), "planningSubstituteDimension2");
            if (value != null && !value.toString().equals("")) {
                theQuery.addAnd("dimension2Id", value, dimCombinations);
            }
            
            value = drm.getFieldValueAt(drm.getLastRowAccessed(), "planningSubstituteDimension1");
            if (value != null && !value.toString().equals("")) {
                theQuery.addAnd("dimension1Id", value, dimCombinations);
            }
        } 
    }
}

