/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.inventory;

/**
 *
 * @author wikus
 */
import emc.datatypes.base.unitsofmeasure.foreignkeys.UnitOfMeasureFKNotMandatory;
import emc.datatypes.inventory.itemdimensiongroups.foreignkeys.ItemDimensionGroupIdFKNotMandatory;
import emc.datatypes.inventory.itemmaster.PurchaseVATCode;
import emc.datatypes.inventory.parameters.AllowDim1NotOnGroup;
import emc.datatypes.inventory.warehouses.foreignkeys.WarehouseFKNotMandatory;
import emc.framework.EMCEntityClass;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.HashMap;

@Entity
@Table(name = "InventoryParameters", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"companyId"})})
public class InventoryParameters extends EMCEntityClass {

    private boolean allowOtherColoursOnItem;
    private boolean allowDim1NotOnGroup;
    private String defaultWarehouse;
    private String baseUOM;
    private String dimensionGroup;
    private String purchaseVatCode;
    private Double overIssuePercentage;
    private Boolean overIssueAllowed;
    private Double underIssuePercentage;
    private Boolean underIssueAllowed;
    private Boolean minQtyFromItemMaster;
    private Double stockTakeValueDiff;
    private Double stockTakeQuantityDiff;
    private Boolean showOnHanndQty;
    private long requirementsPlanningRecordId;
    private boolean includeBatchOnAgeing;
    private String ageingMessageLogUser;
    private Boolean allowRekimbling;
    //Batch Consolidation
    private BigDecimal consolidationCrateQuantity;
    private BigDecimal consolidationLineCost;
    private BigDecimal maxCrateQuantity;
    private int maxNumberOfCrates;
    private int maxLinesPerJournal;
    private String consolidationTransferDefinition;
    private String consolidationMovementDefinition;
    private String consolidationWarehouse;
    private boolean checkPartialReservedCrates;
    private String batchConsolidationActivityUser;
    private String defaultDeliveryPrintItem;
    private String defaultDeliveryLaminateItem;
    private String defaultDeliveryItem;
    private String defaultDeliveryItemForAccessories;
    private String defaultReminderItem;
    //Batch Consolidation

    public InventoryParameters() {
    }

    public boolean isAllowOtherColoursOnItem() {
        return allowOtherColoursOnItem;
    }

    public void setAllowOtherColoursOnItem(boolean allowOtherColoursOnItem) {
        this.allowOtherColoursOnItem = allowOtherColoursOnItem;
    }

    public String getDefaultWarehouse() {
        return defaultWarehouse;
    }

    public void setDefaultWarehouse(String defaultWarehouse) {
        this.defaultWarehouse = defaultWarehouse;
    }

    public String getBaseUOM() {
        return baseUOM;
    }

    public void setBaseUOM(String baseUOM) {
        this.baseUOM = baseUOM;
    }

    public String getDimensionGroup() {
        return dimensionGroup;
    }

    public void setDimensionGroup(String dimensionGroup) {
        this.dimensionGroup = dimensionGroup;
    }

    public String getPurchaseVatCode() {
        return purchaseVatCode;
    }

    public void setPurchaseVatCode(String purchaseVatCode) {
        this.purchaseVatCode = purchaseVatCode;
    }

    public double getOverIssuePercentage() {
        return overIssuePercentage == null ? 0 : overIssuePercentage;
    }

    public void setOverIssuePercentage(double overIssuePercentage) {
        this.overIssuePercentage = overIssuePercentage;
    }

    public Boolean getOverIssueAllowed() {
        return overIssueAllowed == null ? false : overIssueAllowed;
    }

    public void setOverIssueAllowed(Boolean overIssueAllowed) {
        this.overIssueAllowed = overIssueAllowed;
        if (overIssueAllowed == false) {
            setOverIssuePercentage(0);
        }
    }

    public Boolean getMinQtyFromItemMaster() {
        return minQtyFromItemMaster == null ? true : minQtyFromItemMaster;
    }

    public void setMinQtyFromItemMaster(Boolean minQtyFromItemMaster) {
        this.minQtyFromItemMaster = minQtyFromItemMaster;
    }

    public double getStockTakeQuantityDiff() {
        return stockTakeQuantityDiff == null ? 0d : stockTakeQuantityDiff;
    }

    public void setStockTakeQuantityDiff(double stockTakeQuantityDiff) {
        this.stockTakeQuantityDiff = stockTakeQuantityDiff;
    }

    public double getStockTakeValueDiff() {
        return stockTakeValueDiff == null ? 0d : stockTakeValueDiff;
    }

    public void setStockTakeValueDiff(double stockTakeValueDiff) {
        this.stockTakeValueDiff = stockTakeValueDiff;
    }

    public boolean getShowOnHanndQty() {
        return showOnHanndQty == null ? false : showOnHanndQty;
    }

    public void setShowOnHanndQty(boolean showOnHanndQty) {
        this.showOnHanndQty = showOnHanndQty;
    }

    public boolean isAllowDim1NotOnGroup() {
        return allowDim1NotOnGroup;
    }

    public void setAllowDim1NotOnGroup(boolean allowDim1NotOnGroup) {
        this.allowDim1NotOnGroup = allowDim1NotOnGroup;
    }

    public long getRequirementsPlanningRecordId() {
        return requirementsPlanningRecordId;
    }

    public void setRequirementsPlanningRecordId(long requirementsPlanningRecordId) {
        this.requirementsPlanningRecordId = requirementsPlanningRecordId;
    }

    public double getUnderIssuePercentage() {
        return underIssuePercentage == null ? 0 : underIssuePercentage;
    }

