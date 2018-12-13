/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.gl.journalapprovalgroups.foreignkey;

import emc.datatypes.gl.journalapprovalgroups.JournalApprovalGroup;
import emc.entity.gl.journals.GLJournalApprovalGroups;

/**
 *
 * @author wikus
 */
public class JournalApprovalGroupFK extends JournalApprovalGroup {

    public JournalApprovalGroupFK() {
        this.setRelatedTable(GLJournalApprovalGroups.class.getName());
        this.setRelatedField("journalApprovalGroupId");
    }
}
