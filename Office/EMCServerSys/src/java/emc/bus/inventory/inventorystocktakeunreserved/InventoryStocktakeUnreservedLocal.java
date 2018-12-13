/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.inventorystocktakeunreserved;

import emc.framework.EMCEntityBeanLocalInterface;
import javax.ejb.Local;

/**
 *
 * @author claudette
 */
@Local
public interface InventoryStocktakeUnreservedLocal extends EMCEntityBeanLocalInterface {

    public boolean deleteStockTakeUnreserved(boolean deleteCompleted, java.util.Date deletionDate, emc.framework.EMCUserData userData);
}
