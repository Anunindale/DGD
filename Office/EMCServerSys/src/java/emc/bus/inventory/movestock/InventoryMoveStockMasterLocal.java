/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.movestock;

import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import javax.ejb.Local;

/**
 *
 * @author wikus
 */
@Local
public interface InventoryMoveStockMasterLocal extends EMCEntityBeanLocalInterface {

    /**
     * Does the actual movement 
     * @param warehouseId
     * @param locationId
     * @param userData
     * @return
     * @throws emc.framework.EMCEntityBeanException
     */
    public boolean moveStock(String warehouseId, String locationId, String sessionId, EMCUserData userData) throws EMCEntityBeanException;

    /**
     * Keeps the data in the table in sinc with what is in the Summary Table
     * @param warehouse
     * @param location
     * @param userData
     * @throws emc.framework.EMCEntityBeanException
     */
    public String populateTable(String warehouse, String location, EMCUserData userData) throws EMCEntityBeanException;

    public String populateReserveTable(String warehouse, String location, String so, String awo, EMCUserData userData) throws EMCEntityBeanException;

    public boolean moveReservedStock(String warehouseId, String locationId, String sessionId, EMCUserData userData) throws EMCEntityBeanException;
}
