/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.sop;

import emc.enums.emcquery.EMCQueryOrderByDirections;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "SOPSalesOrderMassPostOrderTable")
public class SOPSalesOrderMassPostOrderTable extends EMCEntityClass {

    private String queryId;
    private String tableName;
    private String fieldName;
    private String orderDirection = EMCQueryOrderByDirections.ASC.toString();
    private int orderRank;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getOrderDirection() {
        return orderDirection;
    }

    public void setOrderDirection(String orderDirection) {
        this.orderDirection = orderDirection;
    }

    public int getOrderRank() {
        return orderRank;
    }

    public void setOrderRank(int orderRank) {
        this.orderRank = orderRank;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getQueryId() {
        return queryId;
    }

    public void setQueryId(String queryId) {
        this.queryId = queryId;
    }

    @Override
    public EMCQuery buildQuery() {
        EMCQuery query = super.buildQuery();
        query.addAnd("queryId", null);
        return query;
    }
}
