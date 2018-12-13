/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */ 

package emc.entity.pop;

import emc.datatypes.pop.suppliergroups.SupplierGroupId;
import emc.datatypes.systemwide.Description;
import emc.framework.EMCEntityClass;
import java.io.Serializable;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author riaan
 */
@Entity
@Table(name = "POPSupplierGroup", uniqueConstraints = {@UniqueConstraint(columnNames = {"supplierGroupId", "companyId"})})
public class POPSupplierGroup extends EMCEntityClass implements Serializable {
    private String supplierGroupId;
    private String description;
    
    /** Creates a new instance of POPSupplierGroup */
    public POPSupplierGroup() {
        
    }

    public String getSupplierGroupId() {
        return supplierGroupId;
    }

    public void setSupplierGroupId(String supplierGroupId) {
        this.supplierGroupId = supplierGroupId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();
        
        toBuild.put("supplierGroupId", new SupplierGroupId());
        toBuild.put("description", new Description());
        
        return toBuild;
    }
}
