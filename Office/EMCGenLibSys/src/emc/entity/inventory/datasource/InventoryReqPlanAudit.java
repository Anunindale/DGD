/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.inventory.datasource;

import emc.framework.EMCEntityClass;
import java.math.BigDecimal;

/**
 *
 * @author wikus
 */
public class InventoryReqPlanAudit extends EMCEntityClass {

    private String recordType;
    private BigDecimal reqPlanCount = BigDecimal.ZERO;
    private BigDecimal reqPlanSum = BigDecimal.ZERO;
    private BigDecimal sourceCount = BigDecimal.ZERO;
    private BigDecimal sourceSum = BigDecimal.ZERO;

    public InventoryReqPlanAudit() {
        this.setDataSource(true);
    }

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    public BigDecimal getReqPlanCount() {
        return reqPlanCount;
    }

    public void setReqPlanCount(BigDecimal reqPlanCount) {
        this.reqPlanCount = reqPlanCount;
    }

    public BigDecimal getReqPlanSum() {
        return reqPlanSum;
    }

    public void setReqPlanSum(BigDecimal reqPlanSum) {
        this.reqPlanSum = reqPlanSum;
    }

    public BigDecimal getSourceCount() {
        return sourceCount;
    }

    public void setSourceCount(BigDecimal sourceCount) {
        this.sourceCount = sourceCount;
    }

    public BigDecimal getSourceSum() {
        return sourceSum;
    }

    public void setSourceSum(BigDecimal sourceSum) {
        this.sourceSum = sourceSum;
    }
}
