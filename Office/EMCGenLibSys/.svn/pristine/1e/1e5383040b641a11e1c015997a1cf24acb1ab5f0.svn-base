/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.debtors.superclasses;

import emc.datatypes.EMCDataType;
import emc.datatypes.gl.currency.foreignkeys.CurrencyFK;
import emc.enums.debtors.invoicestatus.DebtorsInvoiceStatus;
import emc.enums.emcquery.EMCQueryConditions;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @description : This is a superclass for Invoices and Credit Notes in the Debtors Module.
 *
 * @date        : 14 Jul 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
@Entity
public class DebtorsInvoiceMasterSuper extends EMCEntityClass {

    //Invoice/Credit Note number
    private String invCNNumber;
    private String invCNType;
    @Temporal(TemporalType.DATE)
    private Date invoiceDate;
    private boolean invoiceStock;
    private String status = DebtorsInvoiceStatus.CAPTURED.toString();
    private String customerNo;
    private String customerTradingAs;
    private String invoiceToCustNo;
    private String invoiceToCustomerName;
    private String invoiceToCustomerExportCurrency; //this is the currency of the customer
    private String billingCurrency; //this is the currency we bill in, not necessarily the system currency as stated on company
    private String invoiceToCustomerCountryOfDestination;
    private String invoiceToCustomerDeliveryInstructions;
    private String invoiceToCustomerVatRegistration;
    private String customerContact;
    private String deliveryAddress1;
    private String deliveryAddress2;
    private String deliveryAddress3;
    private String deliveryAddress4;
    private String deliveryAddress5;
    private String deliveryAddressPostalCode;
    private String invoiceAddress1;
    private String invoiceAddress2;
    private String invoiceAddress3;
    private String invoiceAddress4;
    private String invoiceAddress5;
    private String invoiceAddressPostalCode;
    private String vatNo;
    private String vatCode;
    private String salesGroup;
    private String salesRegion;
    private String salesArea;
    private String customerOrderNumber;
    private String reference;
    private String salesOrderNo;
    private String orderWarehouse;
    private String deliveryMethod;
    private String deliveryTerms;
    private String pricingGroup;
    private String discountGroup;
    private String extraChargeGroup;
    private String termsCode;
    private String settlementDiscCode;
    @Temporal(TemporalType.DATE)
    private Date paymentDueDate;
    @Temporal(TemporalType.DATE)
    private Date settlementDiscDate;
    @Temporal(TemporalType.DATE)
    private Date approvedDate;
    @Temporal(TemporalType.TIME)
    private Date approvedTime;
    private String approvedBy;
    @Temporal(TemporalType.DATE)
    private Date postedDate;
    @Temporal(TemporalType.TIME)
    private Date postedTime;
    private String postedBy;
    @Temporal(TemporalType.DATE)
    private Date printedDate;
    @Temporal(TemporalType.TIME)
    private Date printedTime;
    private String printedBy;
    @Temporal(TemporalType.DATE)
    private Date lastPrintedDate;
    @Temporal(TemporalType.TIME)
    private Date lastPrintedTime;
    private String lastPrintedBy;
    @Column(length = 1000)
    private String comments;
    private String salesRep;
    private BigDecimal numberOfCartons = BigDecimal.ZERO;
    private BigDecimal totalWeight = BigDecimal.ZERO;
    private BigDecimal deliveryCharge = BigDecimal.ZERO;
    private String waybillNumber;
    //Credit note specific fields
    private String reasonCode;
    private String refInvoiceNo;
    private String authorizationNo;
    private String claimNo;
    private String returnOption;
    private String approvalGroup;
    //These fields are used for reporting, and are only set when posting an invoice.
    private BigDecimal salesTotal;
    private BigDecimal discountTotal;
    private BigDecimal vatTotal;
    private BigDecimal invoiceTotal;
    //Indicates whether the Sales Rep on this Invoice/Credit Note qualifies for commission.
    private boolean commissionApplicable = true;
    //Indicates whether a delivery charge should apply to this Invoice.
    private boolean deliveryChargeApplicable;
    private String distributionNumber;

    /** Creates a new instance of DebtorsInvoiceMasterSuper */
    public DebtorsInvoiceMasterSuper() {
    }

