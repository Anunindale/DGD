/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */ 
package emc.bus.inventory.movestock;

import emc.entity.inventory.movestock.InventoryMoveStockMaster;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import javax.ejb.Local;

/**
 *
 * @author wikus
 */
@Local
public interface InventoryMoveStockSummaryLocal extends EMCEntityBeanLocalInterface {

    public void insertRecord(InventoryMoveStockMaster master, EMCUserData userData) throws EMCEntityBeanException;
}
