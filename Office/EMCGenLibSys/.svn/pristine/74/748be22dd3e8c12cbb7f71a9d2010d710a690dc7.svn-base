/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.sop;

import emc.datatypes.EMCDataType;
import emc.datatypes.inventory.dimensions.foreignkeys.Dimension1FKNotMandatory;
import emc.datatypes.inventory.dimensions.foreignkeys.Dimension2FKNotMandatory;
import emc.datatypes.inventory.dimensions.foreignkeys.Dimension3FKNotMandatory;
import emc.datatypes.inventory.itemmaster.foreignkeys.ItemIdFK;
import emc.datatypes.pop.pricegroups.foreignkeys.PriceGroupFKNotMandatory;
import emc.datatypes.sop.customers.foreignkeys.CustomerIdFKNotMandatory;
import emc.datatypes.sop.pricearangements.CustomerType;
import emc.datatypes.sop.pricearangements.FromDate;
import emc.datatypes.sop.pricearangements.Price;
import emc.datatypes.sop.pricearangements.Quantity;
import emc.datatypes.sop.pricearangements.ToDate;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.sop.pricearangement.PricingCustomerType;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import emc.functions.Functions;
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
 * @author wikus
 */
@Entity
@Table(name = "SOPPriceArangements", uniqueConstraints = {@UniqueConstraint(columnNames = {"uniqueFields", "companyId"})})
public class SOPPriceArangements extends EMCEntityClass {

    private String customerType = PricingCustomerType.CUSTOMER.toString();
    private String priceGroup;
    private String customerId;
    private String itemId;
    private String dimension1;
    private int dimension1SortCode;
    private String dimension2;
    private int dimension2SortCode;
    private String dimension3;
    private int dimension3SortCode;
    @Temporal(TemporalType.DATE)
    private Date fromDate;
    @Temporal(TemporalType.DATE)
    private Date toDate;
    private BigDecimal quantity = new BigDecimal(0);
    private BigDecimal price = new BigDecimal(0);
    private String uniqueFields;

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

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public int getDimension1SortCode() {
        return dimension1SortCode;
    }

    public void setDimension1SortCode(int dimension1SortCode) {
        this.dimension1SortCode = dimension1SortCode;
    }

    public int getDimension2SortCode() {
        return dimension2SortCode;
    }

    public void setDimension2SortCode(int dimension2SortCode) {
        this.dimension2SortCode = dimension2SortCode;
    }

    public int getDimension3SortCode() {
        return dimension3SortCode;
    }

    public void setDimension3SortCode(int dimension3SortCode) {
        this.dimension3SortCode = dimension3SortCode;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("customerType", new CustomerType());
        toBuild.put("priceGroup", new PriceGroupFKNotMandatory());
        toBuild.put("customerId", new CustomerIdFKNotMandatory());
        toBuild.put("itemId", new ItemIdFK());
        toBuild.put("dimension1", new Dimension1FKNotMandatory());
        toBuild.put("dimension2", new Dimension2FKNotMandatory());
        toBuild.put("dimension3", new Dimension3FKNotMandatory());
        toBuild.put("fromDate", new FromDate());
        toBuild.put("toDate", new ToDate());
        toBuild.put("quantity", new Quantity());
        toBuild.put("price", new Price());
        return toBuild;
    }

    @Override
    public EMCQuery buildQuery() {
        EMCQuery query = super.buildQuery();
        query.addAnd("toDate", Functions.nowDate(), EMCQueryConditions.GREATER_THAN_EQ);
        query.addOrderBy("customerType");
        query.addOrderBy("customerId");
        query.addOrderBy("priceGroup");
        query.addOrderBy("itemId");
        query.addOrderBy("dimension1SortCode");
        query.addOrderBy("dimension3SortCode");
        query.addOrderBy("dimension2SortCode");
        query.addOrderBy("fromDate");
        query.addOrderBy("toDate");
        query.addOrderBy("quantity");
        return query;
    }

    public String getUniqueFields() {
        return uniqueFields;
    }

    public void setUniqueFields(String uniqueFields) {
        this.uniqueFields = uniqueFields;
    }
}
