/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.debtors;

import emc.datatypes.EMCDataType;
import emc.datatypes.base.journals.journaldefinition.foreignkeys.JournalDefinitionIdFK;
import emc.datatypes.creditors.settlementdicountgroups.foreignkeys.SettlementDiscountTermIdFK;
import emc.datatypes.creditors.termsofpayment.foreignkeys.TermsOfPaymentFK;
import emc.datatypes.creditors.termsofpayment.foreignkeys.TermsOfPaymentFKNotMandatory;
import emc.datatypes.debtors.creditrating.foreignkey.CreditRatingFKNM;
import emc.datatypes.debtors.parameters.Bin1Name;
import emc.datatypes.debtors.parameters.Bin2Name;
import emc.datatypes.debtors.parameters.Bin3Name;
import emc.datatypes.debtors.parameters.Bin4Name;
import emc.datatypes.debtors.parameters.Bin5Name;
import emc.datatypes.debtors.parameters.Bin6Name;
import emc.datatypes.debtors.parameters.CheckBalance;
import emc.datatypes.debtors.parameters.CheckOnInvoicePost;
import emc.datatypes.debtors.parameters.CheckOnPickingListCreate;
import emc.datatypes.debtors.parameters.CheckOnSalesOrderSave;
import emc.datatypes.debtors.parameters.CheckTerms;
import emc.datatypes.debtors.parameters.CreditCheckIncludeVAT;
import emc.datatypes.debtors.parameters.CurrentBinName;
import emc.datatypes.debtors.parameters.DefaultReturnLocation;
import emc.datatypes.debtors.parameters.DefaultReturnWarehouse;
import emc.datatypes.debtors.parameters.HandlingChargeItem;
import emc.datatypes.debtors.parameters.HandlingChargePercentage;
import emc.datatypes.debtors.parameters.PostDatedPaymentJournalDef;
import emc.datatypes.debtors.parameters.ReportFooterCaption;
import emc.datatypes.debtors.parameters.RoyaltyField1;
import emc.datatypes.debtors.parameters.RoyaltyField2;
import emc.datatypes.debtors.parameters.RoyaltyField3;
import emc.datatypes.gl.vatcodes.foreignkeys.VATCodeFK;
import emc.datatypes.inventory.itemmaster.foreignkeys.ItemIdFK;
import emc.datatypes.inventory.warehouses.foreignkeys.WarehouseFK;
import emc.datatypes.pop.pricegroups.foreignkeys.PriceGroupFK;
import emc.datatypes.sop.customers.CustomerGroup;
import emc.datatypes.sop.customers.DeliveryMethod;
import emc.datatypes.sop.customers.DeliveryRules;
import emc.datatypes.sop.customers.MarketingGroup;
import emc.enums.debtors.parameters.DebtorsAgingMode;
import emc.enums.debtors.parameters.DebtorsAgingPeriod;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @description : Entity class for Debtors module parameters.
 *
 * @date        : 19 May 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
@Entity
@Table(name = "DebtorsParameters", uniqueConstraints = {@UniqueConstraint(columnNames = {"companyId"})})
public class DebtorsParameters extends EMCEntityClass {

