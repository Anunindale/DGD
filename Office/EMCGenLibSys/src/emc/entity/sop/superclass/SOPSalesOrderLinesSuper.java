package emc.entity.sop.superclass;

import emc.framework.EMCEntityClass;
import emc.helpers.debtors.DebtorsCreditHeldLinesIF;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @Author wikus
 */
@Entity
public class SOPSalesOrderLinesSuper extends EMCEntityClass implements DebtorsCreditHeldLinesIF {

    private String salesOrderNo;
    private double lineNo;
    private String itemId;
    private String dimension1;
    private String dimension2;
    private String dimension3;
    private String warehouse;
    private BigDecimal quantity = BigDecimal.ZERO;
    private BigDecimal postNowQuantity = BigDecimal.ZERO;
    private String uom;
    private BigDecimal originalPrice = BigDecimal.ZERO;
    private BigDecimal price = BigDecimal.ZERO;
    private BigDecimal originalDiscountPerc = BigDecimal.ZERO;
    private BigDecimal discountPerc = BigDecimal.ZERO;
    private String vatCode;
    private BigDecimal vatAmount = BigDecimal.ZERO;
    private BigDecimal stdUnitPrice = BigDecimal.ZERO;
    private BigDecimal costAtAverage = BigDecimal.ZERO;
    private BigDecimal costAdjustment = BigDecimal.ZERO;
    private String inventTransId;
    //Credit Checking is dependant on this field excluding VAT.  Consult with team before changing!
    private BigDecimal lineTotal = BigDecimal.ZERO;
    @Temporal(TemporalType.DATE)
    private Date requiredDate;
    private boolean lineCanceld;
    private boolean priceApprovelRequired;
    private String priceApprovedBy;
    @Temporal(TemporalType.DATE)
    private Date priceApprovedDate;
    private String priceChangeReason;
    private boolean discountApprovelRequired;
    private String discountApprovedBy;
    @Temporal(TemporalType.DATE)
    private Date discountApprovedDate;
    private String discountChangeReason;
    private String externalReference;
    //Credit held fields.
    private boolean creditHeld;
    private String creditHeldReason;
    private String creditHeldStatus;
    //Blanket Orders
    private BigDecimal blanketOrderQtyReleased = BigDecimal.ZERO;
    private long blanketOrderLineRef;
    private BigDecimal distributionOrderQtyReleased = BigDecimal.ZERO;
    private long distributionOrderLineRef;
    //BO released from DM
    private long forecastCombinationRecodId;
    private BigDecimal originalQuantity = BigDecimal.ZERO;
    private String quantityChangeReason;

    public SOPSalesOrderLinesSuper() {
        this.setDataSource(true);
    }

    public BigDecimal getCostAdjustment() {
        return costAdjustment;
    }

    public void setCostAdjustment(BigDecimal costAdjustment) {
        this.costAdjustment = costAdjustment;
    }

    public BigDecimal getCostAtAverage() {
        return costAtAverage;
    }

