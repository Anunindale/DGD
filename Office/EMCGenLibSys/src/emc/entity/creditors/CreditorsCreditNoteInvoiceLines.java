/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.creditors;

import emc.datatypes.EMCDataType;
import emc.datatypes.creditors.creditnoteinvoice.Batch;
import emc.datatypes.creditors.creditnoteinvoice.Dimension1;
import emc.datatypes.creditors.creditnoteinvoice.Dimension2;
import emc.datatypes.creditors.creditnoteinvoice.Dimension3;
import emc.datatypes.creditors.creditnoteinvoice.DiscountAmount;
import emc.datatypes.creditors.creditnoteinvoice.DiscountPercentage;
import emc.datatypes.creditors.creditnoteinvoice.InventTransId;
import emc.datatypes.creditors.creditnoteinvoice.ItemId;
import emc.datatypes.creditors.creditnoteinvoice.Location;
import emc.datatypes.creditors.creditnoteinvoice.Pallet;
import emc.datatypes.creditors.creditnoteinvoice.Quantity;
import emc.datatypes.creditors.creditnoteinvoice.Serial;
import emc.datatypes.creditors.creditnoteinvoice.StdUnitPrice;
import emc.datatypes.creditors.creditnoteinvoice.TotalCost;
import emc.datatypes.creditors.creditnoteinvoice.UOM;
import emc.datatypes.creditors.creditnoteinvoice.UnitPrice;
import emc.datatypes.creditors.creditnoteinvoice.VatCode;
import emc.datatypes.creditors.creditnoteinvoice.Warehouse;
import emc.datatypes.creditors.creditnoteinvoice.foreignkeys.CreditNoteInvoiceNumberFK;
import emc.datatypes.creditors.transactions.VatAmount;
import emc.framework.EMCEntityClass;
import java.math.BigDecimal;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "CreditorsCreditNoteInvoiceLines", uniqueConstraints = {@UniqueConstraint(columnNames = {"creditNoteInvoiceNumber", "lineNo", "companyId"})})
public class CreditorsCreditNoteInvoiceLines extends EMCEntityClass {

    private String creditNoteInvoiceNumber;
    private BigDecimal lineNo;
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
    private BigDecimal stdUnitPrice = new BigDecimal(0);
    private BigDecimal discountPercentage = new BigDecimal(0);
    private BigDecimal discountAmount = new BigDecimal(0);
    private String vatCode;
    private BigDecimal vatAmount = new BigDecimal(0);
    private BigDecimal totalCost = new BigDecimal(0);
    private String inventoryTransRef;
    private BigDecimal quantityReturned = new BigDecimal(0);

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getCreditNoteInvoiceNumber() {
        return creditNoteInvoiceNumber;
    }

    public void setCreditNoteInvoiceNumber(String creditNoteInvoiceNumber) {
        this.creditNoteInvoiceNumber = creditNoteInvoiceNumber;
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

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public BigDecimal getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(BigDecimal discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public String getInventoryTransRef() {
        return inventoryTransRef;
    }

    public void setInventoryTransRef(String inventoryTransRef) {
        this.inventoryTransRef = inventoryTransRef;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public BigDecimal getLineNo() {
        return lineNo;
    }

    public void setLineNo(BigDecimal lineNo) {
        this.lineNo = lineNo;
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

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public BigDecimal getStdUnitPrice() {
        return stdUnitPrice;
    }

    public void setStdUnitPrice(BigDecimal stdUnitPrice) {
        this.stdUnitPrice = stdUnitPrice;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
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

    public BigDecimal getQuantityReturned() {
        return quantityReturned;
    }

    public void setQuantityReturned(BigDecimal quantityReturned) {
        this.quantityReturned = quantityReturned;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("creditNoteInvoiceNumber", new CreditNoteInvoiceNumberFK());
        toBuild.put("itemId", new ItemId());
        toBuild.put("dimension1", new Dimension1());
        toBuild.put("dimension2", new Dimension2());
        toBuild.put("dimension3", new Dimension3());
        toBuild.put("warehouse", new Warehouse());
        toBuild.put("location", new Location());
        toBuild.put("batch", new Batch());
        toBuild.put("serial", new Serial());
        toBuild.put("pallet", new Pallet());
        toBuild.put("uom", new UOM());
        toBuild.put("quantity", new Quantity());
        toBuild.put("unitPrice", new UnitPrice());
        toBuild.put("stdUnitPrice", new StdUnitPrice());
        toBuild.put("discountPercentage", new DiscountPercentage());
        toBuild.put("discountAmount", new DiscountAmount());
        toBuild.put("vatCode", new VatCode());
        toBuild.put("vatAmount", new VatAmount());
        toBuild.put("totalCost", new TotalCost());
        toBuild.put("inventoryTransRef", new InventTransId());
        return toBuild;
    }
}
