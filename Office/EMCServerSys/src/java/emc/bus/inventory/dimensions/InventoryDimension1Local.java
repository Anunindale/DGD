/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.dimensions;

import emc.entity.inventory.dimensions.InventoryDimension1;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import javax.ejb.Local;

/**
 *
 * @author riaan
 */
@Local
public interface InventoryDimension1Local extends EMCEntityBeanLocalInterface {

    /**
     * Returns an InventoryDimension1 instance for the specified dimension id.
     * @param dimensionId Dimension id to search for.
     * @param userData User data.
     * @return An InventoryDimension1 instance for the specified dimension id, or null, if none is found.
     */
    public InventoryDimension1 getDimension1(String dimensionId, EMCUserData userData);

    public void updateSortCodes(EMCUserData userData) throws EMCEntityBeanException;

    public String findDimensionDescription(String dimensionId, EMCUserData userData);
}
