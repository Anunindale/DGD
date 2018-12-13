/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.base.dblog;

import emc.datatypes.EMCDataType;
import emc.datatypes.base.dblog.DBTable;
import emc.datatypes.base.dblog.DBType;
import emc.datatypes.base.dblog.LogDate;
import emc.datatypes.base.dblog.LogTime;
import emc.datatypes.base.dblog.UniqueIdentifier;
import emc.datatypes.base.dblog.Username;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "BaseDBLog", uniqueConstraints = {@UniqueConstraint(columnNames = {"companyId", "logRecId"})})
public class BaseDBLog extends EMCEntityClass {

    private String tableName;
    private String tableLabel;
    private String uniqueIdentifier;
    private String type;
    private String logRecId;
    private String userName;
    @Column(length=100000)
    private String oldValue;
    @Column(length=100000)
    private String newValue;

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("uniqueIdentifier", new UniqueIdentifier());
        toBuild.put("type", new DBType());
        toBuild.put("createdDate", new LogDate());
        toBuild.put("createdTime", new LogTime());
        toBuild.put("tableLabel", new DBTable());
        toBuild.put("userName", new Username());

        return toBuild;
    }

    public String getLogRecId() {
        return logRecId;
    }

    public void setLogRecId(String logRecId) {
        this.logRecId = logRecId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getTableLabel() {
        return tableLabel;
    }

    public void setTableLabel(String tableLabel) {
        this.tableLabel = tableLabel;
    }

    public String getUniqueIdentifier() {
        return uniqueIdentifier;
    }

    public void setUniqueIdentifier(String uniqueIdentifier) {
        this.uniqueIdentifier = uniqueIdentifier;
    }
}