    private String postDatedPaymentJournalDef;
    private String debtorsAgingPeriod = DebtorsAgingPeriod.CALENDAR_MONTHS.toString();
    private String debtorsAgingMode = DebtorsAgingMode.NONE.toString();
    private String agingCurrentBinName = "Current";
    private String agingBin1Name = "30 Days";
    private String agingBin2Name = "60 Days";
    private String agingBin3Name = "90 Days";
    private String agingBin4Name = "120 Days+";
    private String agingBin5Name;
    private String agingBin6Name;
    private String reportFooterCaption;
    //Handling charge fields
    private double handlingChargePercentage;
    private String handlingChargeItem;
    //Delivery charge fields
    private String deliveryChargeItem;
    //Credit check fields
    private boolean checkBalance;
    private boolean checkTerms;
    private boolean checkOnSalesOrderSave;
    private boolean checkOnPickingListCreate;
    private boolean checkOnInvoicePost;
    private boolean recheckInvoice;
    private boolean creditCheckIncludeVAT = true;
    //Invoice print fields.
    private boolean printPricesOnDeliveryNote;
    //Royalty fields.  These columns will store item master field names.
    private String royaltyField1;
    private String royaltyField2;
    private String royaltyField3;
    //Discount tolerance
    private int discountToleranceDays;
    //Credit Notes
    private String defaultReturnWarehouse;
    private String defaultReturnLocation;
    private String cashTermsCode;
    //Web Customers
    private String marketingGroup;
    private String priceGroup;
    private String deliveryMethod;
    private String deliveryRules;
    private String settlementDiscount;
    private String orderWarehouse;
    private String vatCode;
    private String termsOfPayment;
    private String customerGroup;
    //Web Basket Default Item
    private String defaultItem;
    private String creditRating;
    private String creditJournalDef;
    private String debitJournalDef;
    

    /** Creates a new instance of DebtorsParameters */
    public DebtorsParameters() {
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("postDatedPaymentJournalDef", new PostDatedPaymentJournalDef());
        toBuild.put("agingCurrentBinName", new CurrentBinName());
        toBuild.put("agingBin1Name", new Bin1Name());
        toBuild.put("agingBin2Name", new Bin2Name());
        toBuild.put("agingBin3Name", new Bin3Name());
        toBuild.put("agingBin4Name", new Bin4Name());
        toBuild.put("agingBin5Name", new Bin5Name());
        toBuild.put("agingBin6Name", new Bin6Name());
        toBuild.put("debtorsAgingMode", new emc.datatypes.debtors.parameters.DebtorsAgingMode());
        toBuild.put("reportFooterCaption", new ReportFooterCaption());
        toBuild.put("handlingChargePercentage", new HandlingChargePercentage());
        toBuild.put("handlingChargeItem", new HandlingChargeItem());

        toBuild.put("royaltyField1", new RoyaltyField1());
        toBuild.put("royaltyField2", new RoyaltyField2());
        toBuild.put("royaltyField3", new RoyaltyField3());

        toBuild.put("checkBalance", new CheckBalance());
        toBuild.put("checkTerms", new CheckTerms());
        toBuild.put("checkOnSalesOrderSave", new CheckOnSalesOrderSave());
        toBuild.put("checkOnPickingListCreate", new CheckOnPickingListCreate());
        toBuild.put("checkOnInvoicePost", new CheckOnInvoicePost());
        toBuild.put("creditCheckIncludeVAT", new CreditCheckIncludeVAT());

        toBuild.put("defaultReturnWarehouse", new DefaultReturnWarehouse());
        toBuild.put("defaultReturnLocation", new DefaultReturnLocation());

        toBuild.put("cashTermsCode", new TermsOfPaymentFKNotMandatory());
        toBuild.put("marketingGroup", new MarketingGroup());
        toBuild.put("priceGroup", new PriceGroupFK());
        toBuild.put("deliveryMethod", new DeliveryMethod());
        toBuild.put("deliveryRules", new DeliveryRules());
        toBuild.put("settlementDiscount", new SettlementDiscountTermIdFK());
        toBuild.put("orderWarehouse", new WarehouseFK());
        toBuild.put("vatCode", new VATCodeFK());
        toBuild.put("termsOfPayment", new TermsOfPaymentFK());
        toBuild.put("customerGroup", new CustomerGroup());
        
        toBuild.put("defaultItem", new ItemIdFK());
        toBuild.put("creditRating", new CreditRatingFKNM());
        toBuild.put("creditJournalDef", new JournalDefinitionIdFK());
        toBuild.put("debitJournalDef",new JournalDefinitionIdFK());

        return toBuild;
    }

    public String getPostDatedPaymentJournalDef() {
        return postDatedPaymentJournalDef;
    }

    public void setPostDatedPaymentJournalDef(String postDatedPaymentJournalDef) {
        this.postDatedPaymentJournalDef = postDatedPaymentJournalDef;
    }

