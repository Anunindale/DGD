/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.base.journals;

import emc.datatypes.base.employeetable.foreignkeys.EmployeeIdFK;
import emc.datatypes.base.journals.approvalgroup.foreignkeys.ApprovalGroupIdFK;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author riaan
 */
@Entity
@Table(name = "BaseJournalApprovalGroupEmployees", uniqueConstraints = {@UniqueConstraint(columnNames = {"employeeId", "journalApprovalGroupId", "companyId"})})
public class BaseJournalApprovalGroupEmployees extends EMCEntityClass {

    private String journalApprovalGroupId;
    private String employeeId;
    
    /** Creates a new instance of BaseJournalApprovalGroupEmployees */
    public BaseJournalApprovalGroupEmployees() {
        
    }

    public String getJournalApprovalGroupId() {
        return journalApprovalGroupId;
    }

    public void setJournalApprovalGroupId(String journalApprovalGroupId) {
        this.journalApprovalGroupId = journalApprovalGroupId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
    
    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();
        
        toBuild.put("journalApprovalGroupId", new ApprovalGroupIdFK());
        toBuild.put("employeeId", new EmployeeIdFK());
        
        return toBuild;
    }
}