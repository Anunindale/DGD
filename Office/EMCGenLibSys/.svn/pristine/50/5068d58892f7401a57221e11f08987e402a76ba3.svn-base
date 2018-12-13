/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.pop;

import emc.datatypes.systemwide.PhysicalAddress1;
import emc.datatypes.pop.supplier.SupplierId;
import emc.datatypes.pop.suppliergroups.foreignkeys.SupplierGroupIdFK;
import emc.datatypes.gl.VATApplicable;
import emc.datatypes.gl.currency.foreignkeys.CurrencyFK;
import emc.datatypes.pop.supplier.AccountContactCellphoneNumber;
import emc.datatypes.pop.supplier.AccountContactEmail;
import emc.datatypes.pop.supplier.AccountContactName;
import emc.datatypes.pop.supplier.AccountContactTelephoneNumber;
import emc.datatypes.pop.supplier.AccountOpened;
import emc.datatypes.pop.supplier.ClosedBy;
import emc.datatypes.pop.supplier.ClosedDate;
import emc.datatypes.pop.supplier.ClosedReason;
import emc.datatypes.pop.supplier.CompanyRegistrationNumber;
import emc.datatypes.pop.supplier.CountryOfDestination;
import emc.datatypes.pop.supplier.CreatedBy;
import emc.datatypes.pop.supplier.CreatedDate;
import emc.datatypes.pop.supplier.CreditCloseReason;
import emc.datatypes.pop.supplier.ExportCurrency;
import emc.datatypes.pop.supplier.ModifiedBy;
import emc.datatypes.pop.supplier.ModifiedDate;
import emc.datatypes.pop.supplier.OrderContactCellphoneNum;
import emc.datatypes.pop.supplier.OrderContactEmail;
import emc.datatypes.pop.supplier.OrderContactName;
import emc.datatypes.pop.supplier.OrderContactTelephoneNum;
import emc.datatypes.pop.supplier.PersonResponsible;
import emc.datatypes.pop.supplier.PersonResponsibleCellNo;
import emc.datatypes.pop.supplier.PersonResponsibleEmail;
import emc.datatypes.pop.supplier.PersonResponsibleTelNo;
import emc.datatypes.pop.supplier.Status;
import emc.datatypes.pop.supplier.TermsOfPayment;
import emc.datatypes.pop.supplier.VatRegNum;
import emc.datatypes.systemwide.Cellphone;
import emc.datatypes.systemwide.EmailAddress;
import emc.datatypes.systemwide.Name;
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
import emc.datatypes.systemwide.Telephone;
import emc.datatypes.systemwide.Website;
import emc.enums.pop.suppliers.SupplierStatusEnum;
import emc.framework.EMCEntityClass;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
@Table(name = "POPSuppliers", uniqueConstraints = {@UniqueConstraint(columnNames = {"supplierId", "companyId"})})
public class POPSuppliers extends EMCEntityClass implements Serializable {

    private String supplierId;
    private String supplierName;
    private String supplierGroup;
    private String supplierCurrency;
    private String addressPhysicalLine1;
    private String addressPhysicalLine2;
    private String addressPhysicalLine3;
    private String addressPhysicalLine4;
    private String addressPhysicalLine5;
    private String addressPhysPostalCode;
    private String postalAddressLine1;
    private String postalAddressLine2;
    private String postalAddressLine3;
    private String postalAddressLine4;
    private String postalAddressLine5;
    private String postalCode;
    private String telephone;
    private String cellNo;
    private String emergencyNo;
    private String fax;
    private String email;
    private String website;
    private String contactOrders;
    private String contactOrdersPhone;
    private String contactOrdersEmail;
    private String contactAccounts;
    private String contactAccountsPhone;
    private String contactAccountsEmail;
    private String vatCode;
    private String vatRegistrationNo;
    private String priceGroup;
    private String discountGroup;
    private String extraChargeGroup;
    //New field
    private Boolean vatApplicable = true;
    private double standardLeadTime;
    private double creditLimit;
    private String termsOfPayment;
    private String settlementDiscount;
    private String bank;
    private String bankBranchCode;
    private String bankAccountNumber;
    private String bankAccountName;
    private String accountContactCellphoneNumber;
    private String accountContactEmail;
    private String accountsContactName;
    private String closedBy;
    private String countryOfOrigins;
    private String creditCloseReason;
    private String exportCurrency;
    private String orderContactCellphoneNumber;
    private String orderContactEmail;
    private String orderContactName;
    private String orderContactTelephoneNumber;
    private String personResponsible;
    private String personResponsibleCellNo;
    private String personResponsibleEmail;
    private String personResponsibleTelNo;
    private String status = SupplierStatusEnum.ACTIVE.toString();
    @Temporal(TemporalType.DATE)
    private Date accountOpened;
    @Temporal(TemporalType.DATE)
    private Date closedDate;
    private String companyRegistrationNumber;
    private String closedReason;

