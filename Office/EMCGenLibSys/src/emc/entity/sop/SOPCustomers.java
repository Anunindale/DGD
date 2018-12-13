package emc.entity.sop;

import emc.datatypes.sop.customers.TradingAs;
import emc.datatypes.base.users.foreignkeys.UserIdFK;
import emc.datatypes.creditors.settlementdicountgroups.foreignkeys.SettlementDiscountTermIdFK;
import emc.datatypes.creditors.termsofpayment.foreignkeys.TermsOfPaymentFK;
import emc.datatypes.crm.prospect.Company;
import emc.datatypes.debtors.courier.foreignkeys.CourierIdFK;
import emc.datatypes.debtors.creditclosereason.foreignkey.CreditCloseReasonFKNM;
import emc.datatypes.debtors.creditcontroller.foreignkeys.CreditControllerIDFK;
import emc.datatypes.debtors.creditinsurer.foreignkeys.CreditInsurerIDFK;
import emc.datatypes.debtors.creditrating.foreignkey.CreditRatingFKNM;
import emc.datatypes.debtors.creditstopreason.foreignkey.CreditStopReasonFKNM;
import emc.datatypes.debtors.deliverycharge.foreignkeys.DeliveryChargeCodeFK;
import emc.datatypes.debtors.rebatecodes.foreignkeys.RebateCodeFKNM;
import emc.datatypes.debtors.salesarea.foreignkey.SalesAreaFK;
import emc.datatypes.debtors.salesgroup.foreignkey.SalesGroupFK;
import emc.datatypes.debtors.salesregion.foreignkey.SalesRegionFK;
import emc.datatypes.gl.vatcodes.foreignkeys.VATCodeFK;
import emc.datatypes.inventory.warehouses.foreignkeys.WarehouseFK;
import emc.datatypes.pop.deliveryterms.foreignkeys.DeliveryTermsIdFK;
import emc.datatypes.pop.discountgroups.foreignkeys.DiscountGroupFKNotMandatory;
import emc.datatypes.pop.extrachargegroups.foreignkeys.ExtraChargeGroupFKNotMandatory;
import emc.datatypes.pop.pricegroups.foreignkeys.PriceGroupFK;
import emc.datatypes.sop.classification1.foreignkeys.Classification1FK;
import emc.datatypes.sop.classification2.foreignkeys.Classification2FK;
import emc.datatypes.sop.classification3.foreignkeys.Classification3FK;
import emc.datatypes.sop.classification4.foreignkeys.Classification4FK;
import emc.datatypes.sop.classification5.foreignkeys.Classification5FK;
import emc.datatypes.sop.classification6.foreignkeys.Classification6FK;
import emc.datatypes.sop.customers.AccountContactCellphoneNumber;
import emc.datatypes.sop.customers.AccountContactEmail;
import emc.datatypes.sop.customers.AccountContactName;
import emc.datatypes.sop.customers.AccountContactTelephoneNumber;
import emc.datatypes.sop.customers.AccountOpenedDate;
import emc.datatypes.sop.customers.CountryOfDestination;
import emc.datatypes.sop.customers.CreditInsuredExpiryDate;
import emc.datatypes.sop.customers.CreditLimit;
import emc.datatypes.sop.customers.CreditStoped;
import emc.datatypes.sop.customers.CustomerGroup;
import emc.datatypes.sop.customers.CustomerId;
import emc.datatypes.sop.customers.CustomerName;
import emc.datatypes.sop.customers.CustomerStatus;
import emc.datatypes.sop.customers.DeliverBeforeDate;
import emc.datatypes.sop.customers.DeliveryInstructions;
import emc.datatypes.sop.customers.DeliveryMethod;
import emc.datatypes.sop.customers.DeliveryRules;
import emc.datatypes.sop.customers.ExportCurrency;
import emc.datatypes.sop.customers.InsurerDocRef;
import emc.datatypes.sop.customers.InsurerFileRef;
import emc.datatypes.sop.customers.InvoiceToCustomer;
import emc.datatypes.sop.customers.MarketingGroup;
import emc.datatypes.sop.customers.OrderContactCellphoneNum;
import emc.datatypes.sop.customers.OrderContactEmail;
import emc.datatypes.sop.customers.OrderContactName;
import emc.datatypes.sop.customers.OrderContactTelephoneNum;
import emc.datatypes.sop.customers.Telephone;
import emc.datatypes.sop.customers.VATRegNum;
import emc.datatypes.sop.repcommission.RepIdFKNM;
import emc.datatypes.systemwide.Cellphone;
import emc.datatypes.systemwide.EmailAddress;
import emc.datatypes.systemwide.EmergencyNumber;
import emc.datatypes.systemwide.FaxNumber;
import emc.datatypes.systemwide.PhysicalAddress1;
import emc.datatypes.systemwide.PhysicalAddress2;
import emc.datatypes.systemwide.PhysicalAddress3;
import emc.datatypes.systemwide.PhysicalAddress4;
import emc.datatypes.systemwide.PhysicalAddress5;
import emc.datatypes.systemwide.PhysicalPostalCode;
import emc.datatypes.systemwide.PostalAddress1;
import emc.datatypes.systemwide.PostalAddress2;
import emc.datatypes.systemwide.PostalAddress3;
import emc.datatypes.systemwide.PostalAddress4;
import emc.datatypes.systemwide.PostalAddress5;
import emc.datatypes.systemwide.PostalCode;
import emc.datatypes.systemwide.Website;
import emc.enums.sop.SalesDeliveryRules;
import emc.enums.sop.commission.SOPCommissionTypes;
import emc.enums.sop.customers.CustomerStatusEnum;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * 
 * @Author wikus
 */
