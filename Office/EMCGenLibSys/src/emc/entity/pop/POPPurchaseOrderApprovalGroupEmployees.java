/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.pop;

import emc.datatypes.EMCDataType;
import emc.datatypes.base.employeetable.foreignkeys.EmployeeIdFK;
import emc.datatypes.pop.purchaseorderapprovalgroups.foreignkeys.ApprovalGroupIdFK;
import emc.entity.inventory.journals.*;
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
@Table(name = "POPPurchaseOrderApprovalGroupEmployees", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"purchaseOrderApprovalGroupId", "companyId", "employeeId"})})
public class POPPurchaseOrderApprovalGroupEmployees extends EMCEntityClass {

    private String purchaseOrderApprovalGroupId;
    private String employeeId;

    /**
     * Creates a new instance of POPPurchaseOrderApprovalGroupEmployees
     */
    public POPPurchaseOrderApprovalGroupEmployees() {
    }

    public String getPurchaseOrderApprovalGroupId() {
        return purchaseOrderApprovalGroupId;
    }

    public void setPurchaseOrderApprovalGroupId(String purchaseOrderApprovalGroupId) {
        this.purchaseOrderApprovalGroupId = purchaseOrderApprovalGroupId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("purchaseOrderApprovalGroupId", new ApprovalGroupIdFK());
        toBuild.put("employeeId", new EmployeeIdFK());

        return toBuild;
    }
}