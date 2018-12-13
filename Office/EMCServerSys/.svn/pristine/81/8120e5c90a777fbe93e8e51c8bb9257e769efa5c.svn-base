/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.serialbatch;

import emc.entity.inventory.serialbatch.InventoryRemoveSerialBatch;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import javax.ejb.Local;

/**
 *
 * @author wikus
 */
@Local
public interface InventorySerialBatchLocal extends EMCEntityBeanLocalInterface {

    public boolean checkRegQuantity(String masterId, String transId, EMCUserData userData);

    public boolean checkAllRemovedQty(String masterId, EMCUserData userData);

    public void deleteSB(InventoryRemoveSerialBatch record, EMCUserData userData) throws EMCEntityBeanException;

    public void deleteRemovedSB(String postSetupId, EMCUserData userData);

  }
