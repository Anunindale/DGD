/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.stocktakelogger;

import emc.framework.EMCEntityBeanLocalInterface;
import javax.ejb.Local;

/**
 *
 * @author wikus
 */
@Local
public interface InventoryStockTakeLoggerLocal extends EMCEntityBeanLocalInterface {

    @javax.ejb.TransactionAttribute(value = javax.ejb.TransactionAttributeType.REQUIRES_NEW)
    public Object addStockTakeRecord(java.lang.String journalNumber, java.lang.String itemId, java.lang.String dimension1, java.lang.String dimension2, java.lang.String dimension3, java.lang.String warehouse, java.lang.String location, java.lang.String batch, java.lang.String serial, java.lang.String pallet, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    @javax.ejb.TransactionAttribute(value = javax.ejb.TransactionAttributeType.REQUIRES_NEW)
    public void removeStockTakeRecord(java.lang.String itemId, java.lang.String dimension1, java.lang.String dimension2, java.lang.String dimension3, java.lang.String warehouse, java.lang.String location, java.lang.String batch, java.lang.String serial, java.lang.String pallet, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    public String checkStockTakeRecord(java.lang.String itemId, long dimensionId, emc.framework.EMCUserData userData);
}
