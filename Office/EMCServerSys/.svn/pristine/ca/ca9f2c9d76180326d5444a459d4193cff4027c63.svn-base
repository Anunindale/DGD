/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.pop;

import emc.entity.pop.POPSuppliers;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import javax.ejb.Local;

/**
 *
 * @author riaan
 */
@Local
public interface POPSupplierLocal extends EMCEntityBeanLocalInterface {
   public String getSupplierName(String suppId, EMCUserData userData);

   /**
    * Returns the specified supplier, or null, if the supplier is not found.
    * @param supplierId Supplier id.
    * @param userData User data.
    * @return The specified supplier, or null, iof the supplier is not found.
    */
   public POPSuppliers getSupplier(String supplierId, EMCUserData userData);
}
