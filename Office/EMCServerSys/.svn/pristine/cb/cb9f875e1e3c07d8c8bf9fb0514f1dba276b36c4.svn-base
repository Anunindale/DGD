/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory;

import emc.framework.EMCEntityBeanLocalInterface;
import javax.ejb.Local;

/**
 *
 * @author wikus
 */
@Local
public interface InventoryLocationLocal extends EMCEntityBeanLocalInterface {

    /**
     * This method checks whether the given location exists in the given warehouse.
     * @param warehouse Id of warehouse to check.
     * @param location Id of location to check.
     * @param userData User data.
     * @return A boolean indicating whether the given location exists in the given warehouse.
     */
    public boolean checkLocationInWarehouse(java.lang.String warehouse, java.lang.String location, emc.framework.EMCUserData userData);
}
