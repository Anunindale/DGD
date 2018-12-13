/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.creditors;

import emc.datatypes.EMCDataType;
import emc.datatypes.creditors.creditnoteinvoice.AddressPhysicalCode;
import emc.datatypes.creditors.creditnoteinvoice.AddressPhysicalLine1;
import emc.datatypes.creditors.creditnoteinvoice.AddressPhysicalLine2;
import emc.datatypes.creditors.creditnoteinvoice.AddressPhysicalLine3;
import emc.datatypes.creditors.creditnoteinvoice.AddressPhysicalLine4;
import emc.datatypes.creditors.creditnoteinvoice.AddressPhysicalLine5;
import emc.datatypes.creditors.creditnoteinvoice.AddressPostalCode;
import emc.datatypes.creditors.creditnoteinvoice.AddressPostalLine1;
import emc.datatypes.creditors.creditnoteinvoice.AddressPostalLine2;
import emc.datatypes.creditors.creditnoteinvoice.AddressPostalLine3;
import emc.datatypes.creditors.creditnoteinvoice.AddressPostalLine4;
import emc.datatypes.creditors.creditnoteinvoice.AddressPostalLine5;
import emc.datatypes.creditors.creditnoteinvoice.ApprovedBy;
import emc.datatypes.creditors.creditnoteinvoice.ApprovedDate;
import emc.datatypes.creditors.creditnoteinvoice.ApprovedTime;
import emc.datatypes.creditors.creditnoteinvoice.Comments;
import emc.datatypes.creditors.creditnoteinvoice.CreditNoteInvoiceDate;
import emc.datatypes.creditors.creditnoteinvoice.CreditNoteInvoiceNumber;
import emc.datatypes.creditors.creditnoteinvoice.CreditNoteInvoiceStatus;
import emc.datatypes.creditors.creditnoteinvoice.CreditNoteInvoiceType;
import emc.datatypes.creditors.creditnoteinvoice.DiscountGroup;
import emc.datatypes.creditors.creditnoteinvoice.ExtraChargeGroup;
import emc.datatypes.creditors.creditnoteinvoice.LastPrintedBy;
import emc.datatypes.creditors.creditnoteinvoice.LastPrintedDate;
import emc.datatypes.creditors.creditnoteinvoice.LastPrintedTime;
import emc.datatypes.creditors.creditnoteinvoice.PostedBy;
import emc.datatypes.creditors.creditnoteinvoice.PostedDate;
import emc.datatypes.creditors.creditnoteinvoice.PostedTime;
import emc.datatypes.creditors.creditnoteinvoice.PriceGroup;
import emc.datatypes.creditors.creditnoteinvoice.PrintedBy;
import emc.datatypes.creditors.creditnoteinvoice.PrintedDate;
import emc.datatypes.creditors.creditnoteinvoice.PrintedTime;
import emc.datatypes.creditors.creditnoteinvoice.PurchaseOrderId;
import emc.datatypes.creditors.creditnoteinvoice.SettlementDiscount;
import emc.datatypes.creditors.creditnoteinvoice.SupplierAccountName;
import emc.datatypes.creditors.creditnoteinvoice.SupplierAccountNumber;
import emc.datatypes.creditors.creditnoteinvoice.SupplierBank;
import emc.datatypes.creditors.creditnoteinvoice.SupplierBranch;
import emc.datatypes.creditors.creditnoteinvoice.SupplierId;
import emc.datatypes.creditors.creditnoteinvoice.TermsOfPayment;
import emc.datatypes.creditors.creditnoteinvoice.VatApplicable;
import emc.datatypes.creditors.creditnoteinvoice.VatCode;
import emc.datatypes.creditors.creditnoteinvoice.VatRegistrationNumber;
import emc.datatypes.creditors.transactions.PaymentDueDate;
import emc.datatypes.creditors.transactions.SettlementDiscDate;
import emc.enums.creditors.CreditorsCreditNoteInvoiceStatus;
import emc.framework.EMCEntityClass;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import javax.persistence.Column;
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
@Table(name = "CreditorsCreditNoteInvoiceMaster", uniqueConstraints = {@UniqueConstraint(columnNames = {"creditNoteInvoiceNumber", "companyId"})})
public class CreditorsCreditNoteInvoiceMaster extends EMCEntityClass {

