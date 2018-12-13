/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.base.journals.approvalgroup.foreignkeys;

import emc.datatypes.base.journals.approvalgroup.*;
import emc.entity.base.journals.BaseJournalApprovalGroups;
import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 *
 * @author wikus
 */
public class ApprovalGroupIdFK extends ApprovalGroupId {

    public ApprovalGroupIdFK() {
        this.setRelatedTable(BaseJournalApprovalGroups.class.getName());
        this.setRelatedField("journalApprovalGroupId");
        this.setDeleteAction(enumDeleteUpdateOptions.RESTRICT);
        this.setUpdateAction(enumDeleteUpdateOptions.RESTRICT);
    }
}
