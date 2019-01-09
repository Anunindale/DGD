/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.movestock;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.lookup.EMCLookup;
import emc.app.components.lookup.EMCLookupRelationManager;
import emc.entity.inventory.InventoryLocation;
import emc.enums.enumQueryTypes;
import emc.framework.EMCQuery;
import java.util.List;

/**
 *
 * @author wikus
 */
public class MovementLRM extends EMCLookupRelationManager {

    private EMCQuery newLocQuery;

    public MovementLRM() {
        newLocQuery = new EMCQuery(enumQueryTypes.SELECT, InventoryLocation.class.getName());
        newLocQuery.addAnd("warehouseId", "");
        newLocQuery.setSelectDistinctAll(true);
    }

    @Override
    public void doRelation(EMCLookup lookup) {
        if (this.getLookupName(lookup).equals("warehouse")) {
           setQueries(); 
        }
    }

    @Override
    public void doRowChanged(emcDataRelationManagerUpdate drm) {
        setQueries();
    }

    private void setQueries() {
        EMCLookup lkpWarehouse = getLookupFromName("warehouse").get(0);
        //New Location
        newLocQuery.removeAnd("warehouseId");
        newLocQuery.addAnd("warehouseId", lkpWarehouse.getValue());
        List<EMCLookup> theList = getLookupFromName("location");
        theList.get(0).setTheQuery(newLocQuery);
        theList.get(1).setTheQuery(newLocQuery);
        theList.get(2).setTheQuery(newLocQuery);
    }
}
