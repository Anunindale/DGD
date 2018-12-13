/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.sop;

import emc.datatypes.EMCDataType;
import emc.datatypes.sop.parameters.AllowDuplicateSOLines;
import emc.datatypes.sop.parameters.AllowPartialSTKAssembly;
import emc.enums.sop.parameters.AssemblyPreference;
import emc.framework.EMCEntityClass;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
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
@Table(name = "SOPParameters", uniqueConstraints = {@UniqueConstraint(columnNames = {"companyId"})})
public class SOPParameters extends EMCEntityClass {

    private boolean allowStandardPrice;
    private String customerItemTable1;
    private String customerItemTable2;
    private String customerItemTable3;
    private String customerItemTable4;
    private String customerItemTable5;
    private String customerItemTable6;
    private String customerItemField1;
    private String customerItemField2;
    private String customerItemField3;
    private String customerItemField4;
    private String customerItemField5;
    private String customerItemField6;
    private String customerItemFieldDesc1;
    private String customerItemFieldDesc2;
    private String customerItemFieldDesc3;
    private String customerItemFieldDesc4;
    private String customerItemFieldDesc5;
    private String customerItemFieldDesc6;
    private boolean allowPartialPost;
    private boolean allowPartialBOPost;
    private boolean forcePriceEntry;
    private String useInvoiceTo;
    private boolean useLowestPrice;
    private boolean allowDuplicateSOLines;
    private boolean allowPartialSTKAssembly;
    //Production tab.
    private String assembleWorksOrderGroup;
    private String assembleWorksOrderWC;
    private String assembleBlanketOrderWorksOrderGroup;
    private String assembleBlanketOrderWorksOrderWC;
    private BigDecimal leadTimeInDays = BigDecimal.ZERO;
    private BigDecimal minimumOrderValue = BigDecimal.ZERO;
    private String backOrderCancelReason;
    //Contact
    private String faxNumber;
    private String kimbleDespWorksOrderGroup;
    private String kimbleDespWorksOrderWC;
    private String kimbleStkWorksOrderGroup;
    private String kimbleStkWorksOrderWC;
    private String mtoDespWorksOrderGroup;
    private String mtoDespWorksOrderWC;
    private String mtoStkWorksOrderGroup;
    private String mtoStkWorksOrderWC;
    private String assemblyPreference = AssemblyPreference.ASSEMBLY.toString();
    @Temporal(TemporalType.DATE)
    private Date salesEnquiryLastClearedDate;

    /** Creates a new instance of SOPParameters. */
    public SOPParameters() {
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("allowDuplicateSOLines", new AllowDuplicateSOLines());
        toBuild.put("allowPartialSTKAssembly", new AllowPartialSTKAssembly());

        return toBuild;
    }

    public boolean isAllowStandardPrice() {
        return allowStandardPrice;
    }

    public void setAllowStandardPrice(boolean allowStandardPrice) {
        this.allowStandardPrice = allowStandardPrice;
    }

    public String getCustomerItemField1() {
        return customerItemField1;
    }

    public void setCustomerItemField1(String customerItemField1) {
        this.customerItemField1 = customerItemField1;
    }

    public String getCustomerItemField2() {
        return customerItemField2;
    }

    public void setCustomerItemField2(String customerItemField2) {
        this.customerItemField2 = customerItemField2;
    }

    public String getCustomerItemField3() {
        return customerItemField3;
    }

    public void setCustomerItemField3(String customerItemField3) {
        this.customerItemField3 = customerItemField3;
    }

    public String getCustomerItemField4() {
        return customerItemField4;
    }

    public void setCustomerItemField4(String customerItemField4) {
        this.customerItemField4 = customerItemField4;
    }

    public String getCustomerItemField5() {
        return customerItemField5;
    }

    public void setCustomerItemField5(String customerItemField5) {
        this.customerItemField5 = customerItemField5;
    }

    public String getCustomerItemField6() {
        return customerItemField6;
    }

    public void setCustomerItemField6(String customerItemField6) {
        this.customerItemField6 = customerItemField6;
    }

    public String getCustomerItemTable1() {
        return customerItemTable1;
    }

    public void setCustomerItemTable1(String customerItemTable1) {
        this.customerItemTable1 = customerItemTable1;
    }

    public String getCustomerItemTable2() {
        return customerItemTable2;
    }

    public void setCustomerItemTable2(String customerItemTable2) {
        this.customerItemTable2 = customerItemTable2;
    }

