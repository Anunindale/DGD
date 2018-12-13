/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.base.journals.approvalgroup;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class ApprovalGroupId extends EMCString {

    public ApprovalGroupId() {
        this.setEmcLabel("Group Id");
        this.setColumnWidth(50);
        this.setMandatory(true);
    }
}