@Entity
@Table(name = "SOPCustomers", uniqueConstraints = {@UniqueConstraint(columnNames = {"customerId", "companyId"})})
public class SOPCustomers extends EMCEntityClass {

    //Overview
    private String customerId;
    private String customerName;
    private String customerComapny;
    private String trandingAs;
    private String invoiceToCustomer;
    private String customerGroup;
    private String customerStatus = CustomerStatusEnum.ACTIVE.toString();
    private String marketingGroup;
    @Temporal(TemporalType.DATE)
    private Date accountOpenedDate;
    //Address
    private String physicalAddresLine1;
    private String physicalAddresLine2;
    private String physicalAddresLine3;
    private String physicalAddresLine4;
    private String physicalAddresLine5;
    private String physicalPostalCode;
    private String postalAddresLine1;
    private String postalAddresLine2;
    private String postalAddresLine3;
    private String postalAddresLine4;
    private String postalAddresLine5;
    private String postalPostalCode;
    //Contact
    private String telephoneNumber;
    private String cellNumber;
    private String emergencyNumber;
    private String faxNumber;
    private String email;
    private String website;
    private String orderContactName;
    private String orderContactTelephoneNumber;
    private String orderContactCellNumber;
    private String orderContactEmail;
    private String accountContactName;
    private String accountContactTelephoneNumber;
    private String accountContactCellNumber;
    private String accountContactEmail;
    //Credit/Payment
    private String creditRating;
    private double creaditLimit;
    private boolean creditStoped;
    @Temporal(TemporalType.DATE)
    private Date dateStoped;
    private String stoppedBy;
    private String creditStopReason;
    private String termsOfPayment;
    private String settlementDiscount;
    private boolean surety;
    private double suretyAmount;
    @Temporal(TemporalType.DATE)
    private Date suretyExpiryDate;
    private String creditInsuredBy;
    private String insurerFileRef;
    private String insuredDocRef;
    private BigDecimal insuredAmount = new BigDecimal(0);
    @Temporal(TemporalType.DATE)
    private Date creditInsuredExpiryDate;
    private BigDecimal discretionaryAmount = new BigDecimal(0);
    private String grantedBy;
    @Temporal(TemporalType.DATE)
    private Date discretionaryAmountExpiryDate;
    private String creditController;
    //Status
    private String closedBy;
    @Temporal(TemporalType.DATE)
    private Date closedDate;
    private String closedReason;
    //Pricing
    private String priceGroup;
    private String discountGroup;
    private String extaChargeGroup;
    private String priceOn;
    private String rebateCode;
    //Sales
    private String salesGroup;
    private String salesRegion;
    private String salesArea;
    private String orderWarehouse;
    private boolean allowSubstituteItems;
    private boolean requireKimbling;
    private boolean useShipToKimble;
    private boolean serviced;
    //Delivery
    private String deliveryMethod;
    private String deliveryTerms;
    private boolean deliveryBookingRequired;
    private String deliveryDayOfWeek;
    private int deliveryLeadTime;
    private boolean allowPartialDelivery;
    private int deliverBeforeDate;
    private boolean deliveryCharge;
    private String deliveryChargeCode;
    private String nominatedCourier;
    @Column(length = 1000)
    private String deliveryInstructions;
    //Company Info
    private String companyRegistrationNumber;
    private String vatRegistration;
    private String vatCode;
    private String bankName;
    private String bankBranchCode;
    private String bankAccountNumber;
    private String bankAccountName;
    private String responsiblePerson;
    private String responsiblePersonNumber;
    private String responsiblePersonTelNo;
    private String responsiblePersonCellNo;
    private String responsiblePersonEmail;
    //Classification
    private String classificationGroup1;
    private String classificationGroup2;
    private String classificationGroup3;
    private String classificationGroup4;
    private String classificationGroup5;
    private String classificationGroup6;
    //Link back to prospect
    private String prospectId;
    //Forecast
    private String forecastGroup;
    private boolean export;
    //Rep
    private String salesRep;
    private String repService = SOPCommissionTypes.MULTIPLE.toString();
    private String deliveryRules;
    //Export
    private String countryOfDestination;
    private String exportCurrency;
    //Web Registration
    private String whereHeard;
    private boolean acceptTermsAndConditions;

