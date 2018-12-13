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
public interface InventoryItemDimension3SetupLocal extends EMCEntityBeanLocalInterface {
    public void removeInactive(EMCUserData userData, String className);
}
