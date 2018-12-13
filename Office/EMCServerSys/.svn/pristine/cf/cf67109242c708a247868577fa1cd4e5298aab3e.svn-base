/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.pop;

import emc.entity.pop.POPDiscountGroup;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import javax.ejb.Local;

/**
 *
 * @author riaan
 */
@Local
public interface POPDiscountGroupLocal extends EMCEntityBeanLocalInterface {
    
    /**
     * Returns the specified discount group, or null, if it does not exist.
     * @param discountGroupId Discount group id to search for.
     * @param userData User data.
     * @return A POPDiscountGroup record for the specified discount group id.
     */
    public POPDiscountGroup getDiscountGroup(String discountGroupId, EMCUserData userData);
}
