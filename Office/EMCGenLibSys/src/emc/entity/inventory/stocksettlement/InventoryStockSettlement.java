/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.inventory.stocksettlement;

import emc.datatypes.inventory.stocksettlement.RunStatus;
import emc.datatypes.inventory.stocksettlement.EndDate;
import emc.datatypes.inventory.stocksettlement.StartDate;
import emc.datatypes.EMCDataType;
import emc.datatypes.inventory.stocksettlement.SettlementId;
import emc.datatypes.systemwide.Description;
import emc.enums.emcquery.EMCQueryOrderByDirections;
import emc.enums.inventory.settlement.SettlementStatus;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import java.util.Date;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author rico
 */
@Entity
@Table(name = "InventoryStockSettlement", uniqueConstraints = {@UniqueConstraint(columnNames = {"settlementId", "companyId"})})
public class InventoryStockSettlement extends EMCEntityClass {
    private String settlementId;
    private String description;
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Temporal(TemporalType.DATE)
    private Date endDate;
    private String runStatus = SettlementStatus.CAPTURED.toString();

    public InventoryStockSettlement(){

    }

    /**
     * @return the settlementId
     */
    public String getSettlementId() {
        return settlementId;
    }

    /**
     * @param settlementId the settlementId to set
     */
    public void setSettlementId(String settlementId) {
        this.settlementId = settlementId;
    }

    /**
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the closeDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param closeDate the closeDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the runStatus
     */
    public String getRunStatus() {
        return runStatus;
    }

    /**
     * @param runStatus the runStatus to set
     */
    public void setRunStatus(String runStatus) {
        this.runStatus = runStatus;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public EMCQuery buildQuery() {
        EMCQuery qu = super.buildQuery();
        qu.addOrderBy("endDate", InventoryStockSettlement.class.getName(), EMCQueryOrderByDirections.DESC);
        return qu;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> map = super.buildFieldList();
        map.put("settlementId", new SettlementId());
        map.put("description", new Description());
        map.put("startDate", new StartDate());
        map.put("endDate", new EndDate());
        map.put("runStatus", new RunStatus());
        return map;
    }


}
