/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.sop.repgroups;

import emc.entity.sop.SOPCustomers;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author wikus
 */
@Local
public interface SOPSalesRepGroupsLocal extends EMCEntityBeanLocalInterface {

    /**
     * Returns a list of all the customers that the specified rep can sell to.
     * @param repId Rep id.
     * @param invoiceOnly Indicates whether only Invoice to Customers should be selected.
     * @param invoiceToCustomer Indicates that customers with the specified Invoice to Customer should be selected.  This may be null, in which case it will be ignored.
     * @param userData User data.
     * @return A list of all the customers that the specified rep can sell to.
     */
    public List<SOPCustomers> getCustomersForRep(String repId, boolean invoiceOnly, String invoiceToCustomer, EMCUserData userData);
}
