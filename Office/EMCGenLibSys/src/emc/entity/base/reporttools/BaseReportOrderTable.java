/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.base.reporttools;

import emc.datatypes.EMCDataType;
import emc.datatypes.base.reporttools.FieldName;
import emc.datatypes.base.reporttools.QueryTableRecord;
import emc.datatypes.base.reporttools.TableName;
import emc.enums.emcquery.EMCQueryOrderByDirections;
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
@Table(name = "BaseReportOrderTable", uniqueConstraints = {})
public class BaseReportOrderTable extends EMCEntityClass {

    private long reportQueryId;
    private String tableName;
    private String field;
    private int rank;
    private String direction = EMCQueryOrderByDirections.ASC.toString();
    //Used for ordering
    private double lineNo;

    /** Creates a new instance of BaseReportOrderTable */
    public BaseReportOrderTable() {

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

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public long getReportQueryId() {
        return reportQueryId;
    }

    public void setReportQueryId(long reportQueryId) {
        this.reportQueryId = reportQueryId;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("tableName", new TableName());
        toBuild.put("field", new FieldName());
        toBuild.put("reportQueryId", new QueryTableRecord());

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
}
