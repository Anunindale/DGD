/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.inventory.requirementsplanning;

import emc.inventory.DimensionIDInterfaceOnlyDims;
import emc.inventory.ItemReferenceInterface;

/**
 *
 * @author wikus
 */
public class InventoryRequirementsPlanningHistoryDS extends InventoryRequirementsPlanningHistory implements ItemReferenceInterface, DimensionIDInterfaceOnlyDims {

    private String itemReference;
    private String itemDescription;
    private String dimension1;
    private String dimension2;
    private String dimension3;
    private String salesOrderId;
    private String forecastId;
    private String demandId;
    private String demandType;
    private String plannedWorksOrderId;
    private String plannedPurchaseOrderId;
    private String worksOrdewrId;
    private String purchaseOrderId;
    private String supplyId;
    private String supplyType;

    public InventoryRequirementsPlanningHistoryDS() {
        this.setDataSource(true);
    }

    public String getForecastId() {
        return forecastId;
    }

    public void setForecastId(String forecastId) {
        this.forecastId = forecastId;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemReference() {
        return itemReference;
    }

    public void setItemReference(String itemReference) {
        this.itemReference = itemReference;
    }

    public String getPlannedPurchaseOrderId() {
        return plannedPurchaseOrderId;
    }

    public void setPlannedPurchaseOrderId(String plannedPurchaseOrderId) {
        this.plannedPurchaseOrderId = plannedPurchaseOrderId;
    }

    public String getPlannedWorksOrderId() {
        return plannedWorksOrderId;
    }

    public void setPlannedWorksOrderId(String plannedWorksOrderId) {
        this.plannedWorksOrderId = plannedWorksOrderId;
    }

    public String getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(String purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public String getSalesOrderId() {
        return salesOrderId;
    }

    public void setSalesOrderId(String salesOrderId) {
        this.salesOrderId = salesOrderId;
    }

    public String getWorksOrdewrId() {
        return worksOrdewrId;
    }

    public void setWorksOrdewrId(String worksOrdewrId) {
        this.worksOrdewrId = worksOrdewrId;
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

    public void setDimRecordID(long recordID) {
        super.setDimensionId(recordID);
    }

    public long getDimRecordID() {
        return super.getDimensionId();
    }

    public String getDemandId() {
        return demandId;
    }

    public void setDemandId(String demandId) {
        this.demandId = demandId;
    }

    public String getSupplyId() {
        return supplyId;
    }

    public void setSupplyId(String supplyId) {
        this.supplyId = supplyId;
    }

    public String getDemandType() {
        return demandType;
    }

    public void setDemandType(String demandType) {
        this.demandType = demandType;
    }

    public String getSupplyType() {
        return supplyType;
    }

    public void setSupplyType(String supplyType) {
        this.supplyType = supplyType;
    }
}