    @Override
    public EMCQuery buildQuery() {
        EMCQuery query = super.buildQuery();

        query.addAnd("status", DebtorsInvoiceStatus.POSTED.toString(), EMCQueryConditions.NOT);
        query.addAnd("status", DebtorsInvoiceStatus.CANCELED.toString(), EMCQueryConditions.NOT);
        query.addAnd("status", DebtorsInvoiceStatus.DISTRIBUTION.toString(), EMCQueryConditions.NOT);

        return query;
    }

    @Override
    public EMCQuery buildLookupQuery() {
        EMCQuery query = super.buildQuery();

        //Select all data in table.  Requested for Inventory Register reports.

        return query;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap toBuild = super.buildFieldList();
        toBuild.put("billingCurrency",new CurrencyFK());
        return toBuild;
    }

    public String getInvCNType() {
        return invCNType;
    }

    public void setInvCNType(String invCNType) {
        this.invCNType = invCNType;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public String getRefInvoiceNo() {
        return refInvoiceNo;
    }

    public void setRefInvoiceNo(String refInvoiceNo) {
        this.refInvoiceNo = refInvoiceNo;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public boolean isInvoiceStock() {
        return invoiceStock;
    }

    public void setInvoiceStock(boolean invoiceStock) {
        this.invoiceStock = invoiceStock;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getInvoiceToCustNo() {
        return invoiceToCustNo;
    }

    public void setInvoiceToCustNo(String invoiceToCustNo) {
        this.invoiceToCustNo = invoiceToCustNo;
    }

    public String getCustomerContact() {
        return customerContact;
    }

    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
    }

    public String getDeliveryAddress1() {
        return deliveryAddress1;
    }

    public void setDeliveryAddress1(String deliveryAddress1) {
        this.deliveryAddress1 = deliveryAddress1;
    }

    public String getDeliveryAddress2() {
        return deliveryAddress2;
    }

    public void setDeliveryAddress2(String deliveryAddress2) {
        this.deliveryAddress2 = deliveryAddress2;
    }

    public String getDeliveryAddress3() {
        return deliveryAddress3;
    }

    public void setDeliveryAddress3(String deliveryAddress3) {
        this.deliveryAddress3 = deliveryAddress3;
    }

    public String getDeliveryAddress4() {
        return deliveryAddress4;
    }

    public void setDeliveryAddress4(String deliveryAddress4) {
        this.deliveryAddress4 = deliveryAddress4;
    }

    public String getDeliveryAddress5() {
        return deliveryAddress5;
    }

    public void setDeliveryAddress5(String deliveryAddress5) {
        this.deliveryAddress5 = deliveryAddress5;
    }

    public String getDeliveryAddressPostalCode() {
        return deliveryAddressPostalCode;
    }

    public void setDeliveryAddressPostalCode(String deliveryAddressPostalCode) {
        this.deliveryAddressPostalCode = deliveryAddressPostalCode;
    }

    public String getInvoiceAddress1() {
        return invoiceAddress1;
    }

    public void setInvoiceAddress1(String invoiceAddress1) {
        this.invoiceAddress1 = invoiceAddress1;
    }

    public String getInvoiceAddress2() {
        return invoiceAddress2;
    }

    public void setInvoiceAddress2(String invoiceAddress2) {
        this.invoiceAddress2 = invoiceAddress2;
    }

    public String getInvoiceAddress3() {
        return invoiceAddress3;
    }

    public void setInvoiceAddress3(String invoiceAddress3) {
        this.invoiceAddress3 = invoiceAddress3;
    }

    public String getInvoiceAddress4() {
        return invoiceAddress4;
    }

    public void setInvoiceAddress4(String invoiceAddress4) {
        this.invoiceAddress4 = invoiceAddress4;
    }

    public String getInvoiceAddress5() {
        return invoiceAddress5;
    }

    public void setInvoiceAddress5(String invoiceAddress5) {
        this.invoiceAddress5 = invoiceAddress5;
    }

    public String getInvoiceAddressPostalCode() {
        return invoiceAddressPostalCode;
    }

    public void setInvoiceAddressPostalCode(String invoiceAddressPostalCode) {
        this.invoiceAddressPostalCode = invoiceAddressPostalCode;
    }

    public String getVatNo() {
        return vatNo;
    }

    public void setVatNo(String vatNo) {
        this.vatNo = vatNo;
    }

    public String getVatCode() {
        return vatCode;
    }

    public void setVatCode(String vatCode) {
        this.vatCode = vatCode;
    }

    public String getSalesGroup() {
        return salesGroup;
    }

    public void setSalesGroup(String salesGroup) {
        this.salesGroup = salesGroup;
    }

    public String getSalesRegion() {
        return salesRegion;
    }

    public void setSalesRegion(String salesRegion) {
        this.salesRegion = salesRegion;
    }

    public String getSalesArea() {
        return salesArea;
    }

    public void setSalesArea(String salesArea) {
        this.salesArea = salesArea;
    }

    public String getCustomerOrderNumber() {
        return customerOrderNumber;
    }

    public void setCustomerOrderNumber(String customerOrderNumber) {
        this.customerOrderNumber = customerOrderNumber;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getSalesOrderNo() {
        return salesOrderNo;
    }

    public void setSalesOrderNo(String salesOrderNo) {
        this.salesOrderNo = salesOrderNo;
    }

    public String getOrderWarehouse() {
        return orderWarehouse;
    }

    public void setOrderWarehouse(String orderWarehouse) {
        this.orderWarehouse = orderWarehouse;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public String getDeliveryTerms() {
        return deliveryTerms;
    }

    public void setDeliveryTerms(String deliveryTerms) {
        this.deliveryTerms = deliveryTerms;
    }

    public String getPricingGroup() {
        return pricingGroup;
    }

    public void setPricingGroup(String pricingGroup) {
        this.pricingGroup = pricingGroup;
    }

    public String getDiscountGroup() {
        return discountGroup;
    }

    public void setDiscountGroup(String discountGroup) {
        this.discountGroup = discountGroup;
    }

    public String getExtraChargeGroup() {
        return extraChargeGroup;
    }

    public void setExtraChargeGroup(String extraChargeGroup) {
        this.extraChargeGroup = extraChargeGroup;
    }

    public String getTermsCode() {
        return termsCode;
    }

    public void setTermsCode(String termsCode) {
        this.termsCode = termsCode;
    }

    public String getSettlementDiscCode() {
        return settlementDiscCode;
    }

    public void setSettlementDiscCode(String settlementDiscCode) {
        this.settlementDiscCode = settlementDiscCode;
    }

    public Date getPaymentDueDate() {
        return paymentDueDate;
    }

    public void setPaymentDueDate(Date paymentDueDate) {
        this.paymentDueDate = paymentDueDate;
    }

    public Date getSettlementDiscDate() {
        return settlementDiscDate;
    }

    public void setSettlementDiscDate(Date settlementDiscDate) {
        this.settlementDiscDate = settlementDiscDate;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public Date getApprovedTime() {
        return approvedTime;
    }

    public void setApprovedTime(Date approvedTime) {
        this.approvedTime = approvedTime;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Date getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(Date postedDate) {
        this.postedDate = postedDate;
    }

    public Date getPostedTime() {
        return postedTime;
    }

    public void setPostedTime(Date postedTime) {
        this.postedTime = postedTime;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public Date getPrintedDate() {
        return printedDate;
    }

    public void setPrintedDate(Date printedDate) {
        this.printedDate = printedDate;
    }

    public Date getPrintedTime() {
        return printedTime;
    }

    public void setPrintedTime(Date printedTime) {
        this.printedTime = printedTime;
    }

    public String getPrintedBy() {
        return printedBy;
    }

    public void setPrintedBy(String printedBy) {
        this.printedBy = printedBy;
    }

    public Date getLastPrintedDate() {
        return lastPrintedDate;
    }

    public void setLastPrintedDate(Date lastPrintedDate) {
        this.lastPrintedDate = lastPrintedDate;
    }

    public Date getLastPrintedTime() {
        return lastPrintedTime;
    }

    public void setLastPrintedTime(Date lastPrintedTime) {
        this.lastPrintedTime = lastPrintedTime;
    }

    public String getLastPrintedBy() {
        return lastPrintedBy;
    }

    public void setLastPrintedBy(String lastPrintedBy) {
        this.lastPrintedBy = lastPrintedBy;
    }

    public String getInvCNNumber() {
        return invCNNumber;
    }

    public void setInvCNNumber(String invCNNumber) {
        this.invCNNumber = invCNNumber;
    }

    public String getApprovalGroup() {
        return approvalGroup;
    }

    public void setApprovalGroup(String approvalGroup) {
        this.approvalGroup = approvalGroup;
    }

    public BigDecimal getSalesTotal() {
        return salesTotal;
    }

    public void setSalesTotal(BigDecimal salesTotal) {
        this.salesTotal = salesTotal;
    }

    public BigDecimal getDiscountTotal() {
        return discountTotal;
    }

    public void setDiscountTotal(BigDecimal discountTotal) {
        this.discountTotal = discountTotal;
    }

    public BigDecimal getVatTotal() {
        return vatTotal;
    }

    public void setVatTotal(BigDecimal vatTotal) {
        this.vatTotal = vatTotal;
    }

    public BigDecimal getInvoiceTotal() {
        return invoiceTotal;
    }

    public void setInvoiceTotal(BigDecimal invoiceTotal) {
        this.invoiceTotal = invoiceTotal;
    }

    public BigDecimal getNumberOfCartons() {
        return numberOfCartons;
    }

    public void setNumberOfCartons(BigDecimal numberOfCartons) {
        this.numberOfCartons = numberOfCartons;
    }

    public BigDecimal getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(BigDecimal totalWeight) {
        this.totalWeight = totalWeight;
    }

    public BigDecimal getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(BigDecimal deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public String getAuthorizationNo() {
        return authorizationNo;
    }

    public void setAuthorizationNo(String authorizationNo) {
        this.authorizationNo = authorizationNo;
    }

    public String getClaimNo() {
        return claimNo;
    }

    public void setClaimNo(String claimNo) {
        this.claimNo = claimNo;
    }

    public String getSalesRep() {
        return salesRep;
    }

    public void setSalesRep(String salesRep) {
        this.salesRep = salesRep;
    }

    public String getWaybillNumber() {
        return waybillNumber;
    }

    public void setWaybillNumber(String waybillNumber) {
        this.waybillNumber = waybillNumber;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getReturnOption() {
        return returnOption;
    }

    public void setReturnOption(String returnOption) {
        this.returnOption = returnOption;
    }

    public boolean isCommissionApplicable() {
        return commissionApplicable;
    }

    public void setCommissionApplicable(boolean commissionApplicable) {
        this.commissionApplicable = commissionApplicable;
    }

    public boolean isDeliveryChargeApplicable() {
        return deliveryChargeApplicable;
    }

    public void setDeliveryChargeApplicable(boolean deliveryChargeApplicable) {
        this.deliveryChargeApplicable = deliveryChargeApplicable;
    }

    public String getCustomerTradingAs() {
        return customerTradingAs;
    }

    public void setCustomerTradingAs(String customerTradingAs) {
        this.customerTradingAs = customerTradingAs;
    }

    public String getInvoiceToCustomerName() {
        return invoiceToCustomerName;
    }

    public void setInvoiceToCustomerName(String invoiceToCustomerName) {
        this.invoiceToCustomerName = invoiceToCustomerName;
    }

    public String getInvoiceToCustomerExportCurrency() {
        return invoiceToCustomerExportCurrency;
    }

    public void setInvoiceToCustomerExportCurrency(String invoiceToCustomerExportCurrency) {
        this.invoiceToCustomerExportCurrency = invoiceToCustomerExportCurrency;
    }

    public String getInvoiceToCustomerCountryOfDestination() {
        return invoiceToCustomerCountryOfDestination;
    }

    public void setInvoiceToCustomerCountryOfDestination(String invoiceToCustomerCountryOfDestination) {
        this.invoiceToCustomerCountryOfDestination = invoiceToCustomerCountryOfDestination;
    }

    public String getInvoiceToCustomerDeliveryInstructions() {
        return invoiceToCustomerDeliveryInstructions;
    }

    public void setInvoiceToCustomerDeliveryInstructions(String invoiceToCustomerDeliveryInstructions) {
        this.invoiceToCustomerDeliveryInstructions = invoiceToCustomerDeliveryInstructions;
    }

    public String getInvoiceToCustomerVatRegistration() {
        return invoiceToCustomerVatRegistration;
    }

    public void setInvoiceToCustomerVatRegistration(String invoiceToCustomerVatRegistration) {
        this.invoiceToCustomerVatRegistration = invoiceToCustomerVatRegistration;
    }

    /**
     * @return the billingCurrency
     */
    public String getBillingCurrency() {
        return billingCurrency;
    }

    /**
     * @param billingCurrency the billingCurrency to set
     */
    public void setBillingCurrency(String billingCurrency) {
        this.billingCurrency = billingCurrency;
    }

    public String getDistributionNumber() {
        return distributionNumber;
    }

    public void setDistributionNumber(String distributionNumber) {
        this.distributionNumber = distributionNumber;
    }
}
