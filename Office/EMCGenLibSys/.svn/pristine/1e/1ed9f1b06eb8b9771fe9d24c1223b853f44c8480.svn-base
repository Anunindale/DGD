/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.pop;

/**
 *
 * @author wikus
 */
import emc.datatypes.pop.parameters.AllowBOMItemsPurchase;
import emc.datatypes.pop.parameters.POCommentPrint;
import emc.datatypes.pop.purchaseorderapprovalgroups.foreignkeys.ApprovalGroupIdFKNotMandatory;
import emc.framework.EMCEntityClass;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.HashMap;

@Entity
@Table(name = "POPParameters", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"companyId"})})
public class POPParameters extends EMCEntityClass {

    private boolean allowOverReceive;
    private boolean allowBlanketOrderOverRelease;
    private double overPercentage;
    private double blanketOrderOverReleasePercentage;
    private Boolean serialMoreThanOne;
    private Boolean displaySupplierItemRef;
    private Boolean allowBOMItemsPurchase;
    private String poCommentPrint;
    private Boolean priceReceivedReport;
    private Boolean priceReturnedReport;
    private String itemGrpToReadColMast;
    private boolean printDetailedItemDescription;
    private String itemField1ForPlannedRelease;
    private String itemField2ForPlannedRelease;
    //Contact
    private String faxNumber;
    //Pricing
    private boolean useLowestPrice;
    private boolean allowStandardPrice;
    private String defaultApprovalGroup;
    private String cmtPackagingCutType;
    private String mtoWarehouse;

    public POPParameters() {
    }

    public boolean isAllowOverReceive() {
        return allowOverReceive;
    }

    public void setAllowOverReceive(boolean allowOverReceive) {
        this.allowOverReceive = allowOverReceive;
    }

    public double getOverPercentage() {
        return overPercentage;
    }

    public void setOverPercentage(double overPercentage) {
        this.overPercentage = overPercentage;
    }

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();

        toBuild.put("allowBOMItemsPurchase", new AllowBOMItemsPurchase());
        toBuild.put("poCommentPrint", new POCommentPrint());
        toBuild.put("defaultApprovalGroup", new ApprovalGroupIdFKNotMandatory());

        return toBuild;
    }

    public boolean getDisplaySupplierItemRef() {
        return displaySupplierItemRef == null ? false : displaySupplierItemRef;
    }

    public void setDisplaySupplierItemRef(boolean displaySupplierItemRef) {
        this.displaySupplierItemRef = displaySupplierItemRef;
    }

    public boolean getSerialMoreThanOne() {
        return serialMoreThanOne == null ? false : serialMoreThanOne;
    }

    public void setSerialMoreThanOne(boolean serialMoreThanOne) {
        this.serialMoreThanOne = serialMoreThanOne;
    }

    public boolean getAllowBOMItemsPurchase() {
        return allowBOMItemsPurchase == null ? false : allowBOMItemsPurchase;
    }

    public void setAllowBOMItemsPurchase(boolean allowBOMItemsPurchase) {
        this.allowBOMItemsPurchase = allowBOMItemsPurchase;
    }

    public boolean getAllowBlanketOrderOverRelease() {
        return allowBlanketOrderOverRelease;
    }

    public void setAllowBlanketOrderOverRelease(boolean allowBlanketOrderOverRelease) {
        this.allowBlanketOrderOverRelease = allowBlanketOrderOverRelease;
    }

    public double getBlanketOrderOverReleasePercentage() {
        return blanketOrderOverReleasePercentage;
    }

    public void setBlanketOrderOverReleasePercentage(double blanketOrderOverReleasePercentage) {
        this.blanketOrderOverReleasePercentage = blanketOrderOverReleasePercentage;
    }

    public String getPoCommentPrint() {
        return poCommentPrint;
    }

    public void setPoCommentPrint(String poCommentPrint) {
        this.poCommentPrint = poCommentPrint;
    }

    public void setPriceReceivedReport(boolean priceReceivedReport) {
        this.priceReceivedReport = priceReceivedReport;
    }

    public void setPriceReturnedReport(boolean priceReturnedReport) {
        this.priceReturnedReport = priceReturnedReport;
    }

    public boolean getPriceReceivedReport() {
        return priceReceivedReport == null ? true : priceReceivedReport;
    }

    public boolean getPriceReturnedReport() {
        return priceReturnedReport == null ? true : priceReturnedReport;
    }

    public String getItemGrpToReadColMast() {
        return itemGrpToReadColMast;
    }

    public void setItemGrpToReadColMast(String itemGrpToReadColMast) {
        this.itemGrpToReadColMast = itemGrpToReadColMast;
    }

    public boolean isPrintDetailedItemDescription() {
        return printDetailedItemDescription;
    }

    public void setPrintDetailedItemDescription(boolean printDetailedItemDescription) {
        this.printDetailedItemDescription = printDetailedItemDescription;
    }

    public String getItemField1ForPlannedRelease() {
        return itemField1ForPlannedRelease;
    }

    public void setItemField1ForPlannedRelease(String itemField1ForPlannedRelease) {
        this.itemField1ForPlannedRelease = itemField1ForPlannedRelease;
    }

    public String getItemField2ForPlannedRelease() {
        return itemField2ForPlannedRelease;
    }

    public void setItemField2ForPlannedRelease(String itemField2ForPlannedRelease) {
        this.itemField2ForPlannedRelease = itemField2ForPlannedRelease;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    public boolean isUseLowestPrice() {
        return useLowestPrice;
    }

    public void setUseLowestPrice(boolean useLowestPrice) {
        this.useLowestPrice = useLowestPrice;
    }

    public boolean isAllowStandardPrice() {
        return allowStandardPrice;
    }

    public void setAllowStandardPrice(boolean allowStandardPrice) {
        this.allowStandardPrice = allowStandardPrice;
    }

    public String getDefaultApprovalGroup() {
        return defaultApprovalGroup;
    }

    public void setDefaultApprovalGroup(String defaultApprovalGroup) {
        this.defaultApprovalGroup = defaultApprovalGroup;
    }

    public String getCmtPackagingCutType() {
        return cmtPackagingCutType;
    }

    public void setCmtPackagingCutType(String cmtPackagingCutType) {
        this.cmtPackagingCutType = cmtPackagingCutType;
    }

    public String getMtoWarehouse() {
        return mtoWarehouse;
    }

    public void setMtoWarehouse(String mtoWarehouse) {
        this.mtoWarehouse = mtoWarehouse;
    }
}
