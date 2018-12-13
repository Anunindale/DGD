/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.inventory;

import emc.datatypes.inventory.dimensions.foreignkeys.Dimension1FKNotMandatory;
import emc.datatypes.inventory.dimensions.foreignkeys.Dimension1GroupIdFK;
import emc.datatypes.inventory.dimensions.foreignkeys.Dimension2FKNotMandatory;
import emc.datatypes.inventory.dimensions.foreignkeys.Dimension2GroupIdFK;
import emc.datatypes.inventory.dimensions.foreignkeys.Dimension3FKNotMandatory;
import emc.datatypes.inventory.dimensions.foreignkeys.Dimension3GroupIdFK;
import emc.datatypes.inventory.itemdimensiongroups.foreignkeys.ItemDimensionGroupIdFK;
import emc.datatypes.inventory.itemmaster.AllowSubstitute;
import emc.datatypes.inventory.itemmaster.BrandGroup;
import emc.datatypes.inventory.itemmaster.Classification1;
import emc.datatypes.inventory.itemmaster.Classification2;
import emc.datatypes.inventory.itemmaster.Classification3;
import emc.datatypes.inventory.itemmaster.Classification4;
import emc.datatypes.inventory.itemmaster.Classification5;
import emc.datatypes.inventory.itemmaster.Classification6;
import emc.datatypes.inventory.itemmaster.DefaultWarehouse;
import emc.datatypes.inventory.itemmaster.Description;
import emc.datatypes.inventory.itemmaster.HeightUOM;
import emc.datatypes.inventory.itemmaster.ItemGroupFK;
import emc.datatypes.inventory.itemmaster.ItemId;
import emc.datatypes.inventory.itemmaster.ItemType;
import emc.datatypes.inventory.itemmaster.LengthUOM;
import emc.datatypes.inventory.itemmaster.ProductionUOM;
import emc.datatypes.inventory.itemmaster.PurchaseDiscountGroup;
import emc.datatypes.inventory.itemmaster.PurchaseExtraChargeGroup;
import emc.datatypes.inventory.itemmaster.PurchasePrice;
import emc.datatypes.inventory.itemmaster.PurchasePriceGroup;
import emc.datatypes.inventory.itemmaster.PurchaseSupplier;
import emc.datatypes.inventory.itemmaster.PurchaseUOM;
import emc.datatypes.inventory.itemmaster.PurchaseVATCode;
import emc.datatypes.inventory.itemmaster.ReferenceFK;
import emc.datatypes.inventory.itemmaster.SalesUOM;
import emc.datatypes.inventory.itemmaster.Status;
import emc.datatypes.inventory.itemmaster.UOMBase;
import emc.datatypes.inventory.itemmaster.UOMWidth;
import emc.datatypes.gl.vatcodes.foreignkeys.VATCodeFK;
import emc.datatypes.gl.vatcodes.foreignkeys.VATCodeFKNotMandatory;
import emc.datatypes.inventory.InventoryScrap;
import emc.datatypes.inventory.costinggroups.foreignkeys.CostingGroupIdFK;
import emc.datatypes.inventory.itemmaster.OrderPolicy;
import emc.datatypes.inventory.itemmaster.WebStoreType;
import emc.datatypes.inventory.itemmaster.WeightUOM;
import emc.datatypes.inventory.itemrange.foreignkey.ItemRangeFK;
import emc.datatypes.inventory.productiongroup.foreignkeys.ProductGroupIdFKNM;
import emc.datatypes.systemwide.DetailedDescription;
import emc.enums.inventory.purchasing.InventoryStopPurchasingType;
import emc.enums.trec.TRECWebStoreTypeEnum;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import emc.tables.EMCCloneParameters;
import emc.tables.EMCFieldGroup;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
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
 * @author riaan
 */
@Entity
@Table(name = "InventoryItemMaster", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"itemId", "companyId"})})
public class InventoryItemMaster extends EMCEntityClass implements Serializable {
    //Overview

