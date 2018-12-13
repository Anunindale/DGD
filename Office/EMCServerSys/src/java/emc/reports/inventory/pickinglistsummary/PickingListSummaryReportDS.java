/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.inventory.pickinglistsummary;

/**
 *
 * @author wikus
 */
public class PickingListSummaryReportDS {

    private String awoId;
    private String pickListId;
    private String itemId;
    private String itemDesc;
    private String dimension1;
    private String dimension2;
    private String dimension3;
    private String batch;
    private String serial;
    private double requiredQty;
    private double issuedQty;
    private double isseuedCost;
    private boolean issued;

    public String getAwoId() {
        return awoId;
    }

    public void setAwoId(String awoId) {
        this.awoId = awoId;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
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

    public boolean isIssued() {
        return issued;
    }

    public void setIssued(boolean issued) {
        this.issued = issued;
    }

    public double getIssuedQty() {
        return issuedQty;
    }

    public void setIssuedQty(double issuedQty) {
        this.issuedQty = issuedQty;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getPickListId() {
        return pickListId;
    }

    public void setPickListId(String pickListId) {
        this.pickListId = pickListId;
    }

    public double getRequiredQty() {
        return requiredQty;
    }

    public void setRequiredQty(double requiredQty) {
        this.requiredQty = requiredQty;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public double getIsseuedCost() {
        return isseuedCost;
    }

    public void setIsseuedCost(double isseuedCost) {
        this.isseuedCost = isseuedCost;
    }
}
