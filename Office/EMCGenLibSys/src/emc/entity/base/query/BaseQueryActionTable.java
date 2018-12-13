/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.base.query;

import emc.framework.EMCEntityClass;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "BaseQueryActionTable")
public class BaseQueryActionTable extends EMCEntityClass {

    private long reportQueryId;
    private String recipientType;
    private String templateField;
    private String tableName;
    private String field;
    private double lineNo;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getRecipientType() {
        return recipientType;
    }

    public void setRecipientType(String recipientType) {
        this.recipientType = recipientType;
    }

    public long getReportQueryId() {
        return reportQueryId;
    }

    public void setReportQueryId(long reportQueryId) {
        this.reportQueryId = reportQueryId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTemplateField() {
        return templateField;
    }

    public void setTemplateField(String templateField) {
        this.templateField = templateField;
    }

    public double getLineNo() {
        return lineNo;
    }

    public void setLineNo(double lineNo) {
        this.lineNo = lineNo;
    }
}