    private String itemId;
    private String description;
    private String brandGroup;
    private String itemRange;
    private String itemCategory;
    private String itemType;
    private String dimensionGroup;
    //Definition
    private String status = "Active";
    @Column(length = 1000)
    private String detailedDescription;
    private String baseUOM;
    private String defaultWarehouse;
    //New columns
    private String dimension1Group;
    private String dimension2Group;
    private String dimension3Group;
    private String itemGroup;
    private String itemReference;
    //Purchase
    private double purchasePrice;
    @Temporal(TemporalType.DATE)
    private Date purchasePriceDate;
    private String purchasePer;
    private String purchaseVatCode;
    private String purchasePriceGroup;
    private String purchaseDiscountGroup;
    private String purchaseExtraChargeGroup;
    private String purchaseDefaultSupplier;
    private double purchaseLeadTime;
    private String purchaseUOM;
    private double purchaseMinOrderQty;
    @Temporal(TemporalType.DATE)
    private Date lastPurchaseDate;
    private String lastPurchaseSupplier;
    private double lastPurchasePrice;
    private String lastPurchasePer;
    private long lastPurchaseDDate;
    private String stopPurchase = InventoryStopPurchasingType.NO.toString();
    private String overseePurchaseGroup;
    //Sales
    private double salesSellingPrice;
    private int sellingPer;
    private String sellingVatCode;
    private String sellingPriceGroup;
    private String sellingDiscountGroup;
    private String sellingExtraChargeGroup;
    @Temporal(TemporalType.DATE)
    private Date sellingPriceUpdateDate;
    private String sellingUOM;
    private double sellingMinOrderQty;
    private String sellingCommissionGroup;
    private String sellingRoyaltyGroup;
    private boolean customerRestricted;
    private boolean stopSelling;
    //Costing
    private String currentCostLink;
    private String prevCostLink;
    private double costingCurrentCost;
    @Temporal(TemporalType.DATE)
    private Date costingCostDate;
    private double costingPrevCost;
    @Temporal(TemporalType.DATE)
    private Date costingPrevCostDate;
    private double costingWeightedAve;
    private String costingGroup;
    //Specification
    private String specBatchGroup;
    private String specWarrantyyExpiryGroup;
    private String specBarCodeGroup;
    private double specWidth;
    private double specLength;
    private double specHeight;
    private double specWeight;
    private String specWidthUOM;
    private String specLengthUOM;
    private String specHeightUOM;
    private String specWeightUOM;
    //Planning
    private String planningPlanning;
    private boolean planningAllowSubstitute;
    private String planningSubstituteRule;
    private String planningSubstituteItem;
    private String planningSubstituteDimension1;
    private String planningSubstituteDimension2;
    private String planningSubstituteDimension3;
    //Classification
    private String classificationClassGroup1;
    private String classificationClassGroup2;
    private String classificationClassGroup3;
    private String classificationClassGroup4;
    private String classificationClassGroup5;
    private String classificationClassGroup6;
    //Production
    private String productionBOMId;
    private String productionRoutingId;
    private double productionStdCost;
    @Temporal(TemporalType.DATE)
    private Date productionStdCostDate;
    private double productionLeadTime;
    private String productionUOM;
    private String productionMinProdQty;
    private String productionDrawingPattern;
    private String productionProdRef1;
    private String productionProdRef2;
    private String productionPhantom;
    private String productionBinItem;
    private boolean stopProduction;
    private double scrap;
    private String workCenterPlanningGroup;
    //Min/Max
    private double minOrderQty;
    private double maxOrderQty;
    private double multOrderQty;
    @Temporal(TemporalType.DATE)
    private Date lastCountedDate;
    //Yea
    private String productGroup;
    private String mpsFlag;
    private boolean allowKimbling;
    private String orderPolicy;
    private BigDecimal safetyStock = BigDecimal.ZERO;
    private BigDecimal economicOrderQuantity = BigDecimal.ZERO;
    private String warrantyGroup;
    //webstore
    private String webStoreType = TRECWebStoreTypeEnum.NONE.toString();

