/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.debtors;

import emc.datatypes.EMCDataType;
import emc.datatypes.debtors.creditnoteapprovalgroups.ApprovalGroupId;
import emc.datatypes.debtors.creditnoteapprovalgroups.Description;
import emc.datatypes.debtors.creditnoteapprovalgroups.HigherLevelApprovalGroupId;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @description : This entity class stores Credit Note Approval Groups.
 *
 * @date        : 16 Jul 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
@Entity
@Table(name = "DebtorsCreditNoteApprovalGroups", uniqueConstraints = {@UniqueConstraint(columnNames = {"approvalGroupId", "companyId"})})
public class DebtorsCreditNoteApprovalGroups extends EMCEntityClass {

    private String approvalGroupId;
    private String description;
    private String higherLevelApprovalGroupId;
    
    /** Creates a new instance of DebtorsCreditNoteApprovalGroups */
    public DebtorsCreditNoteApprovalGroups() {

    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("approvalGroupId", new ApprovalGroupId());
        toBuild.put("description", new Description());
        toBuild.put("higherLevelApprovalGroupId", new HigherLevelApprovalGroupId());
        
        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> ret = super.buildDefaultLookupFieldList();

        ret.add("approvalGroupId");
        ret.add("description");

        return ret;
    }

    public String getApprovalGroupId() {
        return approvalGroupId;
    }

    public void setApprovalGroupId(String approvalGroupId) {
        this.approvalGroupId = approvalGroupId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHigherLevelApprovalGroupId() {
        return higherLevelApprovalGroupId;
    }

    public void setHigherLevelApprovalGroupId(String higherLevelApprovalGroupId) {
        this.higherLevelApprovalGroupId = higherLevelApprovalGroupId;
    }
}
