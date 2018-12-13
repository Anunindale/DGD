/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.debtors;

import emc.datatypes.EMCDataType;
import emc.datatypes.base.employeetable.foreignkeys.EmployeeIdFK;
import emc.datatypes.debtors.creditcontroller.CreditControllerID;
import emc.datatypes.systemwide.Description;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @description : Entity class used to store Credit Contoller records.
 *
 * @date        : 06 Jul 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
@Entity
@Table(name = "DebtorsCreditController", uniqueConstraints = {@UniqueConstraint(columnNames = {"creditControllerId", "companyId"})})
public class DebtorsCreditController extends EMCEntityClass {

    private String creditControllerId;
    private String description;
    private String employeeId;

    public String getCreditControllerId() {
        return creditControllerId;
    }

    public void setCreditControllerId(String creditControllerId) {
        this.creditControllerId = creditControllerId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

        toBuild.put("creditControllerId", new CreditControllerID());
        toBuild.put("description", new Description());
        toBuild.put("employeeId", new EmployeeIdFK());

        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("creditControllerId");
        toBuild.add("description");
        toBuild.add("employeeId");
        return toBuild;
    }
}
