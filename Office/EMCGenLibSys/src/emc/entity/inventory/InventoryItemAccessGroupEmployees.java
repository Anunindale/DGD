/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.inventory;

import emc.datatypes.inventory.accessgroupemployees.AccessGroupIdFK;
import emc.datatypes.inventory.accessgroupemployees.EmployeeIdFK;
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
@Table(name = "InventoryItemAccessGroupEmployees", uniqueConstraints = {@UniqueConstraint(columnNames = {"itemAccessGroupId" ,"employeeId", "companyId"})})
public class InventoryItemAccessGroupEmployees extends EMCEntityClass {

    private String itemAccessGroupId;
    private String employeeId;
    
    /** Creates a new instance of InventoryItemAccessGroupUsers */
    public InventoryItemAccessGroupEmployees() {
        
    }

    public String getItemAccessGroupId() {
        return itemAccessGroupId;
    }

    public void setItemAccessGroupId(String itemAccessGroupId) {
        this.itemAccessGroupId = itemAccessGroupId;
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
        
        toBuild.put("itemAccessGroupId", new AccessGroupIdFK());
        toBuild.put("employeeId", new EmployeeIdFK());
                
        
        return toBuild;
    }
}