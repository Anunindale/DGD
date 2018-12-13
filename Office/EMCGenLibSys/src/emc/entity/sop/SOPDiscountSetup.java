/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.sop;

import emc.datatypes.EMCDataType;
import emc.datatypes.sop.discountsetup.CustomerDiscGroup;
import emc.datatypes.sop.discountsetup.CustomerId;
import emc.datatypes.sop.discountsetup.CustomerSelectType;
import emc.datatypes.sop.discountsetup.Dimension1;
import emc.datatypes.sop.discountsetup.Dimension2;
import emc.datatypes.sop.discountsetup.Dimension3;
import emc.datatypes.sop.discountsetup.DiscountPercentage;
import emc.datatypes.sop.discountsetup.ItemDiscGroup;
import emc.datatypes.sop.discountsetup.ItemId;
import emc.datatypes.sop.discountsetup.ItemSelectType;
import emc.datatypes.sop.pricearangements.FromDate;
import emc.datatypes.sop.pricearangements.ToDate;
import emc.enums.sop.discountsetup.CustomerSelectionType;
import emc.enums.sop.discountsetup.ItemSelectionType;
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
@Table(name = "SOPDiscountSetup", uniqueConstraints = {@UniqueConstraint(columnNames = {"customerDiscGroup", "customerId", "itemDiscGroup", "itemId", "dimension1", "dimension2", "dimension3", "companyId"})})
public class SOPDiscountSetup extends EMCEntityClass {

    private String customerSelectType = CustomerSelectionType.CUSTOMER.toString();
    private String customerDiscGroup;
    private String customerId;
    private String itemSelectType = ItemSelectionType.ITEM.toString();
    private String itemDiscGroup;
    private String itemId;
    private String dimension1;
    private String dimension2;
    private String dimension3;
    @Temporal(TemporalType.DATE)
    private Date fromDate;
    @Temporal(TemporalType.DATE)
    private Date toDate;
    private BigDecimal discountPercentage = new BigDecimal(0);

    /** Creates a new instance of SOPDiscountSetup. */
    public SOPDiscountSetup() {
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("customerSelectType", new CustomerSelectType());
        toBuild.put("customerDiscGroup", new CustomerDiscGroup());
        toBuild.put("customerId", new CustomerId());

        toBuild.put("itemSelectType", new ItemSelectType());
        toBuild.put("itemDiscGroup", new ItemDiscGroup());
        toBuild.put("itemId", new ItemId());

        toBuild.put("dimension1", new Dimension1());
        toBuild.put("dimension2", new Dimension2());
        toBuild.put("dimension3", new Dimension3());

        toBuild.put("discountPercentage", new DiscountPercentage());


        toBuild.put("fromDate", new FromDate());
        toBuild.put("toDate", new ToDate());

        return toBuild;
    }

    public String getCustomerSelectType() {
        return customerSelectType;
    }

    public void setCustomerSelectType(String customerSelectType) {
        this.customerSelectType = customerSelectType;
    }

    public String getCustomerDiscGroup() {
        return customerDiscGroup;
    }

    public void setCustomerDiscGroup(String customerDiscGroup) {
        this.customerDiscGroup = customerDiscGroup;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getItemSelectType() {
        return itemSelectType;
    }

    public void setItemSelectType(String itemSelectType) {
        this.itemSelectType = itemSelectType;
    }

    public String getItemDiscGroup() {
        return itemDiscGroup;
    }

    public void setItemDiscGroup(String itemDiscGroup) {
        this.itemDiscGroup = itemDiscGroup;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
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

    public BigDecimal getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(BigDecimal discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }
}
