/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.pop.purchaseorderapprovalgroups.foreignkeys;

import emc.datatypes.pop.purchaseorderapprovalgroups.ApprovalGroupId;
import emc.entity.pop.POPPurchaseOrderApprovalGroups;


/**
 *
 * @author riaan
 */
public class ApprovalGroupIdFKNotMandatory extends ApprovalGroupId {

    /** Creates a new instance of ApprovalGroupIdFK */
    public ApprovalGroupIdFKNotMandatory() {
        this.setRelatedTable(POPPurchaseOrderApprovalGroups.class.getName());
        this.setRelatedField("purchaseOrderApprovalGroupId");
        this.setMandatory(false);
    }
}
