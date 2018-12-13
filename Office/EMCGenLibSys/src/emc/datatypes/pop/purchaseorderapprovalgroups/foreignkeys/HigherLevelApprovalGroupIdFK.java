/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.pop.purchaseorderapprovalgroups.foreignkeys;

import emc.entity.pop.POPPurchaseOrderApprovalGroups;

/**
 *
 * @author claudette
 */
public class HigherLevelApprovalGroupIdFK extends HigherLevelApprovalGroupIdFKNM {

    public HigherLevelApprovalGroupIdFK() {
        this.setRelatedTable(POPPurchaseOrderApprovalGroups.class.getName());
        this.setRelatedField("purchaseOrderApprovalGroupId");
    }
}
