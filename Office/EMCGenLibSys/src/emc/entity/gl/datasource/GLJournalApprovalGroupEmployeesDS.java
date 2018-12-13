package emc.entity.gl.datasource;

import emc.datatypes.datasources.base.employeeNameDS;
import emc.entity.gl.journals.GLJournalApprovalGroupEmployees;
import java.util.HashMap;

/**
 * 
 * @author wikus
 */
public class GLJournalApprovalGroupEmployeesDS extends GLJournalApprovalGroupEmployees {

    private String employeeName;

    public GLJournalApprovalGroupEmployeesDS() {
        this.setDataSource(true);
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();
        toBuild.put("employeeName", new employeeNameDS());
        return toBuild;
    }
}
