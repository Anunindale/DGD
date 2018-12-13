/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.base.employeeaccessgroupemployees;

import emc.datatypes.EMCDataType;
import emc.datatypes.base.employeeaccessgroup.foreignkeys.AccessGroupFK;
import emc.datatypes.base.employeetable.foreignkeys.EmployeeIdFK;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "BaseEmployeeAccessGroupEmployees", uniqueConstraints = {@UniqueConstraint(columnNames = {"accessGroup", "employeeId", "companyId"})})
public class BaseEmployeeAccessGroupEmployees extends EMCEntityClass {

    private String accessGroup;
    private String employeeId;

    public String getAccessGroup() {
        return accessGroup;
    }

    public void setAccessGroup(String accessGroup) {
        this.accessGroup = accessGroup;
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
        toBuild.put("accessGroup", new AccessGroupFK());
        toBuild.put("employeeId", new EmployeeIdFK());
        return toBuild;
    }
}
