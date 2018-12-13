/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.inventory.warehousestockenquiry;

import emc.datatypes.EMCDataType;
import emc.datatypes.inventory.dimensions.foreignkeys.Dimension1FKNotMandatory;
import emc.datatypes.inventory.dimensions.foreignkeys.Dimension2FKNotMandatory;
import emc.datatypes.inventory.dimensions.foreignkeys.Dimension3FKNotMandatory;
import emc.datatypes.inventory.itemmaster.Classification1;
import emc.datatypes.inventory.itemmaster.Classification5;
import emc.datatypes.inventory.itemreference.ItemReferenceIdNotMandatory;
import emc.datatypes.inventory.location.foreignkeys.LocationFKNotManditory;
import emc.datatypes.inventory.productiongroup.foreignkeys.ProductGroupIdFKNM;
import emc.datatypes.inventory.warehousestockenquiry.AvailableQuantity;
import emc.datatypes.inventory.warehousestockenquiry.Bin1Quantity;
import emc.datatypes.inventory.warehousestockenquiry.Bin2Quantity;
import emc.datatypes.inventory.warehousestockenquiry.Bin3Quantity;
import emc.datatypes.inventory.warehousestockenquiry.Bin4Quantity;
import emc.datatypes.inventory.warehousestockenquiry.Bin5Quantity;
import emc.datatypes.inventory.warehousestockenquiry.ReservedQuantity;
import emc.datatypes.inventory.warehousestockenquiry.TotalQuantity;
import emc.datatypes.systemwide.Description;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import java.math.BigDecimal;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "InventoryWarehouseStockEnquiry")
public class InventoryWarehouseStockEnquiry extends EMCEntityClass {

    private String recordOwner;
    private String itemId;
    private String itemReference;
    private String itemDescription;
    private String productGroup;
    private String planningGroup;
    private String classification1;
    private String classification5;
    private String dimension1;
    private String dimension3;
    private String dimension2;
    private int dimension2SortCode;
    private String batch;
    private String serial;
    private String location;
    private BigDecimal availableQuantity = BigDecimal.ZERO;
    private BigDecimal reservedQuantity = BigDecimal.ZERO;
    private BigDecimal totalQuantity = BigDecimal.ZERO;
    private BigDecimal bin1Quantity = BigDecimal.ZERO;
    private BigDecimal bin2Quantity = BigDecimal.ZERO;
    private BigDecimal bin3Quantity = BigDecimal.ZERO;
    private BigDecimal bin4Quantity = BigDecimal.ZERO;
    private BigDecimal bin5Quantity = BigDecimal.ZERO;

    public InventoryWarehouseStockEnquiry() {
    }

