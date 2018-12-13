/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.inventory;

import emc.datatypes.inventory.warehouses.PurchaseOrderReceivedLabelType;
import emc.datatypes.inventory.warehouses.QCAvaliable;
import emc.datatypes.inventory.warehouses.RECAvaliable;
import emc.datatypes.inventory.warehouses.Warehouse;
import emc.datatypes.systemwide.Description;
import emc.datatypes.systemwide.PhysicalAddress1;
import emc.datatypes.systemwide.PhysicalAddress2;
import emc.datatypes.systemwide.PhysicalAddress3;
import emc.datatypes.systemwide.PhysicalAddress4;
import emc.datatypes.systemwide.PhysicalAddress5;
import emc.datatypes.systemwide.PostalCode;
import emc.framework.EMCEntityClass;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author riaan
 */
@Entity
@Table(name = "InventoryWarehouse", uniqueConstraints = {@UniqueConstraint(columnNames = {"warehouseId", "companyId"})})
public class InventoryWarehouse extends EMCEntityClass implements Serializable {

    private String warehouseId;
    private String description;
    private String type;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String addressLine4;
    private String addressLine5;
    private String postalCode;
    private Boolean RECAvailable;
    private Boolean QCAvailable;
    private String printerName;
    private BigDecimal stockTakeValueDiff;
    private BigDecimal stockTakeQuantityDiff;
    private String stockTakePrintType;
    private String purchaseOrderReceivedLabelType;
    private boolean mto;

    /** Creates a new instance of InventoryWarehouse */
    public InventoryWarehouse() {
    }

    public Boolean getQCAvailable() {
        return QCAvailable == null ? false : QCAvailable;
    }

    public void setQCAvailable(Boolean QCAvailable) {
        this.QCAvailable = QCAvailable;
    }

    public Boolean getRECAvailable() {
        return RECAvailable == null ? false : RECAvailable;
    }

    public void setRECAvailable(Boolean RECAvailable) {
        this.RECAvailable = RECAvailable;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine3() {
        return addressLine3;
    }

    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
    }

    public String getAddressLine4() {
        return addressLine4;
    }

    public void setAddressLine4(String addressLine4) {
        this.addressLine4 = addressLine4;
    }

    public String getAddressLine5() {
        return addressLine5;
    }

    public void setAddressLine5(String addressLine5) {
        this.addressLine5 = addressLine5;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public BigDecimal getStockTakeQuantityDiff() {
        return stockTakeQuantityDiff;
    }

    public void setStockTakeQuantityDiff(BigDecimal stockTakeQuantityDiff) {
        this.stockTakeQuantityDiff = stockTakeQuantityDiff;
    }

    public BigDecimal getStockTakeValueDiff() {
        return stockTakeValueDiff;
    }

    public void setStockTakeValueDiff(BigDecimal stockTakeValueDiff) {
        this.stockTakeValueDiff = stockTakeValueDiff;
    }

    /**
     * @return the printerName
     */
    public String getPrinterName() {
        return printerName;
    }

    /**
     * @param printerName the printerName to set
     */
    public void setPrinterName(String printerName) {
        this.printerName = printerName;
    }

    /**
     * @return the purchaseOrderReceivedLabelType
     */
    public String getPurchaseOrderReceivedLabelType() {
        return purchaseOrderReceivedLabelType;
    }

    /**
     * @param purchaseOrderReceivedLabelType the purchaseOrderReceivedLabelType to set
     */
    public void setPurchaseOrderReceivedLabelType(String purchaseOrderReceivedLabelType) {
        this.purchaseOrderReceivedLabelType = purchaseOrderReceivedLabelType;
    }

    public String getStockTakePrintType() {
        return stockTakePrintType;
    }

    public void setStockTakePrintType(String stockTakePrintType) {
        this.stockTakePrintType = stockTakePrintType;
    }

    public boolean isMto() {
        return mto;
    }

    public void setMto(boolean mto) {
        this.mto = mto;
    }

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();
        toBuild.put("warehouseId", new Warehouse());
        toBuild.put("description", new Description());
        toBuild.put("addressLine1", new PhysicalAddress1());
        toBuild.put("addressLine2", new PhysicalAddress2());
        toBuild.put("addressLine3", new PhysicalAddress3());
        toBuild.put("addressLine4", new PhysicalAddress4());
        toBuild.put("addressLine5", new PhysicalAddress5());
        toBuild.put("postalCode", new PostalCode());
        toBuild.put("RECAvailable", new RECAvaliable());
        toBuild.put("QCAvailable", new QCAvaliable());
        toBuild.put("purchaseOrderReceivedLabelType", new PurchaseOrderReceivedLabelType());
        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("warehouseId");
        toBuild.add("description");
        return toBuild;
    }
}
