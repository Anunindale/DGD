/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory;

import emc.entity.inventory.InventoryWarehouse;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import javax.ejb.Local;

/**
 *
 * @author riaan
 */
@Local
public interface InventoryWarehouseLocal extends EMCEntityBeanLocalInterface {

    /**
     * Returns the warehouse with the specified id.
     * @param warehouseId Warehouse id to search for.
     * @param userData User data.
     * @return The warehouse with the specified id.
     */
    public InventoryWarehouse getWarehouse(String warehouseId, EMCUserData userData);

    public void createSystemLocations(EMCUserData userData) throws EMCEntityBeanException;
}
