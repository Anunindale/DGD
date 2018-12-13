/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.base.reporttools;

import emc.datatypes.EMCDataType;
import emc.datatypes.base.reporttools.Condition;
import emc.datatypes.base.reporttools.FieldName;
import emc.datatypes.base.reporttools.LockValue;
import emc.datatypes.base.reporttools.TableName;
import emc.enums.emcquery.EMCQueryConditions;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author riaan
 */
@Entity
@Table(name = "BaseReportWhereTable", uniqueConstraints = {})
public class BaseReportWhereTable extends EMCEntityClass {

    private long reportQueryId;
    private String tableName;
    private String field;
    private String fieldValue = "";  //Default value.  Should be valid for any type of field.
    private String whereCondition = EMCQueryConditions.EQUALS.toString();
    /**
     * If any special permissions should be checked when editing/deleting a row,
     * this field should be populated with a value from the
     * WhereLineSpecialPermissions enum. If no permissions need to be checked,
     * this field should have a null value.
     */
    private String special;
    //Used for ordering
    private double lineNo;
    //Lock
    private boolean lockValue;
    private String lockType;
    private String lockDate;
    private String lockFrom;
    private String lockTo;

    /**
     * Creates a new instance of BaseReportWhereTable
     */
    public BaseReportWhereTable() {
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public String getWhereCondition() {
        return whereCondition;
    }

    public void setWhereCondition(String whereCondition) {
        this.whereCondition = whereCondition;
    }

    public long getReportQueryId() {
        return reportQueryId;
    }

    public void setReportQueryId(long reportQueryId) {
        this.reportQueryId = reportQueryId;
    }

    public String getSpecial() {
        return special;
    }

    /**
     * WhereLineSpecialPermissions*
     */
    public void setSpecial(String special) {
        this.special = special;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("tableName", new TableName());
        toBuild.put("field", new FieldName());
        toBuild.put("whereCondition", new Condition());
        toBuild.put("lockValue", new LockValue());

        return toBuild;
    }

    @Override
    public EMCQuery getQuery() {
        EMCQuery query = super.getQuery();

        query.addOrderBy("lineNo");

        return query;
    }

    public double getLineNo() {
        return lineNo;
    }

    public void setLineNo(double lineNo) {
        this.lineNo = lineNo;
    }

    public String getLockType() {
        return lockType;
    }

    public void setLockType(String lockType) {
        this.lockType = lockType;
    }

    public String getLockFrom() {
        return lockFrom;
    }

    public void setLockFrom(String lockFrom) {
        this.lockFrom = lockFrom;
    }

    public String getLockTo() {
        return lockTo;
    }

    public void setLockTo(String lockTo) {
        this.lockTo = lockTo;
    }

    public boolean isLockValue() {
        return lockValue;
    }

    public void setLockValue(boolean lockValue) {
        this.lockValue = lockValue;
    }

    public String getLockDate() {
        return lockDate;
    }

    public void setLockDate(String lockDate) {
        this.lockDate = lockDate;
    }
}