    public String getDebtorsAgingPeriod() {
        return debtorsAgingPeriod;
    }

    public void setDebtorsAgingPeriod(String debtorsAgingPeriod) {
        this.debtorsAgingPeriod = debtorsAgingPeriod;
    }

    public String getAgingCurrentBinName() {
        return agingCurrentBinName;
    }

    public void setAgingCurrentBinName(String agingCurrentBinName) {
        this.agingCurrentBinName = agingCurrentBinName;
    }

    public String getAgingBin1Name() {
        return agingBin1Name;
    }

    public void setAgingBin1Name(String agingBin1Name) {
        this.agingBin1Name = agingBin1Name;
    }

    public String getAgingBin2Name() {
        return agingBin2Name;
    }

    public void setAgingBin2Name(String agingBin2Name) {
        this.agingBin2Name = agingBin2Name;
    }

    public String getAgingBin3Name() {
        return agingBin3Name;
    }

    public void setAgingBin3Name(String agingBin3Name) {
        this.agingBin3Name = agingBin3Name;
    }

    public String getAgingBin4Name() {
        return agingBin4Name;
    }

    public void setAgingBin4Name(String agingBin4Name) {
        this.agingBin4Name = agingBin4Name;
    }

    public String getAgingBin5Name() {
        return agingBin5Name;
    }

    public void setAgingBin5Name(String agingBin5Name) {
        this.agingBin5Name = agingBin5Name;
    }

    public String getAgingBin6Name() {
        return agingBin6Name;
    }

    public void setAgingBin6Name(String agingBin6Name) {
        this.agingBin6Name = agingBin6Name;
    }

    public String getDebtorsAgingMode() {
        return debtorsAgingMode;
    }

    public void setDebtorsAgingMode(String debtorsAgingMode) {
        this.debtorsAgingMode = debtorsAgingMode;
    }

    public String getReportFooterCaption() {
        return reportFooterCaption;
    }

    public void setReportFooterCaption(String reportFooterCaption) {
        this.reportFooterCaption = reportFooterCaption;
    }

    public String getHandlingChargeItem() {
        return handlingChargeItem;
    }

    public void setHandlingChargeItem(String handlingChargeItem) {
        this.handlingChargeItem = handlingChargeItem;
    }

    public double getHandlingChargePercentage() {
        return handlingChargePercentage;
    }

    public void setHandlingChargePercentage(double handlingChargePercentage) {
        this.handlingChargePercentage = handlingChargePercentage;
    }

    public boolean isCheckBalance() {
        return checkBalance;
    }

    public void setCheckBalance(boolean checkBalance) {
        this.checkBalance = checkBalance;
    }

    public boolean isCheckTerms() {
        return checkTerms;
    }

    public void setCheckTerms(boolean checkTerms) {
        this.checkTerms = checkTerms;
    }

    public boolean isCheckOnSalesOrderSave() {
        return checkOnSalesOrderSave;
    }

    public void setCheckOnSalesOrderSave(boolean checkOnSalesOrderSave) {
        this.checkOnSalesOrderSave = checkOnSalesOrderSave;
    }

    public boolean isCheckOnPickingListCreate() {
        return checkOnPickingListCreate;
    }

    public void setCheckOnPickingListCreate(boolean checkOnPickingListCreate) {
        this.checkOnPickingListCreate = checkOnPickingListCreate;
    }

    public boolean isCheckOnInvoicePost() {
        return checkOnInvoicePost;
    }

    public void setCheckOnInvoicePost(boolean checkOnInvoicePost) {
        this.checkOnInvoicePost = checkOnInvoicePost;
    }

    public boolean isPrintPricesOnDeliveryNote() {
        return printPricesOnDeliveryNote;
    }

    public void setPrintPricesOnDeliveryNote(boolean printPricesOnDeliveryNote) {
        this.printPricesOnDeliveryNote = printPricesOnDeliveryNote;
    }

    public String getRoyaltyField1() {
        return royaltyField1;
    }

    public void setRoyaltyField1(String royaltyField1) {
        this.royaltyField1 = royaltyField1;
    }

