/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.pop.purchaseorderposting;

/**
 *
 * @author riaan
 */
public class POPPurchaseOrderPostingReportDS {

    //Master fields
    private String postId;
    private String purchaseOrderId;
    private String supplierAccount;
    private String supplierId;
    private String documentNumber;
    private String postDate;
    private String orderedBy;
    //Lines fields
    private double lineNumber;
    private String itemId;
    private String inventoryDimensionId;
    private String dimension1;
    private String dimension2;
    private String dimension3;
    private double quantity;
    //Supplier fields
    private String supplierName;
    private String addressPhysicalLine1;
    private String addressPhysicalLine2;
    private String addressPhysicalLine3;
    private String addressPhysicalLine4;
    private String addressPhysicalLine5;
    private String addressPhysPostalCode;
    private String telephone;
    private String fax;
    //Company fields
    private String companyName;
    private String coPhysAdrs1;
    private String coPhysAdrs2;
    private String coPhysAdrs3;
    private String coPhysAdrs4;
    private String coPhysAdrs5;
    private String coPhysPostCode;
    private String coPhoneNo;
    private String coFaxNo;
    private String registration;
    private String vatNumber;
    //Purchase order lines fields
    private double lineNo;
    private String itemName;
    private double purchaseOrderquantity;
    private String uom;
    private double itemPrice;
    private double discountPercentage;
    private double netAmount;
    private String deliveryDate;
    private double itemsInvoiced;
    private double itemsReceived;
    private double receiveNow;
    private double lineVAT;
    //Purchase order master fields
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String addressLine4;
    private String addressLine5;
    private String addressPostalCode;
    private String deleveryTerms;
    private String deleveryTermsDesc;
    private String deleveryMode;
    private String deleveryModeDesc;
    //Item master fields
    private String description;
    private String detailedDescription;
    //Datasource fields
    private double discount;
    private double amount;
    private double remainingQuantity;
    private double quantityOutstanding;
    private String qualityTest;
    private String qualityDesc;
    private boolean qualityTestReq;
    private boolean quatityReportReq;
    private String copy;
    private String blanketOrderRef;
    //"Alternate" fields
    private String alternateItem;
    private String alternateDescription;
    private Boolean printPrice;
    private String masterComments;
    private String lineComments;
    //Goods received note number
    private String grn;
    //Returned note number
    private String rn;

    //External/Internal References
    private String externalRef;
    private String internalRef;

