/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.inventory.dimensions;

import emc.datatypes.inventory.itemdimensiongroups.DimGroupDim1;
import emc.datatypes.inventory.itemdimensiongroups.DimGroupDim2;
import emc.datatypes.inventory.itemdimensiongroups.DimGroupDim3;
import emc.datatypes.inventory.itemdimensiongroups.ItemDimensionGroupId;
import emc.datatypes.systemwide.Description;
import emc.framework.EMCEntityClass;
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
@Table(name = "InventoryItemDimensionGroup", uniqueConstraints = {@UniqueConstraint(columnNames = {"itemDimensionGroupId", "companyId"})})
public class InventoryItemDimensionGroup extends EMCEntityClass {

    private String itemDimensionGroupId;
    private String description;
    private boolean dim1Active;
    private boolean dim2Active;
    private boolean dim3Active;
    private boolean warehouseActive;
    private boolean batchNumberActive;
    private boolean locationActive;
    private boolean palletIdActive;
    private boolean serialNumberActive;
    //private Boolean dimensionCost;

    /** Creates a new instance of InventoryItemDimensionGroup */
    public InventoryItemDimensionGroup() {

    }

    public String getItemDimensionGroupId() {
        return itemDimensionGroupId;
    }

    public void setItemDimensionGroupId(String itemDimensionGroupId) {
        this.itemDimensionGroupId = itemDimensionGroupId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getDim1Active() {
        return dim1Active;
    }

    public void setDim1Active(boolean dim1Active) {
        this.dim1Active = dim1Active;
    }

    public boolean getDim2Active() {
        return dim2Active;
    }

    public void setDim2Active(boolean dim2Active) {
        this.dim2Active = dim2Active;
    }

    public boolean getWarehouseActive() {
        return warehouseActive;
    }

    public void setWarehouseActive(boolean warehouseActive) {
        this.warehouseActive = warehouseActive;
    }

    public boolean getDim3Active() {
        return dim3Active;
    }

    public void setDim3Active(boolean dim3Active) {
        this.dim3Active = dim3Active;
    }

    public boolean getBatchNumberActive() {
        return batchNumberActive;
    }

    public void setBatchNumberActive(boolean batchNumberActive) {
        this.batchNumberActive = batchNumberActive;
    }

    public boolean getPalletIdActive() {
        return palletIdActive;
    }

    public void setPalletIdActive(boolean palletIdActive) {
        this.palletIdActive = palletIdActive;
    }

    public boolean getSerialNumberActive() {
        return serialNumberActive;
    }

    public void setSerialNumberActive(boolean serialNumberActive) {
        this.serialNumberActive = serialNumberActive;
    }

    public boolean getLocationActive() {
        return locationActive;
    }

    public void setLocationActive(boolean locationActive) {
        this.locationActive = locationActive;
    }

    

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();
        toBuild.put("itemDimensionGroupId", new ItemDimensionGroupId());
        toBuild.put("description", new Description());
        toBuild.put("dim1Active", new DimGroupDim1());
        toBuild.put("dim2Active", new DimGroupDim2());
        toBuild.put("dim3Active", new DimGroupDim3());
        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("itemDimensionGroupId");
        toBuild.add("description");
        return toBuild;
    }
}
