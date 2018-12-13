/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory;

import emc.entity.inventory.InventoryLocation;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.inventorytables.InventoryLocationsEnum;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.tables.EMCTable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class InventoryLocationBean extends EMCEntityBean implements InventoryLocationLocal {

    /**
     * This method checks whether the given location exists in the given warehouse.
     * @param warehouse Id of warehouse to check.
     * @param location Id of location to check.
     * @param userData User data.
     * @return A boolean indicating whether the given location exists in the given warehouse.
     */
    @Override
    public boolean checkLocationInWarehouse(String warehouse, String location, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryLocation.class.getName());
        query.addAnd("locationId", location);
        query.addAnd("warehouseId", warehouse);

        return util.exists(query, userData);
    }

    @Override
    public boolean doDeleteValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        boolean valid = super.doDeleteValidation(vobject, userData);
        if (valid) {
            List<String> systemLocations = new ArrayList<String>();
            systemLocations.add(InventoryLocationsEnum.DESPATCH.toString());
            systemLocations.add(InventoryLocationsEnum.QUALITY_CHECK.toString());
            systemLocations.add(InventoryLocationsEnum.RECEIVING_AREA.toString());

            InventoryLocation local = (InventoryLocation) vobject;
            if (systemLocations.contains(local.getLocationId())) {
                Logger.getLogger("emc").log(Level.SEVERE, "The selected location is an EMC system location, it may not be deleted", userData);
                valid = false;
            }
        }

        return valid;
    }
}