    /** Creates a new instance of POPPurchaseOrderPostingReportDS */
    public POPPurchaseOrderPostingReportDS() {
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(String purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public String getSupplierAccount() {
        return supplierAccount;
    }

    public void setSupplierAccount(String supplierAccount) {
        this.supplierAccount = supplierAccount;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public double getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(double lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getInventoryDimensionId() {
        return inventoryDimensionId;
    }

    public void setInventoryDimensionId(String inventoryDimensionId) {
        this.inventoryDimensionId = inventoryDimensionId;
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

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCoPhysAdrs1() {
        return coPhysAdrs1;
    }

    public void setCoPhysAdrs1(String coPhysAdrs1) {
        this.coPhysAdrs1 = coPhysAdrs1;
    }

    public String getCoPhysAdrs2() {
        return coPhysAdrs2;
    }

    public void setCoPhysAdrs2(String coPhysAdrs2) {
        this.coPhysAdrs2 = coPhysAdrs2;
    }

    public String getCoPhysAdrs3() {
        return coPhysAdrs3;
    }

    public void setCoPhysAdrs3(String coPhysAdrs3) {
        this.coPhysAdrs3 = coPhysAdrs3;
    }

    public String getCoPhysAdrs4() {
        return coPhysAdrs4;
    }

    public void setCoPhysAdrs4(String coPhysAdrs4) {
        this.coPhysAdrs4 = coPhysAdrs4;
    }

    public String getCoPhysAdrs5() {
        return coPhysAdrs5;
    }

    public void setCoPhysAdrs5(String coPhysAdrs5) {
        this.coPhysAdrs5 = coPhysAdrs5;
    }

    public String getCoPhysPostCode() {
        return coPhysPostCode;
    }

    public void setCoPhysPostCode(String coPhysPostCode) {
        this.coPhysPostCode = coPhysPostCode;
    }

    public String getCoPhoneNo() {
        return coPhoneNo;
    }

    public void setCoPhoneNo(String coPhoneNo) {
        this.coPhoneNo = coPhoneNo;
    }

    public String getCoFaxNo() {
        return coFaxNo;
    }

    public void setCoFaxNo(String coFaxNo) {
        this.coFaxNo = coFaxNo;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getLineNo() {
        return lineNo;
    }

    public void setLineNo(double lineNo) {
        this.lineNo = lineNo;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getPurchaseOrderquantity() {
        return purchaseOrderquantity;
    }

    public void setPurchaseOrderquantity(double purchaseOrderquantity) {
        this.purchaseOrderquantity = purchaseOrderquantity;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public double getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(double netAmount) {
        this.netAmount = netAmount;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public double getItemsInvoiced() {
        return itemsInvoiced;
    }

    public void setItemsInvoiced(double itemsInvoiced) {
        this.itemsInvoiced = itemsInvoiced;
    }

    public double getItemsReceived() {
        return itemsReceived;
    }

    public void setItemsReceived(double itemsReceived) {
        this.itemsReceived = itemsReceived;
    }

    public double getReceiveNow() {
        return receiveNow;
    }

    public void setReceiveNow(double receiveNow) {
        this.receiveNow = receiveNow;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
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

    public double getRemainingQuantity() {
        return remainingQuantity;
    }

    public void setRemainingQuantity(double remainingQuantity) {
        this.remainingQuantity = remainingQuantity;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public String getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }

    public double getLineVAT() {
        return lineVAT;
    }

    public void setLineVAT(double lineVAT) {
        this.lineVAT = lineVAT;
    }

    public String getQualityTest() {
        return qualityTest;
    }

    public void setQualityTest(String qualityTest) {
        this.qualityTest = qualityTest;
    }

    public boolean isQualityTestReq() {
        return qualityTestReq;
    }

    public void setQualityTestReq(boolean qualityTestReq) {
        this.qualityTestReq = qualityTestReq;
    }

    public boolean isQuatityReportReq() {
        return quatityReportReq;
    }

    public void setQuatityReportReq(boolean quatityReportReq) {
        this.quatityReportReq = quatityReportReq;
    }

    public String getCopy() {
        return copy;
    }

    public void setCopy(String copy) {
        this.copy = copy;
    }

    public String getAlternateItem() {
        return alternateItem;
    }

    public void setAlternateItem(String alternateItem) {
        this.alternateItem = alternateItem;
    }

    public String getAlternateDescription() {
        return alternateDescription;
    }

    public void setAlternateDescription(String alternateDescription) {
        this.alternateDescription = alternateDescription;
    }

    public double getQuantityOutstanding() {
        return quantityOutstanding;
    }

    public void setQuantityOutstanding(double quantityOutstanding) {
        this.quantityOutstanding = quantityOutstanding;
    }

    public String getOrderedBy() {
        return orderedBy;
    }

    public void setOrderedBy(String orderedBy) {
        this.orderedBy = orderedBy;
    }

    public String getBlanketOrderRef() {
        return blanketOrderRef;
    }

    public void setBlanketOrderRef(String blanketOrderRef) {
        this.blanketOrderRef = blanketOrderRef;
    }

    public String getQualityDesc() {
        return qualityDesc;
    }

    public void setQualityDesc(String qualityDesc) {
        this.qualityDesc = qualityDesc;
    }

    public Boolean isPrintPrice() {
        return printPrice;
    }

    public void setPrintPrice(Boolean printPrice) {
        this.printPrice = printPrice;
    }

    public String getMasterComments() {
        return masterComments;
    }

    public void setMasterComments(String masterComments) {
        this.masterComments = masterComments;
    }

    public String getLineComments() {
        return lineComments;
    }

    public void setLineComments(String lineComments) {
        this.lineComments = lineComments;
    }

    public String getGrn() {
        return grn;
    }

    public void setGrn(String grn) {
        this.grn = grn;
    }

    public String getRn() {
        return rn;
    }

    public void setRn(String rn) {
        this.rn = rn;
    }

    public String getDeleveryMode() {
        return deleveryMode;
    }

    public void setDeleveryMode(String deleveryMode) {
        this.deleveryMode = deleveryMode;
    }

    public String getDeleveryModeDesc() {
        return deleveryModeDesc;
    }

    public void setDeleveryModeDesc(String deleveryModeDesc) {
        this.deleveryModeDesc = deleveryModeDesc;
    }

    public String getDeleveryTerms() {
        return deleveryTerms;
    }

    public void setDeleveryTerms(String deleveryTerms) {
        this.deleveryTerms = deleveryTerms;
    }

    public String getDeleveryTermsDesc() {
        return deleveryTermsDesc;
    }

    public void setDeleveryTermsDesc(String deleveryTermsDesc) {
        this.deleveryTermsDesc = deleveryTermsDesc;
    }

    public String getDetailedDescription() {
        return detailedDescription;
    }

    public void setDetailedDescription(String detailedDescription) {
        this.detailedDescription = detailedDescription;
    }

    public String getExternalRef() {
        return externalRef;
    }

    public void setExternalRef(String externalRef) {
        this.externalRef = externalRef;
    }

    public String getInternalRef() {
        return internalRef;
    }

    public void setInternalRef(String internalRef) {
        this.internalRef = internalRef;
    }
}
