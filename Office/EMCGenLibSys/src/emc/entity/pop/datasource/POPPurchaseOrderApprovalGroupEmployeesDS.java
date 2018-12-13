package emc.entity.pop.datasource;

import emc.entity.pop.POPPurchaseOrderApprovalGroupEmployees;
import java.util.HashMap;

/**
 * 
 * @author wikus
 */
public class POPPurchaseOrderApprovalGroupEmployeesDS extends POPPurchaseOrderApprovalGroupEmployees {

    private String employeeName;

    public POPPurchaseOrderApprovalGroupEmployeesDS() {
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
        return toBuild;
    }
}
