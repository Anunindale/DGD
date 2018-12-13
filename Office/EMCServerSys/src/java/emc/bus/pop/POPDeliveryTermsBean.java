/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.pop;

import emc.entity.pop.POPDeliveryTerms;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import javax.ejb.Stateless;

/**
 *
 * @author riaan
 */
@Stateless
public class POPDeliveryTermsBean extends EMCEntityBean implements POPDeliveryTermsLocal {

    /** Creates a new instance of POPDeliveryTermsBean */
    public POPDeliveryTermsBean() {
        
    }
    /**
     * 
     * Returns the specified delivery terms.
     * @param deliveryTermsId Delivery terms id.
     * @param userData User data.
     * @return A POPDeliveryTerms instance, or null, if the specified terms record is not found.
     */
    public POPDeliveryTerms getDeliveryTerms(String deliveryTermsId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPDeliveryTerms.class);
        query.addAnd("deliveryTermsId", deliveryTermsId);

        return (POPDeliveryTerms)util.executeSingleResultQuery(query, userData);
    }
}
