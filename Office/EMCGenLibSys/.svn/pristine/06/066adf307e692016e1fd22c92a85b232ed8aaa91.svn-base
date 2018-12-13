/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.sop;

import emc.datatypes.EMCDataType;
import emc.datatypes.base.users.foreignkeys.UserIdFK;
import emc.datatypes.inventory.itemmaster.foreignkeys.ItemIdFKNotMandatory;
import emc.datatypes.pop.pricegroups.foreignkeys.PriceGroupFKNotMandatory;
import emc.datatypes.sop.priceaudittrail.CurrentPrice;
import emc.datatypes.sop.priceaudittrail.CustomerId;
import emc.datatypes.sop.priceaudittrail.CustomerType;
import emc.datatypes.sop.priceaudittrail.Dimension1;
import emc.datatypes.sop.priceaudittrail.Dimension2;
import emc.datatypes.sop.priceaudittrail.Dimension3;
import emc.datatypes.sop.priceaudittrail.LogDate;
import emc.datatypes.sop.priceaudittrail.LogTime;
import emc.datatypes.sop.priceaudittrail.SOPrice;
import emc.datatypes.sop.priceaudittrail.Quantity;
import emc.datatypes.sop.priceaudittrail.RecordType;
import emc.datatypes.sop.priceaudittrail.ToDate;
import emc.datatypes.sop.priceaudittrail.OriginalPrice;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "SOPPriceAuditTrail")
public class SOPPriceAuditTrail extends EMCEntityClass {

    private String recordType;
    private String userId;
    @Temporal(TemporalType.DATE)
    private Date logDate;
    @Temporal(TemporalType.TIME)
    private Date logTime;
    private String customerType;
    private String priceGroup;
    private String customerId;
    private String itemId;
    private String dimension1;
    private String dimension2;
    private String dimension3;
    @Temporal(TemporalType.DATE)
    private Date fromDate;
    @Temporal(TemporalType.DATE)
    private Date toDate;
    private BigDecimal quantity = new BigDecimal(0);
    private BigDecimal originalPrice = new BigDecimal(0);
    private BigDecimal price = new BigDecimal(0);
    private BigDecimal updatedPrice = new BigDecimal(0);
    private long sourceRecordId;
    private String changeReason;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getDimension1() {
        return dimension1;
    }

    public void setDimension1(String dimension1) {
        this.dimension1 = dimension1;
    }

    public String getDimension2() {
        return dimension2;
    }

    public void setDimension2(String dimension2) {
        this.dimension2 = dimension2;
    }

    public String getDimension3() {
        return dimension3;
    }

    public void setDimension3(String dimension3) {
        this.dimension3 = dimension3;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    public Date getLogTime() {
        return logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getPriceGroup() {
        return priceGroup;
    }

    public void setPriceGroup(String priceGroup) {
        this.priceGroup = priceGroup;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public BigDecimal getUpdatedPrice() {
        return updatedPrice;
    }

    public void setUpdatedPrice(BigDecimal updatedPrice) {
        this.updatedPrice = updatedPrice;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getSourceRecordId() {
        return sourceRecordId;
    }

    public void setSourceRecordId(long sourceRecordId) {
        this.sourceRecordId = sourceRecordId;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getChangeReason() {
        return changeReason;
    }

    public void setChangeReason(String changeReason) {
        this.changeReason = changeReason;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("logTime", new LogTime());
        toBuild.put("customerType", new CustomerType());
        toBuild.put("priceGroup", new PriceGroupFKNotMandatory());
        toBuild.put("itemId", new ItemIdFKNotMandatory());
        toBuild.put("fromDate", new emc.datatypes.sop.priceaudittrail.FromDate());
        toBuild.put("toDate", new ToDate());
        toBuild.put("userId", new UserIdFK());
        toBuild.put("recordType", new RecordType());
        toBuild.put("logDate", new LogDate());
        toBuild.put("customerId", new CustomerId());
        toBuild.put("dimension1", new Dimension1());
        toBuild.put("dimension2", new Dimension2());
        toBuild.put("dimension3", new Dimension3());
        toBuild.put("quantity", new Quantity());
        toBuild.put("price", new CurrentPrice());
        toBuild.put("updatedPrice", new SOPrice());
        toBuild.put("originalPrice", new OriginalPrice());

        return toBuild;
    }

    @Override
    public EMCQuery buildQuery() {
        EMCQuery query = super.buildQuery();
        query.addOrderBy("logDate");

        return query;
    }
}
