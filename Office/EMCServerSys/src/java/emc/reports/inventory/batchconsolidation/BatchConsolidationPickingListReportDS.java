/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.inventory.batchconsolidation;

import java.math.BigDecimal;

/**
 *
 * @author wikus
 */
public class BatchConsolidationPickingListReportDS {

    private String consolidationNumber;
    private String pickingDate;
    private String itemReference;
    private String itemDesc;
    private String dimension1;
    private String dimension3;
    private String colourDesc;
    private String dimension2;
    private String warehouse;
    private String locationId;
    private String batch;
    private String toBatch;
    private BigDecimal qtyToPick = BigDecimal.ZERO;
    private String UoM;

    public BatchConsolidationPickingListReportDS() {
    }

    public String getConsolidationNumber() {
        return consolidationNumber;
    }

    public void setConsolidationNumber(String consolidationNumber) {
        this.consolidationNumber = consolidationNumber;
    }

    public String getPickingDate() {
        return pickingDate;
    }

    public void setPickingDate(String pickingDate) {
        this.pickingDate = pickingDate;
    }

    public String getItemReference() {
        return itemReference;
    }

    public void setItemReference(String itemReference) {
        this.itemReference = itemReference;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public String getDimension1() {
        return dimension1;
    }

    public void setDimension1(String dimension1) {
        this.dimension1 = dimension1;
    }

    public String getDimension3() {
        return dimension3;
    }

    public void setDimension3(String dimension3) {
        this.dimension3 = dimension3;
    }

    public String getColourDesc() {
        return colourDesc;
    }

    public void setColourDesc(String colourDesc) {
        this.colourDesc = colourDesc;
    }

    public String getDimension2() {
        return dimension2;
    }

    public void setDimension2(String dimension2) {
        this.dimension2 = dimension2;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getToBatch() {
        return toBatch;
    }

    public void setToBatch(String toBatch) {
        this.toBatch = toBatch;
    }

    public BigDecimal getQtyToPick() {
        return qtyToPick;
    }

    public void setQtyToPick(BigDecimal qtyToPick) {
        this.qtyToPick = qtyToPick;
    }

    public String getUoM() {
        return UoM;
    }

    public void setUoM(String UoM) {
        this.UoM = UoM;
    }
}
