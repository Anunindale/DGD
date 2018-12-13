/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.inventory.dimensions;

import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import javax.ejb.Local;

/**
 *
 * @author riaan
 */
@Local
public interface InventoryItemDimensionCombinationsLocal extends EMCEntityBeanLocalInterface {

    /**
     * Checks whether combinations for the given item already exist.
     * @param itemId Item to check combinations for.
     * @param userData User data.
     * @return a boolean indicating whether combinations exist.
     */
    public boolean checkCombinationsExist(String itemId, EMCUserData userData); 
}
