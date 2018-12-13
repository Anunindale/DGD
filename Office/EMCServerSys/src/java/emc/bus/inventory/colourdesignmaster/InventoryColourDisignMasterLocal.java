/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.colourdesignmaster;

import emc.framework.EMCEntityBeanLocalInterface;
import javax.ejb.Local;

/**
 *
 * @author wikus
 */
@Local
public interface InventoryColourDisignMasterLocal extends EMCEntityBeanLocalInterface {

    public emc.entity.inventory.colourdisignmaster.InventoryColourDesignMaster findDesignMaster(java.lang.String designNumber, emc.framework.EMCUserData userData);
}
