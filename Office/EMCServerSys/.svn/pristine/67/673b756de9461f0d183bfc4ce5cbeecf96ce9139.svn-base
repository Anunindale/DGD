/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.dimensions;

import emc.enums.inventory.dimensions.DimensionsEnum;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import javax.ejb.Local;

/**
 *
 * @author riaan
 */
@Local
public interface InventoryItemDimensionGroupLocal extends EMCEntityBeanLocalInterface {

    /**
     * Checks if the given dimensiuon is active on the item
     * @param itemId
     * @param dimension
     * @param userData
     * @return
     */
    public boolean isDimensionActiveOnItem(String itemId, DimensionsEnum dimension, EMCUserData userData);
   

}
