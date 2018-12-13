/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.pop.purchaseordersuperclass;

import emc.datatypes.base.employeetable.foreignkeys.EmployeeIdFKNotMandatory;
import emc.datatypes.base.postalcodes.PostalCode;
import emc.datatypes.gl.VATApplicable;
import emc.datatypes.gl.currency.foreignkeys.CurrencyFK;
import emc.datatypes.inventory.warehouses.foreignkeys.WarehouseFK;
import emc.datatypes.inventory.warehouses.foreignkeys.WarehouseFKNotMandatory;
import emc.datatypes.pop.deliverymodes.foreignkeys.DeliveryModeIdFK;
import emc.datatypes.pop.deliveryterms.foreignkeys.DeliveryTermsIdFK;
import emc.datatypes.pop.pricegroups.foreignkeys.PriceGroupFKNotMandatory;
import emc.datatypes.pop.purchaseorderapprovalgroups.foreignkeys.ApprovalGroupIdFKNotMandatory;
import emc.datatypes.pop.purchaseordermaster.BlanketOrderRef;
import emc.datatypes.pop.purchaseordermaster.ConfirmedDeliveryDate;
import emc.datatypes.pop.purchaseordermaster.ContactEmail;
import emc.datatypes.pop.purchaseordermaster.ExternalRef;
import emc.datatypes.pop.purchaseordermaster.InternalRef;
import emc.datatypes.pop.purchaseordermaster.InternalRefType;
import emc.datatypes.pop.purchaseordermaster.OrderedDate;
import emc.datatypes.pop.purchaseordermaster.PurchaseOrderId;
import emc.datatypes.pop.purchaseordermaster.PurchaseOrderType;
import emc.datatypes.pop.purchaseordermaster.Status;
import emc.datatypes.pop.purchaseordermaster.Supplier;
import emc.datatypes.pop.purchaseordermaster.SupplierOrderNumber;
import emc.datatypes.systemwide.Comments;
import emc.datatypes.systemwide.PhysicalAddress1;
import emc.datatypes.systemwide.PhysicalAddress2;
import emc.datatypes.systemwide.PhysicalAddress3;
import emc.datatypes.systemwide.PhysicalAddress4;
import emc.datatypes.systemwide.PhysicalAddress5;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.pop.purchaseorders.PurchaseOrderStatus;
import emc.enums.pop.purchaseorders.PurchaseOrderTypes;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author riaan
 */
@Entity
public class POPPurchaseOrderMasterSuper extends EMCEntityClass {

    private String purchaseOrderId;
    private String purchaseOrderType = PurchaseOrderTypes.PURCHASE_ORDER.toString();
    private String supplier;
    private String status = PurchaseOrderStatus.REQUISITION.toString();
    private String currency;
    private String contactPerson;
    private String contactNo;
    private String contactEmail;
    private String buyerGroup;
    private String orderedBy;
    private String settlementType;
    private String vatRegistrationNo;
    private boolean pricesIncludeVat;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String addressLine4;
    private String addressLine5;
    private String addressPostalCode;
    private String warehouse;
    @Temporal(TemporalType.DATE)
    private Date actualDeliveryDate;
    @Temporal(TemporalType.DATE)
    private Date latestExpectedDeliveryDate;
    @Temporal(TemporalType.DATE)
    private Date requestedDeliveryDate;
    @Temporal(TemporalType.DATE)
    private Date confirmedDeliveryDate;
    private String modeOfDelivery;
    private String deliveryTerms;
    private double payment;
    @Temporal(TemporalType.DATE)
    private Date paymentDueDate;
    private String methodOfPayment;
    
    @Temporal(TemporalType.DATE)
    private Date orderedDate;
    
    private double settlementDiscount;
    private String priceGroup;
    private double totalDiscountPercentage;
    private double linesDiscount;
    private String miscChargesGroup;
    private String approvedBy;
    private String supplierOrderNumber;
    private String qualityTest;
    private Boolean qualityTestReq;
    private Boolean qualityReportReq;
    private Integer postVersion = 0;
    private Boolean vatApplicable = true;
    private String externalRef;
    private String externalRefType;
    private String blanketOrderRef;

    private String internalRef;
    private String internalRefType;
    
    private String comments;
    private String approvalGrp;
    
    /** Creates a new instance of POPPurchaseOrderMasterSuper */
    public POPPurchaseOrderMasterSuper() {
        //Prevent relations from breaking
        this.setDataSource(true);
    }

