/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.app.components.inventory.lookups.itemreference;

import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.components.lookup.popup.EMCLookupPopupSearchTable;
import emc.entity.inventory.InventoryReference;
import emc.enums.emcquery.EMCQueryConditions;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;

/**
 *
 * @author riaan
 */
public class ItemReferencePopup extends EMCLookupPopup {

    /** Creates a new instance onf ItemReferencePopup. Lookup field key should always be the item id.  The keylist should be specified on the data source.  */
    public ItemReferencePopup(String lookupFieldKey, EMCUserData userData) {
        super(new emc.entity.inventory.datasource.InventoryItemMasterLookupDS(), lookupFieldKey, userData);
    }

    @Override
    protected void setupSearchQuery(EMCQuery searchQuery, boolean searchingFromLookup, String lookupFieldKeyValue) {
        String refClassName = InventoryReference.class.getName();       
                        
        if (searchingFromLookup) {
            if (!Functions.checkBlank(lookupFieldKeyValue)) {
                searchQuery.addAnd("reference", lookupFieldKeyValue, refClassName, EMCQueryConditions.STARTS_WITH);
            }
        } else {
            emcTableModelUpdate model = (emcTableModelUpdate)userTable.getModel();

            EMCLookupPopupSearchTable searchTable = this.lookupTablePanel.getSearchTable();
        
            String fieldName = null;
            Object value = null;

            int numColumns = model.getColumnCount();

            for (int i = 0; i < numColumns; i++) {
                fieldName = model.getFieldName(i);
                value = searchTable.getValueAt(0, i);

                if (!Functions.checkBlank(value)) {
                    if (fieldName.equals("reference")) {
                        searchQuery.addAnd("reference", value, refClassName, EMCQueryConditions.STARTS_WITH); 
                    } else if (fieldName.equals("refType")) {
                        searchQuery.addAnd("refType", value, refClassName, EMCQueryConditions.STARTS_WITH); 
                    } else {
                        searchQuery.addAnd(fieldName, value, EMCQueryConditions.STARTS_WITH);
                    }
                } 
            }
        }
    }
    
    /** This method updates the search table when searching from a lookup. */
    @Override
    protected void updateTable(String column, String value) {
        super.updateTable("reference", value);
    }

}
