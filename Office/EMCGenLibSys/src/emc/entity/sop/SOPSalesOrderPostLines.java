/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.sop;

import emc.datatypes.EMCDataType;
import emc.datatypes.inventory.itemmaster.foreignkeys.ItemIdFK;
import emc.datatypes.sop.postlines.TransId;
import emc.datatypes.sop.postmaster.foreignkeys.PostMasterIdFK;
import emc.datatypes.systemwide.BigDecimalLineNo;
import emc.framework.EMCEntityClass;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author riaan
 */
@Entity
@Table(name = "SOPSalesOrderPostLines", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"postMasterId", "lineNumber", "companyId"})})
public class SOPSalesOrderPostLines extends EMCEntityClass {

    private String postMasterId;
    private BigDecimal lineNumber = new BigDecimal(0);
    private String itemId;
    private long dimensionId;
    private String uom;
    private BigDecimal pickQuantity = new BigDecimal(0);
    private BigDecimal assembleQuantity = new BigDecimal(0);
    private BigDecimal kimbleQuantity = new BigDecimal(0);
    private BigDecimal maxQuantity = new BigDecimal(0);
    private String transactionNumber;
    private BigDecimal blanketOrderReleaseQty = BigDecimal.ZERO;
    private BigDecimal distributionOrderReleaseQty = BigDecimal.ZERO;
    private BigDecimal backOrderQuantity = BigDecimal.ZERO;
    private BigDecimal mtoQuantity = BigDecimal.ZERO;
    @Temporal(TemporalType.DATE)
    private Date requiredDate;

    public long getDimensionId() {
        return dimensionId;
    }

    public void setDimensionId(long dimensionId) {
        this.dimensionId = dimensionId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public BigDecimal getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(BigDecimal lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getPostMasterId() {
        return postMasterId;
    }

    public void setPostMasterId(String postMasterId) {
        this.postMasterId = postMasterId;
    }

    public BigDecimal getAssembleQuantity() {
        return assembleQuantity;
    }

    public void setAssembleQuantity(BigDecimal assembleQuantity) {
        this.assembleQuantity = assembleQuantity;
    }

    public BigDecimal getPickQuantity() {

        return pickQuantity;
    }

    public void setPickQuantity(BigDecimal pickQuantity) {
        this.pickQuantity = pickQuantity;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public BigDecimal getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(BigDecimal maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    public BigDecimal getBlanketOrderReleaseQty() {
        return blanketOrderReleaseQty;
    }

    public void setBlanketOrderReleaseQty(BigDecimal blanketOrderReleaseQty) {
        this.blanketOrderReleaseQty = blanketOrderReleaseQty;
    }

    public Date getRequiredDate() {
        return requiredDate;
    }

    public void setRequiredDate(Date requiredDate) {
        this.requiredDate = requiredDate;
    }

    public BigDecimal getBackOrderQuantity() {
        return backOrderQuantity;
    }

    public void setBackOrderQuantity(BigDecimal backOrderQuantity) {
        this.backOrderQuantity = backOrderQuantity;
    }

    public BigDecimal getKimbleQuantity() {
        return kimbleQuantity;
    }

    public void setKimbleQuantity(BigDecimal kimbleQuantity) {
        this.kimbleQuantity = kimbleQuantity;
    }

    public BigDecimal getMtoQuantity() {
        return mtoQuantity;
    }

    public void setMtoQuantity(BigDecimal mtoQuantity) {
        this.mtoQuantity = mtoQuantity;
    }

    public BigDecimal getDistributionOrderReleaseQty() {
        return distributionOrderReleaseQty;
    }

    public void setDistributionOrderReleaseQty(BigDecimal distributionOrderReleaseQty) {
        this.distributionOrderReleaseQty = distributionOrderReleaseQty;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("postMasterId", new PostMasterIdFK());
        toBuild.put("lineNumber", new BigDecimalLineNo());
        toBuild.put("itemId", new ItemIdFK());
        toBuild.put("transactionNumber", new TransId());
        return toBuild;
    }
}
