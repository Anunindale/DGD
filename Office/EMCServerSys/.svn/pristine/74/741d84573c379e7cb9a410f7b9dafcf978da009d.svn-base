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
public interface InventoryWarehouseStockEnquiryLocal extends EMCEntityBeanLocalInterface {

    public void clearUserData(EMCUserData userData);

    public boolean populateEnquiry(java.lang.String warehouse, java.lang.String location, java.lang.String item, java.lang.String productGroup, java.lang.String planningGroup, java.lang.String classification1, java.lang.String classification5, java.lang.String dimension1, java.lang.String dimension2, java.lang.String dimension3, java.lang.String batch, java.lang.String serial, int boxLevel, boolean doAging, emc.framework.EMCUserData userData);
}
