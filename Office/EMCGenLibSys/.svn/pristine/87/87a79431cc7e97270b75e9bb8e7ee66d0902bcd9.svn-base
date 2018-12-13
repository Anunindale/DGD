/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.base.journals;

import emc.datatypes.base.journals.approvalgroup.ApprovalGroupId;
import emc.datatypes.inventory.journals.approvalgroup.GroupModule;
import emc.datatypes.systemwide.Description;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author riaan
 */
@Entity
@Table(name = "BaseJournalApprovalGroups", uniqueConstraints = {@UniqueConstraint(columnNames = {"journalApprovalGroupId", "companyId"})})
public class BaseJournalApprovalGroups extends EMCEntityClass {

    private String journalApprovalGroupId;
    private String description;
    private String groupModule;

    /** Creates a new instance of BaseJournalApprovalGroups */
    public BaseJournalApprovalGroups() {
        
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

    public String getGroupModule() {
        return groupModule;
    }

    public void setGroupModule(String groupModule) {
        this.groupModule = groupModule;
    }
    
    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();
        
        toBuild.put("journalApprovalGroupId", new ApprovalGroupId());
        toBuild.put("description", new Description());
        toBuild.put("groupModule", new GroupModule());
        
        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> ret = super.buildDefaultLookupFieldList();

        ret.add("journalApprovalGroupId");
        ret.add("description");

        return ret;
    }

}
