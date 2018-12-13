/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.sop;

import emc.datatypes.EMCDataType;
import emc.datatypes.sop.enquiry.BrandGroupEnquiry;
import emc.datatypes.sop.enquiry.Classification1;
import emc.datatypes.sop.enquiry.Classification5;
import emc.datatypes.sop.enquiry.Customer;
import emc.datatypes.sop.enquiry.CustomerName;
import emc.datatypes.sop.enquiry.Dimension1;
import emc.datatypes.sop.enquiry.Dimension2;
import emc.datatypes.sop.enquiry.Dimension3;
import emc.datatypes.sop.enquiry.ItemDescription;
import emc.datatypes.sop.enquiry.ItemReference;
import emc.datatypes.sop.enquiry.MarketingGroup;
import emc.datatypes.sop.enquiry.SalesRep;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import java.math.BigDecimal;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "SOPExplodedSalesEnquiry")
public class SOPExplodedSalesEnquiry extends EMCEntityClass {

    private String userId;
    private String customerId;
    private String customerName;
    private String marketingGroup;
    private String itemId;
    private String itemReference;
    private String itemDescription;
    private String classification1;
    private String classification5;
    private String brandGroup;
    private String dimension1;
    private String dimension2;
    private String dimension3;
    private String customField1;
    private String customField2;
    private String customField3;
    private String customField4;
    private String customField5;
    private String salesRep;
    private BigDecimal quantity1 = BigDecimal.ZERO;
    private BigDecimal quantity2 = BigDecimal.ZERO;
    private BigDecimal quantity3 = BigDecimal.ZERO;
    private BigDecimal quantity4 = BigDecimal.ZERO;
    private BigDecimal quantity5 = BigDecimal.ZERO;
    private BigDecimal quantity6 = BigDecimal.ZERO;
    private BigDecimal quantity7 = BigDecimal.ZERO;
    private BigDecimal quantity8 = BigDecimal.ZERO;
    private BigDecimal quantity9 = BigDecimal.ZERO;
    private BigDecimal quantity10 = BigDecimal.ZERO;
    private BigDecimal quantity11 = BigDecimal.ZERO;
    private BigDecimal quantity12 = BigDecimal.ZERO;
    private BigDecimal quantityTotal = BigDecimal.ZERO;

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("brandGroup", new BrandGroupEnquiry());
        toBuild.put("marketingGroup", new MarketingGroup());
        toBuild.put("salesRep", new SalesRep());
        toBuild.put("customerId", new Customer());
        toBuild.put("customerName", new CustomerName());
        toBuild.put("classification1", new Classification1());
        toBuild.put("classification5", new Classification5());
        toBuild.put("itemReference", new ItemReference());
        toBuild.put("itemDescription", new ItemDescription());
        toBuild.put("dimension1", new Dimension1());
        toBuild.put("dimension2", new Dimension2());
        toBuild.put("dimension3", new Dimension3());
        