    /** Creates a new instance of SOPCustomers. */
    public SOPCustomers() {
    }

    public String getAccountContactCellNumber() {
        return accountContactCellNumber;
    }

    public void setAccountContactCellNumber(String accountContactCellNumber) {
        this.accountContactCellNumber = accountContactCellNumber;
    }

    public String getAccountContactEmail() {
        return accountContactEmail;
    }

    public void setAccountContactEmail(String accountContactEmail) {
        this.accountContactEmail = accountContactEmail;
    }

    public String getAccountContactName() {
        return accountContactName;
    }

    public void setAccountContactName(String accountContactName) {
        this.accountContactName = accountContactName;
    }

    public String getAccountContactTelephoneNumber() {
        return accountContactTelephoneNumber;
    }

    public void setAccountContactTelephoneNumber(String accountContactTelephoneNumber) {
        this.accountContactTelephoneNumber = accountContactTelephoneNumber;
    }

    public boolean isAllowPartialDelivery() {
        return allowPartialDelivery;
    }

    public void setAllowPartialDelivery(boolean allowPartialDelivery) {
        this.allowPartialDelivery = allowPartialDelivery;
    }

    public boolean isAllowSubstituteItems() {
        return allowSubstituteItems;
    }

    public void setAllowSubstituteItems(boolean allowSubstituteItems) {
        this.allowSubstituteItems = allowSubstituteItems;
    }

    public String getBankAccountName() {
        return bankAccountName;
    }

    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getBankBranchCode() {
        return bankBranchCode;
    }

