package emc.entity.base.journals.datasource;

import emc.datatypes.datasources.base.employeeNameDS;
import emc.entity.base.journals.BaseJournalApprovalGroupEmployees;
import java.util.HashMap;

/**
 * 
 * @author wikus
 */
public class BaseJournalApprovalGroupEmployeesDS extends BaseJournalApprovalGroupEmployees {

    private String employeeName;

    public BaseJournalApprovalGroupEmployeesDS() {
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
