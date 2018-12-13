package emc.entity.pop.pricearrangements;

import emc.datatypes.EMCDataType;
import emc.datatypes.pop.pricearrangements.Dimension1;
import emc.datatypes.pop.pricearrangements.Dimension1SortCode;
import emc.datatypes.pop.pricearrangements.Dimension2;
import emc.datatypes.pop.pricearrangements.Dimension2SortCode;
import emc.datatypes.pop.pricearrangements.Dimension3;
import emc.datatypes.pop.pricearrangements.Dimension3SortCode;
import emc.datatypes.pop.pricearrangements.FromDate;
import emc.datatypes.pop.pricearrangements.ItemId;
import emc.datatypes.pop.pricearrangements.Price;
import emc.datatypes.pop.pricearrangements.PriceGroup;
import emc.datatypes.pop.pricearrangements.Quantity;
import emc.datatypes.pop.pricearrangements.SupplierId;
import emc.datatypes.pop.pricearrangements.SupplierType;
import emc.datatypes.pop.pricearrangements.ToDate;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.pop.pricearrangement.PricingSupplierType;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import emc.functions.Functions;
import emc.inventory.ItemDimensionInterface;
import java.lang.Override;
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
 * @author riaan
 */
@Entity
@Table(name = "POPPriceArrangements", uniqueConstraints = {@UniqueConstraint(columnNames = {"companyId", "supplierType", "priceGroup", "supplierId", "itemId", "dimension1", "dimension2", "dimension3", "fromDate", "quantity"})})
public class POPPriceArrangements extends EMCEntityClass implements ItemDimensionInterface {

    //Column lengths are restricted to allow unique constraint to be created.
    @Column(length = 20)
    private String supplierType = PricingSupplierType.SUPPLIER.toString();
    @Column(length = 20)
    private String priceGroup;
    @Column(length = 20)
    private String supplierId;
    @Column(length = 20)
    private String itemId;
    @Column(length = 20)
    private String dimension1;
    private int dimension1SortCode;
    @Column(length = 20)
    private String dimension2;
    private int dimension2SortCode;
    @Column(length = 20)
    private String dimension3;
    private int dimension3SortCode;
    @Temporal(TemporalType.DATE)
    private Date fromDate;
    @Temporal(TemporalType.DATE)
    private Date toDate;
    private BigDecimal quantity = BigDecimal.ZERO;
    private BigDecimal price = BigDecimal.ZERO;

    /** Creates a new instance of POPPriceArrangements. */
    public POPPriceArrangements() {
    }

    @Override
    public HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("supplierType", new SupplierType());
        toBuild.put("priceGroup", new PriceGroup());
        toBuild.put("supplierId", new SupplierId());
        toBuild.put("itemId", new ItemId());
        toBuild.put("dimension1", new Dimension1());
        toBuild.put("dimension1SortCode", new Dimension1SortCode());
        toBuild.put("dimension2", new Dimension2());
        toBuild.put("dimension2SortCode", new Dimension2SortCode());
        toBuild.put("dimension3", new Dimension3());
        toBuild.put("dimension3SortCode", new Dimension3SortCode());
        toBuild.put("fromDate", new FromDate());
        toBuild.put("toDate", new ToDate());
        toBuild.put("quantity", new Quantity());
        toBuild.put("price", new Price());
        return toBuild;
    }

    @Override
    public EMCQuery buildQuery() {
        EMCQuery query = super.buildQuery();
        query.addAnd("toDate", Functions.nowDate(), EMCQueryConditions.GREATER_THAN_EQ);
        query.addOrderBy("supplierType");
        query.addOrderBy("supplierId");
        query.addOrderBy("priceGroup");
        query.addOrderBy("itemId");
        query.addOrderBy("dimension1SortCode");
        query.addOrderBy("dimension3SortCode");
        query.addOrderBy("dimension2SortCode");
        query.addOrderBy("fromDate");
        query.addOrderBy("toDate");
        query.addOrderBy("quantity");
        return query;
    }

    public String getSupplierType() {
        return this.supplierType;
    }

    public void setSupplierType(String supplierType) {
        this.supplierType = supplierType;
    }

    public String getPriceGroup() {
        return this.priceGroup;
    }

    public void setPriceGroup(String priceGroup) {
        this.priceGroup = priceGroup;
    }

    public String getSupplierId() {
        return this.supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getItemId() {
        return this.itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getDimension1() {
        return this.dimension1;
    }

    public void setDimension1(String dimension1) {
        this.dimension1 = dimension1;
    }

    public int getDimension1SortCode() {
        return this.dimension1SortCode;
    }

    public void setDimension1SortCode(int dimension1SortCode) {
        this.dimension1SortCode = dimension1SortCode;
    }

    public String getDimension2() {
        return this.dimension2;
    }

    public void setDimension2(String dimension2) {
        this.dimension2 = dimension2;
    }

    public int getDimension2SortCode() {
        return this.dimension2SortCode;
    }

    public void setDimension2SortCode(int dimension2SortCode) {
        this.dimension2SortCode = dimension2SortCode;
    }

    public String getDimension3() {
        return this.dimension3;
    }

    public void setDimension3(String dimension3) {
        this.dimension3 = dimension3;
    }

    public int getDimension3SortCode() {
        return this.dimension3SortCode;
    }

    public void setDimension3SortCode(int dimension3SortCode) {
        this.dimension3SortCode = dimension3SortCode;
    }

    public Date getFromDate() {
        return this.fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return this.toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public BigDecimal getQuantity() {
        return this.quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}