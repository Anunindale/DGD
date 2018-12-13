/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.productgroup;

import emc.framework.EMCEntityBeanLocalInterface;
import javax.ejb.Local;

/**
 *
 * @author wikus
 */
@Local
public interface InventoryProductGroupLocal extends EMCEntityBeanLocalInterface {

    public java.util.List<emc.helpers.inventory.ItemProductGroupWebHelper> getProductGroups(emc.framework.EMCUserData userData);
}
