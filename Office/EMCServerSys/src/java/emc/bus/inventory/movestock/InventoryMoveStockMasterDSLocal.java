/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.movestock;

import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCEntityClass;
import emc.framework.EMCUserData;
import javax.ejb.Local;

/**
 *
 * @author wikus
 */
@Local
public interface InventoryMoveStockMasterDSLocal extends EMCEntityBeanLocalInterface {

    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData);
}
