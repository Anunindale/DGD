/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.pop;

import emc.entity.pop.POPPriceGroup;
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
public class POPPriceGroupBean extends EMCEntityBean implements POPPriceGroupLocal {

    /** Creates a new instance of CreditorPriceGroupBean */
    public POPPriceGroupBean() {
    }

    public POPPriceGroup getPriceGroup(String priceGroup, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPPriceGroup.class);
        query.addAnd("priceGroupId", priceGroup);
        return (POPPriceGroup) util.executeSingleResultQuery(query, userData);
    }
}
