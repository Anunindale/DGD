package emc.entity.creditors;

import emc.datatypes.EMCDataType;
import emc.datatypes.creditors.approvalgroupssetup.ApprovalGroupId;
import emc.datatypes.creditors.approvalgroupssetup.EmployeeId;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/** 
*
* @author claudette
*/
@Entity
@Table(name="CreditorsApprovalGroupSetup", uniqueConstraints = {@UniqueConstraint(columnNames = {"companyId", "approvalGroupId", "employeeId"})})
public class CreditorsApprovalGroupSetup extends EMCEntityClass {

    private String approvalGroupId;
    private String employeeId;

    /** Creates a new instance of CreditorsApprovalGroupSetup. */
    public CreditorsApprovalGroupSetup() {
    }

    @Override
    public HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("approvalGroupId", new ApprovalGroupId());
        toBuild.put("employeeId", new EmployeeId());
        return toBuild;
    }

    public String getApprovalGroupId() {
        return this.approvalGroupId;
    }

    public void setApprovalGroupId(String approvalGroupId) {
        this.approvalGroupId = approvalGroupId;
    }

    public String getEmployeeId() {
        return this.employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

}