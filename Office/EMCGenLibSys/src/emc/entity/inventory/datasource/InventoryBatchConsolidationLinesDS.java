/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.inventory.datasource;

import emc.datatypes.EMCDataType;
import emc.entity.inventory.InventoryBatchConsolidationLines;
import java.util.HashMap;

/**
 *
 * @author wikus
 */
public class InventoryBatchConsolidationLinesDS extends InventoryBatchConsolidationLines {

    private String transId;
    private String itemId;
    private String itemReference;
    private String itemDescription;
    private String dimension1;
    private String dimension2;
    private String dimension3;
    private String warehouse;
    private String fromLocation;
    private String toLocation;
    private String serialNo;
    private String fromBatch;
    private String toBatch;

    public InventoryBatchConsolidationLinesDS() {
        this.setDataSource(true);
    }

    public String getItemReference() {
        return itemReference;
    }

    public void setItemReference(String itemReference) {
        this.itemReference = itemReference;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
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

    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public String getFromBatch() {
        return fromBatch;
    }

    public void setFromBatch(String fromBatch) {
        this.fromBatch = fromBatch;
    }

    public String getToBatch() {
        return toBatch;
    }

    public void setToBatch(String toBatch) {
        this.toBatch = toBatch;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("itemReference", new emc.datatypes.inventory.batchconsolidation.ItemReference());
        toBuild.put("itemDescription", new emc.datatypes.inventory.batchconsolidation.ItemDescription());
        toBuild.put("dimension1", new emc.datatypes.inventory.batchconsolidation.Dimension1());
        toBuild.put("dimension2", new emc.datatypes.inventory.batchconsolidation.Dimension2());
        toBuild.put("dimension3", new emc.datatypes.inventory.batchconsolidation.Dimension3());
        toBuild.put("warehouse", new emc.datatypes.inventory.batchconsolidation.Warehouse());
        toBuild.put("fromLocation", new emc.datatypes.inventory.batchconsolidation.FromLocation());
        toBuild.put("toLocation", new emc.datatypes.inventory.batchconsolidation.ToLocation());
        toBuild.put("fromBatch", new emc.datatypes.inventory.batchconsolidation.FromBatch());
        toBuild.put("toBatch", new emc.datatypes.inventory.batchconsolidation.ToBatch());
        return toBuild;
    }
}
