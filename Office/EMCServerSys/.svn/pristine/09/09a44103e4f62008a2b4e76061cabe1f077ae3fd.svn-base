/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.pop;

import emc.entity.pop.POPDeliveryModes;
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
public class POPDeliveryModesBean extends EMCEntityBean implements POPDeliveryModesLocal {

    /** Creates a new instance of POPDeliveryModesBean. */
    public POPDeliveryModesBean() {
    }

    @Override
    public String findDescription(String deliveryMethod, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPDeliveryModes.class);
        query.addAnd("deliveryModeId", deliveryMethod);
        query.addField("description");
        return (String) util.executeSingleResultQuery(query, userData);
    }
}