    public String getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(String purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public String getPurchaseOrderType() {
        return purchaseOrderType;
    }

    public void setPurchaseOrderType(String purchaseOrderType) {
        this.purchaseOrderType = purchaseOrderType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getBuyerGroup() {
        return buyerGroup;
    }

    public void setBuyerGroup(String buyerGroup) {
        this.buyerGroup = buyerGroup;
    }

    public String getSettlementType() {
        return settlementType;
    }

    public void setSettlementType(String settlementType) {
        this.settlementType = settlementType;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

//    public boolean getPricesIncludeVat() {
//        return pricesIncludeVat;
//    }
//
//    public void setPricesIncludeVat(boolean pricesIncludeVat) {
//        this.pricesIncludeVat = pricesIncludeVat;
//    }
    public String getAddressLine3() {
        return addressLine3;
    }

    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
    }

    public String getAddressLine4() {
        return addressLine4;
    }

    public void setAddressLine4(String addressLine4) {
        this.addressLine4 = addressLine4;
    }

    public String getAddressLine5() {
        return addressLine5;
    }

    public void setAddressLine5(String addressLine5) {
        this.addressLine5 = addressLine5;
    }

    public String getAddressPostalCode() {
        return addressPostalCode;
    }

    public void setAddressPostalCode(String addressPostalCode) {
        this.addressPostalCode = addressPostalCode;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public Date getActualDeliveryDate() {
        return actualDeliveryDate;
    }

    public void setActualDeliveryDate(Date actualDeliveryDate) {
        this.actualDeliveryDate = actualDeliveryDate;
    }

    public Date getLatestExpectedDeliveryDate() {
        return latestExpectedDeliveryDate;
    }

    public void setLatestExpectedDeliveryDate(Date latestExpectedDeliveryDate) {
        this.latestExpectedDeliveryDate = latestExpectedDeliveryDate;
    }

    public Date getRequestedDeliveryDate() {
        return requestedDeliveryDate;
    }

    public void setRequestedDeliveryDate(Date requestedDeliveryDate) {
        this.requestedDeliveryDate = requestedDeliveryDate;
    }

    public Date getConfirmedDeliveryDate() {
        return confirmedDeliveryDate;
    }

    public void setConfirmedDeliveryDate(Date cofirmedDeliveryDate) {
        this.confirmedDeliveryDate = cofirmedDeliveryDate;
    }

    public String getModeOfDelivery() {
        return modeOfDelivery;
    }

    public void setModeOfDelivery(String modeOfDelivery) {
        this.modeOfDelivery = modeOfDelivery;
    }

    public String getDeliveryTerms() {
        return deliveryTerms;
    }

    public void setDeliveryTerms(String deliveryTerms) {
        this.deliveryTerms = deliveryTerms;
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    public Date getPaymentDueDate() {
        return paymentDueDate;
    }

    public void setPaymentDueDate(Date paymentDueDate) {
        this.paymentDueDate = paymentDueDate;
    }

    public String getMethodOfPayment() {
        return methodOfPayment;
    }

    public void setMethodOfPayment(String methodOfPayment) {
        this.methodOfPayment = methodOfPayment;
    }

    public double getSettlementDiscount() {
        return settlementDiscount;
    }

    public void setSettlementDiscount(double settlementDiscount) {
        this.settlementDiscount = settlementDiscount;
    }

    public String getPriceGroup() {
        return priceGroup;
    }

    public void setPriceGroup(String priceGroup) {
        this.priceGroup = priceGroup;
    }

    public double getTotalDiscountPercentage() {
        return totalDiscountPercentage;
    }

    public void setTotalDiscountPercentage(double totalDiscountPercentage) {
        this.totalDiscountPercentage = totalDiscountPercentage;
    }

    public double getLinesDiscount() {
        return linesDiscount;
    }

    public void setLinesDiscount(double linesDiscount) {
        this.linesDiscount = linesDiscount;
    }

    public String getMiscChargesGroup() {
        return miscChargesGroup;
    }

    public void setMiscChargesGroup(String miscChargesGroup) {
        this.miscChargesGroup = miscChargesGroup;
    }

    public Boolean getQualityReportReq() {
        return qualityReportReq == null ? false : qualityReportReq;
    }

    public void setQualityReportReq(Boolean qualityReportReq) {
        this.qualityReportReq = qualityReportReq;
    }

    public String getQualityTest() {
        return qualityTest;
    }

    public void setQualityTest(String qualityTest) {
        this.qualityTest = qualityTest;
    }

    public boolean getQualityTestReq() {
        return qualityTestReq == null ? false : qualityTestReq;
    }

    public void setQualityTestReq(Boolean qualityTestReq) {
        this.qualityTestReq = qualityTestReq;
    }

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();

        toBuild.put("purchaseOrderId", new PurchaseOrderId());
        toBuild.put("purchaseOrderType", new PurchaseOrderType());
        toBuild.put("supplier", new Supplier());
        toBuild.put("status", new Status());
        toBuild.put("currency", new CurrencyFK());

        toBuild.put("warehouse", new WarehouseFKNotMandatory());
        toBuild.put("priceGroup", new PriceGroupFKNotMandatory());
        toBuild.put("addressLine1", new PhysicalAddress1());
        toBuild.put("addressLine2", new PhysicalAddress2());
        toBuild.put("addressLine3", new PhysicalAddress3());
        toBuild.put("addressLine4", new PhysicalAddress4());
        toBuild.put("addressLine5", new PhysicalAddress5());

        toBuild.put("addressPostalCode", new PostalCode());

        EmployeeIdFKNotMandatory emp = new EmployeeIdFKNotMandatory();
        toBuild.put("orderedBy", emp);
        toBuild.put("approvedBy", emp);

        toBuild.put("deliveryTerms", new DeliveryTermsIdFK());
        toBuild.put("modeOfDelivery", new DeliveryModeIdFK());
        toBuild.put("warehouse", new WarehouseFK());

        toBuild.put("supplierOrderNumber", new SupplierOrderNumber());

        toBuild.put("vatApplicable", new VATApplicable());

        toBuild.put("externalRef", new ExternalRef());

        toBuild.put("internalRef", new InternalRef());
        toBuild.put("internalRefType", new InternalRefType());

        toBuild.put("blanketOrderRef", new BlanketOrderRef());

        toBuild.put("confirmedDeliveryDate", new ConfirmedDeliveryDate());
        
        toBuild.put("comments", new Comments());
        
        toBuild.put("contactEmail", new ContactEmail());
        toBuild.put("approvalGrp",new ApprovalGroupIdFKNotMandatory());
        toBuild.put("orderedDate", new OrderedDate());
        
        return toBuild;
    }

    @Override
    public EMCQuery buildQuery() {
        EMCQuery query = super.buildQuery();
        query.addAnd("status", PurchaseOrderStatus.RECEIVED.toString(), EMCQueryConditions.NOT);
        query.addOrderBy("purchaseOrderId");

        return query;
    }
    
    public String getVatRegistrationNo() {
        return vatRegistrationNo;
    }

    public void setVatRegistrationNo(String vatRegistrationNo) {
        this.vatRegistrationNo = vatRegistrationNo;
    }

    public String getOrderedBy() {
        return orderedBy;
    }

    public void setOrderedBy(String orderedBy) {
        this.orderedBy = orderedBy;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getSupplierOrderNumber() {
        return supplierOrderNumber;
    }

    public void setSupplierOrderNumber(String supplierOrderNumber) {
        this.supplierOrderNumber = supplierOrderNumber;
    }

    public boolean getVatApplicable() {
        return vatApplicable == null ? false : vatApplicable;
    }

    public void setVatApplicable(Boolean vatApplicable) {
        this.vatApplicable = vatApplicable;
    }

    public int getPostVersion() {
        return postVersion == null ? 0 : postVersion;
    }

    public void setPostVersion(Integer postVersion) {
        this.postVersion = postVersion;
    }

    public String getExternalRef() {
        return externalRef;
    }

    public void setExternalRef(String externalRef) {
        this.externalRef = externalRef;
    }

    public String getBlanketOrderRef() {
        return blanketOrderRef;
    }

    public void setBlanketOrderRef(String blanketOrderRef) {
        this.blanketOrderRef = blanketOrderRef;
    }

    public boolean getPricesIncludeVat() {
        return pricesIncludeVat;
    }

    public void setPricesIncludeVat(boolean pricesIncludeVat) {
        this.pricesIncludeVat = pricesIncludeVat;
    }
    
    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
    
    @Override
    public List<String> buildDefaultLookupFieldList() {
        List<String> fields = super.buildDefaultLookupFieldList();

        fields.add("purchaseOrderId");
        fields.add("supplier");

        return fields;
    }

    public String getApprovalGrp() {
        return approvalGrp;
    }

    public void setApprovalGrp(String approvalGrp) {
        this.approvalGrp = approvalGrp;
    }

    public Date getOrderedDate() {
        return orderedDate;
    }

    public void setOrderedDate(Date orderedDate) {
        this.orderedDate = orderedDate;
    }

    public String getExternalRefType() {
        return externalRefType;
    }

    public void setExternalRefType(String externalRefType) {
        this.externalRefType = externalRefType;
    }

    public String getInternalRef() {
        return internalRef;
    }

    public void setInternalRef(String internalRef) {
        this.internalRef = internalRef;
    }

    public String getInternalRefType() {
        return internalRefType;
    }

    public void setInternalRefType(String internalRefType) {
        this.internalRefType = internalRefType;
    }

}
