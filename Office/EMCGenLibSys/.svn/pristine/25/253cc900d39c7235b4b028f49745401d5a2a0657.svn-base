/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.base;

import emc.datatypes.base.systemtables.TableName;
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
@Table(name = "BaseSystemTable", uniqueConstraints = {@UniqueConstraint(columnNames = {"tableName", "companyId"})})
public class BaseSystemTable extends EMCEntityClass implements Serializable {
    private String tableName;
    private String description;

    /** Creates a new instance of BaseSystemTable */
    public BaseSystemTable() {
        
    }
    
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
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
       
        toBuild.put("tableName", new TableName());
        toBuild.put("description", new Description());

        return toBuild;
    }
}
