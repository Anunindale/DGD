/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.base;

import emc.datatypes.base.systemlog.DataChanged;
import emc.datatypes.base.systemlog.LogRecordId;
import emc.datatypes.base.systemlog.TableClassPath;
import emc.datatypes.base.systemlog.Type;
import emc.datatypes.base.systemlog.UserName;
import emc.datatypes.systemwide.Description;
import emc.framework.EMCEntityClass;
import java.io.Serializable;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author rico
 */
@Entity
@Table(name = "BaseSystemLog", uniqueConstraints = {@UniqueConstraint(columnNames = {"tableClassPath", "createdTime", "logRecordId"})})
public class BaseSystemLog extends EMCEntityClass implements Serializable {
    private String userName;
    private String description;
    private String type;
    private String dataChanged;
    private String tableClassPath;
    private String logRecordId;
    
    public BaseSystemLog(){
        
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDataChanged() {
        return dataChanged;
    }

    public void setDataChanged(String dataChanged) {
        this.dataChanged = dataChanged;
    }

    public String getTableClassPath() {
        return tableClassPath;
    }

    public void setTableClassPath(String tableClassPath) {
        this.tableClassPath = tableClassPath;
    }

    public String getLogRecordId() {
        return logRecordId;
    }

    public void setLogRecordId(String logRecordId) {
        this.logRecordId = logRecordId;
    }
    
    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();
        
        toBuild.put("userName", new UserName());
        toBuild.put("description", new Description());
        toBuild.put("type", new Type());
        toBuild.put("dataChanged", new DataChanged());
        toBuild.put("tableClassPath", new TableClassPath());
        toBuild.put("logRecordId", new LogRecordId());
        
        return toBuild;
    }
    

   

}
