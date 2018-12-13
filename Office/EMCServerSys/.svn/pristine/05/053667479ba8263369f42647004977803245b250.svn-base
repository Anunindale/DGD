/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.pop;

import emc.entity.pop.POPDiscountGroup;
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
public class POPDiscountGroupBean extends EMCEntityBean implements POPDiscountGroupLocal {

    /** Creates a new instance of POPDiscountGroupBean */
    public POPDiscountGroupBean() {
        
    }

    /**
     * Returns the specified discount group, or null, if it does not exist.
     * @param discountGroupId Discount group id to search for.
     * @param userData User data.
     * @return A POPDiscountGroup record for the specified discount group id.
     */
    public POPDiscountGroup getDiscountGroup(String discountGroupId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPDiscountGroup.class);
        query.addAnd("discountGroupId", discountGroupId);

        return (POPDiscountGroup)util.executeSingleResultQuery(query, userData);
    }
}