    public void setUnderIssuePercentage(double underIssuePercentage) {
        this.underIssuePercentage = underIssuePercentage;
    }

    public Boolean getUnderIssueAllowed() {
        return underIssueAllowed == null ? false : underIssueAllowed;
    }

    public void setUnderIssueAllowed(Boolean underIssueAllowed) {
        this.underIssueAllowed = underIssueAllowed;
        if (underIssueAllowed == false) {
            setUnderIssuePercentage(0);
        }
    }

    public String getAgeingMessageLogUser() {
        return ageingMessageLogUser;
    }

    public void setAgeingMessageLogUser(String ageingMessageLogUser) {
        this.ageingMessageLogUser = ageingMessageLogUser;
    }

    public boolean isIncludeBatchOnAgeing() {
        return includeBatchOnAgeing;
    }

    public void setIncludeBatchOnAgeing(boolean includeBatchOnAgeing) {
        this.includeBatchOnAgeing = includeBatchOnAgeing;
    }

    public boolean getAllowRekimbling() {
        return allowRekimbling == null ? false : allowRekimbling;
    }

    public void setAllowRekimbling(boolean allowRekimbling) {
        this.allowRekimbling = allowRekimbling;
    }

    public BigDecimal getConsolidationCrateQuantity() {
        return consolidationCrateQuantity;
    }

    public void setConsolidationCrateQuantity(BigDecimal consolidationCrateQuantity) {
        this.consolidationCrateQuantity = consolidationCrateQuantity;
    }

    public BigDecimal getConsolidationLineCost() {
        return consolidationLineCost;
    }

    public void setConsolidationLineCost(BigDecimal consolidationLineCost) {
        this.consolidationLineCost = consolidationLineCost;
    }

    public BigDecimal getMaxCrateQuantity() {
        return maxCrateQuantity;
    }

    public void setMaxCrateQuantity(BigDecimal maxCrateQuantity) {
        this.maxCrateQuantity = maxCrateQuantity;
    }

    public int getMaxNumberOfCrates() {
        return maxNumberOfCrates;
    }

    public void setMaxNumberOfCrates(int maxNumberOfCrates) {
        this.maxNumberOfCrates = maxNumberOfCrates;
    }

    public int getMaxLinesPerJournal() {
        return maxLinesPerJournal;
    }

    public void setMaxLinesPerJournal(int maxLinesPerJournal) {
        this.maxLinesPerJournal = maxLinesPerJournal;
    }

    public String getConsolidationTransferDefinition() {
        return consolidationTransferDefinition;
    }

    public void setConsolidationTransferDefinition(String consolidationTransferDefinition) {
        this.consolidationTransferDefinition = consolidationTransferDefinition;
    }

    public String getConsolidationMovementDefinition() {
        return consolidationMovementDefinition;
    }

    public void setConsolidationMovementDefinition(String consolidationMovementDefinition) {
        this.consolidationMovementDefinition = consolidationMovementDefinition;
    }

    public String getConsolidationWarehouse() {
        return consolidationWarehouse;
    }

    public void setConsolidationWarehouse(String consolidationWarehouse) {
        this.consolidationWarehouse = consolidationWarehouse;
    }

    public boolean isCheckPartialReservedCrates() {
        return checkPartialReservedCrates;
    }

    public void setCheckPartialReservedCrates(boolean checkPartialReservedCrates) {
        this.checkPartialReservedCrates = checkPartialReservedCrates;
    }

    public String getBatchConsolidationActivityUser() {
        return batchConsolidationActivityUser;
    }

    public void setBatchConsolidationActivityUser(String batchConsolidationActivityUser) {
        this.batchConsolidationActivityUser = batchConsolidationActivityUser;
    }

    public String getDefaultDeliveryPrintItem() {
        return defaultDeliveryPrintItem;
    }

    public void setDefaultDeliveryPrintItem(String defaultDeliveryPrintItem) {
        this.defaultDeliveryPrintItem = defaultDeliveryPrintItem;
    }

    public String getDefaultDeliveryLaminateItem() {
        return defaultDeliveryLaminateItem;
    }

    public void setDefaultDeliveryLaminateItem(String defaultDeliveryLaminateItem) {
        this.defaultDeliveryLaminateItem = defaultDeliveryLaminateItem;
    }

    public String getDefaultDeliveryItem() {
        return defaultDeliveryItem;
    }

    public void setDefaultDeliveryItem(String defaultDeliveryItem) {
        this.defaultDeliveryItem = defaultDeliveryItem;
    }

    public String getDefaultDeliveryItemForAccessories() {
        return defaultDeliveryItemForAccessories;
    }

    public void setDefaultDeliveryItemForAccessories(String defaultDeliveryItemForAccessories) {
        this.defaultDeliveryItemForAccessories = defaultDeliveryItemForAccessories;
    }

    public String getDefaultReminderItem() {
        return defaultReminderItem;
    }

    public void setDefaultReminderItem(String defaultReminderItem) {
        this.defaultReminderItem = defaultReminderItem;
    }
    
    

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();
        toBuild.put("defaultWarehouse", new WarehouseFKNotMandatory());
        toBuild.put("baseUOM", new UnitOfMeasureFKNotMandatory());
        toBuild.put("dimensionGroup", new ItemDimensionGroupIdFKNotMandatory());
        toBuild.put("purchaseVatCode", new PurchaseVATCode());
        toBuild.put("allowDim1NotOnGroup", new AllowDim1NotOnGroup());
        
        return toBuild;
    }
}