    /**
     * Creates a new instance of InventoryItemMaster
     */
    public InventoryItemMaster() {
        this.setEmcLabel("Inventory Item Master");
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrandGroup() {
        return brandGroup;
    }

    public Date getLastCountedDate() {
        return lastCountedDate;
    }

    public void setLastCountedDate(Date lastCountedDate) {
        this.lastCountedDate = lastCountedDate;
    }

    public void setBrandGroup(String brandGroup) {
        this.brandGroup = brandGroup;
    }

    public String getItemRange() {
        return itemRange;
    }

    public void setItemRange(String itemRange) {
        this.itemRange = itemRange;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDetailedDescription() {
        return detailedDescription;
    }

    public void setDetailedDescription(String detailedDescription) {
        this.detailedDescription = detailedDescription;
    }

    public String getBaseUOM() {
        return baseUOM;
    }

    public void setBaseUOM(String baseUOM) {
        this.baseUOM = baseUOM;
    }

    public String getDefaultWarehouse() {
        return defaultWarehouse;
    }

    public void setDefaultWarehouse(String defaultWarehouse) {
        this.defaultWarehouse = defaultWarehouse;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public String getPurchasePer() {
        return purchasePer;
    }

    public void setPurchasePer(String purchasePer) {
        this.purchasePer = purchasePer;
    }

    public String getPurchaseVatCode() {
        return purchaseVatCode;
    }

    public void setPurchaseVatCode(String purchaseVatCode) {
        this.purchaseVatCode = purchaseVatCode;
    }

    public String getPurchasePriceGroup() {
        return purchasePriceGroup;
    }

    public void setPurchasePriceGroup(String purchasePriceGroup) {
        this.purchasePriceGroup = purchasePriceGroup;
    }

    public String getPurchaseDiscountGroup() {
        return purchaseDiscountGroup;
    }

    public void setPurchaseDiscountGroup(String purchaseDiscountGroup) {
        this.purchaseDiscountGroup = purchaseDiscountGroup;
    }

    public String getPurchaseExtraChargeGroup() {
        return purchaseExtraChargeGroup;
    }

    public void setPurchaseExtraChargeGroup(String purchaseExtraChargeGroup) {
        this.purchaseExtraChargeGroup = purchaseExtraChargeGroup;
    }

    public double getPurchaseLeadTime() {
        return purchaseLeadTime;
    }

    public void setPurchaseLeadTime(double purchaseLeadTime) {
        this.purchaseLeadTime = purchaseLeadTime;
    }

    public String getPurchaseDefaultSupplier() {
        return purchaseDefaultSupplier;
    }

    public void setPurchaseDefaultSupplier(String purchaseDefaultSupplier) {
        this.purchaseDefaultSupplier = purchaseDefaultSupplier;
    }

    public String getPurchaseUOM() {
        return purchaseUOM;
    }

    public void setPurchaseUOM(String purchaseUOM) {
        this.purchaseUOM = purchaseUOM;
    }

    public double getPurchaseMinOrderQty() {
        return purchaseMinOrderQty;
    }

    public void setPurchaseMinOrderQty(double purchaseMinOrderQty) {
        this.purchaseMinOrderQty = purchaseMinOrderQty;
    }

    public Date getLastPurchaseDate() {
        return lastPurchaseDate;
    }

    public void setLastPurchaseDate(Date lastPurchaseDate) {
        this.lastPurchaseDate = lastPurchaseDate;
    }

    public String getLastPurchaseSupplier() {
        return lastPurchaseSupplier;
    }

    public void setLastPurchaseSupplier(String lastPurchaseSupplier) {
        this.lastPurchaseSupplier = lastPurchaseSupplier;
    }

    public double getLastPurchasePrice() {
        return lastPurchasePrice;
    }

    public void setLastPurchasePrice(double lastPurchasePrice) {
        this.lastPurchasePrice = lastPurchasePrice;
    }

    public String getLastPurchasePer() {
        return lastPurchasePer;
    }

    public void setLastPurchasePer(String lastPurchasePer) {
        this.lastPurchasePer = lastPurchasePer;
    }

    public long getLastPurchaseDDate() {
        return lastPurchaseDDate;
    }

    public void setLastPurchaseDDate(long lastPurchaseDDate) {
        this.lastPurchaseDDate = lastPurchaseDDate;
    }

    public Date getPurchasePriceDate() {
        return purchasePriceDate;
    }

    public void setPurchasePriceDate(Date purchasePriceDate) {
        this.purchasePriceDate = purchasePriceDate;
    }

    public String getOverseePurchaseGroup() {
        return overseePurchaseGroup;
    }

    public void setOverseePurchaseGroup(String overseePurchaseGroup) {
        this.overseePurchaseGroup = overseePurchaseGroup;
    }

    public String getStopPurchase() {
        return stopPurchase;
    }

    public void setStopPurchase(String stopPurchase) {
        this.stopPurchase = stopPurchase;
    }

    public double getSalesSellingPrice() {
        return salesSellingPrice;
    }

    public void setSalesSellingPrice(double salesSellingPrice) {
        this.salesSellingPrice = salesSellingPrice;
    }

    public int getSellingPer() {
        return sellingPer;
    }

    public void setSellingPer(int sellingPer) {
        this.sellingPer = sellingPer;
    }

    public String getSellingVatCode() {
        return sellingVatCode;
    }

    public void setSellingVatCode(String sellingVatCode) {
        this.sellingVatCode = sellingVatCode;
    }

    public String getSellingPriceGroup() {
        return sellingPriceGroup;
    }

    public void setSellingPriceGroup(String sellingPriceGroup) {
        this.sellingPriceGroup = sellingPriceGroup;
    }

    public String getSellingDiscountGroup() {
        return sellingDiscountGroup;
    }

    public void setSellingDiscountGroup(String sellingDiscountGroup) {
        this.sellingDiscountGroup = sellingDiscountGroup;
    }

    public String getSellingExtraChargeGroup() {
        return sellingExtraChargeGroup;
    }

    public void setSellingExtraChargeGroup(String sellingExtraChargeGroup) {
        this.sellingExtraChargeGroup = sellingExtraChargeGroup;
    }

    public Date getSellingPriceUpdateDate() {
        return sellingPriceUpdateDate;
    }

    public void setSellingPriceUpdateDate(Date sellingPriceUpdateDate) {
        this.sellingPriceUpdateDate = sellingPriceUpdateDate;
    }

    public String getSellingUOM() {
        return sellingUOM;
    }

    public void setSellingUOM(String sellingUOM) {
        this.sellingUOM = sellingUOM;
    }

    public double getSellingMinOrderQty() {
        return sellingMinOrderQty;
    }

    public void setSellingMinOrderQty(double sellingMinOrderQty) {
        this.sellingMinOrderQty = sellingMinOrderQty;
    }

    public String getSellingCommissionGroup() {
        return sellingCommissionGroup;
    }

    public void setSellingCommissionGroup(String sellingCommissionGroup) {
        this.sellingCommissionGroup = sellingCommissionGroup;
    }

    public String getSellingRoyaltyGroup() {
        return sellingRoyaltyGroup;
    }

    public void setSellingRoyaltyGroup(String sellingRoyaltyGroup) {
        this.sellingRoyaltyGroup = sellingRoyaltyGroup;
    }

    public boolean getCustomerRestricted() {
        return customerRestricted;
    }

    public void setCustomerRestricted(boolean customerRestricted) {
        this.customerRestricted = customerRestricted;
    }

    public boolean getStopSelling() {
        return stopSelling;
    }

    public void setStopSelling(boolean stopSelling) {
        this.stopSelling = stopSelling;
    }

    public double getCostingCurrentCost() {
        return this.costingCurrentCost;
    }

    public void setCostingCurrentCost(double costingCurrentCost) {
        this.costingCurrentCost = costingCurrentCost;
    }

    public Date getCostingCostDate() {
        return costingCostDate;
    }

    public void setCostingCostDate(Date costingCostDate) {
        this.costingCostDate = costingCostDate;
    }

    public double getCostingPrevCost() {
        return costingPrevCost;
    }

    public void setCostingPrevCost(double costingPrevCost) {
        this.costingPrevCost = costingPrevCost;
    }

    public Date getCostingPrevCostDate() {
        return costingPrevCostDate;
    }

    public void setCostingPrevCostDate(Date costingPrevCostDate) {
        this.costingPrevCostDate = costingPrevCostDate;
    }

    public double getCostingWeightedAve() {
        return costingWeightedAve;
    }

    public void setCostingWeightedAve(double costingWeightedAve) {
        this.costingWeightedAve = costingWeightedAve;
    }

    public String getCostingGroup() {
        return costingGroup;
    }

    public void setCostingGroup(String costingGroup) {
        this.costingGroup = costingGroup;
    }

    public String getSpecBatchGroup() {
        return specBatchGroup;
    }

    public void setSpecBatchGroup(String specBatchGroup) {
        this.specBatchGroup = specBatchGroup;
    }

    public String getSpecWarrantyyExpiryGroup() {
        return specWarrantyyExpiryGroup;
    }

    public void setSpecWarrantyyExpiryGroup(String specWarrantyyExpiryGroup) {
        this.specWarrantyyExpiryGroup = specWarrantyyExpiryGroup;
    }

    public String getSpecBarCodeGroup() {
        return specBarCodeGroup;
    }

    public void setSpecBarCodeGroup(String specBarCodeGroup) {
        this.specBarCodeGroup = specBarCodeGroup;
    }

    public double getSpecWidth() {
        return specWidth;
    }

    public void setSpecWidth(double specWidth) {
        this.specWidth = specWidth;
    }

    public double getSpecHeight() {
        return specHeight;
    }

    public void setSpecHeight(double specHeight) {
        this.specHeight = specHeight;
    }

    public double getSpecWeight() {
        return specWeight;
    }

    public void setSpecWeight(double specWeight) {
        this.specWeight = specWeight;
    }

    public double getSpecLength() {
        return specLength;
    }

    public void setSpecLength(double specLength) {
        this.specLength = specLength;
    }

    public String getSpecWidthUOM() {
        return specWidthUOM;
    }

    public void setSpecWidthUOM(String specWidthUOM) {
        this.specWidthUOM = specWidthUOM;
    }

    public String getSpecLengthUOM() {
        return specLengthUOM;
    }

    public void setSpecLengthUOM(String specLengthUOM) {
        this.specLengthUOM = specLengthUOM;
    }

    public String getSpecWeightUOM() {
        return specWeightUOM;
    }

    public void setSpecWeightUOM(String specWeightUOM) {
        this.specWeightUOM = specWeightUOM;
    }

    public String getSpecHeightUOM() {
        return specHeightUOM;
    }

    public void setSpecHeightUOM(String specHeightUOM) {
        this.specHeightUOM = specHeightUOM;
    }

    public String getPlanningPlanning() {
        return planningPlanning;
    }

    public void setPlanningPlanning(String planningPlanning) {
        this.planningPlanning = planningPlanning;
    }

    public boolean getPlanningAllowSubstitute() {
        return planningAllowSubstitute;
    }

    public void setPlanningAllowSubstitute(boolean planningAllowSubstitute) {
        this.planningAllowSubstitute = planningAllowSubstitute;
    }

    public String getPlanningSubstituteRule() {
        return planningSubstituteRule;
    }

    public void setPlanningSubstituteRule(String planningSubstituteRule) {
        this.planningSubstituteRule = planningSubstituteRule;
    }

    public String getPlanningSubstituteItem() {
        return planningSubstituteItem;
    }

    public void setPlanningSubstituteItem(String planningSubstituteItem) {
        this.planningSubstituteItem = planningSubstituteItem;
    }

    public String getClassificationClassGroup1() {
        return classificationClassGroup1;
    }

    public void setClassificationClassGroup1(String classificationClassGroup1) {
        this.classificationClassGroup1 = classificationClassGroup1;
    }

    public String getClassificationClassGroup2() {
        return classificationClassGroup2;
    }

    public void setClassificationClassGroup2(String classificationClassGroup2) {
        this.classificationClassGroup2 = classificationClassGroup2;
    }

    public String getClassificationClassGroup3() {
        return classificationClassGroup3;
    }

    public void setClassificationClassGroup3(String classificationClassGroup3) {
        this.classificationClassGroup3 = classificationClassGroup3;
    }

    public String getClassificationClassGroup4() {
        return classificationClassGroup4;
    }

    public void setClassificationClassGroup4(String classificationClassGroup4) {
        this.classificationClassGroup4 = classificationClassGroup4;
    }

    public String getClassificationClassGroup6() {
        return classificationClassGroup6;
    }

    public void setClassificationClassGroup6(String classificationClassGroup6) {
        this.classificationClassGroup6 = classificationClassGroup6;
    }

    public String getProductionBOMId() {
        return productionBOMId;
    }

    public void setProductionBOMId(String productionBOMId) {
        this.productionBOMId = productionBOMId;
    }

    public String getClassificationClassGroup5() {
        return classificationClassGroup5;
    }

    public void setClassificationClassGroup5(String classificationClassGroup5) {
        this.classificationClassGroup5 = classificationClassGroup5;
    }

    public String getProductionRoutingId() {
        return productionRoutingId;
    }

    public void setProductionRoutingId(String productionRoutingId) {
        this.productionRoutingId = productionRoutingId;
    }

    public double getProductionStdCost() {
        return productionStdCost;
    }

    public void setProductionStdCost(double productionStdCost) {
        this.productionStdCost = productionStdCost;
    }

    public Date getProductionStdCostDate() {
        return productionStdCostDate;
    }

    public void setProductionStdCostDate(Date productionStdCostDate) {
        this.productionStdCostDate = productionStdCostDate;
    }

    public double getProductionLeadTime() {
        return productionLeadTime;
    }

    public void setProductionLeadTime(double productionLeadTime) {
        this.productionLeadTime = productionLeadTime;
    }

    public String getProductionUOM() {
        return productionUOM;
    }

    public void setProductionUOM(String productionUOM) {
        this.productionUOM = productionUOM;
    }

    public String getProductionMinProdQty() {
        return productionMinProdQty;
    }

    public void setProductionMinProdQty(String productionMinProdQty) {
        this.productionMinProdQty = productionMinProdQty;
    }

    public String getProductionDrawingPattern() {
        return productionDrawingPattern;
    }

    public void setProductionDrawingPattern(String productionDrawingPattern) {
        this.productionDrawingPattern = productionDrawingPattern;
    }

    public String getProductionProdRef1() {
        return productionProdRef1;
    }

    public void setProductionProdRef1(String productionProdRef1) {
        this.productionProdRef1 = productionProdRef1;
    }

    public String getProductionPhantom() {
        return productionPhantom;
    }

    public void setProductionPhantom(String productionPhantom) {
        this.productionPhantom = productionPhantom;
    }

    public String getProductionProdRef2() {
        return productionProdRef2;
    }

    public void setProductionProdRef2(String productionProdRef2) {
        this.productionProdRef2 = productionProdRef2;
    }

    public String getProductionBinItem() {
        return productionBinItem;
    }

    public void setProductionBinItem(String productionBinItem) {
        this.productionBinItem = productionBinItem;
    }

    public boolean getStopProduction() {
        return stopProduction;
    }

    public void setStopProduction(boolean stopProduction) {
        this.stopProduction = stopProduction;
    }

    public String getDimensionGroup() {
        return dimensionGroup;
    }

    public void setDimensionGroup(String dimensionGroup) {
        this.dimensionGroup = dimensionGroup;
    }

    public String getWebStoreType() {
        return webStoreType;
    }

    public void setWebStoreType(String webStoreType) {
        this.webStoreType = webStoreType;
    }
    
    

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();
        toBuild.put("brandGroup", new BrandGroup());
        toBuild.put("itemId", new ItemId());
        toBuild.put("description", new Description());
        toBuild.put("detailedDescription", new DetailedDescription());
        toBuild.put("dimensionGroup", new ItemDimensionGroupIdFK());
        toBuild.put("itemRange", new ItemRangeFK());

        toBuild.put("costingGroup", new CostingGroupIdFK());

        toBuild.put("baseUOM", new UOMBase());
        toBuild.put("productionUOM", new ProductionUOM());
        toBuild.put("purchaseUOM", new PurchaseUOM());
        toBuild.put("sellingUOM", new SalesUOM());
        toBuild.put("specHeightUOM", new HeightUOM());
        toBuild.put("specWeightUOM", new WeightUOM());
        toBuild.put("specLengthUOM", new LengthUOM());
        toBuild.put("specWidthUOM", new UOMWidth());

        VATCodeFK vatCode = new VATCodeFKNotMandatory();
        toBuild.put("purchaseVatCode", new PurchaseVATCode());
        toBuild.put("sellingVatCode", vatCode);

        toBuild.put("purchasePriceGroup", new PurchasePriceGroup());
        toBuild.put("purchaseDiscountGroup", new PurchaseDiscountGroup());
        toBuild.put("purchaseExtraChargeGroup", new PurchaseExtraChargeGroup());
        toBuild.put("purchaseDefaultSupplier", new PurchaseSupplier());
        toBuild.put("defaultWarehouse", new DefaultWarehouse());

        toBuild.put("dimension1Group", new Dimension1GroupIdFK());
        toBuild.put("dimension2Group", new Dimension2GroupIdFK());
        toBuild.put("dimension3Group", new Dimension3GroupIdFK());

        toBuild.put("planningSubstituteDimension1", new Dimension1FKNotMandatory());
        toBuild.put("planningSubstituteDimension2", new Dimension2FKNotMandatory());
        toBuild.put("planningSubstituteDimension3", new Dimension3FKNotMandatory());

        toBuild.put("planningAllowSubstitute", new AllowSubstitute());

        toBuild.put("itemGroup", new ItemGroupFK());
        toBuild.put("itemReference", new ReferenceFK());
        toBuild.put("classificationClassGroup1", new Classification1());
        toBuild.put("classificationClassGroup2", new Classification2());
        toBuild.put("classificationClassGroup3", new Classification3());
        toBuild.put("classificationClassGroup4", new Classification4());
        toBuild.put("classificationClassGroup5", new Classification5());
        toBuild.put("classificationClassGroup6", new Classification6());
        toBuild.put("status", new Status());
        toBuild.put("itemType", new ItemType());
        toBuild.put("purchasePrice", new PurchasePrice());
        toBuild.put("productGroup", new ProductGroupIdFKNM());
        toBuild.put("scrap", new InventoryScrap());
        toBuild.put("orderPolicy", new OrderPolicy());
        toBuild.put("webStoreType", new WebStoreType());

        return toBuild;
    }

    @Override
    public EMCQuery buildQuery() {
        EMCQuery q = super.buildQuery();
        q.addOrderBy("itemReference");

        return q;
    }

    public String getDimension2Group() {
        return dimension2Group;
    }

    public void setDimension2Group(String dimension2Group) {
        this.dimension2Group = dimension2Group;
    }

    public String getDimension3Group() {
        return dimension3Group;
    }

    public void setDimension3Group(String dimension3Group) {
        this.dimension3Group = dimension3Group;
    }

    public String getDimension1Group() {
        return dimension1Group;
    }

    public void setDimension1Group(String dimension1Group) {
        this.dimension1Group = dimension1Group;
    }

    public String getItemGroup() {
        return itemGroup;
    }

    public void setItemGroup(String itemGroup) {
        this.itemGroup = itemGroup;
    }

    public String getItemReference() {
        return itemReference;
    }

    public void setItemReference(String itemReference) {
        this.itemReference = itemReference;
    }

    public String getPlanningSubstituteDimension1() {
        return planningSubstituteDimension1;
    }

    public void setPlanningSubstituteDimension1(String planningSubstituteDimension1) {
        this.planningSubstituteDimension1 = planningSubstituteDimension1;
    }

    public String getPlanningSubstituteDimension2() {
        return planningSubstituteDimension2;
    }

    public void setPlanningSubstituteDimension2(String planningSubstituteDimension2) {
        this.planningSubstituteDimension2 = planningSubstituteDimension2;
    }

    public String getPlanningSubstituteDimension3() {
        return planningSubstituteDimension3;
    }

    public void setPlanningSubstituteDimension3(String planningSubstituteDimension3) {
        this.planningSubstituteDimension3 = planningSubstituteDimension3;
    }

    public double getScrap() {
        return this.scrap;
    }

    public void setScrap(double scrap) {
        this.scrap = scrap;
    }

    public String getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(String productGroup) {
        this.productGroup = productGroup;
    }

    public String getMpsFlag() {
        return mpsFlag;
    }

    public void setMpsFlag(String mpsFlag) {
        this.mpsFlag = mpsFlag;
    }

    public boolean isAllowKimbling() {
        return allowKimbling;
    }

    public void setAllowKimbling(boolean allowKimbling) {
        this.allowKimbling = allowKimbling;
    }

    @Override
    public HashMap<String, EMCFieldGroup> buildFieldGroupMap() {
        HashMap<String, EMCFieldGroup> fieldGroup = super.buildFieldGroupMap();

        EMCFieldGroup classifications = new EMCFieldGroup();
        classifications.add("classificationClassGroup1");
        classifications.add("classificationClassGroup2");
        classifications.add("classificationClassGroup3");
        classifications.add("classificationClassGroup4");
        classifications.add("classificationClassGroup5");
        classifications.add("classificationClassGroup6");

        fieldGroup.put("Classifications", classifications);

        return fieldGroup;
    }

    @Override
    public List<String> buildDefaultLookupFieldList() {
        List<String> defaultLookupFieldKeys = new ArrayList<String>();

        defaultLookupFieldKeys.add("itemId");
        defaultLookupFieldKeys.add("itemReference");
        defaultLookupFieldKeys.add("description");

        return defaultLookupFieldKeys;
    }

    @Override
    public EMCCloneParameters buildCloneParameters() {
        EMCCloneParameters cloneParm = super.buildCloneParameters();
        cloneParm.setPrimaryKey("itemId");
        cloneParm.addFieldsToClear("itemReference");
        return cloneParm;
    }

    /**
     * @return the workCenterPlanningGroup
     */
    public String getWorkCenterPlanningGroup() {
        return workCenterPlanningGroup;
    }

    /**
     * @param workCenterPlanningGroup the workCenterPlanningGroup to set
     */
    public void setWorkCenterPlanningGroup(String workCenterPlanningGroup) {
        this.workCenterPlanningGroup = workCenterPlanningGroup;
    }

    public String getOrderPolicy() {
        return orderPolicy;
    }

    public void setOrderPolicy(String orderPolicy) {
        this.orderPolicy = orderPolicy;
    }

    public BigDecimal getSafetyStock() {
        return safetyStock;
    }

    public void setSafetyStock(BigDecimal safetyStock) {
        this.safetyStock = safetyStock;
    }

    public BigDecimal getEconomicOrderQuantity() {
        return economicOrderQuantity;
    }

    public void setEconomicOrderQuantity(BigDecimal economicOrderQuantity) {
        this.economicOrderQuantity = economicOrderQuantity;
    }

    public double getMinOrderQty() {
        return minOrderQty;
    }

    public void setMinOrderQty(double minOrderQty) {
        this.minOrderQty = minOrderQty;
    }

    public double getMaxOrderQty() {
        return maxOrderQty;
    }

    public void setMaxOrderQty(double maxOrderQty) {
        this.maxOrderQty = maxOrderQty;
    }

    public double getMultOrderQty() {
        return multOrderQty;
    }

    public void setMultOrderQty(double multOrderQty) {
        this.multOrderQty = multOrderQty;
    }

    public String getWarrantyGroup() {
        return warrantyGroup;
    }

    public void setWarrantyGroup(String warrantyGroup) {
        this.warrantyGroup = warrantyGroup;
    }

    public String getCurrentCostLink() {
        return currentCostLink;
    }

    public void setCurrentCostLink(String currentCostLink) {
        this.currentCostLink = currentCostLink;
    }

    public String getPrevCostLink() {
        return prevCostLink;
    }

    public void setPrevCostLink(String prevCostLink) {
        this.prevCostLink = prevCostLink;
    }
}
