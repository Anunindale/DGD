/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.gl.journals;

import emc.datatypes.base.employeetable.foreignkeys.EmployeeIdFK;
import emc.datatypes.gl.journalapprovalgroups.foreignkey.JournalApprovalGroupFK;
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
@Table(name = "GLJournalApprovalGroupEmployees", uniqueConstraints = {@UniqueConstraint(columnNames = {"employeeId", "companyId"})})
public class GLJournalApprovalGroupEmployees extends EMCEntityClass {

    private String journalApprovalGroupId;
    private String employeeId;

    /** Creates a new instance of GLJournalApprovalGroupEmployees */
    public GLJournalApprovalGroupEmployees() {
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

        toBuild.put("journalApprovalGroupId", new JournalApprovalGroupFK());
        toBuild.put("employeeId", new EmployeeIdFK());

        return toBuild;
    }
}