    private String creditNoteInvoiceNumber;
    private String creditNoteInvoiceType;
    @Temporal(TemporalType.DATE)
    private Date creditNoteInvoiceDate;
    private String creditNoteInvoiceStatus = CreditorsCreditNoteInvoiceStatus.CAPTURED.toString();
    private String purchaseOrderId;
    private String supplierId;
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
    private String vatCode;
    private String vatRegistrationNo;
    private boolean vatApplicable;
    private String priceGroup;
    private String discountGroup;
    private String extraChargeGroup;
    private String termsOfPayment;
    private String settlementDiscount;
    private boolean stockCreditNoteInvoice;
    private String supplierBank;
    private String supplierBankBranchCode;
    private String supplierBankAccountNumber;
    private String supplierBankAccountName;
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
    private String creditNoteReasonCode;
    private String creditNoteRefInvoiceNo;
    private String returnOption;
    private BigDecimal purchaseTotal = BigDecimal.ZERO;
    private BigDecimal discountTotal = BigDecimal.ZERO;
    private BigDecimal vatTotal = BigDecimal.ZERO;
    private BigDecimal invoiceTotal = BigDecimal.ZERO;

    public String getAddressPhysPostalCode() {
        return addressPhysPostalCode;
    }

    public void setAddressPhysPostalCode(String addressPhysPostalCode) {
        this.addressPhysPostalCode = addressPhysPostalCode;
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

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
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

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Date getCreditNoteInvoiceDate() {
        return creditNoteInvoiceDate;
    }

    public void setCreditNoteInvoiceDate(Date creditNoteInvoiceDate) {
        this.creditNoteInvoiceDate = creditNoteInvoiceDate;
    }

    public String getCreditNoteInvoiceNumber() {
        return creditNoteInvoiceNumber;
    }

    public void setCreditNoteInvoiceNumber(String creditNoteInvoiceNumber) {
        this.creditNoteInvoiceNumber = creditNoteInvoiceNumber;
    }

    public String getCreditNoteInvoiceType() {
        return creditNoteInvoiceType;
    }

    public void setCreditNoteInvoiceType(String creditNoteInvoiceType) {
        this.creditNoteInvoiceType = creditNoteInvoiceType;
    }

    public String getCreditNoteReasonCode() {
        return creditNoteReasonCode;
    }

    public void setCreditNoteReasonCode(String creditNoteReasonCode) {
        this.creditNoteReasonCode = creditNoteReasonCode;
    }

    public String getCreditNoteRefInvoiceNo() {
        return creditNoteRefInvoiceNo;
    }

    public void setCreditNoteRefInvoiceNo(String creditNoteRefInvoiceNo) {
        this.creditNoteRefInvoiceNo = creditNoteRefInvoiceNo;
    }

    public String getCreditNoteInvoiceStatus() {
        return creditNoteInvoiceStatus;
    }

    public void setCreditNoteInvoiceStatus(String creditNoteStatus) {
        this.creditNoteInvoiceStatus = creditNoteStatus;
    }

    public String getDiscountGroup() {
        return discountGroup;
    }

    public void setDiscountGroup(String discountGroup) {
        this.discountGroup = discountGroup;
    }

    public BigDecimal getDiscountTotal() {
        return discountTotal;
    }

    public void setDiscountTotal(BigDecimal discountTotal) {
        this.discountTotal = discountTotal;
    }

    public String getExtraChargeGroup() {
        return extraChargeGroup;
    }

    public void setExtraChargeGroup(String extraChargeGroup) {
        this.extraChargeGroup = extraChargeGroup;
    }

    public BigDecimal getInvoiceTotal() {
        return invoiceTotal;
    }

    public void setInvoiceTotal(BigDecimal invoiceTotal) {
        this.invoiceTotal = invoiceTotal;
    }

    public String getLastPrintedBy() {
        return lastPrintedBy;
    }

    public void setLastPrintedBy(String lastPrintedBy) {
        this.lastPrintedBy = lastPrintedBy;
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

    public Date getPaymentDueDate() {
        return paymentDueDate;
    }

    public void setPaymentDueDate(Date paymentDueDate) {
        this.paymentDueDate = paymentDueDate;
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

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
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

    public String getPriceGroup() {
        return priceGroup;
    }

    public void setPriceGroup(String priceGroup) {
        this.priceGroup = priceGroup;
    }

    public String getPrintedBy() {
        return printedBy;
    }

    public void setPrintedBy(String printedBy) {
        this.printedBy = printedBy;
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

    public String getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(String purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public BigDecimal getPurchaseTotal() {
        return purchaseTotal;
    }

    public void setPurchaseTotal(BigDecimal purchaseTotal) {
        this.purchaseTotal = purchaseTotal;
    }

    public String getReturnOption() {
        return returnOption;
    }

    public void setReturnOption(String returnOption) {
        this.returnOption = returnOption;
    }

    public Date getSettlementDiscDate() {
        return settlementDiscDate;
    }

    public void setSettlementDiscDate(Date settlementDiscDate) {
        this.settlementDiscDate = settlementDiscDate;
    }

    public String getSettlementDiscount() {
        return settlementDiscount;
    }

    public void setSettlementDiscount(String settlementDiscount) {
        this.settlementDiscount = settlementDiscount;
    }

    public boolean isStockCreditNoteInvoice() {
        return stockCreditNoteInvoice;
    }

    public void setStockCreditNoteInvoice(boolean stockCreditNoteInvoice) {
        this.stockCreditNoteInvoice = stockCreditNoteInvoice;
    }

    public String getSupplierBank() {
        return supplierBank;
    }

    public void setSupplierBank(String supplierBank) {
        this.supplierBank = supplierBank;
    }

    public String getSupplierBankAccountName() {
        return supplierBankAccountName;
    }

    public void setSupplierBankAccountName(String supplierBankAccountName) {
        this.supplierBankAccountName = supplierBankAccountName;
    }

    public String getSupplierBankAccountNumber() {
        return supplierBankAccountNumber;
    }

    public void setSupplierBankAccountNumber(String supplierBankAccountNumber) {
        this.supplierBankAccountNumber = supplierBankAccountNumber;
    }

    public String getSupplierBankBranchCode() {
        return supplierBankBranchCode;
    }

    public void setSupplierBankBranchCode(String supplierBankBranchCode) {
        this.supplierBankBranchCode = supplierBankBranchCode;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getTermsOfPayment() {
        return termsOfPayment;
    }

    public void setTermsOfPayment(String termsOfPayment) {
        this.termsOfPayment = termsOfPayment;
    }

    public boolean isVatApplicable() {
        return vatApplicable;
    }

    public void setVatApplicable(boolean vatApplicable) {
        this.vatApplicable = vatApplicable;
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

    public BigDecimal getVatTotal() {
        return vatTotal;
    }

    public void setVatTotal(BigDecimal vatTotal) {
        this.vatTotal = vatTotal;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("creditNoteInvoiceNumber", new CreditNoteInvoiceNumber());
        toBuild.put("creditNoteInvoiceType", new CreditNoteInvoiceType());
        toBuild.put("creditNoteInvoiceDate", new CreditNoteInvoiceDate());
        toBuild.put("creditNoteInvoiceStatus", new CreditNoteInvoiceStatus());
        toBuild.put("purchaseOrderId", new PurchaseOrderId());
        toBuild.put("supplierId", new SupplierId());
        toBuild.put("addressPhysicalLine1", new AddressPhysicalLine1());
        toBuild.put("addressPhysicalLine2", new AddressPhysicalLine2());
        toBuild.put("addressPhysicalLine3", new AddressPhysicalLine3());
        toBuild.put("addressPhysicalLine4", new AddressPhysicalLine4());
        toBuild.put("addressPhysicalLine5", new AddressPhysicalLine5());
        toBuild.put("addressPhysPostalCode", new AddressPhysicalCode());
        toBuild.put("postalAddressLine1", new AddressPostalLine1());
        toBuild.put("postalAddressLine2", new AddressPostalLine2());
        toBuild.put("postalAddressLine3", new AddressPostalLine3());
        toBuild.put("postalAddressLine4", new AddressPostalLine4());
        toBuild.put("postalAddressLine5", new AddressPostalLine5());
        toBuild.put("postalCode", new AddressPostalCode());
        toBuild.put("vatCode", new VatCode());
        toBuild.put("vatRegistrationNo", new VatRegistrationNumber());
        toBuild.put("vatApplicable", new VatApplicable());
        toBuild.put("priceGroup", new PriceGroup());
        toBuild.put("discountGroup", new DiscountGroup());
        toBuild.put("extraChargeGroup", new ExtraChargeGroup());
        toBuild.put("termsOfPayment", new TermsOfPayment());
        toBuild.put("settlementDiscount", new SettlementDiscount());
        toBuild.put("supplierBank", new SupplierBank());
        toBuild.put("supplierBankBranchCode", new SupplierBranch());
        toBuild.put("supplierBankAccountNumber", new SupplierAccountNumber());
        toBuild.put("supplierBankAccountName", new SupplierAccountName());
        toBuild.put("paymentDueDate", new PaymentDueDate());
        toBuild.put("paymentDueDate", new SettlementDiscDate());
        toBuild.put("approvedDate", new ApprovedDate());
        toBuild.put("approvedTime", new ApprovedTime());
        toBuild.put("approvedBy", new ApprovedBy());
        toBuild.put("postedDate", new PostedDate());
        toBuild.put("postedTime", new PostedTime());
        toBuild.put("postedBy", new PostedBy());
        toBuild.put("printedDate", new PrintedDate());
        toBuild.put("printedTime", new PrintedTime());
        toBuild.put("printedBy", new PrintedBy());
        toBuild.put("lastPrintedDate", new LastPrintedDate());
        toBuild.put("lastPrintedTime", new LastPrintedTime());
        toBuild.put("lastPrintedBy", new LastPrintedBy());
        toBuild.put("comments", new Comments());
//    private String creditNoteReasonCode;
//    private String creditNoteRefInvoiceNo;
//    private String returnOption;
        return toBuild;
    }
}
