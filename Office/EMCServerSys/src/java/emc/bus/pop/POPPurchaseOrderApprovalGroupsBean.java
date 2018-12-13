/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.pop;

import emc.entity.pop.POPPurchaseOrderApprovalGroups;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class POPPurchaseOrderApprovalGroupsBean extends EMCEntityBean
        implements POPPurchaseOrderApprovalGroupsLocal {

    /** Creates a new instance of POPPurchaseOrderApprovalGroupsBean */
    public POPPurchaseOrderApprovalGroupsBean() {
        
    }

    public POPPurchaseOrderApprovalGroups getApprovalGroups(String approvalGroupId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPPurchaseOrderApprovalGroups.class);
        query.addAnd("purchaseOrderApprovalGroupId", approvalGroupId);
        
        return (POPPurchaseOrderApprovalGroups)util.executeSingleResultQuery(query, userData);
    }
}
