/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.debtors.creditnoteapprovalgroups;

import emc.datatypes.debtors.creditnoteapprovalgroups.foreignkeys.ApprovalGroupIdFK;

/**
 *
 * @author riaan
 */
public class HigherLevelApprovalGroupId extends ApprovalGroupIdFK {

    /** Creates a new instance of HigherLevelApprovalGroupId. */
    public HigherLevelApprovalGroupId() {
        this.setEmcLabel("Higher Level");
        this.setMandatory(false);
    }
}
