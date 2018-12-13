/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.pop;

import emc.entity.pop.POPDeliveryTerms;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import javax.ejb.Local;

/**
 *
 * @author riaan
 */
@Local
public interface POPDeliveryTermsLocal extends EMCEntityBeanLocalInterface {

    /**
     * Returns the specified delivery terms.
     * @param deliveryTermsId Delivery terms id.
     * @param userData User data.
     * @return A POPDeliveryTerms instance, or null, if the specified terms record is not found.
     */
    public POPDeliveryTerms getDeliveryTerms(String deliveryTermsId, EMCUserData userData);
}