    public String getCustomerItemTable3() {
        return customerItemTable3;
    }

    public void setCustomerItemTable3(String customerItemTable3) {
        this.customerItemTable3 = customerItemTable3;
    }

    public String getCustomerItemTable4() {
        return customerItemTable4;
    }

    public void setCustomerItemTable4(String customerItemTable4) {
        this.customerItemTable4 = customerItemTable4;
    }

    public String getCustomerItemTable5() {
        return customerItemTable5;
    }

    public void setCustomerItemTable5(String customerItemTable5) {
        this.customerItemTable5 = customerItemTable5;
    }

    public String getCustomerItemTable6() {
        return customerItemTable6;
    }

    public void setCustomerItemTable6(String customerItemTable6) {
        this.customerItemTable6 = customerItemTable6;
    }

    public boolean isAllowPartialPost() {
        return allowPartialPost;
    }

    public void setAllowPartialPost(boolean allowPartialPost) {
        this.allowPartialPost = allowPartialPost;
    }

    public String getAssembleWorksOrderGroup() {
        return assembleWorksOrderGroup;
    }

    public void setAssembleWorksOrderGroup(String assembleWorksOrderGroup) {
        this.assembleWorksOrderGroup = assembleWorksOrderGroup;
    }

    public BigDecimal getLeadTimeInDays() {
        return leadTimeInDays;
    }

    public void setLeadTimeInDays(BigDecimal leadTimeInDays) {
        this.leadTimeInDays = leadTimeInDays;
    }

    public boolean isForcePriceEntry() {
        return forcePriceEntry;
    }

    public void setForcePriceEntry(boolean forcePriceEntry) {
        this.forcePriceEntry = forcePriceEntry;
    }

    /**
     * @return the useInvoiceTo
     */
    public String getUseInvoiceTo() {
        return useInvoiceTo;
    }

    /**
     * @param useInvoiceTo the useInvoiceTo to set
     */
    public void setUseInvoiceTo(String useInvoiceTo) {
        this.useInvoiceTo = useInvoiceTo;
    }

    public boolean isAllowPartialBOPost() {
        return allowPartialBOPost;
    }

    public void setAllowPartialBOPost(boolean allowPartialBOPost) {
        this.allowPartialBOPost = allowPartialBOPost;
    }

    public BigDecimal getMinimumOrderValue() {
        return minimumOrderValue;
    }

    public void setMinimumOrderValue(BigDecimal minimumOrderValue) {
        this.minimumOrderValue = minimumOrderValue;
    }

    public String getAssembleBlanketOrderWorksOrderGroup() {
        return assembleBlanketOrderWorksOrderGroup;
    }

    public void setAssembleBlanketOrderWorksOrderGroup(String assembleBlanketOrderWorksOrderGroup) {
        this.assembleBlanketOrderWorksOrderGroup = assembleBlanketOrderWorksOrderGroup;
    }

    public String getAssembleBlanketOrderWorksOrderWC() {
        return assembleBlanketOrderWorksOrderWC;
    }

    public void setAssembleBlanketOrderWorksOrderWC(String assembleBlanketOrderWorksOrderWC) {
        this.assembleBlanketOrderWorksOrderWC = assembleBlanketOrderWorksOrderWC;
    }

    public String getAssembleWorksOrderWC() {
        return assembleWorksOrderWC;
    }

    public void setAssembleWorksOrderWC(String assembleWorksOrderWC) {
        this.assembleWorksOrderWC = assembleWorksOrderWC;
    }

    public boolean isUseLowestPrice() {
        return useLowestPrice;
    }

    public void setUseLowestPrice(boolean useLowestPrice) {
        this.useLowestPrice = useLowestPrice;
    }

    public boolean isAllowDuplicateSOLines() {
        return allowDuplicateSOLines;
    }

    public void setAllowDuplicateSOLines(boolean allowDuplicateSOLines) {
        this.allowDuplicateSOLines = allowDuplicateSOLines;
    }

    public boolean isAllowPartialSTKAssembly() {
        return allowPartialSTKAssembly;
    }

    public void setAllowPartialSTKAssembly(boolean allowPartialSTKAssembly) {
        this.allowPartialSTKAssembly = allowPartialSTKAssembly;
    }

    public String getBackOrderCancelReason() {
        return backOrderCancelReason;
    }

