/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.debtors.superclasses;

import emc.datatypes.EMCDataType;
import emc.datatypes.base.unitsofmeasure.foreignkeys.UnitOfMeasureFK;
import emc.datatypes.debtors.customerinvoice.Batch;
import emc.datatypes.debtors.customerinvoice.CostAdjustment;
import emc.datatypes.debtors.customerinvoice.CostAtAverage;
import emc.datatypes.debtors.customerinvoice.Dimension1;
import emc.datatypes.debtors.customerinvoice.Dimension2;
import emc.datatypes.debtors.customerinvoice.Dimension3;
import emc.datatypes.debtors.customerinvoice.DiscountPercentage;
import emc.datatypes.debtors.customerinvoice.InventTransId;
import emc.datatypes.debtors.customerinvoice.Location;
import emc.datatypes.debtors.customerinvoice.Pallet;
import emc.datatypes.debtors.customerinvoice.Quantity;
import emc.datatypes.debtors.customerinvoice.Serial;
import emc.datatypes.debtors.customerinvoice.StdUnitPrice;
import emc.datatypes.debtors.customerinvoice.TotalCost;
import emc.datatypes.debtors.customerinvoice.UnitPrice;
import emc.datatypes.debtors.customerinvoice.VATAmount;
import emc.datatypes.debtors.customerinvoice.Warehouse;
import emc.datatypes.debtors.journals.LineNo;
import emc.datatypes.gl.vatcodes.foreignkeys.VATCodeFK;
import emc.datatypes.inventory.itemmaster.foreignkeys.ItemIdFKDeleteRestrict;
import emc.framework.EMCEntityClass;
import emc.helpers.debtors.DebtorsCreditHeldLinesIF;
import emc.inventory.ItemDimensionInterface;
import emc.inventory.ItemUOMInterface;
import emc.inventory.ItemUnitPriceInterface;
import emc.inventory.ItemWarehouseInterface;
import java.math.BigDecimal;
import java.util.HashMap;
import javax.persistence.Entity;

/**
 * @description : This is a superclass for Invoice and Credit Note lines in the Debtors Module.
 *
 * @date        : 14 Jul 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
@Entity
public class DebtorsInvoiceLinesSuper extends EMCEntityClass implements ItemDimensionInterface, ItemWarehouseInterface, ItemUOMInterface, ItemUnitPriceInterface, DebtorsCreditHeldLinesIF {

    //Link to master
    private String invCNNumber;
    private double lineNo;
    private String itemId;
    private String dimension1;
    private String dimension2;
    private String dimension3;
    private String warehouse;
    private String batch;
    private String serial;
    private String location;
    private String pallet;
    private String uom;
    private BigDecimal quantity = new BigDecimal(0);
    private BigDecimal unitPrice = new BigDecimal(0);
    private BigDecimal discountPercentage = new BigDecimal(0);
    private BigDecimal discountAmount = new BigDecimal(0);
    private String vatCode;
    private BigDecimal vatAmount = new BigDecimal(0);
    //Credit Checking is dependant on this field excluding VAT.  Consult with team before changing!
    private BigDecimal totalCost = new BigDecimal(0);
    private BigDecimal stdUnitPrice = new BigDecimal(0);
    private BigDecimal costAtAverage = new BigDecimal(0);
    private BigDecimal costAdjustment = new BigDecimal(0);
    private String inventTransId;

    //This will only be applicable to stock Invoices.  In other scenarios,
    //returning will happen against Sales
    private BigDecimal quantityReturned = BigDecimal.ZERO;

    //Credit held fields.
    private boolean creditHeld;
    private String creditHeldReason;
    private String creditHeldStatus;

    //Credit Note fields
    private BigDecimal maxQuantity;

    /** Creates a new instance of DebtorsInvoiceLinesSuper */
    public DebtorsInvoiceLinesSuper() {

    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("lineNo", new LineNo());

        toBuild.put("itemId", new ItemIdFKDeleteRestrict());
        toBuild.put("dimension1", new Dimension1());
        toBuild.put("dimension2", new Dimension2());
        toBuild.put("dimension3", new Dimension3());
        toBuild.put("batch", new Batch());
        toBuild.put("serial", new Serial());
        toBuild.put("warehouse", new Warehouse());
        toBuild.put("location", new Location());
        toBuild.put("pallet", new Pallet());

        toBuild.put("uom", new UnitOfMeasureFK());
        toBuild.put("quantity", new Quantity());
        toBuild.put("unitPrice", new UnitPrice());
        toBuild.put("discountPercentage", new DiscountPercentage());

        toBuild.put("vatCode", new VATCodeFK());
        toBuild.put("vatAmount", new VATAmount());
        toBuild.put("stdUnitPrice", new StdUnitPrice());
        toBuild.put("costAtAverage", new CostAtAverage());
        toBuild.put("costAdjustment", new CostAdjustment());
        toBuild.put("inventTransId", new InventTransId());
        toBuild.put("totalCost", new TotalCost());

        return toBuild;
    }

    public double getLineNo() {
        return lineNo;
    }

    public void setLineNo(double lineNo) {
        this.lineNo = lineNo;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
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

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPallet() {
        return pallet;
    }

    public void setPallet(String pallet) {
        this.pallet = pallet;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(BigDecimal discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public String getVatCode() {
        return vatCode;
    }

    public void setVatCode(String vatCode) {
        this.vatCode = vatCode;
    }

    public BigDecimal getVatAmount() {
        return vatAmount;
    }

    public void setVatAmount(BigDecimal vatAmount) {
        this.vatAmount = vatAmount;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public BigDecimal getStdUnitPrice() {
        return stdUnitPrice;
    }

    public void setStdUnitPrice(BigDecimal stdUnitPrice) {
        this.stdUnitPrice = stdUnitPrice;
    }

    public BigDecimal getCostAtAverage() {
        return costAtAverage;
    }

    public void setCostAtAverage(BigDecimal costAtAverage) {
        this.costAtAverage = costAtAverage;
    }

    public BigDecimal getCostAdjustment() {
        return costAdjustment;
    }

    public void setCostAdjustment(BigDecimal costAdjustment) {
        this.costAdjustment = costAdjustment;
    }

    public String getInventTransId() {
        return inventTransId;
    }

    public void setInventTransId(String inventTransId) {
        this.inventTransId = inventTransId;
    }

    public String getInvCNNumber() {
        return invCNNumber;
    }

    public void setInvCNNumber(String invCNNumber) {
        this.invCNNumber = invCNNumber;
    }

    public BigDecimal getQuantityReturned() {
        return quantityReturned;
    }

    public void setQuantityReturned(BigDecimal quantityReturned) {
        this.quantityReturned = quantityReturned;
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

    public BigDecimal getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(BigDecimal maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }
}
