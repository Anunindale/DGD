/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.sop;

import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "SOPSalesOrderMassPostMainTable", uniqueConstraints = {@UniqueConstraint(columnNames = {"queryId", "companyId"})})
public class SOPSalesOrderMassPostMainTable extends EMCEntityClass {

    private String queryId;
    private boolean displayPostForm;
    private boolean reserveStock;

    public boolean isDisplayPostForm() {
        return displayPostForm;
    }

    public void setDisplayPostForm(boolean displayPostForm) {
        this.displayPostForm = displayPostForm;
    }

    public String getQueryId() {
        return queryId;
    }

    public void setQueryId(String queryId) {
        this.queryId = queryId;
    }

    public boolean isReserveStock() {
        return reserveStock;
    }

    public void setReserveStock(boolean reserveStock) {
        this.reserveStock = reserveStock;
    }

    @Override
    public EMCQuery buildQuery() {
        EMCQuery query = super.buildQuery();
        query.addAnd("queryId", null);
        return query;
    }
}