    /** Creates a new instance of CreditorsSuppliers */
    public POPSuppliers() {
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierGroup() {
        return supplierGroup;
    }

    public void setSupplierGroup(String supplierGroup) {
        this.supplierGroup = supplierGroup;
    }

    public String getSupplierCurrency() {
        return supplierCurrency;
    }

    public void setSupplierCurrency(String supplierCurrency) {
        this.supplierCurrency = supplierCurrency;
    }

    public String getAddressPhysicalLine1() {
        return addressPhysicalLine1;
    }

    public void setAddressPhysicalLine1(String addressPhysicalLine1) {
        this.addressPhysicalLine1 = addressPhysicalLine1;
    }

    public String getAddressPhysicalLine2() {
        return addressPhysicalLine2;
    }

    public void setAddressPhysicalLine2(String addressPhysicalLine2) {
        this.addressPhysicalLine2 = addressPhysicalLine2;
    }

    public String getAddressPhysicalLine3() {
        return addressPhysicalLine3;
    }

    public void setAddressPhysicalLine3(String addressPhysicalLine3) {
        this.addressPhysicalLine3 = addressPhysicalLine3;
    }

    public String getAddressPhysicalLine4() {
        return addressPhysicalLine4;
    }

    public void setAddressPhysicalLine4(String addressPhysicalLine4) {
        this.addressPhysicalLine4 = addressPhysicalLine4;
    }

    public String getAddressPhysicalLine5() {
        return addressPhysicalLine5;
    }

    public void setAddressPhysicalLine5(String addressPhysicalLine5) {
        this.addressPhysicalLine5 = addressPhysicalLine5;
    }

    public String getAddressPhysPostalCode() {
        return addressPhysPostalCode;
    }

    public void setAddressPhysPostalCode(String addressPhysPostalCode) {
        this.addressPhysPostalCode = addressPhysPostalCode;
    }

    public String getPostalAddressLine1() {
        return postalAddressLine1;
    }

    public void setPostalAddressLine1(String postalAddressLine1) {
        this.postalAddressLine1 = postalAddressLine1;
    }

    public String getPostalAddressLine2() {
        return postalAddressLine2;
    }

    public void setPostalAddressLine2(String postalAddressLine2) {
        this.postalAddressLine2 = postalAddressLine2;
    }

    public String getPostalAddressLine3() {
        return postalAddressLine3;
    }

    public void setPostalAddressLine3(String postalAddressLine3) {
        this.postalAddressLine3 = postalAddressLine3;
    }

    public String getPostalAddressLine4() {
        return postalAddressLine4;
    }

    public void setPostalAddressLine4(String postalAddressLine4) {
        this.postalAddressLine4 = postalAddressLine4;
    }

    public String getPostalAddressLine5() {
        return postalAddressLine5;
    }

    public void setPostalAddressLine5(String postalAddressLine5) {
        this.postalAddressLine5 = postalAddressLine5;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCellNo() {
        return cellNo;
    }

    public void setCellNo(String cellNo) {
        this.cellNo = cellNo;
    }

    public String getEmergencyNo() {
        return emergencyNo;
    }

    public void setEmergencyNo(String emergencyNo) {
        this.emergencyNo = emergencyNo;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getContactOrders() {
        return contactOrders;
    }

    public void setContactOrders(String contactOrders) {
        this.contactOrders = contactOrders;
    }

    public String getContactOrdersPhone() {
        return contactOrdersPhone;
    }

    public void setContactOrdersPhone(String contactOrdersPhone) {
        this.contactOrdersPhone = contactOrdersPhone;
    }

    public String getContactOrdersEmail() {
        return contactOrdersEmail;
    }

    public void setContactOrdersEmail(String contactOrdersEmail) {
        this.contactOrdersEmail = contactOrdersEmail;
    }

    public String getContactAccounts() {
        return contactAccounts;
    }

    public void setContactAccounts(String contactAccounts) {
        this.contactAccounts = contactAccounts;
    }

    public String getContactAccountsPhone() {
        return contactAccountsPhone;
    }

    public void setContactAccountsPhone(String contactAccountsPhone) {
        this.contactAccountsPhone = contactAccountsPhone;
    }

    public String getContactAccountsEmail() {
        return contactAccountsEmail;
    }

    public void setContactAccountsEmail(String contactAccountsEmail) {
        this.contactAccountsEmail = contactAccountsEmail;
    }

    public String getVatCode() {
        return vatCode;
    }

    public void setVatCode(String vatCode) {
        this.vatCode = vatCode;
    }

    public String getVatRegistrationNo() {
        return vatRegistrationNo;
    }

    public void setVatRegistrationNo(String vatRegistrationNo) {
        this.vatRegistrationNo = vatRegistrationNo;
    }

    public String getPriceGroup() {
        return priceGroup;
    }

    public void setPriceGroup(String priceGroup) {
        this.priceGroup = priceGroup;
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

    public double getStandardLeadTime() {
        return standardLeadTime;
    }

    public void setStandardLeadTime(double standardLeadTime) {
        this.standardLeadTime = standardLeadTime;
    }

    public double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(double creditLimit) {
        this.creditLimit = creditLimit;
    }

    public String getTermsOfPayment() {
        return termsOfPayment;
    }

    public void setTermsOfPayment(String termsOfPayment) {
        this.termsOfPayment = termsOfPayment;
    }

    public String getSettlementDiscount() {
        return settlementDiscount;
    }

    public void setSettlementDiscount(String settlementDiscount) {
        this.settlementDiscount = settlementDiscount;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBankBranchCode() {
        return bankBranchCode;
    }

    public void setBankBranchCode(String bankBranchCode) {
        this.bankBranchCode = bankBranchCode;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getBankAccountName() {
        return bankAccountName;
    }

    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    public boolean getVatApplicable() {
        return vatApplicable == null ? false : vatApplicable;
    }

    public void setVatApplicable(Boolean vatApplicable) {
        this.vatApplicable = vatApplicable;
    }

    public String getAccountContactCellphoneNumber() {
        return accountContactCellphoneNumber;
    }

    public void setAccountContactCellphoneNumber(String accountContactCellphoneNumber) {
        this.accountContactCellphoneNumber = accountContactCellphoneNumber;
    }

    public String getAccountContactEmail() {
        return accountContactEmail;
    }

    public void setAccountContactEmail(String accountContactEmail) {
        this.accountContactEmail = accountContactEmail;
    }

    public String getAccountsContactName() {
        return accountsContactName;
    }

    public void setAccountsContactName(String accountsContactName) {
        this.accountsContactName = accountsContactName;
    }

    public String getClosedBy() {
        return closedBy;
    }

    public void setClosedBy(String closedBy) {
        this.closedBy = closedBy;
    }

    public String getCountryOfOrigins() {
        return countryOfOrigins;
    }

    public void setCountryOfOrigins(String countryOfOrigins) {
        this.countryOfOrigins = countryOfOrigins;
    }

    public String getCreditCloseReason() {
        return creditCloseReason;
    }

    public void setCreditCloseReason(String creditCloseReason) {
        this.creditCloseReason = creditCloseReason;
    }

    public String getExportCurrency() {
        return exportCurrency;
    }

    public void setExportCurrency(String exportCurrency) {
        this.exportCurrency = exportCurrency;
    }

    public String getOrderContactCellphoneNumber() {
        return orderContactCellphoneNumber;
    }

    public void setOrderContactCellphoneNumber(String orderContactCellphoneNumber) {
        this.orderContactCellphoneNumber = orderContactCellphoneNumber;
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

    public String getPersonResponsible() {
        return personResponsible;
    }

    public void setPersonResponsible(String personResponsible) {
        this.personResponsible = personResponsible;
    }

    public String getPersonResponsibleCellNo() {
        return personResponsibleCellNo;
    }

    public void setPersonResponsibleCellNo(String personResponsibleCellNo) {
        this.personResponsibleCellNo = personResponsibleCellNo;
    }

    public String getPersonResponsibleEmail() {
        return personResponsibleEmail;
    }

    public void setPersonResponsibleEmail(String personResponsibleEmail) {
        this.personResponsibleEmail = personResponsibleEmail;
    }

    public String getPersonResponsibleTelNo() {
        return personResponsibleTelNo;
    }

    public void setPersonResponsibleTelNo(String personResponsibleTelNo) {
        this.personResponsibleTelNo = personResponsibleTelNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getAccountOpened() {
        return accountOpened;
    }

    public void setAccountOpened(Date accountOpened) {
        this.accountOpened = accountOpened;
    }

    public Date getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(Date closedDate) {
        this.closedDate = closedDate;
    }

    public String getCompanyRegistrationNumber() {
        return companyRegistrationNumber;
    }

    public void setCompanyRegistrationNumber(String companyRegistrationNumber) {
        this.companyRegistrationNumber = companyRegistrationNumber;
    }

    public String getClosedReason() {
        return closedReason;
    }

    public void setClosedReason(String closedReason) {
        this.closedReason = closedReason;
    }

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();

        toBuild.put("supplierName", new Name());
        toBuild.put("bank", new Name());
        toBuild.put("bankAccountName", new Name());
        toBuild.put("contactOrders", new Name());
        toBuild.put("contactAccounts", new Name());


        toBuild.put("addressPhysPostalCode", new PhysicalPostalCode());
        toBuild.put("postalCode", new PostalCode());
        toBuild.put("supplierId", new SupplierId());
        toBuild.put("supplierGroup", new SupplierGroupIdFK());

        Telephone telephoneDT = new Telephone();
        toBuild.put("contactAccountsPhone", telephoneDT);
        toBuild.put("contactOrdersPhone", telephoneDT);
        toBuild.put("fax", telephoneDT);
        toBuild.put("telephone", telephoneDT);

        toBuild.put("cellNo", new Cellphone());

        EmailAddress emailDT = new EmailAddress();
        toBuild.put("contactOrdersEmail", emailDT);
        toBuild.put("contactAccountsEmail", emailDT);
        toBuild.put("email", emailDT);

        toBuild.put("supplierCurrency", new CurrencyFK());

        toBuild.put("addressPhysicalLine1", new PhysicalAddress1());
        toBuild.put("addressPhysicalLine2", new PhysicalAddress2());
        toBuild.put("addressPhysicalLine3", new PhysicalAddress3());
        toBuild.put("addressPhysicalLine4", new PhysicalAddress4());
        toBuild.put("addressPhysicalLine5", new PhysicalAddress5());


        toBuild.put("postalAddressLine1", new PostalAddress1());
        toBuild.put("postalAddressLine2", new PostalAddress2());
        toBuild.put("postalAddressLine3", new PostalAddress3());
        toBuild.put("postalAddressLine4", new PostalAddress4());
        toBuild.put("postalAddressLine5", new PostalAddress5());

        toBuild.put("website", new Website());

        toBuild.put("vatApplicable", new VATApplicable());
        toBuild.put("vatRegistrationNo", new VatRegNum());
        toBuild.put("termsOfPayment", new TermsOfPayment());
        toBuild.put("accountContactCellphoneNumber", new AccountContactCellphoneNumber());
        toBuild.put("accountContactEmail", new AccountContactEmail());
        toBuild.put("accountsContactName", new AccountContactName());
        toBuild.put("closedBy", new ClosedBy());
        toBuild.put("countryOfOrigins", new CountryOfDestination());
        toBuild.put("createdBy", new CreatedBy());
        toBuild.put("creditCloseReason", new CreditCloseReason());
        toBuild.put("exportCurrency", new ExportCurrency());
        toBuild.put("modifiedBy", new ModifiedBy());
        toBuild.put("orderContactCellphoneNumber", new OrderContactCellphoneNum());
        toBuild.put("orderContactEmail", new OrderContactEmail());
        toBuild.put("orderContactName", new OrderContactName());
        toBuild.put("orderContactTelephoneNumber", new OrderContactTelephoneNum());
        toBuild.put("personResponsible", new PersonResponsible());
        toBuild.put("personResponsibleCellNo", new PersonResponsibleCellNo());
        toBuild.put("personResponsibleEmail", new PersonResponsibleEmail());
        toBuild.put("personResponsibleTelNo", new PersonResponsibleTelNo());
        toBuild.put("status", new Status());
        toBuild.put("createdDate", new CreatedDate());
        toBuild.put("accountOpened", new AccountOpened());
        toBuild.put("modifiedDate", new ModifiedDate());
        toBuild.put("closedDate", new ClosedDate());
        toBuild.put("closedReason", new ClosedReason());
        toBuild.put("companyRegistrationNumber", new CompanyRegistrationNumber());
        return toBuild;
    }

    @Override
    public List<String> getDefaultLookupFields() {
        List<String> ret = new ArrayList<String>();

        ret.add("supplierId");
        ret.add("supplierName");
        ret.add("supplierGroup");

        return ret;
    }
}