    public String getRoyaltyField2() {
        return royaltyField2;
    }

    public void setRoyaltyField2(String royaltyField2) {
        this.royaltyField2 = royaltyField2;
    }

    public String getRoyaltyField3() {
        return royaltyField3;
    }

    public void setRoyaltyField3(String royaltyField3) {
        this.royaltyField3 = royaltyField3;
    }

    public String getDeliveryChargeItem() {
        return deliveryChargeItem;
    }

    public void setDeliveryChargeItem(String deliveryChargeItem) {
        this.deliveryChargeItem = deliveryChargeItem;
    }

    public int getDiscountToleranceDays() {
        return discountToleranceDays;
    }

    public void setDiscountToleranceDays(int discountToleranceDays) {
        this.discountToleranceDays = discountToleranceDays;
    }

    public boolean isRecheckInvoice() {
        return recheckInvoice;
    }

    public void setRecheckInvoice(boolean recheckInvoice) {
        this.recheckInvoice = recheckInvoice;
    }

    public String getDefaultReturnLocation() {
        return defaultReturnLocation;
    }

    public void setDefaultReturnLocation(String defaultReturnLocation) {
        this.defaultReturnLocation = defaultReturnLocation;
    }

    public String getDefaultReturnWarehouse() {
        return defaultReturnWarehouse;
    }

    public void setDefaultReturnWarehouse(String defaultReturnWarehouse) {
        this.defaultReturnWarehouse = defaultReturnWarehouse;
    }

    public boolean isCreditCheckIncludeVAT() {
        return creditCheckIncludeVAT;
    }

    public void setCreditCheckIncludeVAT(boolean creditCheckIncludeVAT) {
        this.creditCheckIncludeVAT = creditCheckIncludeVAT;
    }

    public String getCashTermsCode() {
        return cashTermsCode;
    }

    public void setCashTermsCode(String cashTermsCode) {
        this.cashTermsCode = cashTermsCode;
    }

    public String getMarketingGroup() {
        return marketingGroup;
    }

    public void setMarketingGroup(String marketingGroup) {
        this.marketingGroup = marketingGroup;
    }

    public String getPriceGroup() {
        return priceGroup;
    }

    public void setPriceGroup(String priceGroup) {
        this.priceGroup = priceGroup;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public String getDeliveryRules() {
        return deliveryRules;
    }

    public void setDeliveryRules(String deliveryRules) {
        this.deliveryRules = deliveryRules;
    }

    public String getSettlementDiscount() {
        return settlementDiscount;
    }

    public void setSettlementDiscount(String settlementDiscount) {
        this.settlementDiscount = settlementDiscount;
    }

    public String getOrderWarehouse() {
        return orderWarehouse;
    }

    public void setOrderWarehouse(String orderWarehouse) {
        this.orderWarehouse = orderWarehouse;
    }

    public String getVatCode() {
        return vatCode;
    }

    public void setVatCode(String vatCode) {
        this.vatCode = vatCode;
    }

    public String getTermsOfPayment() {
        return termsOfPayment;
    }

    public void setTermsOfPayment(String termsOfPayment) {
        this.termsOfPayment = termsOfPayment;
    }

    public String getCustomerGroup() {
        return customerGroup;
    }

    public void setCustomerGroup(String customerGroup) {
        this.customerGroup = customerGroup;
    }

    public String getDefaultItem() {
        return defaultItem;
    }

    public void setDefaultItem(String defaultItem) {
        this.defaultItem = defaultItem;
    }

    public String getCreditRating() {
        return creditRating;
    }

    public void setCreditRating(String creditRating) {
        this.creditRating = creditRating;
    }

    public String getCreditJournalDef() {
        return creditJournalDef;
    }

    public void setCreditJournalDef(String creditJournalDef) {
        this.creditJournalDef = creditJournalDef;
    }

    public String getDebitJournalDef() {
        return debitJournalDef;
    }

    public void setDebitJournalDef(String debitJournalDef) {
        this.debitJournalDef = debitJournalDef;
    }
    
}
