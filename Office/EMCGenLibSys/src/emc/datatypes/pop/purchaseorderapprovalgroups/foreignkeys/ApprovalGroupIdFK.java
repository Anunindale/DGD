/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.pop.purchaseorderapprovalgroups.foreignkeys;

import emc.datatypes.pop.purchaseorderapprovalgroups.ApprovalGroupId;
import emc.entity.pop.POPPurchaseOrderApprovalGroups;
import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 *
 * @author riaan
 */
public class ApprovalGroupIdFK extends ApprovalGroupId {

    /** Creates a new instance of ApprovalGroupIdFK */
    public ApprovalGroupIdFK() {
        this.setRelatedTable(POPPurchaseOrderApprovalGroups.class.getName());
        this.setRelatedField("purchaseOrderApprovalGroupId");
        this.setUpdateAction(enumDeleteUpdateOptions.RESTRICT);
        this.setDeleteAction(enumDeleteUpdateOptions.RESTRICT);
    }
}