    public InventoryWarehouseStockEnquiry(String recordOwner, String itemId, String itemReference, String itemDescription, String productGroup,
            String planningGroup, String classification1, String classification5, String dimension1,
            String dimension2, int dimension2SortCode, String dimension3, String batch, String serial, String location, BigDecimal reservedQuantity,
            BigDecimal bin1Quantity, BigDecimal bin2Quantity, BigDecimal bin3Quantity, BigDecimal bin4Quantity,
            BigDecimal bin5Quantity, BigDecimal totalQuantity, BigDecimal availableQuantity) {
        this.recordOwner = recordOwner;
        this.itemId = itemId;
        this.itemReference = itemReference;
        this.itemDescription = itemDescription;
        this.productGroup = productGroup;
        this.planningGroup = planningGroup;
        this.classification1 = classification1;
        this.classification5 = classification5;
        this.dimension1 = dimension1;
        this.dimension2 = dimension2;
        this.dimension2SortCode = dimension2SortCode;
        this.dimension3 = dimension3;
        this.batch = batch;
        this.serial = serial;
        this.location = location;
        this.reservedQuantity = reservedQuantity;
        this.bin1Quantity = bin1Quantity;
        this.bin2Quantity = bin2Quantity;
        this.bin3Quantity = bin3Quantity;
        this.bin4Quantity = bin4Quantity;
        this.bin5Quantity = bin5Quantity;
        this.totalQuantity = totalQuantity;
        this.availableQuantity = availableQuantity;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public BigDecimal getBin1Quantity() {
        return bin1Quantity;
    }

    public void setBin1Quantity(BigDecimal bin1Quantity) {
        this.bin1Quantity = bin1Quantity;
    }

    public BigDecimal getBin2Quantity() {
        return bin2Quantity;
    }

    public void setBin2Quantity(BigDecimal bin2Quantity) {
        this.bin2Quantity = bin2Quantity;
    }

    public BigDecimal getBin3Quantity() {
        return bin3Quantity;
    }

    public void setBin3Quantity(BigDecimal bin3Quantity) {
        this.bin3Quantity = bin3Quantity;
    }

    public BigDecimal getBin4Quantity() {
        return bin4Quantity;
    }

    public void setBin4Quantity(BigDecimal bin4Quantity) {
        this.bin4Quantity = bin4Quantity;
    }

    public BigDecimal getBin5Quantity() {
        return bin5Quantity;
    }

    public void setBin5Quantity(BigDecimal bin5Quantity) {
        this.bin5Quantity = bin5Quantity;
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

    public int getDimension2SortCode() {
        return dimension2SortCode;
    }

    public void setDimension2SortCode(int dimension2SortCode) {
        this.dimension2SortCode = dimension2SortCode;
    }

    public String getDimension3() {
        return dimension3;
    }

    public void setDimension3(String dimension3) {
        this.dimension3 = dimension3;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemReference() {
        return itemReference;
    }

    public void setItemReference(String itemReference) {
        this.itemReference = itemReference;
    }

    public String getRecordOwner() {
        return recordOwner;
    }

    public void setRecordOwner(String recordOwner) {
        this.recordOwner = recordOwner;
    }

    public BigDecimal getReservedQuantity() {
        return reservedQuantity;
    }

    public void setReservedQuantity(BigDecimal reservedQuantity) {
        this.reservedQuantity = reservedQuantity;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public BigDecimal getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(BigDecimal totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public BigDecimal getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(BigDecimal availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public String getClassification1() {
        return classification1;
    }

    public void setClassification1(String classification1) {
        this.classification1 = classification1;
    }

    public String getClassification5() {
        return classification5;
    }

    public void setClassification5(String classification5) {
        this.classification5 = classification5;
    }

    public String getPlanningGroup() {
        return planningGroup;
    }

    public void setPlanningGroup(String planningGroup) {
        this.planningGroup = planningGroup;
    }

    public String getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(String productGroup) {
        this.productGroup = productGroup;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public EMCQuery getQuery() {
        EMCQuery query = super.getQuery();
        query.addAnd("recordOwner", null);
        query.addGroupBy("recordOwner");
        query.addOrderBy("recordOwner");
        return query;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("itemReference", new ItemReferenceIdNotMandatory());
        toBuild.put("itemDescription", new Description());
        toBuild.put("dimension1", new Dimension1FKNotMandatory());
        toBuild.put("dimension2", new Dimension2FKNotMandatory());
        toBuild.put("dimension3", new Dimension3FKNotMandatory());
        toBuild.put("location", new LocationFKNotManditory());
        toBuild.put("availableQuantity", new AvailableQuantity());
        toBuild.put("reservedQuantity", new ReservedQuantity());
        toBuild.put("totalQuantity", new TotalQuantity());
        toBuild.put("bin1Quantity", new Bin1Quantity());
        toBuild.put("bin2Quantity", new Bin2Quantity());
        toBuild.put("bin3Quantity", new Bin3Quantity());
        toBuild.put("bin4Quantity", new Bin4Quantity());
        toBuild.put("bin5Quantity", new Bin5Quantity());
        toBuild.put("productGroup", new ProductGroupIdFKNM());
        toBuild.put("classification1", new Classification1());
        toBuild.put("classification5", new Classification5());
        return toBuild;
    }
}
