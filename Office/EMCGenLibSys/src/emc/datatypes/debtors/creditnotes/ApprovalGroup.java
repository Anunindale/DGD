/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.debtors.creditnotes;

import emc.datatypes.debtors.creditnoteapprovalgroups.foreignkeys.ApprovalGroupIdFK;

/**
 *
 * @author riaan
 */
public class ApprovalGroup extends ApprovalGroupIdFK {

    /** Creates a new instance of ApprovalGroup. */
    public ApprovalGroup() {
    	this.setColumnWidth(68);
        this.setMandatory(false);
    }
}