    public void setCostAtAverage(BigDecimal costAtAverage) {
        this.costAtAverage = costAtAverage;
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

    public BigDecimal getDiscountPerc() {
        return discountPerc;
    }

    public void setDiscountPerc(BigDecimal discountPerc) {
        this.discountPerc = discountPerc;
    }

    public String getInventTransId() {
        return inventTransId;
    }

    public void setInventTransId(String inventTransId) {
        this.inventTransId = inventTransId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getSalesOrderNo() {
        return salesOrderNo;
    }

    public void setSalesOrderNo(String salesOrderNo) {
        this.salesOrderNo = salesOrderNo;
    }

    public double getLineNo() {
        return lineNo;
    }

    public void setLineNo(double lineNo) {
        this.lineNo = lineNo;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getStdUnitPrice() {
        return stdUnitPrice;
    }

    public void setStdUnitPrice(BigDecimal stdUnitPrice) {
        this.stdUnitPrice = stdUnitPrice;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public BigDecimal getVatAmount() {
        return vatAmount;
    }

    public void setVatAmount(BigDecimal vatAmount) {
        this.vatAmount = vatAmount;
    }

    public String getVatCode() {
        return vatCode;
    }

    public void setVatCode(String vatCode) {
        this.vatCode = vatCode;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public Date getRequiredDate() {
        return requiredDate;
    }

    public void setRequiredDate(Date requiredDate) {
        this.requiredDate = requiredDate;
    }

    public BigDecimal getLineTotal() {
        return lineTotal;
    }

    public void setLineTotal(BigDecimal lineTotal) {
        this.lineTotal = lineTotal;
    }

    public boolean isLineCanceld() {
        return lineCanceld;
    }

    public void setLineCanceld(boolean lineCanceld) {
        this.lineCanceld = lineCanceld;
    }

    public boolean isPriceApprovelRequired() {
        return priceApprovelRequired;
    }

    public void setPriceApprovelRequired(boolean priceApprovelRequired) {
        this.priceApprovelRequired = priceApprovelRequired;
    }

    public String getPriceChangeReason() {
        return priceChangeReason;
    }

    public void setPriceChangeReason(String priceChangeReason) {
        this.priceChangeReason = priceChangeReason;
    }

    public BigDecimal getPostNowQuantity() {
        return postNowQuantity;
    }

    public void setPostNowQuantity(BigDecimal postNowQuantity) {
        this.postNowQuantity = postNowQuantity;
    }

    public boolean isCreditHeld() {
        return creditHeld;
    }

    public void setCreditHeld(boolean creditHeld) {
        this.creditHeld = creditHeld;
    }

    public String getCreditHeldReason() {
        return creditHeldReason;
    }

    public void setCreditHeldReason(String creditHeldReason) {
        this.creditHeldReason = creditHeldReason;
    }

    public String getCreditHeldStatus() {
        return creditHeldStatus;
    }

    public void setCreditHeldStatus(String creditHeldStatus) {
        this.creditHeldStatus = creditHeldStatus;
    }

    public BigDecimal getBlanketOrderQtyReleased() {
        return blanketOrderQtyReleased;
    }

    public void setBlanketOrderQtyReleased(BigDecimal blanketOrderQtyReleased) {
        this.blanketOrderQtyReleased = blanketOrderQtyReleased;
    }

    public long getBlanketOrderLineRef() {
        return blanketOrderLineRef;
    }

    public void setBlanketOrderLineRef(long blanketOrderLineRef) {
        this.blanketOrderLineRef = blanketOrderLineRef;
    }

    public String getExternalReference() {
        return externalReference;
    }

    public void setExternalReference(String externalReference) {
        this.externalReference = externalReference;
    }

    public String getPriceApprovedBy() {
        return priceApprovedBy;
    }

    public void setPriceApprovedBy(String priceApprovedBy) {
        this.priceApprovedBy = priceApprovedBy;
    }

    public Date getPriceApprovedDate() {
        return priceApprovedDate;
    }

    public void setPriceApprovedDate(Date priceApprovedDate) {
        this.priceApprovedDate = priceApprovedDate;
    }

    public long getForecastCombinationRecodId() {
        return forecastCombinationRecodId;
    }

    public void setForecastCombinationRecodId(long forecastCombinationRecodId) {
        this.forecastCombinationRecodId = forecastCombinationRecodId;
    }

    public BigDecimal getOriginalDiscountPerc() {
        return originalDiscountPerc;
    }

    public void setOriginalDiscountPerc(BigDecimal originalDiscountPerc) {
        this.originalDiscountPerc = originalDiscountPerc;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getDiscountApprovedBy() {
        return discountApprovedBy;
    }

    public void setDiscountApprovedBy(String discountApprovedBy) {
        this.discountApprovedBy = discountApprovedBy;
    }

    public boolean isDiscountApprovelRequired() {
        return discountApprovelRequired;
    }

    public void setDiscountApprovelRequired(boolean discountApprovelRequired) {
        this.discountApprovelRequired = discountApprovelRequired;
    }

    public Date getDiscountApprovedDate() {
        return discountApprovedDate;
    }

    public void setDiscountApprovedDate(Date discountApprovedDate) {
        this.discountApprovedDate = discountApprovedDate;
    }

    public String getDiscountChangeReason() {
        return discountChangeReason;
    }

    public void setDiscountChangeReason(String discountChangeReason) {
        this.discountChangeReason = discountChangeReason;
    }

    public BigDecimal getDistributionOrderQtyReleased() {
        return distributionOrderQtyReleased;
    }

    public void setDistributionOrderQtyReleased(BigDecimal distributionOrderQtyReleased) {
        this.distributionOrderQtyReleased = distributionOrderQtyReleased;
    }

    public long getDistributionOrderLineRef() {
        return distributionOrderLineRef;
    }

    public void setDistributionOrderLineRef(long distributionOrderLineRef) {
        this.distributionOrderLineRef = distributionOrderLineRef;
    }

    @Override
    public List<String> buildFieldListToClearOnCopy() {
        List<String> toBuild = super.buildFieldListToClearOnCopy();
        toBuild.add("priceApprovedDate");
        toBuild.add("priceApprovedBy");
        toBuild.add("priceChangeReason");
        toBuild.add("priceApprovelRequired");
        toBuild.add("price");
        toBuild.add("creditHeldReason");
        toBuild.add("creditHeldStatus");
        toBuild.add("creditHeld");

        return toBuild;
    }

    /**
     * @return the originalQuantity
     */
    public BigDecimal getOriginalQuantity() {
        return originalQuantity;
    }

    /**
     * @param originalQuantity the originalQuantity to set
     */
    public void setOriginalQuantity(BigDecimal originalQuantity) {
        this.originalQuantity = originalQuantity;
    }

    /**
     * @return the quantityChangeReason
     */
    public String getQuantityChangeReason() {
        return quantityChangeReason;
    }

    /**
     * @param quantityChangeReason the quantityChangeReason to set
     */
    public void setQuantityChangeReason(String quantityChangeReason) {
        this.quantityChangeReason = quantityChangeReason;
    }
}
