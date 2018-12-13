/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.base.journals.accessgroups;

import emc.datatypes.EMCDataType;
import emc.datatypes.base.employeetable.foreignkeys.EmployeeIdFK;
import emc.datatypes.base.journals.accessgroup.foreignkeys.AccessGroupIdFK;
import emc.datatypes.base.journals.accessgroupemployee.AccessGroupId;
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
@Table(name = "BaseJournalAccessGroupEmployees", uniqueConstraints = {@UniqueConstraint(columnNames = {"accessGroupId", "employeeId"})})
public class BaseJournalAccessGroupEmployees extends EMCEntityClass {

    private String accessGroupId;
    private String employeeId;
    
    /** Creates a new instance of BaseJournalAccessGroupEmployees. */
    public BaseJournalAccessGroupEmployees() {
        
    }

    public String getAccessGroupId() {
        return accessGroupId;
    }

    public void setAccessGroupId(String accessGroupId) {
        this.accessGroupId = accessGroupId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap toBuild = super.buildFieldList();
        toBuild.put("accessGroupId", new AccessGroupId());
        toBuild.put("employeeId", new EmployeeIdFK());
        return toBuild;
    }

}