        return toBuild;
    }
    
    

    public SOPExplodedSalesEnquiry() {
    }

    public SOPExplodedSalesEnquiry(String userId, String customerId, String customerName, String marketingGroup, String itemId, String itemReference,
            String itemDescription, String classification1, String classification5, String brandGroup, String dimension1, String dimension2, String dimension3,
            String customField1, String customField2, String customField3, String customField4, String customField5, String salesRep,
            BigDecimal quantity1, BigDecimal quantity2, BigDecimal quantity3, BigDecimal quantity4, BigDecimal quantity5, BigDecimal quantity6,
            BigDecimal quantity7, BigDecimal quantity8, BigDecimal quantity9, BigDecimal quantity10, BigDecimal quantity11, BigDecimal quantity12,
            BigDecimal quantityTotal) {
        this.userId = userId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.marketingGroup = marketingGroup;
        this.itemId = itemId;
        this.itemReference = itemReference;
        this.itemDescription = itemDescription;
        this.classification1 = classification1;
        this.classification5 = classification5;
        this.brandGroup = brandGroup;
        this.dimension1 = dimension1;
        this.dimension2 = dimension2;
        this.dimension3 = dimension3;
        this.customField1 = customField1;
        this.customField2 = customField2;
        this.customField3 = customField3;
        this.customField4 = customField4;
        this.customField5 = customField5;
        this.salesRep = salesRep;
        this.quantity1 = quantity1;
        this.quantity2 = quantity2;
        this.quantity3 = quantity3;
        this.quantity4 = quantity4;
        this.quantity5 = quantity5;
        this.quantity6 = quantity6;
        this.quantity7 = quantity7;
        this.quantity8 = quantity8;
        this.quantity9 = quantity9;
        this.quantity10 = quantity10;
        this.quantity11 = quantity11;
        this.quantity12 = quantity12;
        this.quantityTotal = quantityTotal;
    }

    public String getClassification1() {
        return classification1;
    }

    public void setClassification1(String classification1) {
        this.classification1 = classification1;
    }

    public String getClassification5() {
        return classification5;
    }

    public void setClassification5(String classification5) {
        this.classification5 = classification5;
    }

    public String getCustomField1() {
        return customField1;
    }

    public void setCustomField1(String customField1) {
        this.customField1 = customField1;
    }

    public String getCustomField2() {
        return customField2;
    }

    public void setCustomField2(String customField2) {
        this.customField2 = customField2;
    }

    public String getCustomField3() {
        return customField3;
    }

    public void setCustomField3(String customField3) {
        this.customField3 = customField3;
    }

    public String getCustomField4() {
        return customField4;
    }

    public void setCustomField4(String customField4) {
        this.customField4 = customField4;
    }

    public String getCustomField5() {
        return customField5;
    }

    public void setCustomField5(String customField5) {
        this.customField5 = customField5;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemReference() {
        return itemReference;
    }

    public void setItemReference(String itemReference) {
        this.itemReference = itemReference;
    }

    public String getMarketingGroup() {
        return marketingGroup;
    }

    public void setMarketingGroup(String marketingGroup) {
        this.marketingGroup = marketingGroup;
    }

    public BigDecimal getQuantity1() {
        return quantity1;
    }

    public void setQuantity1(BigDecimal quantity1) {
        this.quantity1 = quantity1;
    }

    public BigDecimal getQuantity10() {
        return quantity10;
    }

    public void setQuantity10(BigDecimal quantity10) {
        this.quantity10 = quantity10;
    }

    public BigDecimal getQuantity11() {
        return quantity11;
    }

    public void setQuantity11(BigDecimal quantity11) {
        this.quantity11 = quantity11;
    }

    public BigDecimal getQuantity12() {
        return quantity12;
    }

    public void setQuantity12(BigDecimal quantity12) {
        this.quantity12 = quantity12;
    }

    public BigDecimal getQuantity2() {
        return quantity2;
    }

    public void setQuantity2(BigDecimal quantity2) {
        this.quantity2 = quantity2;
    }

    public BigDecimal getQuantity3() {
        return quantity3;
    }

    public void setQuantity3(BigDecimal quantity3) {
        this.quantity3 = quantity3;
    }

    public BigDecimal getQuantity4() {
        return quantity4;
    }

    public void setQuantity4(BigDecimal quantity4) {
        this.quantity4 = quantity4;
    }

    public BigDecimal getQuantity5() {
        return quantity5;
    }

    public void setQuantity5(BigDecimal quantity5) {
        this.quantity5 = quantity5;
    }

    public BigDecimal getQuantity6() {
        return quantity6;
    }

    public void setQuantity6(BigDecimal quantity6) {
        this.quantity6 = quantity6;
    }

    public BigDecimal getQuantity7() {
        return quantity7;
    }

    public void setQuantity7(BigDecimal quantity7) {
        this.quantity7 = quantity7;
    }

    public BigDecimal getQuantity8() {
        return quantity8;
    }

    public void setQuantity8(BigDecimal quantity8) {
        this.quantity8 = quantity8;
    }

    public BigDecimal getQuantity9() {
        return quantity9;
    }

    public void setQuantity9(BigDecimal quantity9) {
        this.quantity9 = quantity9;
    }

    public BigDecimal getQuantityTotal() {
        return quantityTotal;
    }

    public void setQuantityTotal(BigDecimal quantityTotal) {
        this.quantityTotal = quantityTotal;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBrandGroup() {
        return brandGroup;
    }

    public void setBrandGroup(String brandGroup) {
        this.brandGroup = brandGroup;
    }

    public String getSalesRep() {
        return salesRep;
    }

    public void setSalesRep(String salesRep) {
        this.salesRep = salesRep;
    }

    @Override
    public EMCQuery buildQuery() {
        EMCQuery query = super.buildQuery();
        query.addAnd("userId", null);
        return query;
    }
}
