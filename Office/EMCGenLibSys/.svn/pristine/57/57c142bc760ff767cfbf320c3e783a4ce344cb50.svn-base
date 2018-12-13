package emc.entity.sop.superclass;

import emc.enums.sop.SalesOrderTypes;
import emc.enums.sop.assemblytype.EnumAssemblyTypes;
import emc.enums.sop.salesorders.SalesOrderDistributionStatus;
import emc.framework.EMCEntityClass;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @Author wikus
 */
@Entity
public class SOPSalesOrderMasterSuper extends EMCEntityClass {

    private String salesOrderNo;
    private String refSalesOrderNo;
    private String invoiceStatus;
    private String customerNo;
    private String invoiceToCustomerNo;
    private String salsesOrderStatus = emc.enums.sop.salesorders.SalesOrderStatus.CAPTURED.toString();
    private String deliveryAddress1;
    private String deliveryAddress2;
    private String deliveryAddress3;
    private String deliveryAddress4;
    private String deliveryAddress5;
    private String deliveryAddressCode;
    private String invoiceAddress1;
    private String invoiceAddress2;
    private String invoiceAddress3;
    private String invoiceAddress4;
    private String invoiceAddress5;
    private String invoiceAddressCode;
    private String vatNo;
    private String vatCode;
    private String salesGroup;
    private String salesRegion;
    private String salesArea;
    private String customerOrderNo;
    private String reference;
    private String orderWarehouse;
    private String deliveryMethod;
    private String deliveryTerms;
    private String pricingGroup;
    private String discountGroup;
    private String extraChargeGroup;
    private String termsCode;
    private String settlementDiscountCode;
    private int importVersion = 0;
    private String salesOrderType = SalesOrderTypes.SALES_ORDER.toString();
    @Temporal(TemporalType.DATE)
    private Date capturedDate;
    @Temporal(TemporalType.DATE)
    private Date requiredDate;
    private String comments;
    private String salesRep;
    private String blanketOrderRef;
    private String awoRef;
    private String kimbleAwoRef;
    //This is a temporary N & L field.
    private boolean accepted = true;
    private Boolean kimbling;
    private boolean fullyReserved;
    private boolean useShipToKimble;
    private Boolean deliveryChargs;
    @Temporal(TemporalType.DATE)
    private Date originalRequiredDate;
    private String dateChangeReason;
    private String distributionStatus = SalesOrderDistributionStatus.NONE.toString();
    private String assemblyType = EnumAssemblyTypes.AFS.toString();

