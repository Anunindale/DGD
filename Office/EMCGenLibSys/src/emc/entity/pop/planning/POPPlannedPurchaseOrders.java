/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.pop.planning;

import emc.datatypes.EMCDataType;
import emc.datatypes.base.unitsofmeasure.foreignkeys.UnitOfMeasureFK;
import emc.datatypes.gl.vatcodes.foreignkeys.VATCodeFK;
import emc.datatypes.inventory.itemmaster.foreignkeys.ItemIdFK;
import emc.datatypes.pop.planning.Dimension1;
import emc.datatypes.pop.planning.Dimension2;
import emc.datatypes.pop.planning.Dimension3;
import emc.datatypes.pop.planning.IncludeOnRelease;
import emc.datatypes.pop.planning.PlanningOrderId;
import emc.datatypes.pop.planning.QuantityRequired;
import emc.datatypes.pop.planning.ReleaseDate;
import emc.datatypes.pop.planning.RequiredDate;
import emc.datatypes.pop.purchaseorderlines.ItemPrice;
import emc.enums.pop.plannedpurchaseorder.PlannedPOType;
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
 * @author wikus
 */
@Entity
@Table(name = "POPPlannedPurchaseOrders", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"plannedPOId", "companyId"})})
public class POPPlannedPurchaseOrders extends EMCEntityClass {

    private String plannedPOId;
    @Temporal(TemporalType.DATE)
    private Date dateRequired;
    @Temporal(TemporalType.DATE)
    private Date releaseDate;
    private String itemId;
    private String dimension1;
    private String dimension2;
    private String dimension3;
    private String uom;
    private String vatCode;
    private double itemPrice;
    private BigDecimal quantityRequired = BigDecimal.ZERO;
    private BigDecimal quantityToRelease = BigDecimal.ZERO;
    private boolean includeOnRelease;
    private boolean released;
    private String orderType = PlannedPOType.MAN.toString();
    private String warehouse;

    public POPPlannedPurchaseOrders() {
    }

    public Date getDateRequired() {
        return dateRequired;
    }

    public void setDateRequired(Date dateRequired) {
        this.dateRequired = dateRequired;
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

    public boolean isIncludeOnRelease() {
        return includeOnRelease;
    }

    public void setIncludeOnRelease(boolean includeOnRelease) {
        this.includeOnRelease = includeOnRelease;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getPlannedPOId() {
        return plannedPOId;
    }

    public void setPlannedPOId(String plannedPOId) {
        this.plannedPOId = plannedPOId;
    }

    public BigDecimal getQuantityRequired() {
        return quantityRequired;
    }

    public void setQuantityRequired(BigDecimal quantityRequired) {
        this.quantityRequired = quantityRequired;
        this.quantityToRelease = quantityRequired;
    }

    public BigDecimal getQuantityToRelease() {
        return quantityToRelease;
    }

    public void setQuantityToRelease(BigDecimal quantityToRelease) {
        this.quantityToRelease = quantityToRelease;
    }

    public boolean isReleased() {
        return released;
    }

    public void setReleased(boolean released) {
        this.released = released;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getVatCode() {
        return vatCode;
    }

    public void setVatCode(String vatCode) {
        this.vatCode = vatCode;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("plannedPOId", new PlanningOrderId());
        toBuild.put("dateRequired", new RequiredDate());
        toBuild.put("releaseDate", new ReleaseDate());
        toBuild.put("itemId", new ItemIdFK());
        toBuild.put("dimension1", new Dimension1());
        toBuild.put("dimension2", new Dimension2());
        toBuild.put("dimension3", new Dimension3());
        toBuild.put("uom", new UnitOfMeasureFK());
        toBuild.put("vatCode", new VATCodeFK());
        toBuild.put("itemPrice", new ItemPrice());
        toBuild.put("quantityRequired", new QuantityRequired());
        toBuild.put("quantityToRelease", new QuantityRequired());
        toBuild.put("includeOnRelease", new IncludeOnRelease());

        return toBuild;
    }

    @Override
    public EMCQuery buildQuery() {
        EMCQuery query = super.buildQuery();
        query.addAnd("released", false);
        return query;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }
}
