/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.debtors.allocationimport;

import emc.framework.EMCEntityBeanLocalInterface;
import javax.ejb.Local;

/**
 *
 * @author riaan
 */
@Local
public interface DebtorsAllocationImportSetupMasterLocal extends EMCEntityBeanLocalInterface {

    /**
     * Returns the field mappings that have set up for the specified customer.
     * @param customerId Customer id.
     * @param userData User data.
     * @return The field mappings that have been set up for the specified customer.
     */
    public java.util.Map<java.lang.String, java.util.List<emc.entity.debtors.allocationimport.DebtorsAllocationImportSetupLines>> getCustomerMapping(java.lang.String customerId, emc.framework.EMCUserData userData);
}
