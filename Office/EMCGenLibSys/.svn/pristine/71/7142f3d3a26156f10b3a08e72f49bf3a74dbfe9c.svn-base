/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.inventory.dimensions;

import emc.datatypes.inventory.dimensions.foreignkeys.Dimension1FKBean;
import emc.datatypes.inventory.dimensions.foreignkeys.Dimension2FKBean;
import emc.datatypes.inventory.dimensions.foreignkeys.Dimension3FKBean;
import emc.datatypes.inventory.itemdimensioncombinations.Active;
import emc.datatypes.inventory.itemdimensioncombinations.CostPrice;
import emc.datatypes.inventory.itemdimensioncombinations.OrderPolicy;
import emc.datatypes.inventory.itemdimensioncombinations.PurchaseLeadTime;
import emc.datatypes.inventory.itemdimensioncombinations.SellingPrice;
import emc.datatypes.inventory.itemdimensioncombinations.ShowOnWeb;
import emc.datatypes.inventory.itemmaster.foreignkeys.ItemIdFK;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.purchasing.InventoryStopPurchasingType;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
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
 * @author riaan
 */
@Entity
@Table(name = "InventoryItemDimensionCombinations", uniqueConstraints = {@UniqueConstraint(columnNames = {"itemId", "dimensionCombination", "companyId"})})
public class InventoryItemDimensionCombinations extends EMCEntityClass {

    private String itemId;
    private String dimension1Id;
    private String dimension2Id;
    private String dimension3Id;
    //costing and pricing
    private Double costPrice = 0d;
    @Temporal(TemporalType.DATE)
    private Date currentCostDate;
    private double prevCostPrice;
    @Temporal(TemporalType.DATE)
    private Date prevCostDate;
    private Double sellingPrice = 0d;
    //Workaround for not being able to add 5 columns in unique constraint
    private String dimensionCombination;
    private boolean active = true;
    private boolean deactivatedByDimension1 = false;
    //Min/Max
    private Double lastPurchasePrice;
    @Temporal(TemporalType.DATE)
    private Date lastPurchaseDate;
    private Double purchasePrice;
    @Temporal(TemporalType.DATE)
    private Date purchasePriceDate;
    @Temporal(TemporalType.DATE)
    private Date lastCountedDate;
    private BigDecimal productionLeadTime = BigDecimal.ZERO;
    private BigDecimal purchaseLeadTime = BigDecimal.ZERO;
    private String orderPolicy;
    private BigDecimal safetyStock = BigDecimal.ZERO;
    private BigDecimal economicOrderQuantity = BigDecimal.ZERO;
    private BigDecimal minOrderQty = BigDecimal.ZERO;
    private BigDecimal maxOrderQty = BigDecimal.ZERO;
    private BigDecimal multOrderQty = BigDecimal.ZERO;
    private String stopPurchase = InventoryStopPurchasingType.NO.toString();
    private String overseePurchaseGroup;
    private boolean showOnWeb = false;

    /** Creates a new instance of InventoryItemDimensionCombinations */
    public InventoryItemDimensionCombinations() {
    }

    public Date getLastCountedDate() {
        return lastCountedDate;
    }

