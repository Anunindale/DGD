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
public interface InventoryParametersLocal extends EMCEntityBeanLocalInterface{

    public emc.entity.inventory.InventoryParameters getInventoryParameters(emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;
    
}