    public void setBackOrderCancelReason(String backOrderCancelReason) {
        this.backOrderCancelReason = backOrderCancelReason;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    public String getCustomerItemFieldDesc1() {
        return customerItemFieldDesc1;
    }

    public void setCustomerItemFieldDesc1(String customerItemFieldDesc1) {
        this.customerItemFieldDesc1 = customerItemFieldDesc1;
    }

    public String getCustomerItemFieldDesc2() {
        return customerItemFieldDesc2;
    }

    public void setCustomerItemFieldDesc2(String customerItemFieldDesc2) {
        this.customerItemFieldDesc2 = customerItemFieldDesc2;
    }

    public String getCustomerItemFieldDesc3() {
        return customerItemFieldDesc3;
    }

    public void setCustomerItemFieldDesc3(String customerItemFieldDesc3) {
        this.customerItemFieldDesc3 = customerItemFieldDesc3;
    }

    public String getCustomerItemFieldDesc4() {
        return customerItemFieldDesc4;
    }

    public void setCustomerItemFieldDesc4(String customerItemFieldDesc4) {
        this.customerItemFieldDesc4 = customerItemFieldDesc4;
    }

    public String getCustomerItemFieldDesc5() {
        return customerItemFieldDesc5;
    }

    public void setCustomerItemFieldDesc5(String customerItemFieldDesc5) {
        this.customerItemFieldDesc5 = customerItemFieldDesc5;
    }

    public String getCustomerItemFieldDesc6() {
        return customerItemFieldDesc6;
    }

    public void setCustomerItemFieldDesc6(String customerItemFieldDesc6) {
        this.customerItemFieldDesc6 = customerItemFieldDesc6;
    }

    public String getAssemblyPreference() {
        return assemblyPreference;
    }

    public void setAssemblyPreference(String assemblyPreference) {
        this.assemblyPreference = assemblyPreference;
    }

    public String getKimbleDespWorksOrderGroup() {
        return kimbleDespWorksOrderGroup;
    }

    public void setKimbleDespWorksOrderGroup(String kimbleDespWorksOrderGroup) {
        this.kimbleDespWorksOrderGroup = kimbleDespWorksOrderGroup;
    }

    public String getKimbleDespWorksOrderWC() {
        return kimbleDespWorksOrderWC;
    }

    public void setKimbleDespWorksOrderWC(String kimbleDespWorksOrderWC) {
        this.kimbleDespWorksOrderWC = kimbleDespWorksOrderWC;
    }

    public String getKimbleStkWorksOrderGroup() {
        return kimbleStkWorksOrderGroup;
    }

    public void setKimbleStkWorksOrderGroup(String kimbleStkWorksOrderGroup) {
        this.kimbleStkWorksOrderGroup = kimbleStkWorksOrderGroup;
    }

    public String getKimbleStkWorksOrderWC() {
        return kimbleStkWorksOrderWC;
    }

    public void setKimbleStkWorksOrderWC(String kimbleStkWorksOrderWC) {
        this.kimbleStkWorksOrderWC = kimbleStkWorksOrderWC;
    }

    public Date getSalesEnquiryLastClearedDate() {
        return salesEnquiryLastClearedDate;
    }

    public void setSalesEnquiryLastClearedDate(Date salesEnquiryLastClearedDate) {
        this.salesEnquiryLastClearedDate = salesEnquiryLastClearedDate;
    }

    public String getMtoDespWorksOrderGroup() {
        return mtoDespWorksOrderGroup;
    }

    public void setMtoDespWorksOrderGroup(String mtoDespWorksOrderGroup) {
        this.mtoDespWorksOrderGroup = mtoDespWorksOrderGroup;
    }

    public String getMtoDespWorksOrderWC() {
        return mtoDespWorksOrderWC;
    }

    public void setMtoDespWorksOrderWC(String mtoDespWorksOrderWC) {
        this.mtoDespWorksOrderWC = mtoDespWorksOrderWC;
    }

    public String getMtoStkWorksOrderGroup() {
        return mtoStkWorksOrderGroup;
    }

    public void setMtoStkWorksOrderGroup(String mtoStkWorksOrderGroup) {
        this.mtoStkWorksOrderGroup = mtoStkWorksOrderGroup;
    }

    public String getMtoStkWorksOrderWC() {
        return mtoStkWorksOrderWC;
    }

    public void setMtoStkWorksOrderWC(String mtoStkWorksOrderWC) {
        this.mtoStkWorksOrderWC = mtoStkWorksOrderWC;
    }
}
