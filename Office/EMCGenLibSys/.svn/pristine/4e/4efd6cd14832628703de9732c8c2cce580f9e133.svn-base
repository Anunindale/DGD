package emc.entity.creditors;

import emc.datatypes.EMCDataType;
import emc.datatypes.creditors.approvalgroups.ApprovalGroupId;
import emc.datatypes.creditors.approvalgroups.ApproveCreditNote;
import emc.datatypes.creditors.approvalgroups.ApproveInvoice;
import emc.datatypes.creditors.approvalgroups.Description;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/** 
 *
 * @author claudette
 */
@Entity
@Table(name = "CreditorsApprovalGroups", uniqueConstraints = {@UniqueConstraint(columnNames = {"companyId", "approvalGroupId"})})
public class CreditorsApprovalGroups extends EMCEntityClass {

    private String approvalGroupId;
    private String description;
    private boolean approveCreditNote;
    private boolean approveInvoice;

    /** Creates a new instance of CreditorsApprovalGroups. */
    public CreditorsApprovalGroups() {
    }

    @Override
    public HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("approvalGroupId", new ApprovalGroupId());
        toBuild.put("description", new Description());
        toBuild.put("approveCreditNote", new ApproveCreditNote());
        toBuild.put("approveInvoice", new ApproveInvoice());
        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("approvalGroupId");
        toBuild.add("description");

        return toBuild;
    }

    public String getApprovalGroupId() {
        return this.approvalGroupId;
    }

    public void setApprovalGroupId(String approvalGroupId) {
        this.approvalGroupId = approvalGroupId;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getApproveCreditNote() {
        return this.approveCreditNote;
    }

    public void setApproveCreditNote(boolean approveCreditNote) {
        this.approveCreditNote = approveCreditNote;
    }

    public boolean getApproveInvoice() {
        return this.approveInvoice;
    }

    public void setApproveInvoice(boolean approveInvoice) {
        this.approveInvoice = approveInvoice;
    }
}