    public void setBankBranchCode(String bankBranchCode) {
        this.bankBranchCode = bankBranchCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCellNumber() {
        return cellNumber;
    }

    public void setCellNumber(String cellNumber) {
        this.cellNumber = cellNumber;
    }

    public String getClassificationGroup1() {
        return classificationGroup1;
    }

    public void setClassificationGroup1(String classificationGroup1) {
        this.classificationGroup1 = classificationGroup1;
    }

    public String getClassificationGroup2() {
        return classificationGroup2;
    }

    public void setClassificationGroup2(String classificationGroup2) {
        this.classificationGroup2 = classificationGroup2;
    }

    public String getClassificationGroup3() {
        return classificationGroup3;
    }

    public void setClassificationGroup3(String classificationGroup3) {
        this.classificationGroup3 = classificationGroup3;
    }

    public String getClassificationGroup4() {
        return classificationGroup4;
    }

    public void setClassificationGroup4(String classificationGroup4) {
        this.classificationGroup4 = classificationGroup4;
    }

    public String getClassificationGroup5() {
        return classificationGroup5;
    }

    public void setClassificationGroup5(String classificationGroup5) {
        this.classificationGroup5 = classificationGroup5;
    }

    public String getClassificationGroup6() {
        return classificationGroup6;
    }

    public void setClassificationGroup6(String classificationGroup6) {
        this.classificationGroup6 = classificationGroup6;
    }

    public String getCompanyRegistrationNumber() {
        return companyRegistrationNumber;
    }

    public void setCompanyRegistrationNumber(String companyRegistrationNumber) {
        this.companyRegistrationNumber = companyRegistrationNumber;
    }

    public double getCreaditLimit() {
        return creaditLimit;
    }

    public void setCreaditLimit(double creaditLimit) {
        this.creaditLimit = creaditLimit;
    }

    public String getCreditRating() {
        return creditRating;
    }

    public void setCreditRating(String creditRating) {
        this.creditRating = creditRating;
    }

    public String getCreditStopReason() {
        return creditStopReason;
    }

    public void setCreditStopReason(String creditStopReason) {
        this.creditStopReason = creditStopReason;
    }

    public boolean isCreditStoped() {
        return creditStoped;
    }

    public void setCreditStoped(boolean creditStoped) {
        this.creditStoped = creditStoped;
    }

    public String getCustomerComapny() {
        return customerComapny;
    }

    public void setCustomerComapny(String customerComapny) {
        this.customerComapny = customerComapny;
    }

    public String getCustomerGroup() {
        return customerGroup;
    }

    public void setAccountOpenedDate(Date accountOpenedDate) {
        this.accountOpenedDate = accountOpenedDate;
    }

    public Date getAccountOpenedDate() {
        return accountOpenedDate;
    }

    public void setCustomerGroup(String customerGroup) {
        this.customerGroup = customerGroup;
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

    public Date getDateStoped() {
        return dateStoped;
    }

    public void setDateStoped(Date dateStoped) {
        this.dateStoped = dateStoped;
    }

    public boolean isDeliveryBookingRequired() {
        return deliveryBookingRequired;
    }

    public void setDeliveryBookingRequired(boolean deliveryBookingRequired) {
        this.deliveryBookingRequired = deliveryBookingRequired;
    }

    public String getDeliveryDayOfWeek() {
        return deliveryDayOfWeek;
    }

    public void setDeliveryDayOfWeek(String deliveryDayOfWeek) {
        this.deliveryDayOfWeek = deliveryDayOfWeek;
    }

    public String getDeliveryInstructions() {
        return deliveryInstructions;
    }

    public void setDeliveryInstructions(String deliveryInstructions) {
        this.deliveryInstructions = deliveryInstructions;
    }

    public int getDeliveryLeadTime() {
        return deliveryLeadTime;
    }

    public void setDeliveryLeadTime(int deliveryLeadTime) {
        this.deliveryLeadTime = deliveryLeadTime;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmergencyNumber() {
        return emergencyNumber;
    }

    public void setEmergencyNumber(String emergencyNumber) {
        this.emergencyNumber = emergencyNumber;
    }

    public String getExtaChargeGroup() {
        return extaChargeGroup;
    }

    public void setExtaChargeGroup(String extaChargeGroup) {
        this.extaChargeGroup = extaChargeGroup;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    public String getInvoiceToCustomer() {
        return invoiceToCustomer;
    }

    public void setInvoiceToCustomer(String invoiceToCustomer) {
        this.invoiceToCustomer = invoiceToCustomer;
    }

    public String getOrderContactCellNumber() {
        return orderContactCellNumber;
    }

    public void setOrderContactCellNumber(String orderContactCellNumber) {
        this.orderContactCellNumber = orderContactCellNumber;
    }

    public String getOrderContactEmail() {
        return orderContactEmail;
    }

    public void setOrderContactEmail(String orderContactEmail) {
        this.orderContactEmail = orderContactEmail;
    }

    public String getOrderContactName() {
        return orderContactName;
    }

    public void setOrderContactName(String orderContactName) {
        this.orderContactName = orderContactName;
    }

    public String getOrderContactTelephoneNumber() {
        return orderContactTelephoneNumber;
    }

    public void setOrderContactTelephoneNumber(String orderContactTelephoneNumber) {
        this.orderContactTelephoneNumber = orderContactTelephoneNumber;
    }

    public String getOrderWarehouse() {
        return orderWarehouse;
    }

    public void setOrderWarehouse(String orderWarehouse) {
        this.orderWarehouse = orderWarehouse;
    }

    public String getPhysicalAddresLine1() {
        return physicalAddresLine1;
    }

    public void setPhysicalAddresLine1(String physicalAddresLine1) {
        this.physicalAddresLine1 = physicalAddresLine1;
    }

    public String getPhysicalAddresLine2() {
        return physicalAddresLine2;
    }

    public void setPhysicalAddresLine2(String physicalAddresLine2) {
        this.physicalAddresLine2 = physicalAddresLine2;
    }

    public String getPhysicalAddresLine3() {
        return physicalAddresLine3;
    }

    public void setPhysicalAddresLine3(String physicalAddresLine3) {
        this.physicalAddresLine3 = physicalAddresLine3;
    }

    public String getPhysicalAddresLine4() {
        return physicalAddresLine4;
    }

    public void setPhysicalAddresLine4(String physicalAddresLine4) {
        this.physicalAddresLine4 = physicalAddresLine4;
    }

    public String getPhysicalAddresLine5() {
        return physicalAddresLine5;
    }

    public void setPhysicalAddresLine5(String physicalAddresLine5) {
        this.physicalAddresLine5 = physicalAddresLine5;
    }

    public String getPhysicalPostalCode() {
        return physicalPostalCode;
    }

    public void setPhysicalPostalCode(String physicalPostalCode) {
        this.physicalPostalCode = physicalPostalCode;
    }

    public String getPostalAddresLine1() {
        return postalAddresLine1;
    }

    public void setPostalAddresLine1(String postalAddresLine1) {
        this.postalAddresLine1 = postalAddresLine1;
    }

    public String getPostalAddresLine2() {
        return postalAddresLine2;
    }

    public void setPostalAddresLine2(String postalAddresLine2) {
        this.postalAddresLine2 = postalAddresLine2;
    }

    public String getPostalAddresLine3() {
        return postalAddresLine3;
    }

    public void setPostalAddresLine3(String postalAddresLine3) {
        this.postalAddresLine3 = postalAddresLine3;
    }

    public String getPostalAddresLine4() {
        return postalAddresLine4;
    }

    public void setPostalAddresLine4(String postalAddresLine4) {
        this.postalAddresLine4 = postalAddresLine4;
    }

    public String getPostalAddresLine5() {
        return postalAddresLine5;
    }

    public void setPostalAddresLine5(String postalAddresLine5) {
        this.postalAddresLine5 = postalAddresLine5;
    }

    public String getPostalPostalCode() {
        return postalPostalCode;
    }

    public void setPostalPostalCode(String postalPostalCode) {
        this.postalPostalCode = postalPostalCode;
    }

    public String getPriceGroup() {
        return priceGroup;
    }

    public void setPriceGroup(String priceGroup) {
        this.priceGroup = priceGroup;
    }

    public String getPriceOn() {
        return priceOn;
    }

    public void setPriceOn(String priceOn) {
        this.priceOn = priceOn;
    }

    public String getProspectId() {
        return prospectId;
    }

    public void setProspectId(String prospectId) {
        this.prospectId = prospectId;
    }

    public boolean isRequireKimbling() {
        return requireKimbling;
    }

    public void setRequireKimbling(boolean requireKimbling) {
        this.requireKimbling = requireKimbling;
    }

    public boolean isUseShipToKimble() {
        return useShipToKimble;
    }

    public void setUseShipToKimble(boolean useShipToKimble) {
        this.useShipToKimble = useShipToKimble;
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

    public String getSalesRegion() {
        return salesRegion;
    }

    public void setSalesRegion(String salesRegion) {
        this.salesRegion = salesRegion;
    }

    public String getSettlementDiscount() {
        return settlementDiscount;
    }

    public void setSettlementDiscount(String settlementDiscount) {
        this.settlementDiscount = settlementDiscount;
    }

    public String getStoppedBy() {
        return stoppedBy;
    }

    public void setStoppedBy(String stoppedBy) {
        this.stoppedBy = stoppedBy;
    }

    public boolean isSurety() {
        return surety;
    }

    public void setSurety(boolean surety) {
        this.surety = surety;
    }

    public double getSuretyAmount() {
        return suretyAmount;
    }

    public void setSuretyAmount(double suretyAmount) {
        this.suretyAmount = suretyAmount;
    }

    public Date getSuretyExpiryDate() {
        return suretyExpiryDate;
    }

    public void setSuretyExpiryDate(Date suretyExpiryDate) {
        this.suretyExpiryDate = suretyExpiryDate;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getTermsOfPayment() {
        return termsOfPayment;
    }

    public void setTermsOfPayment(String termsOfPayment) {
        this.termsOfPayment = termsOfPayment;
    }

    public String getTrandingAs() {
        return trandingAs;
    }

    public void setTrandingAs(String trandingAs) {
        this.trandingAs = trandingAs;
    }

    public String getVatCode() {
        return vatCode;
    }

    public void setVatCode(String vatCode) {
        this.vatCode = vatCode;
    }

    public String getVatRegistration() {
        return vatRegistration;
    }

    public void setVatRegistration(String vatRegistration) {
        this.vatRegistration = vatRegistration;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getForecastGroup() {
        return forecastGroup;
    }

    public void setForecastGroup(String forecastGroup) {
        this.forecastGroup = forecastGroup;
    }

    public String getCustomerStatus() {
        return customerStatus;
    }

    public void setCustomerStatus(String customerStatus) {
        this.customerStatus = customerStatus;
    }

    public String getMarketingGroup() {
        return marketingGroup;
    }

    public void setMarketingGroup(String marketingGroup) {
        this.marketingGroup = marketingGroup;
    }

    public String getClosedBy() {
        return closedBy;
    }

    public void setClosedBy(String closedBy) {
        this.closedBy = closedBy;
    }

    public Date getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(Date closedDate) {
        this.closedDate = closedDate;
    }

    public String getClosedReason() {
        return closedReason;
    }

    public void setClosedReason(String closedReason) {
        this.closedReason = closedReason;
    }

    public int getDeliverBeforeDate() {
        return deliverBeforeDate;
    }

    public void setDeliverBeforeDate(int deliverBeforeDate) {
        this.deliverBeforeDate = deliverBeforeDate;
    }

    public boolean isDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(boolean deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public String getDeliveryChargeCode() {
        return deliveryChargeCode;
    }

    public void setDeliveryChargeCode(String deliveryChargeCode) {
        this.deliveryChargeCode = deliveryChargeCode;
    }

    public String getNominatedCourier() {
        return nominatedCourier;
    }

    public void setNominatedCourier(String nominatedCourier) {
        this.nominatedCourier = nominatedCourier;
    }

    public boolean isExport() {
        return export;
    }

    public void setExport(boolean export) {
        this.export = export;
    }

    public String getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(String responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    public String getResponsiblePersonNumber() {
        return responsiblePersonNumber;
    }

    public void setResponsiblePersonNumber(String responsiblePersonNumber) {
        this.responsiblePersonNumber = responsiblePersonNumber;
    }

    public String getResponsiblePersonTelNo() {
        return responsiblePersonTelNo;
    }

    public void setResponsiblePersonTelNo(String responsiblePersonTelNo) {
        this.responsiblePersonTelNo = responsiblePersonTelNo;
    }

    public String getResponsiblePersonCellNo() {
        return responsiblePersonCellNo;
    }

    public void setResponsiblePersonCellNo(String responsiblePersonCellNo) {
        this.responsiblePersonCellNo = responsiblePersonCellNo;
    }

    public String getResponsiblePersonEmail() {
        return responsiblePersonEmail;
    }

    public void setResponsiblePersonEmail(String responsiblePersonEmail) {
        this.responsiblePersonEmail = responsiblePersonEmail;
    }

    public String getCreditInsuredBy() {
        return creditInsuredBy;
    }

    public void setCreditInsuredBy(String creditInsuredBy) {
        this.creditInsuredBy = creditInsuredBy;
    }

    public String getInsurerFileRef() {
        return insurerFileRef;
    }

    public void setInsurerFileRef(String insurerFileRef) {
        this.insurerFileRef = insurerFileRef;
    }

    public String getInsuredDocRef() {
        return insuredDocRef;
    }

    public void setInsuredDocRef(String insuredDocRef) {
        this.insuredDocRef = insuredDocRef;
    }

    public BigDecimal getInsuredAmount() {
        return insuredAmount;
    }

    public void setInsuredAmount(BigDecimal insuredAmount) {
        this.insuredAmount = insuredAmount;
    }

    public Date getCreditInsuredExpiryDate() {
        return creditInsuredExpiryDate;
    }

    public void setCreditInsuredExpiryDate(Date creditInsuredExpiryDate) {
        this.creditInsuredExpiryDate = creditInsuredExpiryDate;
    }

    public BigDecimal getDiscretionaryAmount() {
        return discretionaryAmount;
    }

    public void setDiscretionaryAmount(BigDecimal discretionaryAmount) {
        this.discretionaryAmount = discretionaryAmount;
    }

    public String getGrantedBy() {
        return grantedBy;
    }

    public void setGrantedBy(String grantedBy) {
        this.grantedBy = grantedBy;
    }

    public Date getDiscretionaryAmountExpiryDate() {
        return discretionaryAmountExpiryDate;
    }

    public void setDiscretionaryAmountExpiryDate(Date discretionaryAmountExpiryDate) {
        this.discretionaryAmountExpiryDate = discretionaryAmountExpiryDate;
    }

    public String getCreditController() {
        return creditController;
    }

    public void setCreditController(String creditController) {
        this.creditController = creditController;
    }

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();
        toBuild.put("customerId", new CustomerId());
        toBuild.put("customerName", new CustomerName());
        toBuild.put("customerComapny", new Company());
        toBuild.put("trandingAs", new TradingAs());
        toBuild.put("invoiceToCustomer", new InvoiceToCustomer());
        toBuild.put("rebateCode", new RebateCodeFKNM());
        toBuild.put("countryOfDestination", new CountryOfDestination());
        toBuild.put("exportCurrency", new ExportCurrency());
        //REMOVE FOR TREC
        toBuild.put("customerGroup", new CustomerGroup());
        toBuild.put("physicalAddresLine1", new PhysicalAddress1());
        toBuild.put("physicalAddresLine2", new PhysicalAddress2());
        toBuild.put("physicalAddresLine3", new PhysicalAddress3());
        toBuild.put("physicalAddresLine4", new PhysicalAddress4());
        toBuild.put("physicalAddresLine5", new PhysicalAddress5());
        toBuild.put("physicalPostalCode", new PhysicalPostalCode());
        toBuild.put("postalAddresLine1", new PostalAddress1());
        toBuild.put("postalAddresLine2", new PostalAddress2());
        toBuild.put("postalAddresLine3", new PostalAddress3());
        toBuild.put("postalAddresLine4", new PostalAddress4());
        toBuild.put("postalAddresLine5", new PostalAddress5());
        toBuild.put("postalPostalCode", new PostalCode());
        toBuild.put("telephoneNumber", new Telephone());
        toBuild.put("cellNumber", new Cellphone());
        toBuild.put("emergencyNumber", new EmergencyNumber());
        toBuild.put("faxNumber", new FaxNumber());
        toBuild.put("email", new EmailAddress());
        toBuild.put("website", new Website());
        toBuild.put("orderContactName", new OrderContactName());
        toBuild.put("orderContactTelephoneNumber", new OrderContactTelephoneNum());
        toBuild.put("orderContactCellNumber", new OrderContactCellphoneNum());
        toBuild.put("orderContactEmail", new OrderContactEmail());
        toBuild.put("accountContactName", new AccountContactName());
        toBuild.put("accountContactTelephoneNumber", new AccountContactTelephoneNumber());
        toBuild.put("accountContactCellNumber", new AccountContactCellphoneNumber());
        toBuild.put("accountContactEmail", new AccountContactEmail());
        toBuild.put("creditRating", new CreditRatingFKNM());
        toBuild.put("creaditLimit", new CreditLimit());
        toBuild.put("creditStoped", new CreditStoped());
        toBuild.put("stoppedBy", new UserIdFK());
        toBuild.put("creditStopReason", new CreditStopReasonFKNM());
        toBuild.put("termsOfPayment", new TermsOfPaymentFK());
        toBuild.put("settlementDiscount", new SettlementDiscountTermIdFK());
        toBuild.put("vatRegistration", new VATRegNum());
        toBuild.put("vatCode", new VATCodeFK());
        toBuild.put("priceGroup", new PriceGroupFK());
        toBuild.put("discountGroup", new DiscountGroupFKNotMandatory());
        toBuild.put("extaChargeGroup", new ExtraChargeGroupFKNotMandatory());
        toBuild.put("salesGroup", new SalesGroupFK());
        toBuild.put("salesRegion", new SalesRegionFK());
        toBuild.put("salesArea", new SalesAreaFK());
        toBuild.put("orderWarehouse", new WarehouseFK());
        toBuild.put("marketingGroup", new MarketingGroup());
        toBuild.put("customerStatus", new CustomerStatus());
        toBuild.put("deliveryInstructions", new DeliveryInstructions());
        toBuild.put("creditInsuredBy", new CreditInsurerIDFK());
        toBuild.put("creditController", new CreditControllerIDFK());
        toBuild.put("deliveryChargeCode", new DeliveryChargeCodeFK());
        toBuild.put("nominatedCourier", new CourierIdFK());
        toBuild.put("classificationGroup1", new Classification1FK());
        toBuild.put("classificationGroup2", new Classification2FK());
        toBuild.put("classificationGroup3", new Classification3FK());
        toBuild.put("classificationGroup4", new Classification4FK());
        toBuild.put("classificationGroup5", new Classification5FK());
        toBuild.put("classificationGroup6", new Classification6FK());
        toBuild.put("closedReason", new CreditCloseReasonFKNM());
        toBuild.put("deliveryMethod", new DeliveryMethod());
        toBuild.put("creditInsuredExpiryDate", new CreditInsuredExpiryDate());
        toBuild.put("insurerFileRef", new InsurerDocRef());
        toBuild.put("insuredDocRef", new InsurerFileRef());
        toBuild.put("accountOpenedDate", new AccountOpenedDate());
        toBuild.put("salesRep", new RepIdFKNM());
        toBuild.put("deliveryTerms", new DeliveryTermsIdFK());
        toBuild.put("deliverBeforeDate", new DeliverBeforeDate());
        toBuild.put("deliveryRules", new DeliveryRules());
        //REMOVE FOR TREC

        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("customerId");
        toBuild.add("customerName");
        return toBuild;
    }

    @Override
    public EMCQuery getQuery() {
        EMCQuery query = super.getQuery();
        query.addOrderBy("customerName");

        return query;
    }

    public boolean isServiced() {
        return serviced;
    }

    public void setServiced(boolean serviced) {
        this.serviced = serviced;
    }

    public String getRebateCode() {
        return rebateCode;
    }

    public void setRebateCode(String rebateCode) {
        this.rebateCode = rebateCode;
    }

    public String getSalesRep() {
        return salesRep;
    }

    public void setSalesRep(String salesRep) {
        this.salesRep = salesRep;
    }

    public String getRepService() {
        return repService;
    }

    public void setRepService(String repService) {
        this.repService = repService;
    }

    public String getDeliveryRules() {
        return deliveryRules;
    }

    public void setDeliveryRules(String deliveryRules) {
        this.deliveryRules = deliveryRules;
    }

    public String getCountryOfDestination() {
        return countryOfDestination;
    }

    public void setCountryOfDestination(String countryOfDestination) {
        this.countryOfDestination = countryOfDestination;
    }

    public String getExportCurrency() {
        return exportCurrency;
    }

    public void setExportCurrency(String exportCurrency) {
        this.exportCurrency = exportCurrency;
    }

    public String getWhereHeard() {
        return whereHeard;
    }

    public void setWhereHeard(String whereHeard) {
        this.whereHeard = whereHeard;
    }

    public boolean isAcceptTermsAndConditions() {
        return acceptTermsAndConditions;
    }

    public void setAcceptTermsAndConditions(boolean acceptTermsAndConditions) {
        this.acceptTermsAndConditions = acceptTermsAndConditions;
    }
}
