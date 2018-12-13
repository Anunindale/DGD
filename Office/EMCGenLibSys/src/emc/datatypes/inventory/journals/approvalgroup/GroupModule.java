/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.journals.approvalgroup;

import emc.datatypes.EMCString;

/**
 * @description : Data type for groupModule on BaseJournalApprovalGroups;
 *
 * @date        : 11 May 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class GroupModule extends EMCString {

    /** Creates a new instance of GroupModule */
    public GroupModule() {
        this.setEmcLabel("Module");
        this.setMandatory(true);
    }
}
