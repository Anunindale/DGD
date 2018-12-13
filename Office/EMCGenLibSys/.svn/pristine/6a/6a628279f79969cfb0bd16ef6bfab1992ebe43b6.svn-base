/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.inventory.requirementsplanning;

import emc.datatypes.EMCDataType;
import emc.datatypes.inventory.requirementsplanning.DemandId;
import emc.datatypes.inventory.requirementsplanning.DemandType;
import emc.datatypes.inventory.requirementsplanning.Dimension1;
import emc.datatypes.inventory.requirementsplanning.Dimension2;
import emc.datatypes.inventory.requirementsplanning.Dimension3;
import emc.datatypes.inventory.requirementsplanning.ItemDescription;
import emc.datatypes.inventory.requirementsplanning.ItemReference;
import emc.datatypes.inventory.requirementsplanning.SupplyId;
import emc.datatypes.inventory.requirementsplanning.SupplyType;
import emc.inventory.DimensionIDInterfaceOnlyDims;
import emc.inventory.ItemReferenceInterface;
import java.util.HashMap;

/**
 *
 * @author wikus
 */
public class InventoryRequirementsPlanningDS extends InventoryRequirementsPlanning implements ItemReferenceInterface, DimensionIDInterfaceOnlyDims {

    private String itemReference;
    private String itemDescription;
    private String dimension1;
    private String dimension2;
    private String dimension3;
    private String demandId;
    private String demandType;
    private String supplyId;
    private String supplyType;

    public InventoryRequirementsPlanningDS() {
        this.setDataSource(true);
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("itemReference", new ItemReference());
        toBuild.put("itemDescription", new ItemDescription());
        toBuild.put("dimension1", new Dimension1());
        toBuild.put("dimension2", new Dimension2());
        toBuild.put("dimension3", new Dimension3());
        toBuild.put("demandType", new DemandType());
        toBuild.put("supplyType", new SupplyType());
        toBuild.put("demandId", new DemandId());
        toBuild.put("supplyId", new SupplyId());

        return toBuild;
    }

    public String getDemandId() {
        return demandId;
    }

    public void setDemandId(String demandId) {
        this.demandId = demandId;
    }

    public String getDemandType() {
        return demandType;
    }

    public void setDemandType(String demandType) {
        this.demandType = demandType;
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

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemReference() {
        return itemReference;
    }

    public void setItemReference(String itemReference) {
        this.itemReference = itemReference;
    }

    public String getSupplyId() {
        return supplyId;
    }

    public void setSupplyId(String supplyId) {
        this.supplyId = supplyId;
    }

    public String getSupplyType() {
        return supplyType;
    }

    public void setSupplyType(String supplyType) {
        this.supplyType = supplyType;
    }

    public void setDimRecordID(long recordID) {
        setDimensionId(recordID);
    }

    public long getDimRecordID() {
        return getDimensionId();
    }
}