    public void setLastCountedDate(Date lastCountedDate) {
        this.lastCountedDate = lastCountedDate;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getDimension1Id() {
        return dimension1Id;
    }

    public void setDimension1Id(String dimension1Id) {
        this.dimension1Id = dimension1Id;
    }

    public String getDimension2Id() {
        return dimension2Id;
    }

    public void setDimension2Id(String dimension2Id) {
        this.dimension2Id = dimension2Id;
    }

    public String getDimension3Id() {
        return dimension3Id;
    }

    public void setDimension3Id(String dimension3Id) {
        this.dimension3Id = dimension3Id;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getDimensionCombination() {
        return dimensionCombination;
    }

    public void setDimensionCombination(String dimensionCombination) {
        this.dimensionCombination = dimensionCombination;
    }

    public Double getLastPurchasePrice() {
        return lastPurchasePrice == null ? 0d : lastPurchasePrice;
    }

    public void setLastPurchasePrice(Double lastPurchasePrice) {
        this.lastPurchasePrice = lastPurchasePrice;
    }

    public Double getPurchasePrice() {
        return purchasePrice == null ? 0d : purchasePrice;
    }

    public void setPurchasePrice(Double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public boolean isShowOnWeb() {
        return showOnWeb;
    }

    public void setShowOnWeb(boolean showOnWeb) {
        this.showOnWeb = showOnWeb;
    }
    
    

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();

        toBuild.put("itemId", new ItemIdFK());
        toBuild.put("dimension1Id", new Dimension1FKBean());
        toBuild.put("dimension2Id", new Dimension2FKBean());
        toBuild.put("dimension3Id", new Dimension3FKBean());
        toBuild.put("active", new Active());
        toBuild.put("sellingPrice", new SellingPrice());
        toBuild.put("costPrice", new CostPrice());
        toBuild.put("purchaseLeadTime", new PurchaseLeadTime());
        toBuild.put("orderPolicy", new OrderPolicy());
        toBuild.put("showOnWeb", new ShowOnWeb());

        return toBuild;
    }

    public double getCostPrice() {
        return costPrice == null ? 0 : costPrice;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }

    public double getSellingPrice() {
        return sellingPrice == null ? 0 : sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    @Override
    public EMCQuery buildQuery() {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimensionCombinations.class.getName());
        query.addAnd("itemId", "");
        return query;
    }

    /**
     * @return the currentCostDate
     */
    public Date getCurrentCostDate() {
        return currentCostDate;
    }

    /**
     * @param currentCostDate the currentCostDate to set
     */
    public void setCurrentCostDate(Date currentCostDate) {
        this.currentCostDate = currentCostDate;
    }

    /**
     * @return the prevCostPrice
     */
    public double getPrevCostPrice() {
        return prevCostPrice;
    }

    /**
     * @param prevCostPrice the prevCostPrice to set
     */
    public void setPrevCostPrice(double prevCostPrice) {
        this.prevCostPrice = prevCostPrice;
    }

    /**
     * @return the prevCostDate
     */
    public Date getPrevCostDate() {
        return prevCostDate;
    }

    /**
     * @param prevCostDate the prevCostDate to set
     */
    public void setPrevCostDate(Date prevCostDate) {
        this.prevCostDate = prevCostDate;
    }

    public Date getLastPurchaseDate() {
        return lastPurchaseDate;
    }

    public void setLastPurchaseDate(Date lastPurchaseDate) {
        this.lastPurchaseDate = lastPurchaseDate;
    }

    public boolean isDeactivatedByDimension1() {
        return deactivatedByDimension1;
    }

    public void setDeactivatedByDimension1(boolean deactivatedByDimension1) {
        this.deactivatedByDimension1 = deactivatedByDimension1;
    }

    public BigDecimal getProductionLeadTime() {
        return productionLeadTime;
    }

    public void setProductionLeadTime(BigDecimal productionLeadTime) {
        this.productionLeadTime = productionLeadTime;
    }

    public BigDecimal getPurchaseLeadTime() {
        return purchaseLeadTime;
    }

    public void setPurchaseLeadTime(BigDecimal purchaseLeadTime) {
        this.purchaseLeadTime = purchaseLeadTime;
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

    public BigDecimal getMinOrderQty() {
        return minOrderQty;
    }

    public void setMinOrderQty(BigDecimal minOrderQty) {
        this.minOrderQty = minOrderQty;
    }

    public BigDecimal getMaxOrderQty() {
        return maxOrderQty;
    }

    public void setMaxOrderQty(BigDecimal maxOrderQty) {
        this.maxOrderQty = maxOrderQty;
    }

    public BigDecimal getMultOrderQty() {
        return multOrderQty;
    }

    public void setMultOrderQty(BigDecimal multOrderQty) {
        this.multOrderQty = multOrderQty;
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
}
