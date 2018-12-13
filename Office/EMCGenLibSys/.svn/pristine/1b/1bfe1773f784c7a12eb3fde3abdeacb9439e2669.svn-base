/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.sop.datasource;

import emc.datatypes.EMCDataType;
import emc.datatypes.sop.pricearangements.Approval;
import emc.datatypes.sop.pricearangements.PriceListPrice;
import emc.datatypes.sop.pricearangements.SOPrice;
import emc.datatypes.sop.pricechangeapproval.CustomerId;
import emc.datatypes.sop.pricechangeapproval.ItemReference;
import emc.datatypes.sop.pricechangereason.DiscountGroupDiscountPerc;
import emc.datatypes.systemwide.Description;
import emc.entity.sop.SOPSalesOrderLines;
import emc.enums.emcquery.EMCQueryBracketConditions;
import emc.framework.EMCQuery;
import emc.inventory.ItemReferenceInterface;
import java.math.BigDecimal;
import java.util.HashMap;

/**
 *
 * @author wikus
 */
public class SOPPriceChangeApprovalDS extends SOPSalesOrderLines implements ItemReferenceInterface {

    private String customerId;
    private String customerName;
    private String itemReference;
    private String itemDescription;
    private BigDecimal priceListPrice;
    private BigDecimal discountGroupDiscountPerc;
    private boolean approveLine = false;
    private String priceChangeReasonDescription;
    private String discountChangeReasonDescription;

    public SOPPriceChangeApprovalDS() {
        this.setDataSource(true);
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

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemReference() {
        return itemReference;
    }

    public void setItemReference(String itemReference) {
        this.itemReference = itemReference;
    }

    public BigDecimal getPriceListPrice() {
        return priceListPrice;
    }

    public void setPriceListPrice(BigDecimal priceListPrice) {
        this.priceListPrice = priceListPrice;
    }

    public BigDecimal getDiscountGroupDiscountPerc() {
        return discountGroupDiscountPerc;
    }

    public void setDiscountGroupDiscountPerc(BigDecimal discountGroupDiscountPerc) {
        this.discountGroupDiscountPerc = discountGroupDiscountPerc;
    }

    public boolean isApproveLine() {
        return approveLine;
    }

    public void setApproveLine(boolean approveLine) {
        this.approveLine = approveLine;
    }

    public String getDiscountChangeReasonDescription() {
        return discountChangeReasonDescription;
    }

    public void setDiscountChangeReasonDescription(String discountChangeReasonDescription) {
        this.discountChangeReasonDescription = discountChangeReasonDescription;
    }

    public String getPriceChangeReasonDescription() {
        return priceChangeReasonDescription;
    }

    public void setPriceChangeReasonDescription(String priceChangeReasonDescription) {
        this.priceChangeReasonDescription = priceChangeReasonDescription;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("customerId", new CustomerId());
        toBuild.put("itemReference", new ItemReference());
        toBuild.put("priceListPrice", new PriceListPrice());
        toBuild.put("price", new SOPrice());
        toBuild.put("approveLine", new Approval());
        toBuild.put("discountGroupDiscountPerc", new DiscountGroupDiscountPerc());
        toBuild.put("priceChangeReasonDescription", new Description());
        toBuild.put("discountChangeReasonDescription", new Description());

        return toBuild;
    }

    @Override
    public EMCQuery buildQuery() {
        EMCQuery query = super.buildQuery();
        query.openConditionBracket(EMCQueryBracketConditions.NONE);
        query.addOr("priceApprovelRequired", true);
        query.addOr("discountApprovelRequired", true);
        query.closeConditionBracket();
        return query;
    }
}
