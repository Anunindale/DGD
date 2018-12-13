/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.gl.journals;

import emc.datatypes.gl.journalapprovalgroups.JournalApprovalGroup;
import emc.datatypes.systemwide.Description;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "GLJournalApprovalGroups", uniqueConstraints = {@UniqueConstraint(columnNames = {"journalApprovalGroupId", "companyId"})})
public class GLJournalApprovalGroups extends EMCEntityClass {

    private String journalApprovalGroupId;
    private String description;

    /** Creates a new instance of GLJournalApprovalGroups */
    public GLJournalApprovalGroups() {
    }

    public String getJournalApprovalGroupId() {
        return journalApprovalGroupId;
    }

    public void setJournalApprovalGroupId(String journalApprovalGroupId) {
        this.journalApprovalGroupId = journalApprovalGroupId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();

        toBuild.put("journalApprovalGroupId", new JournalApprovalGroup());
        toBuild.put("description", new Description());

        return toBuild;
    }
}
