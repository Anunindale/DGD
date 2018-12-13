/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.warehousestockenquiry;

import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import javax.ejb.Local;

/**
 *
 * @author wikus
 */
@Local
public interface InventoryWarehouseStockEnquiryBySizeLocal extends EMCEntityBeanLocalInterface {

    public Object populateEnquiry(emc.helpers.inventory.WarehouseEnquiryHelperClass helper, emc.framework.EMCUserData userData);


    @javax.ejb.TransactionAttribute(value = javax.ejb.TransactionAttributeType.REQUIRED)
    public void clearUserData(String recordOwner, EMCUserData userData);
}
