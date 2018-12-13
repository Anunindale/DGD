/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.dimensions.additionaldimensions;

import emc.entity.inventory.dimensions.additionaldimensions.InventoryAdditionalDimensions;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import javax.ejb.Local;

/**
 *
 * @author riaan
 */
@Local
public interface InventoryAdditionalDimensionsLocal extends EMCEntityBeanLocalInterface {

    /**
     * Returns an Additional Dimensions record.
     * @param itemId Item id of the Additional Dimensions record to retrieve.
     * @param dimId  Dimension id containing the dimensions of the record to retrieve.
     * @param userData User data.
     * @return An InventoryAdditionalDimensions record, or null if no record is found.
     */
    public InventoryAdditionalDimensions getAdditionalDimensions(String itemId, long dimId, EMCUserData userData);

    /**
     * Returns an Additional Dimensions record.
     * @param itemId Item id to find dimensions for.
     * @param dimension1 Item dimension 1.
     * @param dimension2 Item dimension 2.
     * @param dimension3 Item dimension 3.
     * @param batch Item batch.
     * @param serial Item serial.
     * @param userData User data.
     * @return An InventoryAdditionalDimensions record, or null if no record is found.
     */
    public InventoryAdditionalDimensions getAdditionalDimensions(String itemId, String dimension1, String dimension2, String dimension3, String batch, String serial, EMCUserData userData);
}
