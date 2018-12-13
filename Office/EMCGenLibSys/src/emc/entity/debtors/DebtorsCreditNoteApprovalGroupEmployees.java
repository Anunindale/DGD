/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.debtors;

import emc.datatypes.EMCDataType;
import emc.datatypes.debtors.creditnoteapprovalgroupemployees.ApprovalGroupId;
import emc.datatypes.debtors.creditnoteapprovalgroupemployees.EmployeeId;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @description : This entity class stores Credit Note Approval Group Employees.
 *
 * @date        : 16 Jul 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
@Entity
@Table(name = "DebtorsCreditNoteApprovalGroupEmployees", uniqueConstraints = {@UniqueConstraint(columnNames = {"approvalGroupId", "employeeId", "companyId"})})
public class DebtorsCreditNoteApprovalGroupEmployees extends EMCEntityClass {

    private String approvalGroupId;
    private String employeeId;

    /** Creates a new instance of DebtorsCreditNoteApprovalGroupEmployees */
    public DebtorsCreditNoteApprovalGroupEmployees() {

    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("approvalGroupId", new ApprovalGroupId());
        toBuild.put("employeeId", new EmployeeId());

        return toBuild;
    }

    public String getApprovalGroupId() {
        return approvalGroupId;
    }

    public void setApprovalGroupId(String approvalGroupId) {
        this.approvalGroupId = approvalGroupId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
}
