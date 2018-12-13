package emc.bus.sop.customergroup;

import emc.framework.EMCEntityBeanLocalInterface;
import javax.ejb.Local;

/**
 * 
 * @Author wikus
 */
@Local
public interface SOPCustomerGroupLocal extends EMCEntityBeanLocalInterface {

    /**
     * Returns a list of all customer in the specified customer group.
     * @param customerGroup Customer group identifier.
     * @param userData User data.
     * @return A list of all customer in the specified customer group.
     */
    public java.util.List<emc.entity.sop.SOPCustomers> getGroupCustomers(java.lang.String customerGroup, emc.framework.EMCUserData userData);
}
