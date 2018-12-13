/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.itemdimensionsetup;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.lookup.EMCLookup;
import emc.app.components.lookup.EMCLookupRelationManager;
import emc.entity.inventory.InventoryItemMaster;
import emc.framework.EMCQuery;
import java.util.List;

/**
 *
 * @author wikus
 */
public class Dimension2LookupRelationManager extends EMCLookupRelationManager {

    @Override
    public void doRelation(EMCLookup lookup) {
        String lookupName = getLookupName(lookup);

        if (lookupName.equals("item")) {
            EMCQuery query;
            List<EMCLookup> lookups = getLookupFromName("dimension");
            Object value = lookup.getValue();
            for (EMCLookup lookupToUpdate : lookups) {
                query = lookupToUpdate.getTheQuery();
                query.removeAnd("itemId", InventoryItemMaster.class.getName());

                if (value != null && !value.equals("")) {
                    query.addAnd("itemId", lookup.getValue().toString(), InventoryItemMaster.class.getName());
                }
            }
        }
    }

    @Override
    public void doRowChanged(emcDataRelationManagerUpdate drm) {
    }
    }
