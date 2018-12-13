/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.base.dblog;

import emc.datatypes.EMCDataType;
import emc.datatypes.base.dblog.DBTable;
import emc.datatypes.base.dblog.DBType;
import emc.datatypes.base.dblogsetup.ActionValue;
import emc.datatypes.base.dblogsetup.ActionWorkFlow;
import emc.datatypes.base.dblogsetup.LogField;
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
@Table(name = "BaseDBLogSetup", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"companyId", "tableName", "fieldName", "type"})})
public class BaseDBLogSetup extends EMCEntityClass {

    private String tableName;
    private String fieldName;
    private String type;
    @Column(length = 1000)
    private String actionValue;
    private String actionWorkFlow;
    private String uniqueConstraintValue;

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap toBuild = super.buildFieldList();
        toBuild.put("tableName", new DBTable());
        toBuild.put("fieldName", new LogField());
        toBuild.put("type", new DBType());
        toBuild.put("actionValue", new ActionValue());
        toBuild.put("actionWorkFlow", new ActionWorkFlow());
        return toBuild;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
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

    public String getActionValue() {
        return actionValue;
    }

    public void setActionValue(String actionValue) {
        this.actionValue = actionValue;
    }

    public String getActionWorkFlow() {
        return actionWorkFlow;
    }

    public void setActionWorkFlow(String actionWorkFlow) {
        this.actionWorkFlow = actionWorkFlow;
    }

    public String getUniqueConstraintValue() {
        return uniqueConstraintValue;
    }

    public void setUniqueConstraintValue(String uniqueConstraintValue) {
        this.uniqueConstraintValue = uniqueConstraintValue;
    }
}