    public SOPSalesOrderMasterSuper() {
        this.setDataSource(true);
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getCustomerOrderNo() {
        return customerOrderNo;
    }

    public void setCustomerOrderNo(String customerOrderNo) {
        this.customerOrderNo = customerOrderNo;
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

    public String getDeliveryAddressCode() {
        return deliveryAddressCode;
    }

    public void setDeliveryAddressCode(String deliveryAddressCode) {
        this.deliveryAddressCode = deliveryAddressCode;
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

    public String getInvoiceAddressCode() {
        return invoiceAddressCode;
    }

    public void setInvoiceAddressCode(String invoiceAddressCode) {
        this.invoiceAddressCode = invoiceAddressCode;
    }

    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public String getInvoiceToCustomerNo() {
        return invoiceToCustomerNo;
    }

    public void setInvoiceToCustomerNo(String invoiceToCustomerNo) {
        this.invoiceToCustomerNo = invoiceToCustomerNo;
    }

    public String getOrderWarehouse() {
        return orderWarehouse;
    }

    public void setOrderWarehouse(String orderWarehouse) {
        this.orderWarehouse = orderWarehouse;
    }

    public String getPricingGroup() {
        return pricingGroup;
    }

    public void setPricingGroup(String pricingGroup) {
        this.pricingGroup = pricingGroup;
    }

    public String getRefSalesOrderNo() {
        return refSalesOrderNo;
    }

    public void setRefSalesOrderNo(String refSalesOrderNo) {
        this.refSalesOrderNo = refSalesOrderNo;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getSalesArea() {
        return salesArea;
    }

    public void setSalesArea(String salesArea) {
        this.salesArea = salesArea;
    }

    public String getSalesGroup() {
        return salesGroup;
    }

    public void setSalesGroup(String salesGroup) {
        this.salesGroup = salesGroup;
    }

    public String getSalesOrderNo() {
        return salesOrderNo;
    }

    public void setSalesOrderNo(String salesOrderNo) {
        this.salesOrderNo = salesOrderNo;
    }

    public String getSalesRegion() {
        return salesRegion;
    }

    public void setSalesRegion(String salesRegion) {
        this.salesRegion = salesRegion;
    }

    public String getSalsesOrderStatus() {
        return salsesOrderStatus;
    }

    public void setSalsesOrderStatus(String salsesOrderStatus) {
        this.salsesOrderStatus = salsesOrderStatus;
    }

    public String getSettlementDiscountCode() {
        return settlementDiscountCode;
    }

    public void setSettlementDiscountCode(String settlementDiscountCode) {
        this.settlementDiscountCode = settlementDiscountCode;
    }

    public String getTermsCode() {
        return termsCode;
    }

    public void setTermsCode(String termsCode) {
        this.termsCode = termsCode;
    }

    public String getVatCode() {
        return vatCode;
    }

    public void setVatCode(String vatCode) {
        this.vatCode = vatCode;
    }

    public String getVatNo() {
        return vatNo;
    }

    public void setVatNo(String vatNo) {
        this.vatNo = vatNo;
    }

    public int getImportVersion() {
        return importVersion;
    }

    public void setImportVersion(int importVersion) {
        this.importVersion = importVersion;
    }

    public String getSalesOrderType() {
        return salesOrderType;
    }

    public void setSalesOrderType(String salesOrderType) {
        this.salesOrderType = salesOrderType;
    }

    public Date getCapturedDate() {
        return capturedDate;
    }

    public void setCapturedDate(Date capturedDate) {
        this.capturedDate = capturedDate;
    }

    public Date getRequiredDate() {
        return requiredDate;
    }

    public void setRequiredDate(Date requiredDate) {
        this.requiredDate = requiredDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("salesOrderNo");
        toBuild.add("customerNo");
        toBuild.add("salsesOrderStatus");
        return toBuild;
    }

    public String getBlanketOrderRef() {
        return blanketOrderRef;
    }

    public void setBlanketOrderRef(String blanketOrderRef) {
        this.blanketOrderRef = blanketOrderRef;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public String getSalesRep() {
        return salesRep;
    }

    public void setSalesRep(String salesRep) {
        this.salesRep = salesRep;
    }

    public boolean getKimbling() {
        return kimbling == null ? false : kimbling;
    }

    public void setKimbling(boolean kimbling) {
        this.kimbling = kimbling;
    }

    public String getAwoRef() {
        return awoRef;
    }

    public void setAwoRef(String awoRef) {
        this.awoRef = awoRef;
    }

    public boolean isFullyReserved() {
        return fullyReserved;
    }

    public void setFullyReserved(boolean fullyReserved) {
        this.fullyReserved = fullyReserved;
    }

    public boolean isUseShipToKimble() {
        return useShipToKimble;
    }

    public void setUseShipToKimble(boolean useShipToKimble) {
        this.useShipToKimble = useShipToKimble;
    }

    public boolean getDeliveryChargs() {
        return deliveryChargs == null ? false : deliveryChargs;
    }

    public void setDeliveryChargs(boolean deliveryChargs) {
        this.deliveryChargs = deliveryChargs;
    }

    public Date getOriginalRequiredDate() {
        return originalRequiredDate;
    }

    public void setOriginalRequiredDate(Date originalRequiredDate) {
        this.originalRequiredDate = originalRequiredDate;
    }

    public String getDateChangeReason() {
        return dateChangeReason;
    }

    public void setDateChangeReason(String dateChangeReason) {
        this.dateChangeReason = dateChangeReason;
    }

    public String getKimbleAwoRef() {
        return kimbleAwoRef;
    }

    public void setKimbleAwoRef(String kimbleAwoRef) {
        this.kimbleAwoRef = kimbleAwoRef;
    }

    public String getDistributionStatus() {
        return distributionStatus;
    }

    public void setDistributionStatus(String distributionStatus) {
        this.distributionStatus = distributionStatus;
    }

    public String getAssemblyType() {
        return assemblyType;
    }

    public void setAssemblyType(String assemblyType) {
        this.assemblyType = assemblyType;
    }
}
