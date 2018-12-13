/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory;

import emc.entity.inventory.InventoryItemGroup;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import javax.ejb.Local;

/**
 *
 * @author rico
 */
@Local
public interface InventoryItemGroupLocal extends EMCEntityBeanLocalInterface {

    public InventoryItemGroup findItemsItemGroup(String itemId, EMCUserData userData);
}
