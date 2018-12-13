/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.base.journals.journaldefinition;

import emc.datatypes.base.journals.approvalgroup.foreignkeys.ApprovalGroupIdFK;

/**
 *
 * @author riaan
 */
public class ApprovedBy extends ApprovalGroupIdFK {

    /** Creates a new instance of ApprovedBy */
    public ApprovedBy() {
        this.setEmcLabel("Approved By");
        this.setColumnWidth(60);
        this.setMandatory(false);
    }
}
