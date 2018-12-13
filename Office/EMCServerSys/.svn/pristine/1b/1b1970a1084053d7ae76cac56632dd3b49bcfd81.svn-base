/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.crm.prospectclosereason;

import emc.entity.crm.CRMProspectCloseReason;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;

/**
 *
 * @author rico
 */
@Stateless
public class CRMProspectCloseReasonBean extends EMCEntityBean implements CRMProspectCloseReasonLocal{
    public boolean validateCancelReason(String reason, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, CRMProspectCloseReason.class);
        query.addAnd("reasonId", reason);
        if (!util.exists(query, userData)) {
            Logger.getLogger("emc").log(Level.SEVERE, "The value entered for close reason does not exist, please reselect.", userData);
            return false;
        }
        return true;
    }
}
