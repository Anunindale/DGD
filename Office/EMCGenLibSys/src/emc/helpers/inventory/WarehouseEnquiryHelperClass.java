/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.helpers.inventory;

/**
 *
 * @author wikus
 */
public class WarehouseEnquiryHelperClass {

    private String recordOwner;
    private String warehouse;
    private String location;
    private String item;
    private String productGroup;
    private String planningGroup;
    private String classification1;
    private String classification5;
    private String brandGroup;
    private String dimension1;
    private String dimension3;
    private boolean totalQuantity;
    private boolean crates;

    public String getBrandGroup() {
        return brandGroup;
    }

    public void setBrandGroup(String brandGroup) {
        this.brandGroup = brandGroup;
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

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
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

    public String getRecordOwner() {
        return recordOwner;
    }

    public void setRecordOwner(String recordOwner) {
        this.recordOwner = recordOwner;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public boolean isTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(boolean totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isCrates() {
        return crates;
    }

    public void setCrates(boolean crates) {
        this.crates = crates;
    }
}
